package com.akiong

import com.akiong.security.Role
import com.akiong.security.User
import com.akiong.security.UserDetails
import com.akiong.security.UserRole

class BankUserService {

	static transactional = true

	def springSecurityService
	def passwordGeneratorService
	def cifService
	def messageSource

	def list(def params) {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		params.properties = ["firstName", "lastName"]

		if (!params.sort && !params.order) {
			params.sort = "username"
			params.order = "asc"
		}

		def query = "+deleteFlag:N +userType:1"
		if (params.userId) {
			query += " UserDetails.user.username:*${params.userId}*"
		}
		if (params.userName) {
			query += " *${params.userName}*"
		}

		def results = new ArrayList()
		def searchResult = UserDetails.search(query, params)

		results.add(searchResult.results)
		results.add(searchResult.total)

		if (searchResult.total > 0) {
			def authorisation = [:]
			searchResult.results.each { x ->
				def roles = UserRole.executeQuery("SELECT ur.role.authority FROM UserRole ur WHERE ur.user.id=?", [x.user.id])
				authorisation.put(x.id, roles.join(", "))
			}
			results.add(authorisation)
		}
		else {
			results.add([:])
		}

		return results
	}

	def create(def params) {
		def userInstance = new User()
		def userDetailsInstance = new UserDetails()

		def passwordList = passwordGeneratorService.generateRegistrationCode()
		userInstance.username = params.username
		userInstance.enabled = 1
		if (passwordList != null && passwordList.size() > 1) {
			userInstance.password = springSecurityService.encodePassword(passwordList[0] + passwordList[1])
		}
		userInstance.save(failOnError: true)

		def role
		def userRoleInstance
		if (params.roleName.class == String) {
			role = Role.findByAuthority(params.roleName)
			if (!role) {
				throw new MissingResourceException("Role '" + params.roleName + "' doesn't exist", "Role", "default")
			}
			userRoleInstance = new UserRole()
			userRoleInstance.user = userInstance
			userRoleInstance.role = role
			userRoleInstance.save(failOnError: true)
		}
		else {
			for (def i = 0; i < params.roleName.size(); i++) {
				role = Role.findByAuthority(params.roleName[i])
				if (!role) {
					throw new MissingResourceException("Role '" + params.roleName[i] + "' doesn't exist", "Role", "default")
				}
				userRoleInstance = new UserRole()
				userRoleInstance.user = userInstance
				userRoleInstance.role = role
				userRoleInstance.save(failOnError: true)
			}
		}

		userDetailsInstance.dateCreated = new Date()
		userDetailsInstance.lastUpdated = new Date()
		userDetailsInstance.firstName = params.firstName
		userDetailsInstance.lastName = params.lastName
		userDetailsInstance.user = userInstance
		userDetailsInstance.userAlias = params.userAlias
		userDetailsInstance.email = params.email
		userDetailsInstance.mobilePhoneNo =  params.mobilePhoneNo
		userDetailsInstance.status = '1'
		userDetailsInstance.forceChangePassword = true
		userDetailsInstance.createdBy = springSecurityService.principal.username

		if(userDetailsInstance.mobilePhoneNo != null && !userDetailsInstance.mobilePhoneNo.equals("")){
			String mobileNo = userDetailsInstance.mobilePhoneNo
			String mobileNo2 = null
			if(mobileNo.substring(0, 1).equals("0")){
				mobileNo2 = "62" + mobileNo.substring(1)
			}
			if(mobileNo2 == null && mobileNo.substring(0, 2).equals("62")){
				mobileNo2 = "0" + mobileNo.substring(2)
			}
			def countnya = UserDetails.executeQuery("SELECT count(ud) FROM UserDetails ud WHERE ud.deleteFlag = 'N' AND (ud.mobilePhoneNo = ? OR ud.mobilePhoneNo = ?)",[mobileNo, mobileNo2])[0]
			if(countnya > 0){
				def request = org.codehaus.groovy.grails.plugins.springsecurity.SecurityRequestHolder.getRequest()
				def session = request.getSession(false)
				def locale = session[org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME]

				Object[] testArgs = new Object[1]
				testArgs[0] = messageSource.getMessage("cif.phoneNo.label", null, locale)
				throw new SecurityException(messageSource.getMessage("default.already.exist.message", testArgs, locale))
			}
		}

		userDetailsInstance.save(failOnError: true)

		if (userDetailsInstance.email) {
			cifService.emailPasswordBO(userInstance.username, userDetailsInstance.email, userDetailsInstance.firstName + " " + userDetailsInstance.lastName, passwordList[0] + passwordList[1])
		}

		return userDetailsInstance
	}

	def show(def params) {
		def results = []

		def userDetails = UserDetails.get(params.id)
		def listRole = userDetails?.user?.getAuthorities().sort {it.authority}

		results << listRole
		results << userDetails

		return results
	}

