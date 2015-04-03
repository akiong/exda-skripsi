package com.akiong


import com.akiong.app.cif.Cif
import com.akiong.app.cif.CifUser
import com.akiong.app.cif.Grup
import com.akiong.security.Role
import com.akiong.security.User
import com.akiong.security.UserDetails
import com.akiong.security.UserRole
import com.akiong.helper.Common

class CorporateService {

	static transactional = true

	def corporateUserService
	def passwordGeneratorService
	def springSecurityService
	def corporateAcitiviService
	def activitiService
	def messageSource
	def cifService
	def grupService

	def createUserPICPending(def pic, def cifInstance){
		def cifUserInstance = new CifUser()
		cifUserInstance.firstName = pic.firstName
		cifUserInstance.lastName = pic.lastName
		cifUserInstance.sysAdmin = "Y"
		cifUserInstance.cif = cifInstance
		cifUserInstance.createdBy = cifInstance.createdBy

		def userInstance = new User()
		def userDetailsInstance = new UserDetails()

		userInstance.username = pic.username
		userInstance.enabled = 0
		userInstance.password = springSecurityService.encodePassword("password")
		userInstance.save(failOnError: true)

		def role = Role.findByAuthority(Common.ROLE_CORP_ADMIN)
		if (role) {
			def userRoleInstance = new UserRole()
			userRoleInstance.user = userInstance
			userRoleInstance.role = role
			userRoleInstance.save(failOnError: true)
		}

		userDetailsInstance.dateCreated = new Date()
		userDetailsInstance.lastUpdated = new Date()
		userDetailsInstance.firstName = pic.firstName
		userDetailsInstance.lastName = pic.lastName
		userDetailsInstance.user = userInstance
		userDetailsInstance.userAlias = pic.userAlias
		userDetailsInstance.email = pic.email
		userDetailsInstance.mobilePhoneNo =  pic.mobilePhoneNo
		userDetailsInstance.status = '1'
		userDetailsInstance.forceChangePassword = true
		userDetailsInstance.createdBy = cifInstance.createdBy
		userDetailsInstance.userType = "2"

		if(userDetailsInstance.mobilePhoneNo == null){
			def request = org.codehaus.groovy.grails.plugins.springsecurity.SecurityRequestHolder.getRequest()
			def session = request.getSession(false)
			def locale = session[org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME]

			Object[] testArgs = new Object[1]
			testArgs[0] = messageSource.getMessage("cif.phoneNo.label", null, locale)
			throw new SecurityException(messageSource.getMessage("default.blank.message", testArgs, locale))
		}

//		String mobileNo = userDetailsInstance.mobilePhoneNo
//		String mobileNo2 = null
//		if(mobileNo.substring(0, 1).equals("0")){
//			mobileNo2 = "62" + mobileNo.substring(1)
//		}
//		if(mobileNo2 == null && mobileNo.substring(0, 2).equals("62")){
//			mobileNo2 = "0" + mobileNo.substring(2)
//		}
//		def countnya = UserDetails.executeQuery("SELECT count(ud) FROM UserDetails ud WHERE ud.deleteFlag = 'N' AND (ud.mobilePhoneNo = ? OR ud.mobilePhoneNo = ?)",[mobileNo, mobileNo2])[0]
//		if(countnya > 0){
//			def request = org.codehaus.groovy.grails.plugins.springsecurity.SecurityRequestHolder.getRequest()
//			def session = request.getSession(false)
//			def locale = session[org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME]
//
//			Object[] testArgs = new Object[1]
//			testArgs[0] = messageSource.getMessage("cif.phoneNo.label", null, locale)
//			throw new SecurityException(messageSource.getMessage("default.already.exist.message", testArgs, locale))
//		}
		userDetailsInstance.save(failOnError: true)

		cifUserInstance.userDetails = userDetailsInstance
		cifUserInstance.save(failOnError: true)
	}

