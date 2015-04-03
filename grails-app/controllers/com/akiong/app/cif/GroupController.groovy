
package com.akiong.app.cif

import com.akiong.maintenance.*
import grails.converters.JSON

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import com.akiong.BackOfficeController


class GroupController extends BackOfficeController{

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def springSecurityService
	def grupService
	def corporateUserService
	def taskService
	def corporateRNService
	def virtualAccountService

	private static final Logger logger = LoggerFactory.getLogger(this)

	def index = {
		redirect(action: "list", params: params)
	}

	def list = {
		def results = grupService.list(params)
		[groupInstanceList: results[0], grupId: params.grupId ?: "", name: params.name ?: "", groupInstanceTotal: results[1]]
	}

	def create = {
		def groupInstance = new Grup()
		groupInstance.properties = params

		if (flash.model){
			return [groupInstance: flash.model, error: true, ownernya : flash.model.owner, cifUser: flash.model.leader.cifUser]
		}

		return [groupInstance: groupInstance,cityList:City.findAllByDeleteFlag('N'),provinceList:Province.findAllByDeleteFlag('N')]
	}

	def save = {
		withForm {
			def group = new Grup(params)
			group.createdBy = springSecurityService.principal.username
			def owner = new CifUser(params.owner)
			def cifUserInstance = new CifUser(params)
			boolean error = false
			try {
				grupService.create(group, cifUserInstance, owner)
			}
			catch (grails.validation.ValidationException e) {
				error = true
				logger.error("Missing property or validation fails", e)
			}
			catch(SecurityException e){
				flash.error = e.getMessage()
				error = true
				logger.error(e.getMessage(), e)
			}
			catch (RuntimeException e) {
				logger.error(e.getMessage(), e)
				redirect(controller: "error", action: "serverError")
				return
			}
			if (!error && !group.hasErrors()) {
				flash.message = "${message(code: 'default.created.message', args: [message(code: 'grup.label', default: 'Group'), group.name])}"
				redirect(action: "show", id: group.id)
			}
			else {
				flash.model = group
				def member = new GroupMember()
				member.cifUser = cifUserInstance

				flash.model.leader = member
				flash.model.owner = owner
				redirect(action: "create")
			}
		}.invalidToken {
			redirect(controller: "error", action: "forbidden")
		}
	}

