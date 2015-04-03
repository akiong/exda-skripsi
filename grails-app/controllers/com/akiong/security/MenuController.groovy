package com.akiong.security

import grails.converters.JSON
import grails.converters.XML
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import com.akiong.BackOfficeController

class MenuController extends BackOfficeController {

	static allowedMethods = [save: "POST", update: "POST", delete: "POST", saveTree: "POST"]

	def springSecurityService
	def menuService

	private static final Logger logger = LoggerFactory.getLogger(this)

	def index = {
		redirect(action: "list", params: params)
	}

	def list = {

		def results = menuService.list(params)
		withFormat {
			html {
				[menuInstanceList: results[0], menuInstanceTotal: results[1], searchName: params.name ?: ""]
			}
		}
	}

	def create = {
		def menuInstance = new Menu()
		menuInstance.properties = params
		if (flash.model){
			menuInstance = flash.model
		}
		return [menuInstance: menuInstance]
	}

	def save = {
		def menuInstance = new Menu(params)
		menuInstance.createdBy = springSecurityService.principal.username
		menuInstance.properties = params

		withFormat {
			html {
				withForm {
					try {
						menuInstance = menuService.create(menuInstance)
					}
					catch (grails.validation.ValidationException e) {
						logger.error("Missing property or validation fails", e)
					}
					catch (RuntimeException e) {
						logger.error(e.getMessage(), e)
						redirect(controller: "error", action: "serverError")
						return
					}

					if (!menuInstance.hasErrors()) {
						flash.message = "${message(code: 'default.created.message', args: [message(code: 'menu.label', default: 'Menu'), menuInstance.id])}"
						redirect(action: "show", id: menuInstance.id)
					}
					else {
						flash.model = menuInstance
						redirect(action: "create")
					}
				}.invalidToken {
					redirect(controller: "error", action: "forbidden")
				}
			}
		}
	}

	def show = {
		if (!checkShowUrl()) {
			return
		}

		withFormat {
			html {
				def menuInstance = Menu.get(params.id)
				if (!menuInstance) {
					flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'menu.label', default: 'Menu'), params.id])}"
					redirect(action: "list")
				}
				else {
					return [menuInstance: menuInstance]
				}
			}
		}
	}

	def edit = {
		if (flash.model){
			return [menuInstance: flash.model]
		}
		else{
			def menuInstance = Menu.get(params.id)
			if (!menuInstance) {
				flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'menu.label', default: 'Menu'), params.id])}"
				redirect(action: "list")
			}
			else {
				return [menuInstance: menuInstance]
			}
		}
	}

	def update = {
		def menuIns = Menu.get(params.id)

		withFormat {
			html {
				withForm {
					if (menuIns) {
						if (params.version) {
							def version = params.version.toLong()
							if (menuIns.version > version) {

								menuIns.errors.rejectValue("version", "default.optimistic.locking.failure", [
									message(code: 'menu.label', default: 'Menu')]
								as Object[], "Another user has updated this Menu while you were editing")

								flash.model = menuIns
								redirect(action: "edit", id: params.id)
								return
							}
						}

						menuIns.updatedBy = springSecurityService.principal.username
						menuIns.properties = params
						try {
							menuIns = menuService.update(menuIns)
						}
						catch (grails.validation.ValidationException e) {
							logger.error("Missing property or validation fails", e)
						}
						catch (RuntimeException e) {
							logger.error(e.getMessage(), e)
							redirect(controller: "error", action: "serverError")
							return
						}

						if (!menuIns.hasErrors()) {
							flash.message = "${message(code: 'default.updated.message', args: [message(code: 'menu.label', default: 'Menu'), menuIns.id])}"
							redirect(action: "show", id: menuIns.id)
						}
						else {
							flash.model = menuIns
							redirect(action: "edit", id: params.id)
						}
					}
					else {
						flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'menu.label', default: 'Menu'), params.id])}"
						redirect(action: "list")
					}
				}.invalidToken {
					redirect(controller: "error", action: "forbidden")
				}
			}
		}
	}

	def delete = {
		def menuIns = Menu.get(params.id)
		withFormat {
			html {
				withForm {
					if (menuIns) {
						if (params.version) {
							def version = params.version.toLong()
							if (menuIns.version > version) {

								flash.message = "${message(code: 'default.optimistic.locking.failure', args: [message(code: 'menu.label')])}"
								redirect(action: "show", id: params.id)
								return
							}
						}

						try {
							menuService.delete(menuIns)
							flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'menu.label', default: 'Menu'), params.id])}"
							redirect(action: "list")
						}
						catch (org.springframework.dao.DataIntegrityViolationException e) {
							logger.error(e.getMessage(), e)
							flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'menu.label', default: 'Menu'), params.id])}"
							redirect(action: "show", id: params.id)
						}
						catch (RuntimeException e) {
							logger.error(e.getMessage(), e)
							redirect(controller: "error", action: "serverError")
							return
						}
					}
					else {
						flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'menu.label', default: 'Menu'), params.id])}"
						redirect(action: "list")
					}
				}.invalidToken {
					redirect(controller: "error", action: "forbidden")
				}
			}
		}
	}

	def tree = {

		def options = Menu.createCriteria().list { eq("sequence", 0) }
		return [options: options]
	}

	def saveTree = {
		withForm {
			try {
				menuService.saveTree(params.menu)
			}
			catch (RuntimeException e) {
				logger.error(e.getMessage(), e)
				redirect(controller: "error", action: "serverError")
				return
			}
			flash.message = "${message(code: 'default.updated.message', args: [message(code: 'menu.tree.label', default: 'Menu Tree'), ''])}"
			redirect(action: "treeDetail")
		}.invalidToken {
			redirect(controller: "error", action: "forbidden")
		}
	}

	def treeDetail = { return []}

	def returnJSON = {
		params.offset = params.iDisplayStart ? params.iDisplayStart : 0
		params.max = params.iDisplayLength ? params.iDisplayLength : 10
		params.sort = params.iSortCol_0 ? getColumnToField(params.iSortCol_0) : "id"
		params.order = params.sSortDir_0 ? params.sSortDir_0 : "asc"

		def list = []
		def listData = Menu.findAll(params)
		def totalRecord = Menu.count()


		listData.each {
			list << [
				it.id,
				it.name,
				it.url,
				it.parentMenu,
				it.sequence,
				it.createdBy
			]
		}

		def data = ["iTotalRecords": totalRecord, "iTotalDisplayRecords": totalRecord, "aaData": list]
		//render "${params.callback}(data as JSON)"
		render data as JSON
	}

	String getColumnToField(String index) {
		if (index == '0') return "id";
		else if (index == '1') return "name";
		else if (index == '2') return "url";
		else if (index == '3') return "parentMenu";
		else if (index == '4') return "sequence";
		else if (index == '5') return "createdBy";
	}
}
