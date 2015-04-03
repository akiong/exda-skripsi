package com.akiong.security

import grails.converters.JSON
import grails.converters.XML

import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import java.security.KeyPair
import com.akiong.helper.JCryptionUtil
import com.akiong.maintenance.SystemConfiguration

class UserDetailsController {

	def springSecurityService
	def bankUserService

	private static final Logger logger = LoggerFactory.getLogger(this)

	def index = {
		redirect(action: "list", params: params)
	}

	def list = {

		def results = bankUserService.list(params)
		withFormat {
			html {
				[userDetailsInstanceList: results[0], auth: results[2], userId: params.userId ?: "", name: params.userName ?: "", userDetailsInstanceTotal: results[1]]
			}
		}
	}

	def create = {

		def userDetailsInstance = new UserDetails()
		userDetailsInstance.properties = params
		def roleUserList = new ArrayList()

		def autocom = new HashMap()
		autocom.put("delay", ConfigurationHolder.config.delay.autocomplete.ms)
		autocom.put("minLength", ConfigurationHolder.config.minLength.autocomplete)

		if (flash.model){
			userDetailsInstance = flash.model[0]
			roleUserList = flash.model[1]
		}
		else{
			roleUserList.add('')
		}
		def passConfig = SystemConfiguration.findByName("Password")?.description
		return [userDetailsInstance: userDetailsInstance, roleUserList: roleUserList, autocom: autocom, passConfig : passConfig]
	}

	def save = {
		def result
		def userDetails
		def roleUserList
		def error = false
		def userName = springSecurityService.principal.username

		withFormat {
			html {
				withForm {
					try {
						result = bankUserService.create(params)
					}
					catch (RuntimeException e) {
						error = true
						logger.error(e.getMessage(), e)
						if (e instanceof MissingResourceException) {
							flash.error = e.getMessage()
						}
						else if (e instanceof SecurityException) {
							flash.error = e.getMessage()
						}
						userDetails = new UserDetails(params)
						userDetails.createdBy = userName
						userDetails.status = "1"
						userDetails.user = new User(params)
						userDetails.user.password = "pass"
						userDetails.validate()
						userDetails.mobilePhoneNo = params.mobilePhoneNo
						roleUserList = new ArrayList()
						if (params.roleName.class == String) {
							roleUserList.add(params.roleName)
						}
						else {
							for (def i = 0; i < params.roleName.size(); i++) {
								roleUserList.add(params.roleName[i])
							}
						}
					}

					if (!error) {
						flash.message = "${message(code: 'default.created.message', args: [message(code: 'userDetails.label', default: 'UserDetails'), result.user.username])}"
						redirect(action: "show", id: result.id)
					}
					else {
						flash.model = [userDetails, roleUserList]
						redirect(action: "create")
					}
				}.invalidToken {
					redirect(controller: "error", action: "forbidden")
				}
			}
		}
	}

