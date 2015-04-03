package com.akiong.app.cif


import grails.converters.JSON

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import com.akiong.BackOfficeController
import com.akiong.security.*

class CifUserController extends BackOfficeController{

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def springSecurityService
	def corporateUserService
	def cifService
	def passwordGeneratorService


	private static final Logger logger = LoggerFactory.getLogger(this)

	def index = {
		redirect(action: "list", params: params)
	}

	def list = {

		def results = corporateUserService.list(params)
		withFormat {
			html {
				[userList: results[0], userInstanceTotal: results[1], name: params.name ?: "", userID: params.userID ?: ""]
			}
		}
	}

	def create = {
		if (flash.model){
			return [cifUserInstance: flash.model]
		}
		else{
			def cifUserInstance = new CifUser()
			cifUserInstance.properties = params
			return [cifUserInstance: cifUserInstance]
		}
	}

	def save = {
		withForm {
			def cifUserInstance = new CifUser(params)
			cifUserInstance.finance = (params.finance == "on") ? "Y" : "N"
			cifUserInstance.bo = (params.bo == "on") ? "Y" : "N"
			cifUserInstance.createdBy = springSecurityService.principal.username
			boolean error = false
			try {
				corporateUserService.create(cifUserInstance)
			}
			catch (grails.validation.ValidationException e) {
				error = true
				logger.error("Missing property or validation fails", e)
			}
			catch (MissingResourceException e) {
				error = true
				logger.error(e.getMessage(), e)

				if(e.getMessage().equals("Phone no cannot blank")){
					cifUserInstance.userDetails.errors.rejectValue("mobilePhoneNo", "default.blank.message", [
						message(code: 'userDetails.mobilePhoneNo2.label'),
						'class UserDetails']
					as Object[], "Phone no cannot blank")
				}
			}
			catch (RuntimeException e) {
				logger.error(e.getMessage(), e)
				redirect(controller: "error", action: "serverError")
				return
			}
			if (!error) {
				flash.message = "${message(code: 'default.created.message', args: [message(code: 'cifUser.label', default: 'CifUser'), cifUserInstance.userDetails.user.username])}"
				redirect(action: "show", id: cifUserInstance.id)
			}
			else {
				if(cifUserInstance.userDetails != null){
					cifUserInstance.userDetails.mobilePhoneNo = params.userDetails.mobilePhoneNo
				}
				flash.model = cifUserInstance
				redirect(action: "create", id: params.id)
			}
		}.invalidToken {
			redirect(controller: "error", action: "forbidden")
		}
	}

