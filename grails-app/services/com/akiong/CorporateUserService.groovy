package com.akiong

import java.text.SimpleDateFormat

import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import com.akiong.app.cif.Cif
import com.akiong.app.cif.CifUser
import com.akiong.app.cif.GroupMember
//import com.akiong.app.cif.MemberLimit
import com.akiong.security.Role
import com.akiong.security.User
import com.akiong.security.UserDetails
import com.akiong.security.UserRole
import com.akiong.helper.Common

class CorporateUserService {

	static transactional = true

	def springSecurityService
	def cifService
	//	def vpinService
	def passwordGeneratorService
	def messageSource
	//	def tokenService
	//	def otpService
	def corporateAcitiviService
	def sequenceGeneratorService
	//	def activitiService
	def virtualAccountService


	private static final Logger logger = LoggerFactory.getLogger(this)
	private static final int MAX_SEARCH = ConfigurationHolder.config.max.autocomplete


	def create(def cifUserInstance) {
		cifUserInstance.sysAdmin = "Y"
		def passwordList

		if (cifUserInstance.userDetails) {
			cifUserInstance.userDetails.mobilePhoneNo = cifUserInstance.userDetails.mobilePhoneNo

			cifUserInstance.userDetails.createdBy = cifUserInstance.createdBy
			cifUserInstance.userDetails.firstName = cifUserInstance.firstName
			cifUserInstance.userDetails.lastName = cifUserInstance.lastName
			cifUserInstance.userDetails.status = com.teravin.helper.Common.INACTIVE_USER_STATUS
			cifUserInstance.userDetails.forceChangePassword = true
			cifUserInstance.userDetails.userType = "2"

			if (cifUserInstance.userDetails.user) {
				passwordList = passwordGeneratorService.generateRegistrationCode()
				if (passwordList != null && passwordList.size() > 1) {
					cifUserInstance.userDetails.user.password = springSecurityService.encodePassword(passwordList[0] + passwordList[1])
					cifUserInstance.userDetails.user.enabled = 1
				}
				cifUserInstance.userDetails.user.save(failOnError: true)
			}

			if (!cifUserInstance.userDetails.mobilePhoneNo) {
				throw new MissingResourceException("Phone no cannot blank", "UserDetails Table", "Mobile Phone")
			}

			def users = cifUserInstance.userDetails.user
			if(cifUserInstance.bo.equals("Y")){
				def roleCoSysAdmin = Role.findByAuthority(Common.ROLE_CORP_ADMIN)
				if (roleCoSysAdmin) {
					if (!users.authorities.contains(roleCoSysAdmin)) {
						UserRole.create(users, roleCoSysAdmin)
					}
				}
			}

			if(cifUserInstance.finance.equals("Y")){
				def roleCoFinance = Role.findByAuthority(Common.ROLE_CORP_FINANCE)
				if (roleCoFinance) {
					if (!users.authorities.contains(roleCoFinance)) {
						UserRole.create(users, roleCoFinance)
					}
				}
			}

			cifUserInstance.userDetails.save(failOnError: true)
		}

		def cifInstance = getCurrentCifUser().cif
		cifUserInstance.cif = cifInstance
		cifUserInstance.save(failOnError: true)

		if (cifUserInstance.userDetails.email) {
			cifService.emailPasswordBO(cifUserInstance.userDetails.user.username, cifUserInstance.userDetails.email, cifUserInstance.userDetails.firstName + " " + cifUserInstance.userDetails.lastName, passwordList[0] + passwordList[1])
		}

		return cifUserInstance
	}


