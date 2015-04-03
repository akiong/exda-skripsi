package com.akiong


import org.codehaus.groovy.grails.commons.ConfigurationHolder

import com.akiong.app.cif.Cif
import com.akiong.app.cif.GroupMember
import com.akiong.app.cif.Grup
//import com.akiong.app.cif.MemberLimit
import com.akiong.security.Role
import com.akiong.security.UserDetails
import com.akiong.security.UserRole
import com.akiong.helper.Common

class GrupService {

	static transactional = true

	def corporateUserService
	def springSecurityService
	def passwordGeneratorService
	def cifService
	def messageSource
	def sequenceGeneratorService
	def virtualAccountService

	private static final int MAX_SEARCH = ConfigurationHolder.config.max.autocomplete

	/**
	 * Register group
	 * @param group, cifuser Object
	 */
	def create(def group, def cifUserInstance, def owner) {
		def passwordList
		def passwordList2
		def cif = corporateUserService.getCurrentCifUser().cif

		owner.sysAdmin = "N"
		def groupMember = new GroupMember()
		group.owner = owner
		group.leader = groupMember
		group.cif = cif
		owner.createdBy = group.createdBy
		cifUserInstance.createdBy = group.createdBy
		groupMember.position = "1"
		groupMember.createdBy = group.createdBy
		groupMember.cifUser = cifUserInstance
		groupMember.userLimit = cifUserInstance.userLimit
		groupMember.status = "4"

		def grr = Grup.findWhere(grupId: group.grupId, deleteFlag: "N")
		if(grr){
			def request = org.codehaus.groovy.grails.plugins.springsecurity.SecurityRequestHolder.getRequest()
			def session = request.getSession(false)
			def locale = session[org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME]

			Object[] testArgs = new Object[2]
			testArgs[0] = messageSource.getMessage("grup.label", null, locale)
			testArgs[1] = messageSource.getMessage("grup.grupId.label", null, locale)
			throw new SecurityException(messageSource.getMessage("default.duplicate.msg.label", testArgs, locale))
		}

		if(owner.accoutnNo == null){
			def request = org.codehaus.groovy.grails.plugins.springsecurity.SecurityRequestHolder.getRequest()
			def session = request.getSession(false)
			def locale = session[org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME]

			Object[] testArgs = new Object[1]
			testArgs[0] = messageSource.getMessage("account.no.label", null, locale)
			throw new SecurityException(messageSource.getMessage("default.blank.message", testArgs, locale))
		}
		if (owner.userDetails) {
			owner.userDetails.createdBy = owner.createdBy
			owner.userDetails.firstName = owner.firstName
			owner.userDetails.lastName = owner.lastName
			owner.userDetails.status = com.teravin.helper.Common.INACTIVE_USER_STATUS
			owner.userDetails.forceChangePassword = true
			owner.userDetails.userType = "2"

			if (owner.userDetails.user) {
				passwordList = passwordGeneratorService.generateRegistrationCode()
				owner.userDetails.user.password = springSecurityService.encodePassword(passwordList[0] + passwordList[1])
				owner.userDetails.user.enabled = 1
				owner.userDetails.user.save(failOnError: true)
			}

			if(owner.userDetails.mobilePhoneNo == null){
				def request = org.codehaus.groovy.grails.plugins.springsecurity.SecurityRequestHolder.getRequest()
				def session = request.getSession(false)
				def locale = session[org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME]

				Object[] testArgs = new Object[1]
				testArgs[0] = messageSource.getMessage("cif.phoneNo.label", null, locale)
				throw new SecurityException(messageSource.getMessage("default.blank.message", testArgs, locale))
			}

//			String mobileNo = owner.userDetails.mobilePhoneNo
//			String mobileNo2 = null
//			if(mobileNo.substring(0, 1).equals("0")){
//				mobileNo2 = "62" + mobileNo.substring(1)
//			}
//			if(mobileNo2 == null && mobileNo.substring(0, 2).equals("62")){
//				mobileNo2 = "0" + mobileNo.substring(2)
//			}
//			def countnya = UserDetails.executeQuery("SELECT count(ud) FROM UserDetails ud WHERE ud.deleteFlag = 'N' AND (ud.mobilePhoneNo = ? OR ud.mobilePhoneNo = ?)",[mobileNo, mobileNo2])[0]
//			if(countnya > 0){
//				def request = org.codehaus.groovy.grails.plugins.springsecurity.SecurityRequestHolder.getRequest()
//				def session = request.getSession(false)
//				def locale = session[org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME]
//
//				Object[] testArgs = new Object[1]
//				testArgs[0] = messageSource.getMessage("cif.phoneNo.label", null, locale)
//				throw new SecurityException(messageSource.getMessage("default.already.exist.message", testArgs, locale))
//			}

			def roleGroupOwner = Role.findByAuthority(Common.ROLE_GROUP_OWNER)
			if (roleGroupOwner) {
				new UserRole( user: owner.userDetails.user, role: roleGroupOwner ).save(failOnError: true)
			}
			owner.userDetails.save(failOnError: true)
		}
		if (cifUserInstance.userDetails) {
			cifUserInstance.userDetails.createdBy = cifUserInstance.createdBy
			cifUserInstance.userDetails.firstName = cifUserInstance.firstName
			cifUserInstance.userDetails.lastName = cifUserInstance.lastName
			cifUserInstance.userDetails.status = com.teravin.helper.Common.INACTIVE_USER_STATUS
			cifUserInstance.userDetails.forceChangePassword = true
			cifUserInstance.userDetails.userType = "2"

			if (cifUserInstance.userDetails.user) {
				passwordList2 = passwordGeneratorService.generateRegistrationCode()
				cifUserInstance.userDetails.user.password = springSecurityService.encodePassword(passwordList2[0] + passwordList2[1])
				cifUserInstance.userDetails.user.enabled = 1
				cifUserInstance.userDetails.user.save(failOnError: true)
			}

			if(cifUserInstance.userDetails.mobilePhoneNo == null){
				def request = org.codehaus.groovy.grails.plugins.springsecurity.SecurityRequestHolder.getRequest()
				def session = request.getSession(false)
				def locale = session[org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME]

				Object[] testArgs = new Object[1]
				testArgs[0] = messageSource.getMessage("cif.phoneNo.label", null, locale)
				throw new SecurityException(messageSource.getMessage("default.blank.message", testArgs, locale))
			}

//			String mobileNo = cifUserInstance.userDetails.mobilePhoneNo
//			String mobileNo2 = null
//			if(mobileNo.substring(0, 1).equals("0")){
//				mobileNo2 = "62" + mobileNo.substring(1)
//			}
//			if(mobileNo2 == null && mobileNo.substring(0, 2).equals("62")){
//				mobileNo2 = "0" + mobileNo.substring(2)
//			}
//			def countnya = UserDetails.executeQuery("SELECT count(ud) FROM UserDetails ud WHERE ud.deleteFlag = 'N' AND (ud.mobilePhoneNo = ? OR ud.mobilePhoneNo = ?)",[mobileNo, mobileNo2])[0]
//			if(countnya > 0){
//				def request = org.codehaus.groovy.grails.plugins.springsecurity.SecurityRequestHolder.getRequest()
//				def session = request.getSession(false)
//				def locale = session[org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME]
//
//				Object[] testArgs = new Object[1]
//				testArgs[0] = messageSource.getMessage("cif.phoneNo.label", null, locale)
//				throw new SecurityException(messageSource.getMessage("default.already.exist.message", testArgs, locale))
//			}

			def roleGroupLeader = Role.findByAuthority(Common.ROLE_GROUP_LEADER)
			if (roleGroupLeader) {
				new UserRole( user: cifUserInstance.userDetails.user, role: roleGroupLeader ).save(failOnError: true)
			}
			cifUserInstance.userDetails.save(failOnError: true)
		}

		owner.cif = cif
		owner.save(failOnError: true)

		cifUserInstance.cif = cif
		cifUserInstance.save(failOnError: true)

//		def limit = new MemberLimit()
//		limit.userLimit = new BigDecimal(0)
//		limit.outstandingLimit = new BigDecimal(0)
//		limit.createdBy = group.createdBy
//		limit.save(failOnError: true)

//		groupMember.memberLimit = limit
		groupMember.save(failOnError: true)
		group.save(failOnError: true)

		groupMember.group = group
		groupMember.save(failOnError: true)

		if (owner.userDetails.email) {
			cifService.emailPasswordBO(owner.userDetails.user.username, owner.userDetails.email, owner.userDetails.firstName + " " + owner.userDetails.lastName, passwordList[0] + passwordList[1])
		}
		if (cifUserInstance.userDetails.email) {
			cifService.emailPasswordBO(cifUserInstance.userDetails.user.username, cifUserInstance.userDetails.email, cifUserInstance.userDetails.firstName + " " + cifUserInstance.userDetails.lastName, passwordList2[0] + passwordList2[1])
		}
	}