	def createUserPIC(def pic, def cifInstance){
		def cifUserInstance = new CifUser()
		cifUserInstance.firstName = pic.firstName
		cifUserInstance.lastName = pic.lastName
		cifUserInstance.sysAdmin = "Y"
		cifUserInstance.bo = "Y"
		cifUserInstance.cif = cifInstance
		cifUserInstance.createdBy = cifInstance.createdBy

		def userInstance = new User()
		def userDetailsInstance = new UserDetails()

		def passwordList = passwordGeneratorService.generateRegistrationCode()
		userInstance.username = pic.username
		userInstance.enabled = 1
		userInstance.password = springSecurityService.encodePassword(passwordList[0] + passwordList[1])
		userInstance.save(failOnError: true)

		def role = Role.findByAuthority(Common.ROLE_CORP_ADMIN)
		println "role = "+role
		if (role) {
			println "11111"
			def userRoleInstance = new UserRole()
			userRoleInstance.user = userInstance
			userRoleInstance.role = role
			userRoleInstance.save(failOnError: true)
			println "2222"
		}

		userDetailsInstance.dateCreated = new Date()
		userDetailsInstance.lastUpdated = new Date()
		userDetailsInstance.firstName = pic.firstName
		userDetailsInstance.lastName = pic.lastName
		userDetailsInstance.user = userInstance
		userDetailsInstance.userAlias = pic.userAlias
		userDetailsInstance.email = pic.email
		userDetailsInstance.mobilePhoneNo =  pic.mobilePhoneNo
		userDetailsInstance.status = '1'
		userDetailsInstance.forceChangePassword = true
		userDetailsInstance.createdBy = cifInstance.createdBy
		userDetailsInstance.userType = "2"

		if(userDetailsInstance.mobilePhoneNo == null){
			def request = org.codehaus.groovy.grails.plugins.springsecurity.SecurityRequestHolder.getRequest()
			def session = request.getSession(false)
			def locale = session[org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME]

			Object[] testArgs = new Object[1]
			testArgs[0] = messageSource.getMessage("cif.phoneNo.label", null, locale)
			throw new SecurityException(messageSource.getMessage("default.blank.message", testArgs, locale))
		}

//		String mobileNo = userDetailsInstance.mobilePhoneNo
//		String mobileNo2 = null
//		if(mobileNo.substring(0, 1).equals("0")){
//			mobileNo2 = "62" + mobileNo.substring(1)
//		}
//		if(mobileNo2 == null && mobileNo.substring(0, 2).equals("62")){
//			mobileNo2 = "0" + mobileNo.substring(2)
//		}
//		def countnya = UserDetails.executeQuery("SELECT count(ud) FROM UserDetails ud WHERE ud.deleteFlag = 'N' AND (ud.mobilePhoneNo = ? OR ud.mobilePhoneNo = ?)",[mobileNo, mobileNo2])[0]
//		if(countnya > 0){
//			def request = org.codehaus.groovy.grails.plugins.springsecurity.SecurityRequestHolder.getRequest()
//			def session = request.getSession(false)
//			def locale = session[org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME]
//
//			Object[] testArgs = new Object[1]
//			testArgs[0] = messageSource.getMessage("cif.phoneNo.label", null, locale)
//			throw new SecurityException(messageSource.getMessage("default.already.exist.message", testArgs, locale))
//		}
		userDetailsInstance.save(failOnError: true)

		cifUserInstance.userDetails = userDetailsInstance
		cifUserInstance.save(failOnError: true)

		return passwordList[0] + passwordList[1]
	}

	/**
	 * Register corporate
	 * @param cifInstance Cif Object
	 */
	def create(def cifInstance, def pic1, def pic2) {
		cifInstance.pic1 = pic1.username
		cifInstance.pic2 = pic2.username
		cifInstance.joinDate = new Date()
		cifInstance.save(failOnError: true)

		createUserPICPending(pic1, cifInstance)
		createUserPICPending(pic2, cifInstance)
	}