	def edit(def params) {
		def results = []

		def userDetailsInstance = UserDetails.get(params.id)
		results << userDetailsInstance

		def listRole = userDetailsInstance?.user.getAuthorities().sort {it.authority}
		results << listRole

		return results
	}

	def update(def userDetailsInstance, def params) {
		userDetailsInstance.mobilePhoneNo =  userDetailsInstance.mobilePhoneNo

		if(userDetailsInstance.mobilePhoneNo != null && !userDetailsInstance.mobilePhoneNo.equals("")){
			String mobileNo = userDetailsInstance.mobilePhoneNo
			String mobileNo2 = null
			if(mobileNo.substring(0, 1).equals("0")){
				mobileNo2 = "62" + mobileNo.substring(1)
			}
			if(mobileNo2 == null && mobileNo.substring(0, 2).equals("62")){
				mobileNo2 = "0" + mobileNo.substring(2)
			}
			def countnya = UserDetails.executeQuery("SELECT count(ud) FROM UserDetails ud WHERE ud.deleteFlag = 'N' AND (ud.mobilePhoneNo = ? OR ud.mobilePhoneNo = ?) AND ud.id != ?",[
				mobileNo,
				mobileNo2,
				userDetailsInstance.id
			])[0]
			if(countnya > 0){
				def request = org.codehaus.groovy.grails.plugins.springsecurity.SecurityRequestHolder.getRequest()
				def session = request.getSession(false)
				def locale = session[org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME]

				Object[] testArgs = new Object[1]
				testArgs[0] = messageSource.getMessage("cif.phoneNo.label", null, locale)
				throw new SecurityException(messageSource.getMessage("default.already.exist.message", testArgs, locale))
			}
		}

		userDetailsInstance.lastUpdated = new Date()
		userDetailsInstance.save(failOnError: true)

		def userInstance = userDetailsInstance?.user
		def userRoles = userInstance.authorities

		def role
		def userRoleInstance
		if (params.roleName.class == String) {
			role = Role.findByAuthority(params.roleName)
			if (!role) {
				throw new MissingResourceException("Role '" + params.roleName + "' doesn't exist", "Role", "default")
			}
			if (!userInstance.authorities.contains(role)) {
				UserRole.create(userInstance, role)
			}
			else{
				userRoles -= role
			}
		}
		else {
			for (def i = 0; i < params.roleName.size(); i++) {
				role = Role.findByAuthority(params.roleName[i])
				if (!role) {
					throw new MissingResourceException("Role '" + params.roleName[i] + "' doesn't exist", "Role", "default")
				}
				if (!userInstance.authorities.contains(role)) {
					UserRole.create(userInstance, role)
				}
				else{
					userRoles -= role
				}
			}
		}

		userRoles.each{
			UserRole.remove(userInstance, it)
		}

		return userDetailsInstance
	}

	def reset(def userDetailsInstance) {
		def userInstance = userDetailsInstance.user

		def passwordList = passwordGeneratorService.generateRegistrationCode()
		if (passwordList != null && passwordList.size() > 1) {
			userInstance.password = springSecurityService.encodePassword(passwordList[0] + passwordList[1])
		}
		userInstance.accountExpired = 0
		userInstance.accountLocked = 0
		userInstance.passwordExpired = 0
		userInstance.enabled = 1
		userInstance.save(failOnError: true)

		userDetailsInstance.forceChangePassword = 1
		userDetailsInstance.retry = 0
		userDetailsInstance.noOfWrongChangedPassword = 0
		userDetailsInstance.status = '1'
		userDetailsInstance.save(failOnError: true)

		if (userDetailsInstance.email) {
			cifService.emailResetedPasswordBO(userDetailsInstance.email, userDetailsInstance.firstName + " " + userDetailsInstance.lastName, passwordList[0] + passwordList[1], userDetailsInstance.language)
		}

		return userDetailsInstance
	}

	def block(def userDetailsInstance) {
		userDetailsInstance.status = '4'
		userDetailsInstance.save(failOnError: true)

		def userInstance = userDetailsInstance.user
		userInstance.accountExpired = 1
		userInstance.accountLocked = 0
		userInstance.passwordExpired = 0
		userInstance.enabled = 0
		userInstance.save(failOnError: true)

		return userInstance
	}

	def delete(def userDetailsInstance) {
		def userInstance = userDetailsInstance.user
		UserRole.removeAll(userInstance)
		userInstance.enabled = 0
		userInstance.accountExpired = 0
		userInstance.accountLocked = 0
		userInstance.passwordExpired = 0
		userInstance.save(failOnError: true)

		userDetailsInstance.deleteFlag = "Y"
		userDetailsInstance.save(failOnError: true)

		return userDetailsInstance
	}
}
