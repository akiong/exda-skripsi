package com.akiong.app.cif


import grails.converters.JSON

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import com.akiong.BackOfficeController
import com.akiong.helper.Common

class GroupMemberController extends BackOfficeController{

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def springSecurityService
	def corporateUserService
	def cifService
	def taskService
	def corporateRNService
	def virtualAccountService

	private static final Logger logger = LoggerFactory.getLogger(this)

	def index = {
		redirect(action: "list", params: params)
	}

	def list = {
		def results = corporateUserService.listMember(params)
		[memberList: results[0], name: params.name ?: "", phone: params.phone ?: "", memberTotal: results[1], group: results[2]]
	}

	def create = {
		if (flash.model){
			return [cifUser: flash.model]
		}
		else{
			def cifUserInstance = new CifUser()
			return [cifUser: cifUserInstance]
		}
	}

	//	def save = {
	//		withForm {
	//			def cifUserInstance = new CifUser(params)
	//			cifUserInstance.createdBy = springSecurityService.principal.username
	////			cifUserInstance.userLimit = new BigDecimal(params.userLimit)
	//			cifUserInstance.userLimit = new BigDecimal(0)
	//			boolean error = false
	//			def member
	//			try {
	//				member = corporateUserService.createMemberPending(cifUserInstance)
	//			}
	//			catch (grails.validation.ValidationException e) {
	//				error = true
	//				logger.error("Missing property or validation fails", e)
	//			}
	//			catch (SecurityException e) {
	//				error = true
	//				flash.error = e.getMessage()
	//				logger.error("Missing property or validation fails", e)
	//			}
	//			catch (RuntimeException e) {
	//				logger.error(e.getMessage(), e)
	//				redirect(controller: "error", action: "serverError")
	//				return
	//			}
	//			if (!error) {
	//				flash.message = "${message(code: 'create.need.approval.message')}"
	//				redirect(action: "pending", id: member.id)
	//			}
	//			else {
	//				flash.model = cifUserInstance
	//				redirect(action: "create")
	//			}
	//		}.invalidToken {
	//			redirect(controller: "error", action: "forbidden")
	//		}
	//	}

	def save = {
		withForm {
			def cifUserInstance = new CifUser(params)
			cifUserInstance.createdBy = springSecurityService.principal.username
			//			cifUserInstance.userLimit = new BigDecimal(params.userLimit)
			cifUserInstance.userLimit = new BigDecimal(0)
			boolean error = false
			def member
			try {
				member = corporateUserService.createMember2(cifUserInstance)
			}
			catch (grails.validation.ValidationException e) {
				error = true
				logger.error("Missing property or validation fails", e)
			}
			catch (SecurityException e) {
				error = true
				flash.error = e.getMessage()
				logger.error("Missing property or validation fails", e)
			}
			catch (RuntimeException e) {
				logger.error(e.getMessage(), e)
				redirect(controller: "error", action: "serverError")
				return
			}
			if (!error) {
				flash.message = "${message(code: 'default.created.message', args: [message(code: 'group.member.label', default: 'Group Member'), cifUserInstance.userDetails.user.username])}"
				redirect(action: "show", id: member.id)
			}
			else {
				flash.model = cifUserInstance
				redirect(action: "create")
			}
		}.invalidToken {
			redirect(controller: "error", action: "forbidden")
		}
	}

	def pending = {
		def curmember = corporateUserService.getCurrentMemberUser()
		def cifUserInstance = GroupMember.get(params.id)
		if (!cifUserInstance || cifUserInstance.status.equals(Common.STATUS_ACTIVE) || cifUserInstance.group?.id != curmember.group?.id) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'group.member.label'), params.id])}"
			redirect(action: "list")
		}
		else {
			if(cifUserInstance.status.equals(Common.PENDING_UPDATE)){
				def changes =  JSON.parse(cifUserInstance.pending)
				cifUserInstance.properties = changes
				cifUserInstance.discard()
			}

			render(view: "approval", model: [cifUserInstance: cifUserInstance, fromPending: true])
		}
	}

	def show = {
		def curmember = corporateUserService.getCurrentMemberUser()
		def cifUserInstance = GroupMember.get(params.id)
		if (!cifUserInstance || cifUserInstance.deleteFlag.equals("Y") || cifUserInstance.group?.id != curmember.group?.id) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'group.member.label'), params.id])}"
			redirect(action: "list")
		}
		else {
			if(cifUserInstance.cifUser?.userDetails?.activationKey != null){
				return [cifUserInstance: cifUserInstance, email: cifUserInstance.cifUser?.userDetails.newEmail]
			}
			else{
				[cifUserInstance: cifUserInstance]
			}
		}
	}

	def approval = {
		try{
			def cif = corporateUserService.getCurrentCifUser().cif
			def variables = taskService.getVariables(params.id, ["id", "type", "groupId"])
			def groupMember = GroupMember.findWhere(id: variables["id"])
			if (!groupMember || groupMember.status.equals(Common.STATUS_ACTIVE) || groupMember.cifUser.cif?.id != cif.id) {
				flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'group.member.label'), params.id])}"
				redirect(controller: "myTask")
			}
			else {
				if(variables["type"].equals("update")){
					def changes =  JSON.parse(groupMember.pending)
					groupMember.properties = changes
					groupMember.discard()
				}
				render(view: "approval", model: [cifUserInstance: groupMember, isApproval: "Y", taskId: params.id, groupId: variables["groupId"] ])
			}
		}
		catch(Exception e){
			logger.error(e.getMessage(), e)
			redirect(controller: "group" , action: "list")
		}
	}

	def approve = {
		withForm {
			String[] idnya = new String[1]
			try{
				def groupId = corporateRNService.approveMember(params.taskId, springSecurityService.principal.username, params.userLimit)
				flash.message = "${message(code: 'approve.success.label')}"
				redirect(controller: "myTask")
			}
			catch (SecurityException e) {
				flash.error = e.getMessage()
				logger.error(e.getMessage(), e)
				redirect(action: "approval", id: params.taskId)
				return
			}
			catch (RuntimeException e) {
				logger.error(e.getMessage(), e)
				redirect(controller: "error", action: "serverError")
				return
			}
		}.invalidToken {
			redirect(controller: "error", action: "forbidden")
		}
	}

	def reject = {
		withForm {
			try{
				def groupId = corporateRNService.rejectMember(params.taskId, springSecurityService.principal.username)
				flash.message = "${message(code: 'reject.success.label')}"
				redirect(controller: "myTask")
			}
			catch (SecurityException e) {
				flash.error = e.getMessage()
				logger.error(e.getMessage(), e)
				redirect(action: "approval", id: params.taskId)
				return
			}
			catch (RuntimeException e) {
				logger.error(e.getMessage(), e)
				redirect(controller: "error", action: "serverError")
				return
			}
		}.invalidToken {
			redirect(controller: "error", action: "forbidden")
		}
	}

	def getMemberLikeName = {

		def members = corporateUserService.getMemberLike(params.name)
		response.status = 200

		if (params.callback) {
			render "${params.callback}(${members as JSON})"
		}
		else {
			render "${members as JSON}"
		}
	}

	def getActiveMemberLikeName = {

		def members = corporateUserService.getActiveMemberLike(params.name)
		response.status = 200

		if (params.callback) {
			render "${params.callback}(${members as JSON})"
		}
		else {
			render "${members as JSON}"
		}
	}
}