	def update(def group, def oldEmail) {
		if(group.owner.accoutnNo == null){
			def request = org.codehaus.groovy.grails.plugins.springsecurity.SecurityRequestHolder.getRequest()
			def session = request.getSession(false)
			def locale = session[org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME]

			Object[] testArgs = new Object[1]
			testArgs[0] = messageSource.getMessage("account.no.label", null, locale)
			throw new SecurityException(messageSource.getMessage("default.blank.message", testArgs, locale))
		}

		if(group.owner.userDetails.mobilePhoneNo == null){
			def request = org.codehaus.groovy.grails.plugins.springsecurity.SecurityRequestHolder.getRequest()
			def session = request.getSession(false)
			def locale = session[org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME]

			Object[] testArgs = new Object[1]
			testArgs[0] = messageSource.getMessage("cif.phoneNo.label", null, locale)
			throw new SecurityException(messageSource.getMessage("default.blank.message", testArgs, locale))
		}

//		String mobileNo = group.owner.userDetails.mobilePhoneNo
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
//			group.owner.userDetails.id
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

		group.owner.save(failOnError: true)
		group.save(failOnError: true)


		String newEmail = group.owner.userDetails.email
		if(group.owner.userDetails.activationKey != null){
			group.owner.userDetails.email = oldEmail
			def oldEmail2 = group.owner.userDetails.newEmail
			if (oldEmail2 != newEmail) {
				group.owner.userDetails.newEmail = newEmail
				corporateUserService.processChangingEmail(group.owner.userDetails)
			}
		}
		else{
			if (oldEmail != newEmail) {
				group.owner.userDetails.email = oldEmail
				group.owner.userDetails.newEmail = newEmail
				corporateUserService.processChangingEmail(group.owner.userDetails)
			}
		}

		return group
	}

