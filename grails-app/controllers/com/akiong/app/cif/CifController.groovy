
package com.akiong.app.cif


import grails.converters.JSON

import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import com.akiong.maintenance.City
//import com.teravin.collection.maintenance.Network;
import com.akiong.maintenance.Province
//import com.teravin.collection.maintenance.RateType
import com.akiong.security.User
import com.akiong.security.UserDetails
import com.akiong.helper.Common


class CifController{

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def springSecurityService
	def corporateService
	def corporateRNService
	def taskService

	private static final Logger logger = LoggerFactory.getLogger(this)

	def index = {
		redirect(action: "list", params: params)
	}

	def list = {
		def results = corporateService.list(params)
		[cifInstanceList: results[0], corporate: params.corporate ?: "", cifInstanceTotal: results[1]]
	}

	def create = {
		def cifInstance = new Cif()
		cifInstance.properties = params

		if (flash.model){
			cifInstance = flash.model
			return [cifInstance: cifInstance, userDetailsInstance1: flash.model.picc1, userDetailsInstance2: flash.model.picc2,
				cityList: City.findAllByDeleteFlag("N", [sort:"city", order:"asc"]),
				error: true]
		}
		return [cifInstance: cifInstance, cityList: City.findAllByDeleteFlag("N", [sort:"city", order:"asc"])]
	}

	def save = {
		withForm {
			def cifInstance = new Cif(params)
			cifInstance.createdBy = springSecurityService.principal.username
			boolean error = false
			try {
				corporateService.createNoPending(cifInstance, params.pic1, params.pic2)
			}
			catch (grails.validation.ValidationException e) {
				error = true
				logger.error("Missing property or validation fails", e)
			}
			catch(MissingResourceException e){
				error = true
				flash.error = e.getMessage()
			}
			catch(SecurityException e){
				error = true
				flash.error = e.getMessage()
			}
			catch (RuntimeException e) {
				logger.error(e.getMessage(), e)
				redirect(controller: "error", action: "serverError")
				return
			}
			if (!error && !cifInstance.hasErrors()) {
				flash.message = "${message(code: 'default.created.message', args: [message(code: 'cif.label', default: 'Cif'), cifInstance.id])}"
				redirect(action: "show", id: cifInstance.id)
			}
			else {
				flash.model = cifInstance
				flash.model.city = cifInstance.city

				def userDetails = new UserDetails(params.pic1)
				userDetails.createdBy = cifInstance.createdBy
				userDetails.status = "1"
				userDetails.user = new User(params.pic1)
				userDetails.user.password = "pass"
				userDetails.user.validate()
				userDetails.validate()
				flash.model.picc1 = userDetails

				def userDetails2 = new UserDetails(params.pic2)
				userDetails2.createdBy = cifInstance.createdBy
				userDetails2.status = "1"
				userDetails2.user = new User(params.pic2)
				userDetails2.user.password = "pass"
				userDetails2.user.validate()
				userDetails2.validate()
				flash.model.picc2 = userDetails2

				redirect(action: "create")
			}
		}.invalidToken {
			redirect(controller: "error", action: "forbidden")
		}
	}