	def createNoPending(def cifInstance, def pic1, def pic2) {
		cifInstance.pic1 = pic1.username
		cifInstance.pic2 = pic2.username
		cifInstance.joinDate = new Date()
		cifInstance.status = Common.STATUS_ACTIVE
		cifInstance.save(failOnError: true)

		def password1 = createUserPIC(pic1, cifInstance)
		def password2 = createUserPIC(pic2, cifInstance)

		if (pic1.email) {
			cifService.emailPasswordBO(pic1.username, pic1.email, pic1.firstName + " " + pic1.lastName, password1)
		}
		if (pic2.email) {
			cifService.emailPasswordBO(pic2.username, pic2.email, pic2.firstName + " " + pic2.lastName, password2)
		}
	}

	def createPending(def cifInstance, def pic1, def pic2) {
		cifInstance.status = Common.PENDING_CREATE
		cifInstance.pending = "create"

		create(cifInstance, pic1, pic2)
		corporateAcitiviService.initiateCorporateManagement(cifInstance.id, cifInstance.corpName, "create", "cif", cifInstance.id)

		return cifInstance
	}

	/**
	 * Return corporate record
	 * @param params
	 * @return ArrayList containing List of cif objects and the total of cifs
	 */
	def list(def params) {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		params.properties = ["corpName", "cifId"]

		if (!params.sort && !params.order) {
			params.sort = "corpName"
			params.order = "asc"
		}

		def query = "-status:1 +deleteFlag:N"
		if (params.corporate) {
			query += " *${params.corporate}*"
		}

		def results = new ArrayList()
		def result = Cif.search(query, params)
		results.add(result.results)
		results.add(result.total)

		return results
	}

	/**
	 * Delete corporate by soft deletion
	 * @param cifInstance Cif Object
	 */
	def delete(def cifInstance, def username) {
		cifInstance.deleteFlag = "Y"
		cifInstance.updatedBy = username

		def cifUsers = CifUser.findAllByCifAndDeleteFlag(cifInstance, "N")
		cifUsers.each { corporateUserService.delete(it) }

		def groups = Grup.findAllByCifAndDeleteFlag(cifInstance, "N")
		groups.each { grupService.delete(it) }

		cifInstance.save(failOnError: true)
		return cifInstance
	}

