package com.akiong.security


import grails.converters.JSON

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import com.akiong.BackOfficeController
import com.akiong.HttpSessionCollector
import com.akiong.maintenance.SystemConfiguration

class ChangePasswordController extends BackOfficeController {

	static allowedMethods = [updatePassword: "POST"]

	def springSecurityService
	def cifService

	private static final Logger logger = LoggerFactory.getLogger(this)

	def index = {
		redirect(action: "edit", params: params)
	}

	def redirectForm = { redirect(controller: "logout") }

	def edit = {


		def user = User.findByUsername(springSecurityService.principal.username)
		def firstTime = "Y"
		def userDetails
		if (user) {
			userDetails = UserDetails.findByUser(user)
			if (userDetails) {
				if (userDetails.status != "1")
					firstTime = "N"
			}
		}

		def passConfig = SystemConfiguration.findByName("Password")?.description
		return [firstTime: (firstTime == "Y" ? true : false), name: (userDetails?.firstName + " " + userDetails?.lastName), passConfig: passConfig]
	}

	def updatePassword = {
		def success = "1"
		try {
			success = cifService.changePasswordBO(springSecurityService.authentication.name, params.oldPass, params.newPass)
		}
		catch (RuntimeException e) {
			logger.error(e.getMessage(), e)
			success = false
		}

		if (success == 1) {
			params.result = "Y"
			params.status = 200
		}
		else if (success == 2) {
			params.result = "N"
			params.error = "Incorrect New Password"
			params.status = 400
		}
		else if (success == 3) {
			params.result = "N"
			params.error = "ID has been locked"
			params.status = 406

			def sessionId = UserDetails.executeQuery("SELECT ud.sessionId FROM UserDetails ud, User u WHERE ud.user = u AND u.username = ?", [
				springSecurityService.authentication.name
			])[0]
			if(sessionId){
				def sessionx = HttpSessionCollector.find(sessionId)
				if (sessionx) {
					sessionx.invalidate()
					HttpSessionCollector.remove(sessionId)
				}
			}
		}
		else {
			params.result = "N"
			params.error = "Incorrect Password"
			params.status = 405
		}

		response.status = 200

		if (params.callback) {
			render "${params.callback}(${params as JSON})"
		}
		else {
			render "${params as JSON}}"
		}
	}
}
