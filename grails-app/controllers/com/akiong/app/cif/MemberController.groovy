package com.akiong.app.cif


import grails.converters.JSON

import java.text.SimpleDateFormat

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import com.akiong.BackOfficeController

class MemberController extends BackOfficeController{

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def springSecurityService
	def corporateUserService
	def cifService
	def virtualAccountService

	private static final Logger logger = LoggerFactory.getLogger(this)

	def create = {
		def group
		def cif = corporateUserService.getCurrentCifUser().cif
		if(params.id != null){
			group = Grup.findWhere(id: Long.parseLong(params.id), deleteFlag: 'N')
		}
		if (!group|| group.cif.id != cif.id) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'grup.label', default: 'Group'), params.id])}"
			redirect(controller: "group", action: "list")
			return
		}

		if (flash.model){
			return [cifUser: flash.model, group: group]
		}
		else{
			def cifUserInstance = new CifUser()
			return [cifUser: cifUserInstance, group: group]
		}
	}

	def save = {
		withForm {
			def cif = corporateUserService.getCurrentCifUser().cif
			def group = Grup.findWhere(id: Long.parseLong(params.id), deleteFlag: 'N')
			if(!group || group.cif.id != cif.id){
				flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'grup.label', default: 'Group'), params.gid])}"
				redirect(controller: "group", action: "list")
				return
			}

			def cifUserInstance = new CifUser(params)
			cifUserInstance.createdBy = springSecurityService.principal.username
//			cifUserInstance.userLimit = new BigDecimal(params.userLimit)
			cifUserInstance.userLimit = new BigDecimal(0)
			boolean error = false
			def member
			try {
				member = corporateUserService.createMember(group, cifUserInstance)
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
				redirect(action: "create", id: params.id)
			}
		}.invalidToken {
			redirect(controller: "error", action: "forbidden")
		}
	}

	def show = {
		def cif = corporateUserService.getCurrentCifUser().cif
		def cifUserInstance = GroupMember.get(params.id)
		if (!cifUserInstance || cifUserInstance.deleteFlag.equals("Y") || cifUserInstance.cifUser?.cif.id != cif.id) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'group.member.label'), params.id])}"
			redirect(controller: "group", action: "list")
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

	def edit = {
		if (flash.model){
			def cifUserInstance = GroupMember.get(params.id)
			def cuser = cifUserInstance.cifUser
			cuser?.userDetails?.user
			cuser.properties = flash.model
//			cifUserInstance.memberLimit.userLimit = new BigDecimal(flash.model.userLimit)

			cuser.validate()
			cuser.userDetails?.validate()

			cuser.discard()
			cifUserInstance.discard()

			return [member: cifUserInstance, cifUser: cuser, vagroup: flash.model.vagroup]
		}
		else{
			def cifUserInstance = GroupMember.get(params.id)
			def cif = corporateUserService.getCurrentCifUser().cif
			if (!cifUserInstance || cifUserInstance.deleteFlag.equals("Y") || cifUserInstance.cifUser?.cif.id != cif.id) {
				flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'group.member.label', default: 'Group Member'), params.id])}"
				redirect(controller: "group", action: "list")
			}
			else {
				def cifUser = cifUserInstance.cifUser
//				cifUser.userLimit = cifUserInstance.userLimit
				cifUser.position = cifUserInstance.position

				if(cifUser.userDetails?.activationKey != null){
					return [cifUser: cifUser, member: cifUserInstance , email: cifUser.userDetails.newEmail]
				}
				else{
					return [cifUser: cifUser, member: cifUserInstance]
				}
			}
		}
	}

	def update = {
		withForm {
			def cifUserInstance = GroupMember.get(params.id)
			def cif = corporateUserService.getCurrentCifUser().cif
			if (cifUserInstance && cifUserInstance.deleteFlag.equals("N") && cifUserInstance.cifUser?.cif.id == cif.id) {
				if (params.version) {
					def version = params.version.toLong()
					if (cifUserInstance.version > version) {
						flash.message = "${message(code: 'default.optimistic.locking.failure', args: [message(code: 'group.member.label')])}"
						redirect(action: "edit", id: params.id)
						return
					}
				}
				def oldEmail = cifUserInstance.cifUser?.userDetails?.email
				cifUserInstance.cifUser.properties = params
//				cifUserInstance.userLimit = new BigDecimal(params.userLimit)
				cifUserInstance?.updatedBy = springSecurityService.principal.username
				boolean error = false
				try {
					corporateUserService.updateMember(cifUserInstance, oldEmail)
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
					cifUserInstance.cifUser.discard()
					cifUserInstance.discard()
					logger.error(e.getMessage(), e)
					redirect(controller: "error", action: "serverError")
					return
				}
				if (!error) {
					flash.message = "${message(code: 'default.updated.message', args: [message(code: 'group.member.label', default: 'Group Member'), cifUserInstance.cifUser.userDetails.user.username])}"
					redirect(action: "show", id: cifUserInstance.id)
				}
				else {
					flash.model = params
					redirect(action: "edit", id: params.id)
				}
			}
			else {
				flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'group.member.label', default: 'Group Member'), params.id])}"
				redirect(controller: "group", action: "list")
			}
		}.invalidToken {
			redirect(controller: "error", action: "forbidden")
		}
	}

	def delete = {
		withForm {
			def cifUserInstance = GroupMember.get(params.id)
			def cif = corporateUserService.getCurrentCifUser().cif
			if (cifUserInstance && cifUserInstance.cifUser?.cif?.id == cif.id) {
				String username = cifUserInstance.cifUser?.userDetails.user.username
				if (params.version) {
					def version = params.version.toLong()
					if (cifUserInstance.version > version) {
						flash.message = "${message(code: 'default.optimistic.locking.failure', args: [message(code: 'group.member.label')])}"
						redirect(action: "show", id: params.id)
						return
					}
				}

				try {
					cifUserInstance?.updatedBy = springSecurityService.principal.username
					corporateUserService.deleteMember(cifUserInstance)
					flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'group.member.label', default: 'Group Member'), username])}"
					redirect(action: "show", controller: "group", id: cifUserInstance.group?.id)
				}
				catch (RuntimeException e) {
					logger.error(e.getMessage(), e)
					flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'group.member.label', default: 'Group Member'), username])}"
					redirect(action: "show", id: params.id)
				}
			}
			else {
				flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'group.member.label', default: 'Group Member'), params.id])}"
				redirect(controller: "group", action: "list")
			}
		}.invalidToken {
			redirect(controller: "error", action: "forbidden")
		}
	}

	def promote = {
		def member = GroupMember.get(params.id)
		if (member) {
			try {
				corporateUserService.promote(member)
				response.status = 200
			}
			catch (RuntimeException e) {
				logger.error(e.getMessage(), e)
				response.status = 405
			}
		}
		else {
			response.status = 404
		}

		if (params.callback) {
			render "${params.callback}(${params as JSON})"
		}
		else {
			render "${params as JSON}"
		}
	}

	def getMemberLikeName = {

		def members = corporateUserService.getMember2Like(params.name)
		response.status = 200

		if (params.callback) {
			render "${params.callback}(${members as JSON})"
		}
		else {
			render "${members as JSON}"
		}
	}
}