	/**
	 * Update corporate
	 * @param cifInstance Cif Object
	 */	
	def update(def cifInstance, def username, def picc1, def picc2) {
		def pic1 = UserDetails.executeQuery("SELECT ud FROM UserDetails ud WHERE ud.user.username = ?",[cifInstance.pic1])[0]
		if(picc1.mobilePhoneNo == null){
			def request = org.codehaus.groovy.grails.plugins.springsecurity.SecurityRequestHolder.getRequest()
			def session = request.getSession(false)
			def locale = session[org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME]

			Object[] testArgs = new Object[1]
			testArgs[0] = messageSource.getMessage("cif.phoneNo.label", null, locale)
			throw new SecurityException(messageSource.getMessage("default.blank.message", testArgs, locale))
		}

//		String mobileNo = picc1.mobilePhoneNo
//		String mobileNo2 = null
//		if(mobileNo.substring(0, 1).equals("0")){
//			mobileNo2 = "62" + mobileNo.substring(1)
//		}
//		if(mobileNo2 == null && mobileNo.substring(0, 2).equals("62")){
//			mobileNo2 = "0" + mobileNo.substring(2)
//		}
//		def countnya = UserDetails.executeQuery("SELECT count(ud) FROM UserDetails ud WHERE ud.deleteFlag = 'N' AND (ud.mobilePhoneNo = ? OR ud.mobilePhoneNo = ?) AND ud.id != ?",[
//			mobileNo,
//			mobileNo2,
//			pic1.id
//		])[0]
//		if(countnya > 0){
//			def request = org.codehaus.groovy.grails.plugins.springsecurity.SecurityRequestHolder.getRequest()
//			def session = request.getSession(false)
//			def locale = session[org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME]
//
//			Object[] testArgs = new Object[1]
//			testArgs[0] = messageSource.getMessage("cif.phoneNo.label", null, locale)
//			throw new SecurityException(messageSource.getMessage("default.already.exist.message", testArgs, locale))
//		}

		def pic2 = UserDetails.executeQuery("SELECT ud FROM UserDetails ud WHERE ud.user.username = ?",[cifInstance.pic2])[0]
		if(picc2.mobilePhoneNo == null){
			def request = org.codehaus.groovy.grails.plugins.springsecurity.SecurityRequestHolder.getRequest()
			def session = request.getSession(false)
			def locale = session[org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME]

			Object[] testArgs = new Object[1]
			testArgs[0] = messageSource.getMessage("cif.phoneNo.label", null, locale)
			throw new SecurityException(messageSource.getMessage("default.blank.message", testArgs, locale))
		}

//		mobileNo = picc2.mobilePhoneNo
//		mobileNo2 = null
//		if(mobileNo.substring(0, 1).equals("0")){
//			mobileNo2 = "62" + mobileNo.substring(1)
//		}
//		if(mobileNo2 == null && mobileNo.substring(0, 2).equals("62")){
//			mobileNo2 = "0" + mobileNo.substring(2)
//		}
//		countnya = UserDetails.executeQuery("SELECT count(ud) FROM UserDetails ud WHERE ud.deleteFlag = 'N' AND (ud.mobilePhoneNo = ? OR ud.mobilePhoneNo = ?) AND ud.id != ?",[
//			mobileNo,
//			mobileNo2,
//			pic2.id
//		])[0]
//		if(countnya > 0){
//			def request = org.codehaus.groovy.grails.plugins.springsecurity.SecurityRequestHolder.getRequest()
//			def session = request.getSession(false)
//			def locale = session[org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME]
//
//			Object[] testArgs = new Object[1]
//			testArgs[0] = messageSource.getMessage("cif.phoneNo.label", null, locale)
//			throw new SecurityException(messageSource.getMessage("default.already.exist.message", testArgs, locale))
//		}

		cifInstance.updatedBy = username
		cifInstance.save(failOnError: true)

		def oldEmail = pic1.email
		pic1.properties = picc1
		pic1.updatedBy = cifInstance.updatedBy
		def newEmail = pic1.email
		if(pic1.activationKey != null){
			pic1.email = oldEmail
			def oldEmail2 = pic1.newEmail
			if (oldEmail2 != newEmail) {
				pic1.newEmail = newEmail
				corporateUserService.processChangingEmail(pic1)
			}
		}
		else{
			if (oldEmail != newEmail) {
				pic1.email = oldEmail
				pic1.newEmail = newEmail
				corporateUserService.processChangingEmail(pic1)
			}
		}
		pic1.save(failOnError: true)
		def cifuser = CifUser.findByUserDetails(pic1)
		cifuser.updatedBy = cifInstance.updatedBy
		cifuser.firstName = pic1.firstName
		cifuser.lastName = pic1.lastName
		cifuser.save(failOnError: true)

		oldEmail = pic2.email
		pic2.properties = picc2
		pic2.updatedBy = cifInstance.updatedBy
		newEmail = pic2.email
		if(pic2.activationKey != null){
			pic2.email = oldEmail
			def oldEmail2 = pic2.newEmail
			if (oldEmail2 != newEmail) {
				pic2.newEmail = newEmail
				corporateUserService.processChangingEmail(pic2)
			}
		}
		else{
			if (oldEmail != newEmail) {
				pic2.email = oldEmail
				pic2.newEmail = newEmail
				corporateUserService.processChangingEmail(pic2)
			}
		}
		pic2.save(failOnError: true)

		def cifuser2 = CifUser.findByUserDetails(pic2)
		cifuser2.updatedBy = cifInstance.updatedBy
		cifuser2.firstName = pic2.firstName
		cifuser2.lastName = pic2.lastName
		cifuser2.save(failOnError: true)

		cifuser.reindex()
		cifuser2.reindex()
	}
}