	def show={
		def cif = corporateUserService.getCurrentCifUser().cif
		def cifUserInstance = CifUser.get(params.id)
		if (!cifUserInstance || cifUserInstance.sysAdmin.equals("N") || cifUserInstance.deleteFlag.equals("Y") || cifUserInstance.cif.id != cif.id) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'cifUser.label'), params.id])}"
			redirect(controller: "cifUser", action: "list")
		}
		else {
			if(cifUserInstance.userDetails.activationKey != null){
				return [cifuser: cifUserInstance, email: cifUserInstance.userDetails.newEmail]
			}
			else{
				[cifuser: cifUserInstance]
			}
		}
	}

	def edit = {
		if (flash.model){
			return [cifUserInstance: flash.model]
		}
		else{
			def cif = corporateUserService.getCurrentCifUser().cif
			def cifUserInstance = CifUser.get(params.id)
			if (!cifUserInstance || cifUserInstance.sysAdmin.equals("N") || cifUserInstance.deleteFlag.equals("Y") || cifUserInstance.cif.id != cif.id) {
				flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'cifUser.label', default: 'CifUser'), params.id])}"
				redirect(controller: "cifUser", action: "list")
			}
			else {
				return [cifUserInstance: cifUserInstance]
			}
		}
	}

	def update = {
		withForm {
			def cif = corporateUserService.getCurrentCifUser().cif
			def cifUserInstance = CifUser.get(params.id)
			if (cifUserInstance && cifUserInstance.sysAdmin.equals("Y") && cifUserInstance.deleteFlag.equals("N") && cifUserInstance.cif.id == cif.id) {
				if (params.version) {
					def version = params.version.toLong()
					if (cifUserInstance.version > version) {
						flash.message = "${message(code: 'default.optimistic.locking.failure', args: [message(code: 'cifUser.label')])}"
						redirect(action: "edit", id: params.id)
						return
					}
				}
				def oldEmail = cifUserInstance.userDetails?.email
				cifUserInstance.properties = params
				cifUserInstance.finance = (params.finance == "on") ? "Y" : "N"
				cifUserInstance.bo = (params.bo == "on") ? "Y" : "N"
				cifUserInstance?.updatedBy = springSecurityService.principal.username
				boolean error = false
				String missingException
				try {
					corporateUserService.update(cifUserInstance, oldEmail)
				}
				catch (grails.validation.ValidationException e) {
					error = true
					logger.error("Missing property or validation fails", e)
				}
				catch (MissingResourceException e) {
					error = true
					missingException = e.getMessage()
					logger.error(e.getMessage(), e)
				}
				catch (SecurityException e) {
					error = true
					logger.error(e.getMessage(), e)
					flash.error = e.getMessage()
				}
				catch (RuntimeException e) {
					cifUserInstance.discard()
					logger.error(e.getMessage(), e)
					redirect(controller: "error", action: "serverError")
					return
				}
				if (!error) {
					flash.message = "${message(code: 'default.updated.message', args: [message(code: 'cifUser.label', default: 'CifUser'), cifUserInstance.userDetails.user.username])}"
					redirect(action: "show", id: cifUserInstance.id)
				}
				else {
					cifUserInstance = CifUser.get(params.id)
					cifUserInstance?.userDetails?.user
					cifUserInstance.properties = params
					cifUserInstance.finance = (params.finance == "on") ? "Y" : "N"
					cifUserInstance.bo = (params.bo == "on") ? "Y" : "N"

					cifUserInstance.validate()
					cifUserInstance.userDetails?.validate()

					if(missingException.equals("Phone no cannot blank")){
						cifUserInstance.userDetails.errors.rejectValue("mobilePhoneNo", "default.blank.message", [
							message(code: 'userDetails.mobilePhoneNo2.label'),
							'class UserDetails']
						as Object[], "Phone no cannot blank")
					}
					else if(missingException.equals("Wrong Country Code")){
						cifUserInstance.userDetails.errors.rejectValue("mobilePhoneNo", "default.wrong.message", [
							message(code: 'country.code.label'),
							'class UserDetails']
						as Object[], "Wrong Country Code")
					}
					cifUserInstance.discard()
					cifUserInstance.userDetails.mobilePhoneNo = params.userDetails.mobilePhoneNo

					flash.model = cifUserInstance
					flash.model.cif = cifUserInstance.cif

					redirect(action: "edit", id: params.id)
				}
			}
			else {
				flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'cifUser.label', default: 'CifUser'), params.id])}"
				redirect(controller: "cifUser", action: "list")
			}
		}.invalidToken {
			redirect(controller: "error", action: "forbidden")
		}
	}

	def delete = {
		withForm {
			def cif = corporateUserService.getCurrentCifUser().cif
			def cifUserInstance = CifUser.get(params.id)
			if (cifUserInstance && cifUserInstance.sysAdmin.equals("Y") && cifUserInstance.deleteFlag.equals("N") && cifUserInstance.cif.id == cif.id) {
				if (params.version) {
					def version = params.version.toLong()
					if (cifUserInstance.version > version) {
						flash.message = "${message(code: 'default.optimistic.locking.failure', args: [message(code: 'cifUser.label')])}"
						redirect(action: "show", id: params.id)
						return
					}
				}

				try {
					cifUserInstance?.updatedBy = springSecurityService.principal.username
					corporateUserService.delete(cifUserInstance)
					flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'cifUser.label', default: 'CifUser'), cifUserInstance.userDetails.user.username])}"
					redirect(action: "list", id: cifUserInstance.cif?.id)
				}
				catch (RuntimeException e) {
					logger.error(e.getMessage(), e)
					flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'cifUser.label', default: 'CifUser'), cifUserInstance.userDetails.user.username])}"
					redirect(action: "show", id: params.id)
				}
			}
			else {
				flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'cifUser.label', default: 'CifUser'), params.id])}"
				redirect(controller: "cifUser", action: "list")
			}
		}.invalidToken {
			redirect(controller: "error", action: "forbidden")
		}
	}

	def block = {
		def cuser = CifUser.get(params.id)
		def cif = corporateUserService.getCurrentCifUser().cif
		if (cuser.cif.id == cif.id && cuser.deleteFlag.equals("N")) {
			try {
				cifService.blockUserStatus(cuser.userDetails?.user)
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

	def blockUser = {
		def user = User.get(params.id)
		if (user) {
			try {
				cifService.blockUserStatus(user)
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

	def reset = {
		def cuser = CifUser.get(params.id)
		def cif = corporateUserService.getCurrentCifUser().cif
		if (cuser.cif.id == cif.id && cuser.deleteFlag.equals("N")) {
			try {
				def reset = cifService.resetUser(cuser.userDetails?.user?.id)
				if (reset)
					response.status = 200
				else
					response.status = 404
			}
			catch (SecurityException e) {
				logger.error(e.getMessage(), e)
				response.status = 405
			}
			catch (RuntimeException e) {
				logger.error(e.getMessage(), e)
				response.status = 404
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

	def resetPassword = {

		try {
			def reset = cifService.resetUser(params.id)
			if (reset)
				response.status = 200
			else
				response.status = 404
		}
		catch (SecurityException e) {
			logger.error(e.getMessage(), e)
			response.status = 405
		}
		catch (RuntimeException e) {
			logger.error(e.getMessage(), e)
			response.status = 404
		}

		if (params.callback) {
			render "${params.callback}(${params as JSON})"
		}
		else {
			render "${params as JSON}"
		}
	}
}