	def pending = {
		def cifInstance = Cif.findWhere(id: Long.parseLong(params.id))
		if (!cifInstance || cifInstance.status.equals(Common.STATUS_ACTIVE)) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'cif.label', default: 'Cif'), params.id])}"
			redirect(controller: "corporateMaintenance", action: "list")
		}
		else {
			if(cifInstance.status.equals(Common.PENDING_UPDATE)){
				def changes =  JSON.parse(cifInstance.pending)
				cifInstance.properties = changes
				cifInstance.discard()
			}

			def pic1 = UserDetails.executeQuery("SELECT ud FROM UserDetails ud WHERE ud.user.username = ?",[cifInstance.pic1])[0]
			def pic2 = UserDetails.executeQuery("SELECT ud FROM UserDetails ud WHERE ud.user.username = ?",[cifInstance.pic2])[0]
			render(view: "approval", model: [cifInstance: cifInstance, fromPending: true, pic1: pic1, pic2: pic2])
		}
	}

	def show = {
		def cifInstance = Cif.findWhere(id: Long.parseLong(params.id), deleteFlag: 'N')
		if (!cifInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'cif.label', default: 'Cif'), params.id])}"
			redirect(action: "list")
		}
		else {
			def pic1 = UserDetails.executeQuery("SELECT ud FROM UserDetails ud WHERE ud.user.username = ?",[cifInstance.pic1])[0]
			def pic2 = UserDetails.executeQuery("SELECT ud FROM UserDetails ud WHERE ud.user.username = ?",[cifInstance.pic2])[0]
			[cifInstance: cifInstance, pic1: pic1, pic2: pic2]
		}
	}

	def edit = {
		if (flash.model){
			return [cifInstance: flash.model, error: true, provinceList: Province.findAllByDeleteFlag("N", [sort:"province", order:"asc"]), cityList: City.findAllByDeleteFlag("N", [sort:"city", order:"asc"]), userDetailsInstance1: flash.model.picc1, userDetailsInstance2: flash.model.picc2, rateTypeList: RateType.findAllByDeleteFlag("N")]
		}
		else{
			def cifInstance = Cif.findWhere(id: Long.parseLong(params.id), deleteFlag: 'N')
			if (!cifInstance) {
				flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'cif.label', default: 'Cif'), params.id])}"
				redirect(action: "list")
			}
			else {
				def pic1 = UserDetails.executeQuery("SELECT ud FROM UserDetails ud WHERE ud.user.username = ?",[cifInstance.pic1])[0]
				def pic2 = UserDetails.executeQuery("SELECT ud FROM UserDetails ud WHERE ud.user.username = ?",[cifInstance.pic2])[0]
				return [cifInstance: cifInstance, network:Network.findAllByDeleteFlag("N",[sort:"code", order:"asc"]), provinceList: Province.findAllByDeleteFlag("N", [sort:"province", order:"asc"]), cityList: City.findAllByDeleteFlag("N", [sort:"city", order:"asc"]), userDetailsInstance1: pic1, userDetailsInstance2: pic2, rateTypeList: RateType.findAllByDeleteFlag("N")]
			}
		}
	}

	def update = {
		withForm {
			def cifInstance = Cif.findWhere(id: Long.parseLong(params.id), deleteFlag: 'N')
			if (cifInstance) {
				if (params.version) {
					def version = params.version.toLong()
					if (cifInstance.version > version) {

						cifInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [
							message(code: 'cif.label', default: 'Cif')]
						as Object[], "Another user has updated this Cif while you were editing")

						redirect(action: "edit", id: params.id)
						return
					}
				}

				cifInstance.properties = params
				boolean error = false
				try{
					corporateService.update(cifInstance, springSecurityService.principal.username, params.pic1, params.pic2)
				}
				catch (grails.validation.ValidationException e) {
					error = true
					logger.error("Missing property or validation fails", e)
				}
				catch (MissingResourceException e) {
					error = true
					cifInstance.discard()
					logger.error(e.getMessage(), e)
					flash.error = e.getMessage()
				}
				catch (SecurityException e) {
					error = true
					cifInstance.discard()
					logger.error(e.getMessage(), e)
					flash.error = e.getMessage()
				}
				catch (RuntimeException e) {
					error = true
					cifInstance.discard()
					logger.error(e.getMessage(), e)
					redirect(controller: "error", action: "serverError")
					return
				}
				if (!error) {
					flash.message = "${message(code: 'default.updated.message', args: [message(code: 'cif.label', default: 'Cif'), cifInstance.toString()])}"
					redirect(action: "show", id: cifInstance.id)
				}
				else {
					flash.model = cifInstance
					def pic1 = UserDetails.executeQuery("SELECT ud FROM UserDetails ud WHERE ud.user.username = ?",[cifInstance.pic1])[0]
					def pic2 = UserDetails.executeQuery("SELECT ud FROM UserDetails ud WHERE ud.user.username = ?",[cifInstance.pic2])[0]
					def user1 = pic1.user
					def user2 = pic2.user
					pic1.properties = params.pic1
					pic2.properties = params.pic2
					pic1.validate()
					pic2.validate()
					pic1.discard()
					pic2.discard()
					pic1.user = user1
					pic2.user = user2
					flash.model.picc1 = pic1
					flash.model.picc2 = pic2

					redirect(action: "edit", id: params.id)
				}
			}
			else {
				flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'cif.label', default: 'Cif'), params.id])}"
				redirect(action: "list")
			}
		}.invalidToken {
			redirect(controller: "error", action: "forbidden")
		}
	}

	def delete = {
		withForm {
			def cifInstance = Cif.findWhere(id: Long.parseLong(params.id), deleteFlag: 'N')
			if (cifInstance) {
				if (params.version) {
					def version = params.version.toLong()
					if (cifInstance.version > version) {
						flash.message = "${message(code: 'default.optimistic.locking.failure', args: [message(code: 'cif.label')])}"
						redirect(action: "show", id: params.id)
						return
					}
				}

				try {
					corporateService.delete(cifInstance, springSecurityService.principal.username)
					flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'cif.label', default: 'Cif'), cifInstance.toString()])}"
					redirect(action: "list")
				}
				catch (RuntimeException e) {
					logger.error(e.getMessage(), e)
					flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'cif.label', default: 'Cif'), cifInstance.toString()])}"
					redirect(action: "show", id: params.id)
				}
			}
			else {
				flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'cif.label', default: 'Cif'), params.id])}"
				redirect(action: "list")
			}
		}.invalidToken {
			redirect(controller: "error", action: "forbidden")
		}
	}

	def isCorpIdAvailable = {
		def user = Cif.findByCifId(params.id)
		if (user)
			response.status = 405
		else
			response.status = 200

		if (params.callback) {
			render "${params.callback}(${params as JSON})"
		}
		else {
			render "${params as JSON}"
		}
	}

	def approve = {
		withForm {
			try{
				corporateRNService.approveCorporate(params.taskId, springSecurityService.principal.username)
				flash.message = "${message(code: 'approve.success.label')}"
				redirect(controller: "corporateMaintenance" , action: "list")
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
				corporateRNService.rejectCorporate(params.taskId, springSecurityService.principal.username)
				flash.message = "${message(code: 'reject.success.label')}"
				redirect(controller: "corporateMaintenance" , action: "list")
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

	def approval = {
		try{
			def variables = taskService.getVariables(params.id, ["id", "type"])
			def cifInstance = Cif.findWhere(id: variables["id"])
			if (!cifInstance || cifInstance.status.equals(Common.STATUS_ACTIVE)) {
				flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'cif.label', default: 'Cif'), params.id])}"
				redirect(controller: "corporateMaintenance", action: "list")
			}
			else {
				if(variables["type"].equals("update")){
					def changes =  JSON.parse(cifInstance.pending)
					cifInstance.properties = changes
					cifInstance.discard()
				}

				def pic1 = UserDetails.executeQuery("SELECT ud FROM UserDetails ud WHERE ud.user.username = ?",[cifInstance.pic1])[0]
				def pic2 = UserDetails.executeQuery("SELECT ud FROM UserDetails ud WHERE ud.user.username = ?",[cifInstance.pic2])[0]
				render(view: "approval", model: [cifInstance: cifInstance, isApproval: "Y", taskId: params.id, pic1: pic1, pic2: pic2 ])
			}
		}
		catch(Exception e){
			logger.error(e.getMessage(), e)
			redirect(controller: "corporateMaintenance" , action: "list")
		}
	}

	def beforeInterceptor = [action: this.&backOfficeInterceptor, except: ['isCorpIdAvailable']]

	def backOfficeInterceptor() {
		if (ConfigurationHolder.config.netbank.debug.action)
			logger.debug((!springSecurityService.loggedIn ? "Anonymous" : springSecurityService.principal?.username) + " execute action :  ${actionUri}");
		if (!springSecurityService.loggedIn) {
			redirect(controller: "login", action: "auth")
			return false
		}
		else if (session.getAttribute("change")) {
			if (!controllerUri.toString().equals("/changePassword")) {
				redirect(controller: "changePassword", action: "edit")
				return false
			}
		}
	}
}