	//	def create(def cifUserInstance, def cifInstance, def roleApproval, def amount) {
	//		def passwordList
	//
	//		def request = org.codehaus.groovy.grails.plugins.springsecurity.SecurityRequestHolder.getRequest()
	//		def session = request.getSession(false)
	//		def locale = session[org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME]
	//
	//		if (cifUserInstance.userDetails) {
	//			cifUserInstance.userDetails.createdBy = cifUserInstance.createdBy
	//			cifUserInstance.userDetails.firstName = cifUserInstance.firstName
	//			cifUserInstance.userDetails.lastName = cifUserInstance.lastName
	//			cifUserInstance.userDetails.status = INACTIVE_USER_STATUS
	//			cifUserInstance.userDetails.password2 = vpinService.generateRandomEncryptedVpin()
	//			cifUserInstance.userDetails.forceChangePassword = true
	//			cifUserInstance.userDetails.userType = CIF_USER_TYPE
	//
	//			if (cifUserInstance.userDetails.user) {
	//				passwordList = passwordGeneratorService.generateRegistrationCode()
	//				if (passwordList != null && passwordList.size() > 1) {
	//					cifUserInstance.userDetails.user.password = springSecurityService.encodePassword(passwordList[0] + passwordList[1])
	//					cifUserInstance.userDetails.user.enabled = 1
	//				}
	//				cifUserInstance.userDetails.user.save(failOnError: true)
	//			}
	//
	//			if (!cifUserInstance.userDetails.mobilePhoneNo) {
	//				throw new MissingResourceException("Phone no cannot blank", "UserDetails Table", "Mobile Phone")
	//			}
	//
	//			cifUserInstance.userDetails.save(failOnError: true)
	//		}
	//		cifUserInstance.cif = cifInstance
	//		cifUserInstance.save(failOnError: true)
	//
	//		def cifUserLimitInstance
	//		def transactionMapping
	//		def roleUserPending
	//		def limit
	//
	//		def users = cifUserInstance.userDetails.user
	//		def roleUser = Role.findByAuthority(ROLE_CO_USER)
	//		UserRole.create(users, roleUser)
	//
	//		if(cifUserInstance.sysAdmin){
	//			def roleCoSysAdmin = Role.findByAuthority(ROLE_CO_SYS_ADMIN)
	//			if (roleCoSysAdmin) {
	//				if (!users.authorities.contains(roleCoSysAdmin)) {
	//					UserRole.create(users, roleCoSysAdmin)
	//				}
	//			}
	//		}
	//
	//		def chargeDetails = CifChargePackageDetails.executeQuery("select cd from CifChargePackageDetails cd, CifChargePackage c, CifServicePackage s where s.cif = ? and s.deleteFlag = 'N' and c.cifServicePackage = s and cd.cifChargePackage = c", [cifInstance])
	//		roleApproval.each { key, value ->
	//			transactionMapping = TransactionMapping.get(key as Integer)
	//
	//			value.each {
	//				cifUserLimitInstance = new CifUserLimit()
	//				cifUserLimitInstance.cifUser = cifUserInstance
	//				cifUserLimitInstance.status = ACTIVE
	//				cifUserLimitInstance.createdBy = cifUserInstance.createdBy
	//				limit = new BigDecimal(amount.get(transactionMapping.id + "_" + it) ?: 0)
	//				cifUserLimitInstance.limitAmount = limit
	//				cifUserLimitInstance.roleApproval = it
	//				cifUserLimitInstance.transactionMapping = transactionMapping
	//				cifUserLimitInstance.ccy = chargeDetails.find {it.transactionCode == transactionMapping.transactionCode}?.ccy
	//				cifUserLimitInstance.save(failOnError: true)
	//
	//				roleUserPending = addUserRole(it, roleUserPending, users, transactionMapping.role)
	//			}
	//		}
	//
	//		def vpinType = AuthenticationType.findByName(AUTH_TYPE_VPIN)
	//		if (vpinType) {
	//			new AuthenticationDetails(userDetails: cifUserInstance.userDetails, authenticationType: vpinType, createdBy: springSecurityService.principal.username).save(failOnError: true)
	//		}
	//
	//		if (cifUserInstance.userDetails.activateToken) {
	//			try {
	//				tokenService.registration(cifUserInstance.userDetails.user.username, cifUserInstance.userDetails.mobilePhoneNo)
	//			}
	//			catch (RuntimeException e) {
	//				logger.error(e.getMessage(), e)
	//				throw new SecurityException(messageSource.getMessage("token.registration.error.msg", null, locale))
	//			}
	//		}
	//
	//		if (cifUserInstance.viewInquiry.equals("Y")) {
	//			roleUser = Role.findByAuthority(ROLE_CO_INQ)
	//			if (roleUser && !users.authorities.contains(roleUser)) {
	//				UserRole.create(users, roleUser)
	//			}
	//		}
	//
	//		if (cifUserInstance.viewPayrollDetail.equals("Y")) {
	//			roleUser = Role.findByAuthority(ROLE_CO_PAYROLL)
	//			if (roleUser && !users.authorities.contains(roleUser)) {
	//				UserRole.create(users, roleUser)
	//			}
	//		}
	//
	//		if (cifUserInstance.viewReportCorp.equals("Y")) {
	//			[
	//				ROLE_FO_REPORT_USER,
	//				ROLE_FO_REPORT_CORP_PROFILE
	//			].each{
	//				roleUser = Role.findByAuthority(it)
	//				if (roleUser && !users.authorities.contains(roleUser)) {
	//					UserRole.create(users, roleUser)
	//				}
	//			}
	//		}
	//
	//		if (cifUserInstance.viewReportTrx.equals("Y")) {
	//			[
	//				ROLE_FO_REPORT_TRX,
	//				ROLE_FO_REPORT_STANDING_TRX,
	//				ROLE_FO_REPORT_BULK_TRX,
	//				ROLE_FO_ADVICE_PRINTING
	//			].each{
	//				roleUser = Role.findByAuthority(it)
	//				if (roleUser && !users.authorities.contains(roleUser)) {
	//					UserRole.create(users, roleUser)
	//				}
	//			}
	//		}
	//
	//		if (cifUserInstance.userDetails.email) {
	//			if (ConfigurationHolder.config.netbank.send.sms) {
	//				cifService.regEmail(users.username, cifUserInstance.userDetails.email, cifUserInstance.userDetails.firstName + " " + cifUserInstance.userDetails.lastName, passwordList[0], EncryptionUtil.decrypt(cifUserInstance.userDetails.password2).substring(0, 4))
	//			}
	//			else {
	//				cifService.regEmail(users.username, cifUserInstance.userDetails.email, cifUserInstance.userDetails.firstName + " " + cifUserInstance.userDetails.lastName, passwordList[0] + passwordList[1], EncryptionUtil.decrypt(cifUserInstance.userDetails.password2))
	//			}
	//		}
	//		if (cifUserInstance.userDetails.mobilePhoneNo) {
	//			if (ConfigurationHolder.config.netbank.send.sms) {
	//				cifService.regSms(cifUserInstance.userDetails.mobilePhoneNo, cifUserInstance.userDetails.firstName + " " + cifUserInstance.userDetails.lastName, passwordList[1], EncryptionUtil.decrypt(cifUserInstance.userDetails.password2).substring(4, 8))
	//			}
	//		}
	//
	//		return cifUserInstance
	//	}
	//
	//	private def createPendingUser(def cifUserInstance, def cifInstance, def roleApproval, def amount) {
	//		cifUserInstance.cif = cifInstance
	//		cifUserInstance.status = PENDING_CREATE
	//		def passwordList
	//
	//		def request = org.codehaus.groovy.grails.plugins.springsecurity.SecurityRequestHolder.getRequest()
	//		def session = request.getSession(false)
	//		def locale = session[org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME]
	//
	//		if (cifUserInstance.userDetails) {
	//			cifUserInstance.userDetails.createdBy = cifUserInstance.createdBy
	//			cifUserInstance.userDetails.firstName = cifUserInstance.firstName
	//			cifUserInstance.userDetails.lastName = cifUserInstance.lastName
	//			cifUserInstance.userDetails.status = INACTIVE_USER_STATUS
	//			cifUserInstance.userDetails.password2 = vpinService.generateRandomEncryptedVpin()
	//			cifUserInstance.userDetails.forceChangePassword = true
	//			cifUserInstance.userDetails.userType = CIF_USER_TYPE
	//
	//			if (cifUserInstance.userDetails.user) {
	//				passwordList = passwordGeneratorService.generateRegistrationCode()
	//				if (passwordList != null && passwordList.size() > 1) {
	//					def mod = [:]
	//					mod["passwordList1"] = EncryptionUtil.encrypt(passwordList[0]);
	//					mod["passwordList2"] = EncryptionUtil.encrypt(passwordList[1]);
	//					cifUserInstance.pending = (mod as JSON) as String
	//					cifUserInstance.userDetails.user.password = springSecurityService.encodePassword(passwordList[0] + passwordList[1])
	//					cifUserInstance.userDetails.user.enabled = 0
	//				}
	//				cifUserInstance.userDetails.user.save(failOnError: true)
	//			}
	//
	//			if (!cifUserInstance.userDetails.mobilePhoneNo) {
	//				throw new MissingResourceException("Phone no cannot blank", "UserDetails Table", "Mobile Phone")
	//			}
	//
	//			cifUserInstance.userDetails.save(failOnError: true)
	//		}
	//		cifUserInstance.save(failOnError: true)
	//
	//		def cifUserLimitInstance
	//		def transactionMapping
	//		def roleUserPending
	//		def limit
	//
	//		def users = cifUserInstance.userDetails.user
	//		def roleUser = Role.findByAuthority(ROLE_CO_USER)
	//		UserRole.create(users, roleUser)
	//		if(cifUserInstance.viewInquiry.equals("Y")){
	//			roleUser = Role.findByAuthority(ROLE_CO_INQ)
	//			UserRole.create(users, roleUser)
	//		}
	//
	//		if(cifUserInstance.sysAdmin){
	//			def roleCoSysAdmin = Role.findByAuthority(ROLE_CO_SYS_ADMIN)
	//			if (roleCoSysAdmin) {
	//				if (!users.authorities.contains(roleCoSysAdmin)) {
	//					UserRole.create(users, roleCoSysAdmin)
	//				}
	//			}
	//		}
	//
	//		if (cifUserInstance.viewPayrollDetail.equals("Y")) {
	//			roleUser = Role.findByAuthority(ROLE_CO_PAYROLL)
	//			if (roleUser && !users.authorities.contains(roleUser)) {
	//				UserRole.create(users, roleUser)
	//			}
	//		}
	//
	//		if (cifUserInstance.viewReportCorp.equals("Y")) {
	//			[
	//				ROLE_FO_REPORT_USER,
	//				ROLE_FO_REPORT_CORP_PROFILE
	//			].each{
	//				roleUser = Role.findByAuthority(it)
	//				if (roleUser && !users.authorities.contains(roleUser)) {
	//					UserRole.create(users, roleUser)
	//				}
	//			}
	//		}
	//
	//		if (cifUserInstance.viewReportTrx.equals("Y")) {
	//			[
	//				ROLE_FO_REPORT_TRX,
	//				ROLE_FO_REPORT_STANDING_TRX,
	//				ROLE_FO_REPORT_BULK_TRX,
	//				ROLE_FO_ADVICE_PRINTING
	//			].each{
	//				roleUser = Role.findByAuthority(it)
	//				if (roleUser && !users.authorities.contains(roleUser)) {
	//					UserRole.create(users, roleUser)
	//				}
	//			}
	//		}
	//
	//		def chargeDetails = CifChargePackageDetails.executeQuery("select cd from CifChargePackageDetails cd, CifChargePackage c, CifServicePackage s where s.cif = ? and s.deleteFlag = 'N' and c.cifServicePackage = s and cd.cifChargePackage = c", [cifInstance])
	//		roleApproval.each { key, value ->
	//			transactionMapping = TransactionMapping.get(key as Integer)
	//
	//			value.each {
	//				cifUserLimitInstance = new CifUserLimit()
	//				cifUserLimitInstance.cifUser = cifUserInstance
	//				cifUserLimitInstance.status = ACTIVE
	//				cifUserLimitInstance.createdBy = cifUserInstance.createdBy
	//				limit = new BigDecimal(amount.get(transactionMapping.id + "_" + it) ?: 0)
	//				cifUserLimitInstance.limitAmount = limit
	//				cifUserLimitInstance.roleApproval = it
	//				cifUserLimitInstance.transactionMapping = transactionMapping
	//				cifUserLimitInstance.ccy = chargeDetails.find {it.transactionCode == transactionMapping.transactionCode}?.ccy
	//				cifUserLimitInstance.save(failOnError: true)
	//
	//				roleUserPending = addUserRole(it, roleUserPending, users, transactionMapping.role)
	//			}
	//		}
	//
	//		def vpinType = AuthenticationType.findByName(AUTH_TYPE_VPIN)
	//		if (vpinType) {
	//			new AuthenticationDetails(userDetails: cifUserInstance.userDetails, authenticationType: vpinType, createdBy: springSecurityService.principal.username).save(failOnError: true)
	//		}
	//
	//		return cifUserInstance
	//	}
	//
	//	def createPending(def cifUserInstance, def cifInstance, def roleApproval, def amount) {
	//		cifUserInstance = createPendingUser(cifUserInstance, cifInstance, roleApproval, amount)
	//		corporateAcitiviService.initiateCorporateManagement(cifUserInstance.id, cifUserInstance.cif.firstName + " " +cifUserInstance.cif.lastName, "create", "cifUser", cifUserInstance.cif.id)
	//
	//		return cifUserInstance
	//	}
	//
	//	def createPending2(def cifUserInstance, def cifInstance, def roleApproval, def amount) {
	//		cifUserInstance = createPendingUser(cifUserInstance, cifInstance, roleApproval, amount)
	//		corporateAcitiviService.initiateCorporateManagement2(cifUserInstance.id, cifUserInstance.cif.firstName + " " +cifUserInstance.cif.lastName, "create", "cifUser", cifUserInstance.cif.id)
	//
	//		return cifUserInstance
	//	}
	//
	//	private def addUserRole(def role, def roleUserPending, def users, def serviceRole) {
	//		if (role.equals(MAKER)) {
	//			def roleMaker = Role.findByAuthority(serviceRole)
	//			if (roleMaker) {
	//				if (!users.authorities.contains(roleMaker)) {
	//					UserRole.create(users, roleMaker)
	//				}
	//			}
	//		}
	//		else {
	//			if (!roleUserPending) {
	//				roleUserPending = Role.findByAuthority(ROLE_CO_PENDING_TASK)
	//				if (roleUserPending) {
	//					if (!users.authorities.contains(roleUserPending)) {
	//						UserRole.create(users, roleUserPending)
	//					}
	//				}
	//			}
	//		}
	//
	//		return roleUserPending
	//	}