	def show = {
		def group = Grup.findWhere(id: Long.parseLong(params.id), deleteFlag: 'N')
		def cif = corporateUserService.getCurrentCifUser().cif
		if (!group || group.cif?.id != cif.id) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'grup.label', default: 'Group'), params.id])}"
			redirect(action: "list")
		}
		else {
			params.max = Math.min(params.max ? params.int('max') : 10, 100)

			def result = GroupMember.createCriteria().list(params) {
				eq("group", group)
				eq("deleteFlag", "N")
				ne("status", "1")
				order("position", "asc")
				cifUser{
					eq("cif", group.cif)
					order("firstName", "asc")
					order("lastName", "asc")
				}
			}
			[group: group, memberList: result, memberTotal: result.totalCount]
		}
	}

	def getGroupLikeName = {

		def groups = grupService.getGroupLike(params.name)
		response.status = 200

		if (params.callback) {
			render "${params.callback}(${groups as JSON})"
		}
		else {
			render "${groups as JSON}"
		}
	}

	def getActiveGroupLikeName = {

		def groups = grupService.getActiveGroupLike(params.name)
		response.status = 200

		if (params.callback) {
			render "${params.callback}(${groups as JSON})"
		}
		else {
			render "${groups as JSON}"
		}
	}

	def findById = {
		def va = Grup.findWhere(grupId: params.grupId, deleteFlag: "N")
		if (va) {
			if (!params.id) {
				response.status = 405
			}
			else {
				if (params.id.equals(va.id.toString()))
					response.status = 200
				else {
					response.status = 405
				}
			}
		}
		else {
			response.status = 200
		}

		if (params.callback) {
			render "${params.callback}(${params as JSON})"
		}
		else {
			render "${params as JSON}"
		}
	}

	def findByName = {
		def cif = corporateUserService.getCurrentCifUser().cif
		def va = Grup.findWhere(name: params.name, cif: cif, deleteFlag: "N")
		if (va) {
			if (!params.id) {
				response.status = 405
			}
			else {
				if (params.id.equals(va.id.toString()))
					response.status = 200
				else {
					response.status = 405
				}
			}
		}
		else {
			response.status = 200
		}

		if (params.callback) {
			render "${params.callback}(${params as JSON})"
		}
		else {
			render "${params as JSON}"
		}
	}

	def edit = {
		if (flash.model){
			return [groupInstance: flash.model, ownernya: flash.model.owner]
		}
		else{
			def group = Grup.findWhere(id: Long.parseLong(params.id), deleteFlag: 'N')
			def cif = corporateUserService.getCurrentCifUser().cif
			if (!group || group.cif?.id != cif.id) {
				flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'grup.label', default: 'Group'), params.id])}"
				redirect(action: "list")
			}
			else {
				return [groupInstance: group, ownernya: group.owner,cityList:City.findAllByDeleteFlag('N'),provinceList:Province.findAllByDeleteFlag('N')]
			}
		}
	}

	def update = {
		withFormat {
			html {
				withForm {
					def group = Grup.findWhere(id: Long.parseLong(params.id), deleteFlag: 'N')
					def cif = corporateUserService.getCurrentCifUser().cif
					if (group && group.deleteFlag.equals("N") && group.cif.id == cif.id) {
						if (params.version) {
							def version = params.version.toLong()
							if (group.version > version) {

								group.errors.rejectValue("version", "default.optimistic.locking.failure", [
									message(code: 'grup.label', default: 'Group')]
								as Object[], "Another user has updated this Group while you were editing")

								flash.model = group
								redirect(action: "edit", id: params.id)
								return
							}
						}

						if(group.pending != null){
							flash.error = "${message(code: 'cannot.update.message')}"
							redirect(action: "show", id: group.id)
							return
						}

						def oldEmail = group.owner.userDetails.email
						group.updatedBy = springSecurityService.principal.username
						group.properties = params
						boolean error = false
						group.owner.properties = params.owner
						def user = group.owner.userDetails.user
						try {
							group = grupService.update(group, oldEmail)
						}
						catch (grails.validation.ValidationException e) {
							error = true
							logger.error("Missing property or validation fails", e)
						}
						catch(SecurityException e){
							error = true
							flash.error = e.getMessage()
							logger.error(e.getMessage(), e)
						}
						catch (RuntimeException e) {
							logger.error(e.getMessage(), e)
							redirect(controller: "error", action: "serverError")
							return
						}

						if (!error && !group.hasErrors()) {
							flash.message = "${message(code: 'default.updated.message', args: [message(code: 'grup.label', default: 'Group'), group.id])}"
							redirect(action: "show", id: group.id)
						}
						else {
							flash.model = group
							flash.model.owner = group.owner
							flash.model.owner.userDetails = group.owner.userDetails
							flash.model.owner.userDetails.user = user
							redirect(action: "edit", id: params.id)
						}
					}
					else {
						flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'grup.label', default: 'Group'), params.id])}"
						redirect(action: "list")
					}
				}.invalidToken {
					redirect(controller: "error", action: "forbidden")
				}
			}
		}
	}

	def delete = {
		withForm {
			def group = Grup.findWhere(id: Long.parseLong(params.id), deleteFlag: 'N')
			def cif = corporateUserService.getCurrentCifUser().cif
			if (group && group.deleteFlag.equals("N") && group.cif.id == cif.id) {
				if (params.version) {
					def version = params.version.toLong()
					if (group.version > version) {

						group.errors.rejectValue("version", "default.optimistic.locking.failure", [
							message(code: 'grup.label', default: 'Group')]
						as Object[], "Another user has updated this Group while you were editing")

						redirect(action: "show", id: params.id)
						return
					}
				}

				if(group.pending != null){
					flash.error = "${message(code: 'cannot.update.message')}"
					redirect(action: "show", id: group.id)
					return
				}

				try {
					group.updatedBy = springSecurityService.principal.username
					grupService.delete(group)
					flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'grup.label', default: 'Group'), group.toString()])}"
					redirect(action: "list")
				}
				catch (RuntimeException e) {
					logger.error(e.getMessage(), e)
					flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'grup.label', default: 'Group'), group.toString()])}"
					redirect(action: "show", id: params.id)
				}
			}
			else {
				flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'grup.label', default: 'Group'), params.id])}"
				redirect(action: "list")
			}
		}.invalidToken {
			redirect(controller: "error", action: "forbidden")
		}
	}

	def approval = {
		try{
			def cif = corporateUserService.getCurrentCifUser().cif
			def variables = taskService.getVariables(params.id, ["id", "type"])
			def group = Grup.findWhere(id: variables["id"])
			if (!group || group.pending == null || group.cif?.id != cif.id) {
				flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'grup.label', default: 'Group'), params.id])}"
				redirect(controller: "myTask")
			}
			else {
				if(variables["type"].equals("update")){
					def changes =  JSON.parse(group.pending)
					group.properties = changes
					group.discard()
				}
				[group: group, isApproval: "Y", taskId: params.id]
			}
		}
		catch(Exception e){
			logger.error(e.getMessage(), e)
			redirect(controller: "group" , action: "list")
		}
	}

	def approve = {
		withForm {
			try{
				def groupId = corporateRNService.approveGroup(params.taskId, springSecurityService.principal.username)
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
				def groupId = corporateRNService.rejectGroup(params.taskId, springSecurityService.principal.username)
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
	
	def findCityByProvince={
		def province = Province.get(params.province)
		def city = City.findAllByProvince(province)
		if (params.callback) {
			render "${params.callback}(${city as JSON})"
		}
		else {
			render "${city as JSON}"
		}
	}
}