	/**
	 * Return corporate record
	 * @param params
	 * @return ArrayList containing List of cif objects and the total of cifs
	 */
	def list(def params) {
		def cif = corporateUserService.getCurrentCifUser().cif

		params.max = Math.min(params.max ? params.int('max') : 10, 100)

		if (!params.sort && !params.order) {
			params.sort = "name"
			params.order = "asc"
		}

		def results = new ArrayList()
		def result = Grup.createCriteria().list(params) {
			eq("cif", cif)
			eq("deleteFlag", "N")
			if (params.grupId) {
				ilike("grupId", '%' + params.grupId + '%')
			}
			if (params.name) {
				ilike("name", '%' + params.name + '%')
			}
		}

		results.add(result)
		results.add(result.totalCount)

		return results
	}

	/**
	 * Return corporate record
	 * @param params
	 * @return ArrayList containing List of cif objects and the total of cifs
	 */
	def list2(def params) {
		def cif = Cif.get(params.id)
		if(cif == null){
			return null
		}

		params.max = Math.min(params.max ? params.int('max') : 10, 100)

		if (!params.sort && !params.order) {
			params.sort = "name"
			params.order = "asc"
		}

		def results = new ArrayList()
		def result = Grup.createCriteria().list(params) {
			eq("cif", cif)
			eq("deleteFlag", "N")
			if (params.grupId) {
				ilike("grupId", '%' + params.grupId + '%')
			}
			if (params.name) {
				ilike("name", '%' + params.name + '%')
			}
		}

		results.add(result)
		results.add(result.totalCount)
		results.add(cif)

		return results
	}

	def switchEnable(def group){
		if(group.status.equals("E")){
			group.status = "D"
		}
		else{
			group.status = "E"
		}
		group.save(failOnError: true)
	}

	def getGroupLike(def name) {
		def params = [:]
		def cif = corporateUserService.getCurrentCifUser().cif

		params.max = MAX_SEARCH
		params.properties = ["name"]

		if (!params.sort && !params.order) {
			params.sort = "sortableCorpName"
			params.order = "asc"
		}

		def query = "+deleteFlag:N +Grup.cif.id:" + cif.id
		if (name) {
			query += " *${name}*"
		}

		return Grup.search(query, params).results
	}

	def getActiveGroupLike(def name) {
		def params = [:]
		def cif = corporateUserService.getCurrentCifUser().cif

		params.max = MAX_SEARCH
		params.properties = ["name"]

		if (!params.sort && !params.order) {
			params.sort = "sortableCorpName"
			params.order = "asc"
		}

		def query = "+deleteFlag:N +Grup.status:E +Grup.cif.id:" + cif.id
		if (name) {
			query += " *${name}*"
		}

		return Grup.search(query, params).results
	}

	def delete(def group){
		group.deleteFlag = "Y"
		group.save(failOnError: true)

		def members = GroupMember.findAllByGroupAndDeleteFlag(group, "N")
		members.each {
			it.deleteFlag = "Y"
			it.updatedBy = group.updatedBy
			it.save(failOnError: true)
			it.reindex()
		}
	}
}