	def getActivationKey(username, email) {
		def emptyList = []
		emptyList.add(username)
		emptyList.add(email)

		Date currentDate = new Date()
		SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy hh:mm:ss")
		String timefactor = formatter.format(currentDate)

		emptyList.add(timefactor)

		return emptyList
	}

	def processChangingEmail(def pic) {
		pic.status = Common.INACTIVE_USER_STATUS
		pic.user.enabled = false

		/* generate the activate_key*/
		def factorList, activate_key, code
		factorList = getActivationKey(pic.user?.username, pic.newEmail)
		code = passwordGeneratorService.generateRegistrationCode()
		activate_key = code.get(0) + code.get(1)
		pic.activationKey = activate_key

		def request = org.codehaus.groovy.grails.plugins.springsecurity.SecurityRequestHolder.getRequest()
		def contextPath = request.getContextPath() + "/"
		def urlPath = request.getRequestURL()
		def index = urlPath.indexOf(contextPath)
		if (index > 0) {
			urlPath = urlPath.substring(0, index)
		}
		def tagLib = new org.codehaus.groovy.grails.plugins.web.taglib.ApplicationTagLib()
		urlPath += tagLib.createLink(controller: 'activation', action: 'index')

		def locale
		if(pic.language){
			locale= new Locale(pic.language)
		}
		else{
			locale= new Locale(Common.DEFAULT_LANGUAGE)
		}

		def emailTitle = messageSource.getMessage("activation.new.email.title", null, locale)

		StringBuffer contentSB = new StringBuffer();
		contentSB.append("Dear ").append(pic.firstName).append(" ").append(pic.lastName).append(",\n\n").append(messageSource.getMessage("activation.new.email.msg1", null, locale)).append("\n").append(messageSource.getMessage("activation.new.email.msg2", null, locale)).append(" ").append(urlPath).append("?id=").append(pic.user.id).append("&activationKey=").append(activate_key)

		try {
			sendQueueJMSMessage("queue.sendEmailCC", [emailTo: pic.newEmail, emailSubject: emailTitle, content: contentSB.toString()])
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e)
		}
	}

	//	def processChangingMobile(def cifUserInstance, oldMobileNo) {
	//		def request = org.codehaus.groovy.grails.plugins.springsecurity.SecurityRequestHolder.getRequest()
	//
	//		def contextPath = request.getContextPath() + "/"
	//		def urlPath = request.getRequestURL()
	//		def index = urlPath.indexOf(contextPath)
	//		if (index > 0) {
	//			urlPath = urlPath.substring(0, index)
	//		}
	//		def tagLib = new org.codehaus.groovy.grails.plugins.web.taglib.ApplicationTagLib()
	//		urlPath += tagLib.createLink(controller: 'login', action: 'index')
	//
	//		def locale
	//		if(cifUserInstance.userDetails?.language){
	//			locale= new Locale(cifUserInstance.userDetails?.language)
	//		}
	//		else{
	//			locale= new Locale(DEFAULT_LANGUAGE)
	//		}
	//
	//		def emailTitle = messageSource.getMessage("activation.new.mobile.title", null, locale)
	//
	//		StringBuffer contentSB = new StringBuffer();
	//		contentSB.append("Dear ").append(cifUserInstance.userDetails?.firstName).append(" ").append(cifUserInstance.userDetails?.lastName).append(",\n\n").append(messageSource.getMessage("activation.new.mobile.msg1", null, locale)).append("\n").append(messageSource.getMessage("activation.new.mobile.msg2", null, locale)).append("\n\n").append(messageSource.getMessage("activation.new.mobile.msg3", null, locale)).append("(").append(urlPath).append(")").append("\n").append(messageSource.getMessage("activation.new.mobile.msg4", null, locale))
	//
	//		try {
	//			sendQueueJMSMessage("queue.sendEmail", [emailTo: cifUserInstance.userDetails?.email, emailSubject: emailTitle, content: contentSB.toString()])
	//		}
	//		catch (Exception e) {
	//			logger.error(e.getMessage(), e)
	//		}
	//
	//		tokenService.changeMobileNo(cifUserInstance.userDetails?.user.username, oldMobileNo, cifUserInstance.userDetails?.mobilePhoneNo)
	//	}

	def update(def cifUserInstance, def oldEmail) {

		def newEmail = cifUserInstance.userDetails?.email
		if(!(newEmail ==~ "[a-zA-Z0-9._%+-]+@(?:[a-zA-Z0-9-]+.)+[a-zA-Z]{2,4}")){
			throw new MissingResourceException("Email is not valid", "UserDetails Table", "Email is not valid")
		}

		if (cifUserInstance.userDetails) {
			cifUserInstance.userDetails.updatedBy = cifUserInstance.updatedBy
			cifUserInstance.userDetails.firstName = cifUserInstance.firstName
			cifUserInstance.userDetails.lastName = cifUserInstance.lastName
			cifUserInstance.userDetails.lastUpdated = new Date()

			if (!cifUserInstance.userDetails.mobilePhoneNo) {
				throw new MissingResourceException("Phone no cannot blank", "UserDetails Table", "Mobile Phone")
			}
			cifUserInstance.userDetails.save(failOnError: true)
		}
		cifUserInstance.lastUpdated = new Date()
		cifUserInstance.save(failOnError: true)

		if(cifUserInstance.userDetails.activationKey != null){
			cifUserInstance.userDetails.email = oldEmail
			def oldEmail2 = cifUserInstance.userDetails.newEmail
			if (oldEmail2 != newEmail) {
				cifUserInstance.userDetails.newEmail = newEmail
				processChangingEmail(cifUserInstance)
			}
		}
		else{
			if (oldEmail != newEmail) {
				cifUserInstance.userDetails.email = oldEmail
				cifUserInstance.userDetails.newEmail = newEmail
				processChangingEmail(cifUserInstance)
			}
		}


		def users = cifUserInstance.userDetails.user
		def roleCoSysAdmin = Role.findByAuthority(Common.ROLE_CORP_ADMIN)
		if (roleCoSysAdmin) {
			if(cifUserInstance.bo.equals("Y")){
				if (!users.authorities.contains(roleCoSysAdmin)) {
					UserRole.create(users, roleCoSysAdmin)
				}
			}
			else{
				if (users.authorities.contains(roleCoSysAdmin)) {
					UserRole.remove(users, roleCoSysAdmin)
				}
			}
		}

		def roleCoFinance = Role.findByAuthority(Common.ROLE_CORP_FINANCE)
		if (roleCoFinance) {
			if(cifUserInstance.finance.equals("Y")){
				if (!users.authorities.contains(roleCoFinance)) {
					UserRole.create(users, roleCoFinance)
				}
			}
			else{
				if (users.authorities.contains(roleCoFinance)) {
					UserRole.remove(users, roleCoFinance)
				}
			}
		}

		cifUserInstance.reindex()
		return cifUserInstance
	}

	//	private def updatePendingUser(def cifUserInstance, def roleApproval, def amount, def username){
	//		if (!cifUserInstance.userDetails.mobilePhoneNo) {
	//			throw new MissingResourceException("Phone no cannot blank", "UserDetails Table", "Mobile Phone")
	//		}
	//
	//		def modifiedFieldNames = cifUserInstance.getDirtyPropertyNames()
	//		def mod = [:]
	//		for (fieldName in modifiedFieldNames) {
	//			def currentValue = cifUserInstance."$fieldName"
	//			mod["$fieldName"] = currentValue
	//		}
	//
	//		modifiedFieldNames = cifUserInstance.userDetails.getDirtyPropertyNames()
	//		for (fieldName in modifiedFieldNames) {
	//			def currentValue = cifUserInstance.userDetails."$fieldName"
	//			mod["$fieldName"] = currentValue
	//		}
	//
	//		def transactionMapping
	//		def roleUserPending, roleMaker
	//		def limit
	//		def changes = false
	//
	//		def cifUserLimitInstance = CifUserLimit.findAllByCifUserAndDeleteFlag(cifUserInstance, "N")
	//		def cifUserLimitIds = cifUserLimitInstance*.id
	//		def isHavingRolePending = false
	//		def objUpdate
	//
	//		def chargeDetails = CifChargePackageDetails.executeQuery("select cd from CifChargePackageDetails cd, CifChargePackage c, CifServicePackage s where s.cif = ? and s.deleteFlag = 'N' and c.cifServicePackage = s and cd.cifChargePackage = c", [cifUserInstance.cif])
	//		roleApproval.each { key, value ->
	//			transactionMapping = TransactionMapping.get(key as Integer)
	//			value.each { newIt ->
	//				objUpdate = cifUserLimitInstance.find {it.roleApproval == newIt && it.transactionMapping == transactionMapping}
	//				if (!objUpdate) {
	//					changes = true
	//					objUpdate = new CifUserLimit()
	//					objUpdate.cifUser = cifUserInstance
	//					objUpdate.status = PENDING_CREATE
	//					objUpdate.createdBy = cifUserInstance.updatedBy
	//					objUpdate.transactionMapping = transactionMapping
	//					objUpdate.roleApproval = newIt
	//					objUpdate.ccy = chargeDetails.find {it.transactionCode == transactionMapping.transactionCode}?.ccy
	//					objUpdate.limitAmount = new BigDecimal(amount.get(transactionMapping.id + "_" + newIt) ?: 0)
	//					objUpdate.save(failOnError: true)
	//
	//					if (!newIt.equals(MAKER)) {
	//						isHavingRolePending = true
	//					}
	//				}
	//				else {
	//					cifUserLimitIds -= objUpdate.id
	//					if (!isHavingRolePending) {
	//						if (objUpdate.roleApproval == APPROVER || objUpdate.roleApproval == RELEASER) {
	//							isHavingRolePending = true
	//						}
	//					}
	//					limit = new BigDecimal(amount.get(transactionMapping.id + "_" + newIt) ?: 0)
	//					if(limit.compareTo(objUpdate.limitAmount) != 0){
	//						changes = true
	//						objUpdate.pending = limit
	//						objUpdate.status = PENDING_UPDATE
	//						objUpdate.updatedBy = username
	//						objUpdate.save(failOnError: true)
	//					}
	//				}
	//			}
	//		}
	//
	//		def objDel
	//		cifUserLimitIds.each { x ->
	//			objDel = cifUserLimitInstance.find {it.id == x}
	//			if (objDel) {
	//				changes = true
	//				objDel.status = PENDING_DELETE
	//				objDel.updatedBy = username
	//				objDel.save(failOnError: true)
	//			}
	//		}
	//
	//		if(mod.size() > 0 || changes){
	//			mod["isHavingRolePending"] = isHavingRolePending
	//			return mod
	//		}
	//
	//		return null
	//	}
	//
	//	def updatePending(def cifUserInstance, def roleApproval, def amount, def username) {
	//		def changes = false
	//		def mod = updatePendingUser(cifUserInstance, roleApproval, amount, username)
	//		if(mod != null){
	//			changes = true
	//
	//			cifUserInstance.refresh()
	//			cifUserInstance.status = PENDING_UPDATE
	//			cifUserInstance.pending = (mod as JSON) as String
	//			cifUserInstance.updatedBy = username
	//			cifUserInstance.save(failOnError: true)
	//
	//			corporateAcitiviService.initiateCorporateManagement(cifUserInstance.id, cifUserInstance.cif.firstName + " " +cifUserInstance.cif.lastName, "update", "cifUser", cifUserInstance.cif.id)
	//		}
	//
	//		return changes
	//	}
	//
	//	def updatePending2(def cifUserInstance, def roleApproval, def amount, def username) {
	//		def changes = false
	//		def mod = updatePendingUser(cifUserInstance, roleApproval, amount, username)
	//		if(mod != null){
	//			changes = true
	//
	//			cifUserInstance.refresh()
	//			cifUserInstance.status = PENDING_UPDATE
	//			cifUserInstance.pending = (mod as JSON) as String
	//			cifUserInstance.updatedBy = username
	//			cifUserInstance.save(failOnError: true)
	//
	//			corporateAcitiviService.initiateCorporateManagement2(cifUserInstance.id, cifUserInstance.cif.firstName + " " +cifUserInstance.cif.lastName, "update", "cifUser", cifUserInstance.cif.id)
	//		}
	//
	//		return changes
	//	}
	//
	//	private def removeUserRole(def objDel, def roleUserPending, def users, def isHavingRolePending, def cifUserLimits) {
	//		if (objDel.roleApproval.equals(MAKER)) {
	//			def delte = false
	//			if (objDel.transactionMapping.parentTransactionMapping == null) {
	//				delte = true
	//			}
	//			else {
	//				if (objDel.transactionMapping.parentTransactionMapping.transactionCode.equals("T020P") || objDel.transactionMapping.parentTransactionMapping.transactionCode.equals("T022P")) {
	//					if (!cifUserLimits.find {it.roleApproval.equals(MAKER) && it.transactionMapping.parentTransactionMapping != null && (it.transactionMapping.parentTransactionMapping.transactionCode.equals("T020P") || it.transactionMapping.parentTransactionMapping.transactionCode.equals("T022P"))}) {
	//						delte = true
	//					}
	//				}
	//				else if (!cifUserLimits.find {it.roleApproval.equals(MAKER) && it.transactionMapping.parentTransactionMapping == objDel.transactionMapping.parentTransactionMapping}) {
	//					delte = true
	//				}
	//			}
	//
	//			if (delte) {
	//				if (objDel.transactionMapping.role) {
	//					def roleMaker = Role.findByAuthority(objDel.transactionMapping.role)
	//					if (roleMaker) {
	//						if (users.authorities.contains(roleMaker)) {
	//							UserRole.remove(users, roleMaker)
	//						}
	//					}
	//				}
	//			}
	//		}
	//		else {
	//			if (!isHavingRolePending) {
	//				if (!roleUserPending) {
	//					roleUserPending = Role.findByAuthority(ROLE_CO_PENDING_TASK)
	//				}
	//				if (roleUserPending) {
	//					if (users.authorities.contains(roleUserPending)) {
	//						UserRole.remove(users, roleUserPending)
	//					}
	//				}
	//			}
	//		}
	//		objDel.deleteFlag = "Y"
	//		objDel.limitAmount = 0
	//		objDel.save(failOnError: true)
	//
	//		return roleUserPending
	//	}

	def delete(def cifUserInstance) {
		UserRole.removeAll(cifUserInstance.userDetails.user)

		cifUserInstance.deleteFlag = "Y"
		cifUserInstance.userDetails.deleteFlag = "Y"
		cifUserInstance.userDetails.updatedBy = cifUserInstance.updatedBy
		cifUserInstance.userDetails.user.enabled = false
		cifUserInstance.userDetails.user.save(failOnError: true)
		cifUserInstance.userDetails.save(failOnError: true)
		cifUserInstance.save(failOnError: true)
		cifUserInstance.reindex()
		return cifUserInstance
	}

	//	def deletePending(def cifUserInstance){
	//		cifUserInstance.status = PENDING_DELETE
	//		cifUserInstance.pending = "delete"
	//		cifUserInstance.save(failOnError: true)
	//
	//		corporateAcitiviService.initiateCorporateManagement(cifUserInstance.id, cifUserInstance.cif.firstName + " " +cifUserInstance.cif.lastName, "delete", "cifUser", cifUserInstance.cif.id)
	//	}
	//
	//	def deletePending2(def cifUserInstance){
	//		cifUserInstance.status = PENDING_DELETE
	//		cifUserInstance.pending = "delete"
	//		cifUserInstance.save(failOnError: true)
	//
	//		corporateAcitiviService.initiateCorporateManagement2(cifUserInstance.id, cifUserInstance.cif.firstName + " " +cifUserInstance.cif.lastName, "delete", "cifUser", cifUserInstance.cif.id)
	//	}

	def list(def params) {

		def results = new ArrayList()
		def cifInstance = getCurrentCifUser().cif

		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		params.properties = [
			"CifUser.firstName",
			"CifUser.lastName"
		]

		if (!params.sort && !params.order) {
			params.sort = "username"
			params.order = "asc"
		}

		def query = "+CifUser.sysAdmin:Y +CifUser.deleteFlag:N +CifUser.cif.id:" + cifInstance.id

		if (params.userID) {
			query += " username:*${params.userID}*"
		}

		if (params.name) {
			query += " *${params.name}*"
		}

		def searchResult = CifUser.search(query, params)
		results.add(searchResult.results)
		results.add(searchResult.total)
		return results
	}

	def list2(def params) {

		def results = new ArrayList()
		def cifInstance = Cif.get(params.id)
		if(cifInstance == null){
			return null
		}

		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		params.properties = [
			"CifUser.firstName",
			"CifUser.lastName"
		]

		if (!params.sort && !params.order) {
			params.sort = "username"
			params.order = "asc"
		}

		def query = "+CifUser.sysAdmin:Y +CifUser.deleteFlag:N +CifUser.cif.id:" + cifInstance.id

		if (params.userID) {
			query += " username:*${params.userID}*"
		}

		if (params.name) {
			query += " *${params.name}*"
		}

		def searchResult = CifUser.search(query, params)
		results.add(searchResult.results)
		results.add(searchResult.total)
		results.add(cifInstance)
		return results
	}

	//	def list2(def params) {
	//
	//		def results = new ArrayList()
	//
	//		params.max = Math.min(params.max ? params.int('max') : 10, 100)
	//		params.properties = [
	//			"CifUser.firstName",
	//			"CifUser.lastName"
	//		]
	//
	//		if (!params.sort && !params.order) {
	//			params.sort = "username"
	//			params.order = "asc"
	//		}
	//
	//		def query = "-CifUser.status:1 +CifUser.deleteFlag:N +CifUser.cif.id:" + params.cif.id
	//
	//		if (params.userID) {
	//			query += " username:*${params.userID}*"
	//		}
	//
	//		if (params.name) {
	//			query += " *${params.name}*"
	//		}
	//
	//		def searchResult = CifUser.search(query, params)
	//		results.add(searchResult.results)
	//		results.add(searchResult.total)
	//
	//		if (searchResult.total > 0) {
	//			def token = Token.executeQuery("SELECT t FROM Token t WHERE t.id in (SELECT max(id) from Token where user in ('" + searchResult.results*.userDetails*.user*.username.join("','") + "') group by user)")
	//			results.add(token)
	//		}
	//		else {
	//			results.add(new ArrayList())
	//		}
	//
	//		return results
	//	}
	//	def getCandidateUser(def role, def amount, def transactionCode, def cifId) {
	//		def cifUserLimit = CifUserLimit.findAll("from com.teravin.netbank.app.cif.CifUserLimit cul where cul.roleApproval=? and cul.transactionMapping.transactionCode=? and cul.limitAmount >= ? and cul.cifUser.cif=? and cul.deleteFlag = 'N' and status != ?", [
	//			role,
	//			transactionCode,
	//			amount as BigDecimal,
	//			cifId,
	//			PENDING_CREATE
	//		])
	//
	//		return cifUserLimit*.cifUser*.userDetails*.user*.username
	//	}
	//
	//	def isHaveRightToMake(def cif, def amount, def transactionCode) {
	//		def username = springSecurityService.principal.username
	//		def cifUserLimit = CifUserLimit.find("from com.teravin.netbank.app.cif.CifUserLimit cul where cul.cifUser.userDetails.user.username = ? and cul.deleteFlag = 'N' and cul.roleApproval = '1' and cul.transactionMapping.transactionCode=? and status != ?", [
	//			username,
	//			transactionCode,
	//			PENDING_CREATE
	//		])
	//		def request = org.codehaus.groovy.grails.plugins.springsecurity.SecurityRequestHolder.getRequest()
	//		def session = request.getSession(false)
	//		if (cifUserLimit) {
	//			if (cifUserLimit.limitAmount.compareTo(amount) == -1) {
	//				throw new SecurityException(messageSource.getMessage("transaction.make.amount.no.right.label", null, session[org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME]))
	//			}
	//		} else {
	//			throw new SecurityException(messageSource.getMessage("transaction.make.no.right.label", null, session[org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME]))
	//		}
	//
	//		def maker = CifApprovalMatrix.find("from com.teravin.netbank.app.cif.CifApprovalMatrix cam where cam.deleteFlag = 'N' and cam.cif = ? and cam.roleApproval = '1' and cam.transactionMapping.transactionCode = ?", [cif, transactionCode])
	//		if (maker) {
	//			if (maker.limitAmount.compareTo(amount) == -1) {
	//				throw new SecurityException(messageSource.getMessage("transaction.make.amount.no.right.label", null, session[org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME]))
	//			}
	//		}
	//		else {
	//			throw new SecurityException(messageSource.getMessage("transaction.make.no.right.label", null, session[org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME]))
	//		}
	//	}
	//
	//	def isIndvHaveRightTo(def cif, def amount, def transactionCode) {
	//		def role = CifServicePackageDetails.executeQuery("Select tm.transactionCode from CifServicePackageDetails csd, TransactionMapping tm, CifServicePackage cs where cs.cif = ? and cs.deleteFlag = 'N' and cs.status != ? and csd.cifServicePackage = cs and csd.service = tm and tm.transactionCode = ?", [
	//			cif,
	//			PENDING_CREATE,
	//			transactionCode
	//		])[0]
	//		if(!role){
	//			def request = org.codehaus.groovy.grails.plugins.springsecurity.SecurityRequestHolder.getRequest()
	//			def session = request.getSession(false)
	//			throw new SecurityException(messageSource.getMessage("transaction.make.no.right.label", null, session[org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME]))
	//		}
	//
	//		//TODO: check whether it has exceed daily limit
	//	}

	def getCurrentCifUser() {
		def cifUser = CifUser.find("from com.akiong.app.cif.CifUser cu where cu.userDetails.user.id=?", [
			springSecurityService.principal.id
		])

		return cifUser
	}

	def getCurrentMemberUser() {
		def member = GroupMember.find("from com.akiong.app.cif.GroupMember gm where gm.cifUser.userDetails.user.id=?", [
			springSecurityService.principal.id
		])

		return member
	}

	def activate(def id, def activationKey) {
		def activate = false
		def user = User.get(id)
		if (user) {
			def userDetail = UserDetails.findByUser(user)
			if (userDetail) {
				if (userDetail.activationKey.equals(activationKey)) {
					userDetail.activationKey = null
					userDetail.status = Common.ACTIVE_USER_STATUS
					userDetail.email = userDetail.newEmail
					userDetail.newEmail = null
					user.enabled = true
					userDetail.save(failOnError: true)
					user.save(failOnError: true)
					activate = true
					CifUser.findByUserDetails(userDetail).reindex()
				}
			}
		}

		return activate
	}

	//	private def approveUser(def cifUserInstance, def type, def username, def taskId){
	//		if(type.equals("create")){
	//			if (cifUserInstance.userDetails.activateToken) {
	//				try {
	//					if(cifUserInstance.userDetails.tokenotp.equals("t")){
	//						tokenService.registration(cifUserInstance.userDetails.user.username, cifUserInstance.userDetails.mobilePhoneNo)
	//					}
	//					else{
	//						otpService.registration(cifUserInstance.userDetails.user.username)
	//					}
	//				}
	//				catch (RuntimeException e) {
	//					logger.error(e.getMessage(), e)
	//					def request = org.codehaus.groovy.grails.plugins.springsecurity.SecurityRequestHolder.getRequest()
	//					def session = request.getSession(false)
	//					def locale = session[org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME]
	//					throw new SecurityException(messageSource.getMessage("token.registration.error.msg", null, locale))
	//				}
	//			}
	//
	//			def passwordList = JSON.parse(cifUserInstance.pending)
	//			def users = cifUserInstance.userDetails.user
	//			users.enabled = 1
	//			users.save(failOnError: true)
	//
	//			def passOnlyType = AuthenticationType.findByName('PassOnly')
	//			if (passOnlyType) {
	//				new PasswordHistory(user: users, password: users.password, authenticationType: passOnlyType, status: '2', createdBy: username).save(failOnError: true)
	//			}
	//
	//			def vpinType = AuthenticationType.findByName(AUTH_TYPE_VPIN)
	//			if(vpinType){
	//				new PasswordHistory(user: users, password: cifUserInstance.userDetails.password2, authenticationType: vpinType, status: '2', createdBy: username).save(failOnError: true)
	//			}
	//
	//			new UserChannel(user: users, channel: Channel.findByNameAndDeleteFlag("Netbank", "N"), createdBy: username).save(failOnError: true)
	//
	//			if (cifUserInstance.userDetails.email) {
	//				if (ConfigurationHolder.config.netbank.send.sms) {
	//					cifService.regEmail(users.username, cifUserInstance.userDetails.email, cifUserInstance.userDetails.firstName + " " + cifUserInstance.userDetails.lastName, EncryptionUtil.decrypt(passwordList["passwordList1"]), EncryptionUtil.decrypt(cifUserInstance.userDetails.password2).substring(0, 4))
	//				}
	//				else {
	//					cifService.regEmail(users.username, cifUserInstance.userDetails.email, cifUserInstance.userDetails.firstName + " " + cifUserInstance.userDetails.lastName, EncryptionUtil.decrypt(passwordList["passwordList1"]) + EncryptionUtil.decrypt(passwordList["passwordList2"]), EncryptionUtil.decrypt(cifUserInstance.userDetails.password2))
	//				}
	//			}
	//			if (cifUserInstance.userDetails.mobilePhoneNo) {
	//				if (ConfigurationHolder.config.netbank.send.sms) {
	//					cifService.regSms(cifUserInstance.userDetails.mobilePhoneNo, cifUserInstance.userDetails.firstName + " " + cifUserInstance.userDetails.lastName, EncryptionUtil.decrypt(passwordList["passwordList2"]), EncryptionUtil.decrypt(cifUserInstance.userDetails.password2).substring(4, 8))
	//				}
	//			}
	//
	//			cifUserInstance.updatedBy = username
	//			cifUserInstance.pending = null
	//			cifUserInstance.status = ACTIVE
	//			cifUserInstance.save(failOnError: true)
	//			cifUserInstance.reindex()
	//		}
	//		else if(type.equals("update")){
	//			String oldMobileNo = cifUserInstance.userDetails?.mobilePhoneNo
	//			String oldEmail = cifUserInstance.userDetails?.email
	//			def changes = JSON.parse(cifUserInstance.pending)
	//			cifUserInstance.properties = changes
	//			cifUserInstance.userDetails.properties = changes
	//			String newEmail = cifUserInstance.userDetails.email
	//
	//			if(cifUserInstance.userDetails.activationKey != null){
	//				cifUserInstance.userDetails.email = oldEmail
	//				def oldEmail2 = cifUserInstance.userDetails.newEmail
	//				if (oldEmail2 != newEmail) {
	//					cifUserInstance.userDetails.newEmail = newEmail
	//					processChangingEmail(cifUserInstance)
	//				}
	//			}
	//			else{
	//				if (oldEmail != newEmail) {
	//					cifUserInstance.userDetails.email = oldEmail
	//					cifUserInstance.userDetails.newEmail = newEmail
	//					processChangingEmail(cifUserInstance)
	//				}
	//			}
	//
	//			def users = cifUserInstance.userDetails.user
	//			if (!oldMobileNo.equals(cifUserInstance.userDetails?.mobilePhoneNo)) {
	//				def token = Token.find("from Token as t where t.user=? and t.deleteFlag='N' and t.status!='3'", [users.username])
	//				if (token) {
	//					processChangingMobile(cifUserInstance, oldMobileNo)
	//				}
	//			}
	//
	//			def roleCoSysAdmin = Role.findByAuthority(ROLE_CO_SYS_ADMIN)
	//			if (roleCoSysAdmin) {
	//				if(cifUserInstance.sysAdmin){
	//					if (!users.authorities.contains(roleCoSysAdmin)) {
	//						UserRole.create(users, roleCoSysAdmin)
	//					}
	//				}
	//				else{
	//					if (users.authorities.contains(roleCoSysAdmin)) {
	//						UserRole.remove(users, roleCoSysAdmin)
	//					}
	//				}
	//			}
	//
	//			def roleUser = Role.findByAuthority(ROLE_CO_INQ)
	//			if (cifUserInstance.viewInquiry.equals("Y")) {
	//				if (roleUser && !users.authorities.contains(roleUser)) {
	//					UserRole.create(users, roleUser)
	//				}
	//			}
	//			else{
	//				if (roleUser && users.authorities.contains(roleUser)) {
	//					UserRole.remove(users, roleUser)
	//				}
	//			}
	//
	//			roleUser = Role.findByAuthority(ROLE_CO_PAYROLL)
	//			if (cifUserInstance.viewPayrollDetail.equals("Y")) {
	//				if (roleUser && !users.authorities.contains(roleUser)) {
	//					UserRole.create(users, roleUser)
	//				}
	//			}
	//			else{
	//				if (roleUser && users.authorities.contains(roleUser)) {
	//					UserRole.remove(users, roleUser)
	//				}
	//			}
	//
	//			if (cifUserInstance.viewReportCorp.equals("Y")) {
	//				[
	//					ROLE_FO_REPORT_USER,
	//					ROLE_FO_REPORT_CORP_PROFILE
	//				].find{
	//					roleUser = Role.findByAuthority(it)
	//					if (roleUser){
	//						if(users.authorities.contains(roleUser)){
	//							return true
	//						}
	//						UserRole.create(users, roleUser)
	//					}
	//					return false
	//				}
	//			}
	//			else{
	//				[
	//					ROLE_FO_REPORT_USER,
	//					ROLE_FO_REPORT_CORP_PROFILE
	//				].find{
	//					roleUser = Role.findByAuthority(it)
	//					if (roleUser){
	//						if(!users.authorities.contains(roleUser)) {
	//							return true
	//						}
	//						UserRole.remove(users, roleUser)
	//					}
	//					return false
	//				}
	//			}
	//
	//			if (cifUserInstance.viewReportTrx.equals("Y")) {
	//				[
	//					ROLE_FO_REPORT_TRX,
	//					ROLE_FO_REPORT_STANDING_TRX,
	//					ROLE_FO_REPORT_BULK_TRX,
	//					ROLE_FO_ADVICE_PRINTING
	//				].find{
	//					roleUser = Role.findByAuthority(it)
	//					if (roleUser){
	//						if(users.authorities.contains(roleUser)) {
	//							return true
	//						}
	//						UserRole.create(users, roleUser)
	//					}
	//					return false
	//				}
	//			}
	//			else{
	//				[
	//					ROLE_FO_REPORT_TRX,
	//					ROLE_FO_REPORT_STANDING_TRX,
	//					ROLE_FO_REPORT_BULK_TRX,
	//					ROLE_FO_ADVICE_PRINTING
	//				].each{
	//					roleUser = Role.findByAuthority(it)
	//					if (roleUser){
	//						if(!users.authorities.contains(roleUser)) {
	//							return true
	//						}
	//						UserRole.remove(users, roleUser)
	//					}
	//					return false
	//				}
	//			}
	//
	//			def isHavingRolePending = changes["isHavingRolePending"]
	//			def cifUserLimitList = CifUserLimit.findAllByCifUserAndDeleteFlag(cifUserInstance, "N")
	//			def culExist = cifUserLimitList.findAll{!it.status.equals(PENDING_DELETE)}
	//			def roleUserPending
	//			cifUserLimitList.each{ x->
	//				if(x.status.equals(PENDING_CREATE)){
	//					roleUserPending = addUserRole(x.roleApproval, roleUserPending, users, x.transactionMapping.role)
	//				}
	//				else if(x.status.equals(PENDING_DELETE)){
	//					roleUserPending = removeUserRole(x, roleUserPending, users, isHavingRolePending, culExist)
	//				}
	//				else if(x.status.equals(PENDING_UPDATE)){
	//					x.limitAmount = new BigDecimal(x.pending)
	//				}
	//				if(!x.status.equals(ACTIVE)){
	//					x.pending = null
	//					x.status = ACTIVE
	//					x.save(failOnError: true)
	//				}
	//			}
	//
	//			cifUserInstance.updatedBy = username
	//			cifUserInstance.pending = null
	//			cifUserInstance.status = ACTIVE
	//			cifUserInstance.save(failOnError: true)
	//			cifUserInstance.reindex()
	//		}
	//		else if(type.equals("delete")){
	//			cifUserInstance.updatedBy = username
	//			cifUserInstance.pending = null
	//			cifUserInstance.status = ACTIVE
	//			delete(cifUserInstance)
	//		}
	//
	//		activitiService.claimTask(taskId, username)
	//		def params = [:]
	//		params.approved = true
	//		params.enoughApproval = true
	//		params.releaser = false
	//		activitiService.completeTask(taskId, params)
	//	}
	//
	//	@Transactional(propagation = Propagation.REQUIRES_NEW)
	//	def approve(def taskId, def username, def variables = null){
	//		if(!variables){
	//			variables = corporateAcitiviService.checkPermision(taskId, username)
	//		}
	//		else{
	//			corporateAcitiviService.checkUserAccess(variables, username)
	//		}
	//
	//		def cifUserInstance = CifUser.get(variables["id"])
	//		approveUser(cifUserInstance, variables["type"], username, taskId)
	//	}
	//
	//	private def rejectUser(def cifUserInstance, def type, def username, def taskId){
	//		if(type.equals("create")){
	//			CifUserLimit.executeUpdate("delete from CifUserLimit WHERE cifUser = ?", [cifUserInstance])
	//			cifUserInstance.delete(failOnError: true)
	//			AuthenticationDetails.executeUpdate("delete from AuthenticationDetails WHERE userDetails = ?", [cifUserInstance.userDetails])
	//			cifUserInstance.userDetails.delete(failOnError: true)
	//			def users = cifUserInstance.userDetails.user
	//			UserRole.removeAll(users)
	//			users.delete(failOnError: true)
	//		}
	//		else if(type.equals("update")){
	//			CifUserLimit.executeUpdate("delete from CifUserLimit WHERE cifUser = ? and deleteFlag = 'N' and status = ?", [
	//				cifUserInstance,
	//				PENDING_CREATE
	//			])
	//			CifUserLimit.executeUpdate("update CifUserLimit set pending = NULL, updatedBy = ?, lastUpdated = ?, status = ? WHERE cifUser = ? and deleteFlag = 'N' and status != ?", [
	//				username,
	//				new Date(),
	//				ACTIVE,
	//				cifUserInstance,
	//				ACTIVE
	//			])
	//
	//			cifUserInstance.pending = null
	//			cifUserInstance.status = ACTIVE
	//			cifUserInstance.updatedBy = username
	//			cifUserInstance.save(failOnError: true)
	//		}
	//		else if(type.equals("delete")){
	//			cifUserInstance.updatedBy = username
	//			cifUserInstance.pending = null
	//			cifUserInstance.status = ACTIVE
	//			cifUserInstance.save(failOnError: true)
	//		}
	//
	//		activitiService.claimTask(taskId, username)
	//		def params = [:]
	//		params.approved = false
	//		params.enoughApproval = true
	//		params.releaser = false
	//		activitiService.completeTask(taskId, params)
	//	}
	//
	//	@Transactional(propagation = Propagation.REQUIRES_NEW)
	//	def reject(def taskId, def username, def variables = null){
	//		if(!variables){
	//			variables = corporateAcitiviService.checkPermision(taskId, username)
	//		}
	//		else{
	//			corporateAcitiviService.checkUserAccess(variables, username)
	//		}
	//
	//		def cifUserInstance = CifUser.get(variables["id"])
	//		rejectUser(cifUserInstance, variables["type"], username, taskId)
	//	}
	//
	//	@Transactional(propagation = Propagation.REQUIRES_NEW)
	//	def reject2(def taskId, def refNo, def tokenPin, def username, def variables = null){
	//		if(!variables){
	//			def request = org.codehaus.groovy.grails.plugins.springsecurity.SecurityRequestHolder.getRequest()
	//			def session = request.getSession(false)
	//			if (session != null) {
	//				KeyPair keys = (KeyPair) session.getAttribute("keys");
	//				if(keys != null && tokenPin != null){
	//					JCryptionUtil jCryptionUtil = new JCryptionUtil();
	//					tokenPin = jCryptionUtil.decrypt(tokenPin, keys);
	//				}
	//			}
	//			variables = corporateAcitiviService.checkPermision2(taskId, username)
	//			if (!tokenService.isValidTokenFOMaintenance(variables, refNo, tokenPin, username)) {
	//				def locale = session[org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME]
	//				throw new SecurityException(messageSource.getMessage("default.wrong.token.pin.message", null, locale))
	//			}
	//		}
	//		else{
	//			corporateAcitiviService.checkUserAccess2(variables, username)
	//		}
	//
	//		def cifUserInstance = CifUser.get(variables["id"])
	//		def cifInstance = getCurrentCifUser().cif
	//		if (variables["corpId"] != cifInstance.id) {
	//			throw new SecurityException("Task id was changed by user manually")
	//		}
	//		rejectUser(cifUserInstance, variables["type"], username, taskId)
	//	}
	//
	//	@Transactional(propagation = Propagation.REQUIRES_NEW)
	//	def approve2(def taskId, def refNo, def tokenPin, def username, def variables = null){
	//		if(!variables){
	//			def request = org.codehaus.groovy.grails.plugins.springsecurity.SecurityRequestHolder.getRequest()
	//			def session = request.getSession(false)
	//			if (session != null) {
	//				KeyPair keys = (KeyPair) session.getAttribute("keys");
	//				if(keys != null && tokenPin != null){
	//					JCryptionUtil jCryptionUtil = new JCryptionUtil();
	//					tokenPin = jCryptionUtil.decrypt(tokenPin, keys);
	//				}
	//			}
	//			variables = corporateAcitiviService.checkPermision2(taskId, username)
	//			if (!tokenService.isValidTokenFOMaintenance(variables, refNo, tokenPin, username)) {
	//				def locale = session[org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME]
	//				throw new SecurityException(messageSource.getMessage("default.wrong.token.pin.message", null, locale))
	//			}
	//		}
	//		else{
	//			corporateAcitiviService.checkUserAccess2(variables, username)
	//		}
	//
	//		def cifUserInstance = CifUser.get(variables["id"])
	//		def cifInstance = getCurrentCifUser().cif
	//		if (variables["corpId"] != cifInstance.id) {
	//			throw new SecurityException("Task id was changed by user manually")
	//		}
	//
	//		if(variables["type"].equals("create")){
	//			cifUserInstance.userDetails.activateToken = false
	//		}
	//
	//		approveUser(cifUserInstance, variables["type"], username, taskId)
	//	}
	//
	//	def setFbAccount(def username, def fbId, def fbToken){
	//		def userDetails = UserDetails.executeQuery("select ud from UserDetails ud where ud.user.username = ?", [username])[0];
	//		userDetails.fbAccountId = fbId
	//		userDetails.fbAccessToken = fbToken
	//		userDetails.save(failOnError: true)
	//	}
	//
	//	def getUserLike(String username){
	//		def params = [:]
	//		params.max = MAX_SEARCH
	//		params.properties = [
	//			"CifUser.firstName",
	//			"CifUser.lastName",
	//			"username"
	//		]
	//
	//		if (!params.sort && !params.order) {
	//			params.sort = "username"
	//			params.order = "asc"
	//		}
	//
	//		def result = new ArrayList()
	//		def cif = getCurrentCifUser()?.cif
	//		if(cif){
	//			def query = "-CifUser.status:1 +CifUser.deleteFlag:N +CifUser.cif.id:" + cif.id
	//			query += " *${username}*"
	//
	//			def users = CifUser.search(query, params).results
	//			def map
	//			for(int i = 0; i<users.size(); i++){
	//				map = new HashMap()
	//				map['firstName'] = users[i]?.firstName
	//				map['lastName'] = users[i]?.lastName
	//				map['username'] = users[i]?.userDetails?.user?.username
	//				result.add(map)
	//			}
	//		}
	//		return result
	//	}

	def createMember(def group, def cifUserInstance) {
		def passwordList
		def cif = group.cif
		def groupMember = new GroupMember()
		groupMember.position = "2"
		groupMember.createdBy = cifUserInstance.createdBy
		groupMember.cifUser = cifUserInstance
//		groupMember.userLimit = cifUserInstance.userLimit
		groupMember.status = "4"

		if (cifUserInstance.userDetails) {
			cifUserInstance.userDetails.createdBy = cifUserInstance.createdBy
			cifUserInstance.userDetails.firstName = cifUserInstance.firstName
			cifUserInstance.userDetails.lastName = cifUserInstance.lastName
			cifUserInstance.userDetails.status = com.teravin.helper.Common.INACTIVE_USER_STATUS
			cifUserInstance.userDetails.forceChangePassword = true
			cifUserInstance.userDetails.userType = "2"

			if (cifUserInstance.userDetails.user) {
				passwordList = passwordGeneratorService.generateRegistrationCode()
				cifUserInstance.userDetails.user.password = springSecurityService.encodePassword(passwordList[0] + passwordList[1])
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

			def roleGroupMember = Role.findByAuthority(Common.ROLE_GROUP_MEMBER)
			if (roleGroupMember) {
				new UserRole( user: cifUserInstance.userDetails.user, role: roleGroupMember ).save(failOnError: true)
			}
			cifUserInstance.userDetails.save(failOnError: true)
		}
		cifUserInstance.cif = cif
		cifUserInstance.save(failOnError: true)

//		def limit = new MemberLimit()
//		limit.userLimit = groupMember.userLimit
//		limit.outstandingLimit = new BigDecimal(0)
//		limit.createdBy = cifUserInstance.createdBy
//		limit.save(failOnError: true)

//		groupMember.memberLimit = limit
		groupMember.group = group
		groupMember.save(failOnError: true)

		if (cifUserInstance.userDetails.email) {
			cifService.emailPasswordBO(cifUserInstance.userDetails.user.username, cifUserInstance.userDetails.email, cifUserInstance.userDetails.firstName + " " + cifUserInstance.userDetails.lastName, passwordList[0] + passwordList[1])
		}

		return groupMember
	}

	def createMember2(def cifUserInstance) {
		def group = getCurrentMemberUser().group
		createMember(group, cifUserInstance)
	}

	def updateMember(def member, def oldEmail) {
		def cifUserInstance = member.cifUser
		cifUserInstance.updatedBy = member.updatedBy

		if (cifUserInstance.userDetails) {
			cifUserInstance.userDetails.updatedBy = cifUserInstance.updatedBy
			cifUserInstance.userDetails.firstName = cifUserInstance.firstName
			cifUserInstance.userDetails.lastName = cifUserInstance.lastName

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
//			def countnya = UserDetails.executeQuery("SELECT count(ud) FROM UserDetails ud WHERE ud.deleteFlag = 'N' AND (ud.mobilePhoneNo = ? OR ud.mobilePhoneNo = ?) AND ud.id != ?",[
//				mobileNo,
//				mobileNo2,
//				cifUserInstance.userDetails.id
//			])[0]
//			if(countnya > 0){
//				def request = org.codehaus.groovy.grails.plugins.springsecurity.SecurityRequestHolder.getRequest()
//				def session = request.getSession(false)
//				def locale = session[org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME]
//
//				Object[] testArgs = new Object[1]
//				testArgs[0] = messageSource.getMessage("cif.phoneNo.label", null, locale)
//				throw new SecurityException(messageSource.getMessage("default.already.exist.message", testArgs, locale))
//			}


			cifUserInstance.userDetails.lastUpdated = new Date()
			cifUserInstance.userDetails.save(failOnError: true)
		}
		cifUserInstance.lastUpdated = new Date()
		cifUserInstance.save(failOnError: true)

		member.memberLimit.userLimit = member.userLimit
		member.memberLimit.updatedBy = member.updatedBy
		member.memberLimit.save(failOnError: true)

		member.lastUpdated = new Date()
		member.save(failOnError: true)

		String newEmail = cifUserInstance.userDetails.email
		if(cifUserInstance.userDetails.activationKey != null){
			cifUserInstance.userDetails.email = oldEmail
			def oldEmail2 = cifUserInstance.userDetails.newEmail
			if (oldEmail2 != newEmail) {
				cifUserInstance.userDetails.newEmail = newEmail
				processChangingEmail(cifUserInstance.userDetails)
			}
		}
		else{
			if (oldEmail != newEmail) {
				cifUserInstance.userDetails.email = oldEmail
				cifUserInstance.userDetails.newEmail = newEmail
				processChangingEmail(cifUserInstance.userDetails)
			}
		}

		cifUserInstance.reindex()
		member.reindex()
		member.group.reindex()
		return member
	}

	def deleteMember(def member) {
		member.deleteFlag = "Y"

		def cifUserInstance = member.cifUser
		UserRole.removeAll(cifUserInstance.userDetails.user)

		cifUserInstance.deleteFlag = "Y"
		cifUserInstance.userDetails.deleteFlag = "Y"
		cifUserInstance.userDetails.updatedBy = cifUserInstance.updatedBy
		cifUserInstance.userDetails.user.enabled = false
		cifUserInstance.userDetails.user.save(failOnError: true)
		cifUserInstance.userDetails.save(failOnError: true)
		cifUserInstance.save(failOnError: true)
		member.save(failOnError: true)

		cifUserInstance.reindex()
		member.reindex()

		return member
	}

	def promote(def member){
		def currentUser = getCurrentCifUser()
		if(currentUser.cif.id != member.cifUser.cif.id){
			throw new SecurityException("You are not authorized to do this action")
		}
		def leader = member.group.leader
		leader.position = "2"
		leader.updatedBy = springSecurityService.principal.username
		leader.save(failOnError: true)

		member.position = '1'
		member.updatedBy = springSecurityService.principal.username
		member.save(failOnError: true)

		member.group.leader = member
		member.group.save(failOnError: true)

		leader.reindex()
		member.reindex()
		member.group.reindex()
	}

	def listMember(def params){
		def group = getCurrentMemberUser().group

		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		params.properties = [
			"GroupMember.cifUser.firstName",
			"GroupMember.cifUser.lastName"
		]

		if (!params.sort && !params.order) {
			params.sort = "cuFirstName"
			params.order = "asc"
		}

		def query = "+GroupMember.deleteFlag:N +GroupMember.group.id:" + group.id + " -GroupMember.status:1 +GroupMember.position:2"
		if (params.name) {
			query += " *${params.name}*"
		}
		if (params.phone) {
			query += " +GroupMember.cifUser.userDetails.mobilePhoneNo: *${params.phone}*"
		}

		def results = new ArrayList()
		def result = GroupMember.search(query, params)
		results.add(result.results)
		results.add(result.total)
		results.add(group)

		return results
	}

	def createMemberPending(def cifUserInstance) {
		def group = getCurrentMemberUser().group

		def cif = group.cif
		def groupMember = new GroupMember()
		groupMember.position = "2"
		groupMember.createdBy = cifUserInstance.createdBy
		groupMember.cifUser = cifUserInstance
		groupMember.userLimit = cifUserInstance.userLimit
		groupMember.status = "1"

		if (cifUserInstance.userDetails) {
			cifUserInstance.userDetails.createdBy = cifUserInstance.createdBy
			cifUserInstance.userDetails.firstName = cifUserInstance.firstName
			cifUserInstance.userDetails.lastName = cifUserInstance.lastName
			cifUserInstance.userDetails.status = com.teravin.helper.Common.INACTIVE_USER_STATUS
			cifUserInstance.userDetails.forceChangePassword = true
			cifUserInstance.userDetails.userType = "2"

			if (cifUserInstance.userDetails.user) {
				cifUserInstance.userDetails.user.password = springSecurityService.encodePassword("password")
				cifUserInstance.userDetails.user.enabled = 0
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

			def roleGroupMember = Role.findByAuthority(Common.ROLE_GROUP_MEMBER)
			if (roleGroupMember) {
				new UserRole( user: cifUserInstance.userDetails.user, role: roleGroupMember ).save(failOnError: true)
			}
			cifUserInstance.userDetails.save(failOnError: true)
		}
		cifUserInstance.cif = cif
		cifUserInstance.save(failOnError: true)

//		def limit = new MemberLimit()
//		limit.userLimit = groupMember.userLimit
//		limit.outstandingLimit = new BigDecimal(0)
//		limit.createdBy = cifUserInstance.createdBy
//		limit.save(failOnError: true)

//		groupMember.memberLimit = limit
		groupMember.group = group
		groupMember.save(failOnError: true)

		corporateAcitiviService.initiateMemberManagement(groupMember, group.id, "create", "groupMember", cif.id)

		return groupMember
	}

	def getMemberLike(def name){
		def params = [:]
		def group = getCurrentMemberUser().group

		params.max = MAX_SEARCH
		params.properties = [
			"GroupMember.cifUser.firstName",
			"GroupMember.cifUser.lastName",
			"GroupMember.cifUser.userDetails.user.username"
		]

		if (!params.sort && !params.order) {
			params.sort = "cuFirstName"
			params.order = "asc"
		}

		def query = "+deleteFlag:N -GroupMember.status:1 +GroupMember.group.id:" + group.id
		if (name) {
			query += " *${name}*"
		}

		def members = GroupMember.search(query, params).results
		def array = new ArrayList()
		def map
		for(int i =0; i< members.size();i++){
			map = new HashMap();
			map.name = members[i].cifUser.firstName + " " + members[i].cifUser.lastName
			map.username = members[i].cifUser.userDetails.user.username
			array.add(map)
		}

		return array
	}

	def getActiveMemberLike(def name){
		def params = [:]
		def group = getCurrentMemberUser().group

		params.max = MAX_SEARCH
		params.properties = [
			"GroupMember.cifUser.firstName",
			"GroupMember.cifUser.lastName",
			"GroupMember.cifUser.userDetails.user.username"
		]

		if (!params.sort && !params.order) {
			params.sort = "cuFirstName"
			params.order = "asc"
		}

		def query = "+deleteFlag:N -GroupMember.cifUser.userDetails.status:3 -GroupMember.cifUser.userDetails.status:4 -GroupMember.status:1 +GroupMember.group.id:" + group.id
		if (name) {
			query += " *${name}*"
		}

		def members = GroupMember.search(query, params).results
		def array = new ArrayList()
		def map
		for(int i =0; i< members.size();i++){
			map = new HashMap();
			map.name = members[i].cifUser.firstName + " " + members[i].cifUser.lastName
			map.username = members[i].cifUser.userDetails.user.username
			array.add(map)
		}

		return array
	}

	def getMember2Like(def name){
		def params = [:]
		def cif = getCurrentCifUser().cif

		params.max = MAX_SEARCH
		params.properties = [
			"GroupMember.cifUser.firstName",
			"GroupMember.cifUser.lastName",
			"GroupMember.cifUser.userDetails.user.username"
		]

		if (!params.sort && !params.order) {
			params.sort = "cuFirstName"
			params.order = "asc"
		}

		def query = "+deleteFlag:N -GroupMember.status:1 +GroupMember.cifUser.cif.id:" + cif.id
		if (name) {
			query += " *${name}*"
		}

		def members = GroupMember.search(query, params).results
		def array = new ArrayList()
		def map
		for(int i =0; i< members.size();i++){
			map = new HashMap();
			map.name = members[i].cifUser.firstName + " " + members[i].cifUser.lastName
			map.username = members[i].cifUser.userDetails.user.username
			array.add(map)
		}

		return array
	}
}