	def show = {

		def results
		try {
			results = bankUserService.show(params)
		}
		catch (RuntimeException e) {
			logger.error(e.getMessage(), e)
			redirect(controller: "error", action: "serverError")
			return
		}

		withFormat {
			html {
				if (!results) {
					flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'userDetails.label', default: 'UserDetails'), params.id])}"
					redirect(action: "list")
				}
				else {
					def loginList = LoginHistory.findAllByUserid(results[1]?.user?.username)
					return [userDetailsInstance: results[1], listRole: results[0], loginList: loginList]
				}
			}
		}
	}

	def edit = {
		if (flash.model){
			def autocom = new HashMap()
			autocom.put("delay", ConfigurationHolder.config.delay.autocomplete.ms)
			autocom.put("minLength", ConfigurationHolder.config.minLength.autocomplete)
			def userDetails = flash.model[0]
			userDetails.user = flash.model[2]
			return [userDetailsInstance: flash.model[0], roleUserList: flash.model[1], autocom: autocom]
		}
		else{
			def results
			try {
				results = bankUserService.edit(params)
			}
			catch (RuntimeException e) {
				logger.error(e.getMessage(), e)
				redirect(controller: "error", action: "serverError")
				return
			}

			if (!results[0]) {
				flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'userDetails.label', default: 'UserDetails'), params.id])}"
				redirect(action: "list")
			}
			else {
				def autocom = new HashMap()
				autocom.put("delay", ConfigurationHolder.config.delay.autocomplete.ms)
				autocom.put("minLength", ConfigurationHolder.config.minLength.autocomplete)

				return [userDetailsInstance: results[0], roleUserList: results[1], autocom: autocom]
			}
		}
	}

	def update = {
		def userDetailsInstance = UserDetails.get(params.id)
		def userName = springSecurityService.principal.username

		withFormat {
			html {
				withForm {
					if (userDetailsInstance) {
						if (params.version) {
							def version = params.version.toLong()
							if (userDetailsInstance.version > version) {

								flash.message = "${message(code: 'default.optimistic.locking.failure', args: [message(code: 'userDetails.label')])}"
								redirect(action: "edit", id: params.id)
								return
							}
						}

						def userInstance = userDetailsInstance.user
						userDetailsInstance.properties = params
						userDetailsInstance.updatedBy = userName
						def roleUserList
						def error = false
						try {
							userDetailsInstance = bankUserService.update(userDetailsInstance, params)
						}
						catch (RuntimeException e) {
							error = true
							logger.error(e.getMessage(), e)
							if (e instanceof MissingResourceException) {
								flash.error = e.getMessage()
							}
							if (e instanceof SecurityException) {
								flash.error = e.getMessage()
							}
							roleUserList = new ArrayList()
							if (params.roleName.class == String) {
								roleUserList.add(params.roleName)
							}
							else {
								for (def i = 0; i < params.roleName.size(); i++) {
									roleUserList.add(params.roleName[i])
								}
							}
						}
						if (!error && !userDetailsInstance.hasErrors()) {
							flash.message = "${message(code: 'default.updated.message', args: [message(code: 'userDetails.label', default: 'UserDetails'), userDetailsInstance.user.username])}"
							redirect(action: "show", id: userDetailsInstance.id)
						}
						else {
							flash.model = [
								userDetailsInstance,
								roleUserList,
								userInstance
							]
							redirect(action: "edit", id: params.id)
						}
					}
					else {
						flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'userDetails.label', default: 'UserDetails'), params.id])}"
						redirect(action: "list")
					}
				}.invalidToken {
					redirect(controller: "error", action: "forbidden")
				}
			}
		}
	}

	def block = {
		def userDetailsInstance = UserDetails.get(params.id)
		userDetailsInstance?.updatedBy = springSecurityService.principal.username
		String username = userDetailsInstance?.user?.username

		withFormat {
			html {
				withForm {
					if (userDetailsInstance) {
						try {
							userDetailsInstance = bankUserService.block(userDetailsInstance)
						}
						catch (grails.validation.ValidationException e) {
							logger.error("Missing property or validation fails", e)
						}
						catch (RuntimeException e) {
							logger.error(e.getMessage(), e)
							redirect(controller: "error", action: "serverError")
							return
						}

						if (!userDetailsInstance.hasErrors()) {
							flash.message = "${message(code: 'default.block.message', args: [message(code: 'userDetails.label', default: 'UserDetails'), username])}"
							redirect(action: "show", id: params.id)
						}
						else {
							flash.message = "${message(code: 'default.not.block.message', args: [message(code: 'userDetails.label', default: 'UserDetails'), username])}"
							redirect(action: "edit", id: params.id)
						}
					}
					else {
						flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'userDetails.label', default: 'UserDetails'), params.id])}"
						redirect(action: "list")
					}
				}.invalidToken {
					redirect(controller: "error", action: "forbidden")
				}
			}
		}
	}

	def reset = {

		def userDetailsInstance = UserDetails.get(params.id)
		userDetailsInstance?.updatedBy = springSecurityService.principal.username
		String username = userDetailsInstance?.user?.username

		withFormat {
			html {
				withForm {
					if (userDetailsInstance) {
						try {
							userDetailsInstance = bankUserService.reset(userDetailsInstance)
							flash.message = "${message(code: 'default.reset.message', args: [message(code: 'userDetails.label', default: 'UserDetails'), username])}"
							redirect(action: "show", id: params.id)
						}
						catch (RuntimeException e) {
							logger.error(e.getMessage(), e)
							flash.message = "${message(code: 'default.not.reset.message', args: [message(code: 'userDetails.label', default: 'UserDetails'), username])}"
							redirect(action: "edit", id: params.id)
						}
					}
					else {
						flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'userDetails.label', default: 'UserDetails'), params.id])}"
						redirect(action: "list")
					}
				}.invalidToken {
					redirect(controller: "error", action: "forbidden")
				}
			}
		}
	}

	def delete = {
		def userDetailsInstance = UserDetails.get(params.id)
		userDetailsInstance?.updatedBy = springSecurityService.principal.username

		withFormat {
			html {
				withForm {
					if (userDetailsInstance) {
						if (params.version) {
							def version = params.version.toLong()
							if (userDetailsInstance.version > version) {

								flash.message = "${message(code: 'default.optimistic.locking.failure', args: [message(code: 'userDetails.label')])}"
								if (params.show)
									redirect(action: "show", id: params.id)
								else
									redirect(action: "edit", id: params.id)
								return
							}
						}
						try {
							userDetailsInstance = bankUserService.delete(userDetailsInstance)
							flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'userDetails.label', default: 'UserDetails'), userDetailsInstance.user.username])}"
							redirect(action: "list")
						}
						catch (RuntimeException e) {
							logger.error(e.getMessage(), e)
							redirect(controller: "error", action: "serverError")
							return
						}
					}
					else {
						flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'userDetails.label', default: 'UserDetails'), params.id])}"
						redirect(action: "list")
					}
				}.invalidToken {
					redirect(controller: "error", action: "forbidden")
				}
			}
		}
	}

	def isUsernameAvailable = {
		def user = User.findByUsername(params.id)
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

	def beforeInterceptor = [action: this.&backOfficeInterceptor, except: [
			'encryptionPassword' ,
			'isUsernameAvailable'
		]]

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

	def encryptionPassword = {

		JCryptionUtil jCryptionUtil = new JCryptionUtil();
		KeyPair keys = null;
		if (session.getAttribute("keys") == null) {
			keys = jCryptionUtil.generateKeypair(512);
			session.setAttribute("keys", keys);
		}

		String e = JCryptionUtil.getPublicKeyExponent(session.getAttribute("keys"));
		String n = JCryptionUtil.getPublicKeyModulus(session.getAttribute("keys"));
		String md = String.valueOf(JCryptionUtil.getMaxDigits(512));

		Map m = new HashMap();
		m.put('e', e);
		m.put('n', n);
		m.put('maxdigits', md);

		println "m = "+m
		response.status = 200
		if (params.callback) {
			render "${params.callback}(${m as JSON})"
		}
		else {
			render "${m as JSON}"
		}
	}
}
