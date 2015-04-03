package com.akiong.security

import grails.converters.JSON
import grails.converters.XML
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import com.akiong.BackOfficeController

class RoleController extends BackOfficeController {

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def springSecurityService
	def roleService

	private static final Logger logger = LoggerFactory.getLogger(this)

	def index = {
		redirect(action: "list", params: params)
	}

	def list = {
		def results = roleService.list(params)
		withFormat {
			html {
				[roleInstanceList: results[0], roleInstanceTotal: results[1], searchName: params.name ?: ""]
			}
		}
	}

	def create = {
		def roleInstance = new Role()
		roleInstance.properties = params
		if (flash.model){
			roleInstance = flash.model
		}
		return [roleInstance: roleInstance]
	}

	def save = {
		def roleInstance = new Role(params)

		withFormat {
			html {
				withForm {
					def error = false
					try {
						roleService.create(roleInstance, params.menu, springSecurityService.principal.username)
					}
					catch (grails.validation.ValidationException e) {
						error = true
						logger.error("Missing property or validation fails", e)
					}
					catch (SecurityException e){
						error = true
						flash.error = e.getMessage()
						logger.error(e.getMessage(), e)
					}
					catch (RuntimeException e) {
						logger.error(e.getMessage(), e)
						redirect(controller: "error", action: "serverError")
						return
					}

					if (!error && !roleInstance.hasErrors()) {
						springSecurityService.clearCachedRequestmaps()
						flash.message = "${message(code: 'default.created.message', args: [message(code: 'role.label', default: 'Role'), roleInstance.id])}"
						redirect(action: "show", id: roleInstance.id)
					}
					else {
						roleInstance.name = "name"
						roleInstance.validate()
						
						flash.model = roleInstance
						redirect(action: "create")
					}
				}.invalidToken {
					redirect(controller: "error", action: "forbidden")
				}
			}
		}
	}

	def show = {

		withFormat {
			html {
				def roleInstance = Role.get(params.id)
				if (!roleInstance) {
					flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'role.label', default: 'Role'), params.id])}"
					redirect(action: "list")
				}
				else {
					return [roleInstance: roleInstance]
				}
			}
		}
	}

	def edit = {
		if (flash.model){
			return [roleInstance: flash.model]
		}
		else{
			def roleInstance = Role.get(params.id)
			if (!roleInstance) {
				flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'role.label', default: 'Role'), params.id])}"
				redirect(action: "list")
			}
			else {
				if (roleInstance.isSystem == true) {
					flash.message = "${message(code: 'default.not.edit.message', args: [message(code: 'role.label', default: 'Role'), params.id])}"
					redirect(action: "show", id: params.id)
					return
				}
				return [roleInstance: roleInstance]
			}
		}
	}

	def update = {
		def roleInstance = Role.get(params.id)
		withFormat {
			html {
				withForm {
					if (roleInstance) {
						if (roleInstance.isSystem == true) {
							flash.message = "${message(code: 'default.not.edit.message', args: [message(code: 'role.label', default: 'Role'), params.id])}"
							redirect(action: "show", id: params.id)
							return
						}
						
						if (params.version) {
							def version = params.version.toLong()
							if (roleInstance.version > version) {
								
								flash.message = "${message(code: 'default.optimistic.locking.failure', args: [message(code: 'role.label')])}"
								redirect(action: "show", id: params.id)
								return
							}
						}

						try {
							roleInstance = roleService.update(roleInstance, params.menu, springSecurityService.principal.username)
						}
						catch (grails.validation.ValidationException e) {
							logger.error("Missing property or validation fails", e)
						}
						catch (RuntimeException e) {
							logger.error(e.getMessage(), e)
							redirect(controller: "error", action: "serverError")
							return
						}

						if (!roleInstance.hasErrors()) {
							springSecurityService.clearCachedRequestmaps()
							flash.message = "${message(code: 'default.updated.message', args: [message(code: 'role.label', default: 'Role'), roleInstance.id])}"
							redirect(action: "show", id: roleInstance.id)
						}
						else {
							render(view: "edit", model: [roleInstance: roleInstance])
						}
					}
					else {
						flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'role.label', default: 'Role'), params.id])}"
						redirect(action: "list")
					}
				}.invalidToken {
					redirect(controller: "error", action: "forbidden")
				}
			}
		}
	}

	def delete = {

		def roleInstance = Role.get(params.id)
		withFormat {
			html {
				withForm {
					if (roleInstance) {
						if (roleInstance.isSystem == true) {
							flash.message = "${message(code: 'default.not.edit.message', args: [message(code: 'role.label', default: 'Role'), params.id])}"
							redirect(action: "show", id: params.id)
							return
						}
						
						if (params.version) {
							def version = params.version.toLong()
							if (roleInstance.version > version) {
								
								flash.message = "${message(code: 'default.optimistic.locking.failure', args: [message(code: 'role.label')])}"
								if (params.show)
									redirect(action: "show", id: params.id)
								else
									redirect(action: "edit", id: params.id)
								return
							}
						}
						try {
							roleService.delete(roleInstance)
							springSecurityService.clearCachedRequestmaps()
							flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'role.label', default: 'Role'), params.id])}"
							redirect(action: "list")
						}
						catch (org.springframework.dao.DataIntegrityViolationException e) {
							logger.error(e.getMessage(), e)
							flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'role.label', default: 'Role'), params.id])}"
							redirect(action: "show", id: params.id)
						}
						catch (RuntimeException e) {
							logger.error(e.getMessage(), e)
							redirect(controller: "error", action: "serverError")
							return
						}
					}
					else {
						flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'role.label', default: 'Role'), params.id])}"
						redirect(action: "list")
					}
				}.invalidToken {
					redirect(controller: "error", action: "forbidden")
				}
			}
		}
	}

	def findByAuthority = {
		def role = Role.findByAuthority(params.authority)
		if (role) {
			if (params.id) {
				if (params.id.equals(role.id.toString()))
					response.status = 200
				else
					response.status = 405
			}
			else {
				response.status = 405
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

	def getRoleLikeName = {

		def roles = roleService.getRoleLike(params.name)
		response.status = 200

		if (params.callback) {
			render "${params.callback}(${roles as JSON})"
		}
		else {
			render "${roles as JSON}"
		}
	}

	def returnJSON = {
		params.offset = params.iDisplayStart ? params.iDisplayStart : 0
		params.max = params.iDisplayLength ? params.iDisplayLength : 10
		params.sort = params.iSortCol_0 ? getColumnToField(params.iSortCol_0) : "id"
		params.order = params.sSortDir_0 ? params.sSortDir_0 : "asc"

		def list = []
		def listData = Role.findAll(params)
		def totalRecord = Role.count()


		listData.each {list << [it.id, it.authority]}

		def data = ["iTotalRecords": totalRecord, "iTotalDisplayRecords": totalRecord, "aaData": list]
		render data as JSON
	}

	String getColumnToField(String index) {
		if (index == '0') return "id";
		else if (index == '1') return "authority";
	}

	private def sendValidationFailedResponse(callback, roleInstance, status) {
		response.status = status
		if (callback) {
			render contentType: "application/json", callback({
				errors {
					roleInstance?.errors?.fieldErrors?.each { err ->
						field(err.field)
						message(g.message(error: err))
					}
				}
			})
		}
		else {
			render contentType: "application/json", {
				errors {
					roleInstance?.errors?.fieldErrors?.each { err ->
						field(err.field)
						message(g.message(error: err))
					}
				}
			}
		}
	}
}
