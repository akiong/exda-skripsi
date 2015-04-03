package com.akiong

import java.security.KeyPair

import org.slf4j.Logger
import org.slf4j.LoggerFactory

//import com.akiong.app.cif.Absensi
import com.akiong.app.cif.CifUser
import com.akiong.app.cif.GroupMember
import com.akiong.maintenance.SystemConfiguration
//import com.akiong.maintenance.Terminal
import com.akiong.security.LoginHistory
import com.akiong.security.User
import com.akiong.security.UserDetails
import com.akiong.helper.Common
import com.akiong.helper.JCryptionUtil

class CifService {

	static transactional = true

	def passwordGeneratorService
	def springSecurityService
	def messageSource

	private static final Logger logger = LoggerFactory.getLogger(this)

	/**
	 * Send email containing password for Bank User
	 * @param userId
	 * @param emailTo
	 * @param name
	 * @param password
	 */
	void emailPasswordBO(String userId, String emailTo, String name, String password) {
		def locale = new Locale(Common.DEFAULT_LANGUAGE)

		String emailSubject = messageSource.getMessage("register.email.co.title", null, locale)
		StringBuffer contentSB = new StringBuffer();
		contentSB.append("Dear ").append(name).append(",\n\n");
		contentSB.append(messageSource.getMessage("register.email.bo.message1", null, locale)).append("\n\n").append(messageSource.getMessage("register.email.co.message8", null, locale)).append(": " + userId).append("\n").append(messageSource.getMessage("register.email.co.message9", null, locale)).append(": " + password).append("\n\n").append(messageSource.getMessage("register.email.co.message12", null, locale))
		String content = contentSB.toString();
		try {
			sendQueueJMSMessage("queue.sendEmailCC", [emailTo: emailTo, emailSubject: emailSubject, content: content])
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e)
		}
	}

	/**
	 * Send email containing reseted password for Bank User
	 * @param emailTo
	 * @param name
	 * @param password
	 */
	void emailResetedPasswordBO(String emailTo, String name, String password, String language = null) {
		def locale
		if(language) {
			locale= new Locale(language)
		}
		else{
			locale= new Locale(Common.DEFAULT_LANGUAGE)
		}

		String emailSubject = messageSource.getMessage("reset.password.title", null, locale)
		StringBuffer contentSB = new StringBuffer();
		contentSB.append("Dear ").append(name).append(",\n\n");
		contentSB.append(messageSource.getMessage("reset.email.bo.message1", null, locale)).append("\n").append(messageSource.getMessage("reset.email.bo.message2", null, locale)).append("\n\n").append(messageSource.getMessage("register.email.co.message9", null, locale)).append(": " + password).append("\n\n").append(messageSource.getMessage("register.email.co.message12", null, locale))
		String content = contentSB.toString();
		try {
			sendQueueJMSMessage("queue.sendEmailCC", [emailTo: emailTo, emailSubject: emailSubject, content: content])
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e)
		}
	}

	/**
	 * Change Password for Back Office User
	 * @param username
	 * @param oldPass Old Password
	 * @param newPassword New Password
	 * @return return true if password is changed successfully, return false otherwise
	 */
	def changePasswordBO(def username, def oldPass, def newPassword) {
		//change 0 = false, change 1 = true, change 2 = invalid new pass, change 3 = lock

		def request = org.codehaus.groovy.grails.plugins.springsecurity.SecurityRequestHolder.getRequest()
		def session = request.getSession(false)
		if (session != null) {
			KeyPair keys = (KeyPair) session.getAttribute("keys")

			if(keys != null){
				JCryptionUtil jCryptionUtil = new JCryptionUtil();
				if(oldPass != null){
					oldPass = jCryptionUtil.decrypt(oldPass, keys);
				}
				if(newPassword != null){
					newPassword = jCryptionUtil.decrypt(newPassword, keys);
				}
			}
		}

		int change = 0
		boolean valid = true
		def userInstance = User.findByUsername(username)
		String oldPassword_input = springSecurityService.encodePassword(oldPass)
		if (userInstance && userInstance.password.equals(oldPassword_input)) {
			def passConfig = SystemConfiguration.findByName("Password")
			boolean canChangePass = true
			def encodedPassword
			if(passConfig){
				canChangePass = (newPassword ==~ passConfig.description)
				if(canChangePass){
					encodedPassword = springSecurityService.encodePassword(newPassword)
					//					def repearConfig = SystemConfiguration.findByName("Repeat after")
					//					if(repearConfig){
					//						int max = Integer.parseInt(repearConfig.description) - 1
					//						def pass = PasswordHistory.executeQuery("SELECT password FROM PasswordHistory WHERE user = ? and status = ? order by id desc", [userInstance, "1"], [max:max, sort:"id", order:"desc", offset:0])
					//						if(pass.contains(encodedPassword)){
					//							canChangePass = false
					//						}
					//					}
				}
			}

			if(canChangePass){
				def userDetailsInstance = UserDetails.findByUser(userInstance)
				if (userDetailsInstance) {
					userInstance.password = encodedPassword
					userDetailsInstance.forceChangePassword = false;
					userDetailsInstance.status = Common.ACTIVE_USER_STATUS
					userInstance.save(failOnError: true)
					userDetailsInstance.save(failOnError: true)
					change = 1

					def cifUser = CifUser.findByUserDetails(userDetailsInstance)
					if (cifUser) {
						cifUser.reindex()
						if(cifUser.sysAdmin.equals("N")){
							def groupMember = GroupMember.findByCifUser(cifUser)
							if (groupMember) {
								groupMember.reindex()
							}
						}
					}
				}

				//				def passOnlyType = AuthenticationType.findByName('PassOnly')
				//				if (passOnlyType) {
				//					new PasswordHistory(user: userInstance, password: userInstance.password, authenticationType: passOnlyType, status: '1', createdBy: springSecurityService.principal.username).save(failOnError: true)
				//				}
			}
			else{
				change = 2
			}
		}
		else{
			valid = false
		}

		if(!valid){
			def userDetailsInstance
			if(userInstance){
				userDetailsInstance = UserDetails.findByUser(userInstance)
			}
			if(userDetailsInstance){
				if (userDetailsInstance.noOfWrongChangedPassword < (Common.MAX_TRY_CHANGE - 1)) {
					userDetailsInstance.noOfWrongChangedPassword += 1
					userDetailsInstance.save(failOnError: true)
				}
				else if (userDetailsInstance.noOfWrongChangedPassword == (Common.MAX_TRY_CHANGE - 1)) {
					userDetailsInstance.noOfWrongChangedPassword += 1
					userDetailsInstance.status = Common.LOCK_USER_STATUS
					userDetailsInstance.save(failOnError: true)
					blockUser(userInstance)
					change = 3

					def cifUser = CifUser.findByUserDetails(userDetailsInstance)
					if (cifUser) {
						cifUser.reindex()
						if(cifUser.sysAdmin.equals("N")){
							def groupMember = GroupMember.findByCifUser(cifUser)
							if (groupMember) {
								groupMember.reindex()
							}
						}
					}
				}
				else {
					change = 3
				}
			}
		}
		return change;
	}

	/**
	 * Add retry flag when user login unsuccessfully
	 * @param username
	 * @return return true if user is blocked, return false otherwise
	 */
	def addTryLogin(def username) {
		def userInstance = User.findByUsername(username)
		def block = false
		if (userInstance) {
			def userDetailsInstance = UserDetails.findByUser(userInstance)
			if (userDetailsInstance) {
				if (userDetailsInstance.status == Common.INACTIVE_USER_STATUS || userDetailsInstance.status == Common.ACTIVE_USER_STATUS) {
					if (userDetailsInstance.retry < (Common.MAX_TRY - 1)) {
						userDetailsInstance.retry += 1
						userDetailsInstance.save(failOnError: true)
					}
					else if (userDetailsInstance.retry == (Common.MAX_TRY - 1)) {
						userDetailsInstance.retry += 1
						userDetailsInstance.status = Common.BLOCK_USER_STATUS
						userDetailsInstance.save(failOnError: true)
						blockUser(userInstance)
						block = true
					}
					else {
						block = true
					}
				}
				else {
					block = true
				}

				if (block == true) {
					def cifUser = CifUser.findByUserDetails(userDetailsInstance)
					if (cifUser) {
						cifUser.reindex()
						if(cifUser.sysAdmin.equals("N")){
							def groupMember = GroupMember.findByCifUser(cifUser)
							if (groupMember) {
								groupMember.reindex()
							}
						}
					}
				}
			}
		}
		return block
	}

	/**
	 * Block user
	 * @param userInstance User Object
	 */
	def blockUser(def userInstance) {
		userInstance.accountExpired = true
		userInstance.accountLocked = false
		userInstance.passwordExpired = false
		userInstance.enabled = false
		userInstance.save(failOnError: true)
	}

	/**
	 * Block user and change user status to block
	 * @param userInstance User Object
	 */
	def blockUserStatus(def userInstance) {
		blockUser(userInstance)
		def userDetailsInstance = UserDetails.findByUser(userInstance)
		if (userDetailsInstance) {
			userDetailsInstance.status = Common.BLOCK_USER_STATUS
			userDetailsInstance.save(failOnError: true)
			def cifUser = CifUser.findByUserDetails(userDetailsInstance)
			if (cifUser) {
				cifUser.reindex()
				if(cifUser.sysAdmin.equals("N")){
					def groupMember = GroupMember.findByCifUser(cifUser)
					if (groupMember) {
						groupMember.reindex()
					}
				}
			}
		}
	}

	def blockUserStatus2(def userInstance, def cifInstance) {

		def userDetailsInstance = UserDetails.findByUser(userInstance)
		if (userDetailsInstance) {
			def cifUser = CifUser.findByUserDetails(userDetailsInstance)
			if (cifUser) {
				if(cifUser.cif?.id != cifInstance.id){
					throw new SecurityException("Not allowed to block this user")
				}
				blockUser(userInstance)
				userDetailsInstance.status = Common.BLOCK_USER_STATUS
				userDetailsInstance.save(failOnError: true)
				cifUser.reindex()
				if(cifUser.sysAdmin.equals("N")){
					def groupMember = GroupMember.findByCifUser(cifUser)
					if (groupMember) {
						groupMember.reindex()
					}
				}
			}
		}
	}

	/**
	 * Reset user second password
	 * @param userDetailsInstance UserDetails Object
	 */
	def resetPassword2(def userDetailsInstance) {
		userDetailsInstance.forceChangePassword = true
		userDetailsInstance.retry = 0
		userDetailsInstance.noOfWrongChangedPassword = 0
		userDetailsInstance.status = Common.INACTIVE_USER_STATUS
		userDetailsInstance.save(failOnError: true)
	}

	/**
	 * Reset user (reset password, reset second password, and release user)
	 * @param userId
	 * @return return true if reset successful, return false otherwise
	 */
	def resetUser(def userId) {
		def userInstance = User.get(userId)
		def reset = false
		if (userInstance) {
			def userDetailsInstance = UserDetails.findByUser(userInstance)
			if(userDetailsInstance.activationKey != null){
				throw new SecurityException("Cannot reset user detail status because new email hasn't been activated")
			}

			def passwordList = passwordGeneratorService.generateRegistrationCode()
			if (passwordList != null && passwordList.size() > 1) {
				userInstance.password = springSecurityService.encodePassword(passwordList[0] + passwordList[1])
				releaseUser(userInstance)
				if (userDetailsInstance) {
					resetPassword2(userDetailsInstance)
					emailResetedPasswordBO(userDetailsInstance.email, userDetailsInstance.firstName + " " + userDetailsInstance.lastName, passwordList[0] + passwordList[1], userDetailsInstance.language)
					reset = true

					def cifUser = CifUser.findByUserDetails(userDetailsInstance)
					if (cifUser) {
						cifUser.reindex()
						if(cifUser.sysAdmin.equals("N")){
							def groupMember = GroupMember.findByCifUser(cifUser)
							if (groupMember) {
								groupMember.reindex()
							}
						}
					}
				}
			}
		}
		return reset
	}

	def resetUser2(def userId, def cifInstance) {
		def userInstance = User.get(userId)
		def reset = false
		if (userInstance) {
			def userDetailsInstance = UserDetails.findByUser(userInstance)
			def cifUser = CifUser.findByUserDetails(userDetailsInstance)
			if (cifUser) {
				if(cifUser.cif?.id != cifInstance.id){
					throw new SecurityException("Not allowed to reset this user")
				}

				def passwordList = passwordGeneratorService.generateRegistrationCode()
				if (passwordList != null && passwordList.size() > 1) {
					userInstance.password = springSecurityService.encodePassword(passwordList[0] + passwordList[1])
					releaseUser(userInstance)
					if (userDetailsInstance) {
						resetPassword2(userDetailsInstance)
						emailResetedPasswordBO(userDetailsInstance.email, userDetailsInstance.firstName + " " + userDetailsInstance.lastName, passwordList[0] + passwordList[1], userDetailsInstance.language)
						reset = true
					}
				}
				cifUser.reindex()
				if(cifUser.sysAdmin.equals("N")){
					def groupMember = GroupMember.findByCifUser(cifUser)
					if (groupMember) {
						groupMember.reindex()
					}
				}
			}
		}
		return reset
	}

	/**
	 * Release/Unblock User
	 * @param userInstance User Object
	 */
	def releaseUser(def userInstance) {
		userInstance.accountExpired = false
		userInstance.accountLocked = false
		userInstance.passwordExpired = false
		userInstance.enabled = true
		userInstance.save(failOnError: true)
	}

	/**
	 * Reset retry and noOfWrongChangedPassword flag when login successful
	 * @param userDetailsInstance UserDetails Object
	 */
	def resetTryLogin(def userDetailsInstance) {
		userDetailsInstance.retry = 0
		userDetailsInstance.noOfWrongChangedPassword = 0
		userDetailsInstance.save(failOnError: true)
	}

	/**
	 * Reset retry and noOfWrongChangedPassword flag when login successful save additional info
	 * @param userDetailsInstance UserDetails Object
	 */
	def resetTryLoginAddInfo(def userDetailsInstance, def ipAddress, def sessionId, def tid) {
		userDetailsInstance.retry = 0
		userDetailsInstance.noOfWrongChangedPassword = 0
		userDetailsInstance.isLogin = "1"
		userDetailsInstance.ipAddress = ipAddress
		userDetailsInstance.sessionId = sessionId
		userDetailsInstance.loginTime = new Date()
		if(tid){
			userDetailsInstance.tid = tid
		}
		userDetailsInstance.save(failOnError: true)

		def loginH = new LoginHistory(userid: springSecurityService.authentication.name, loginTime: userDetailsInstance.loginTime)
		loginH.save(failOnError: true)

//		if(tid){
//			def group = Terminal.executeQuery("SELECT t.group from Terminal t WHERE t.tid = ? AND t.deleteFlag = 'N' AND t.status != '1'", [tid])[0]
//			def absensi = new Absensi()
//			absensi.tid = tid
//			absensi.username = springSecurityService.authentication.name
//			absensi.group = group
//			absensi.loginDate = new Date()
//			absensi.save(failOnError : true)
//		}
	}

	def changeDefaultLanguage(String language){
		UserDetails.executeUpdate("update UserDetails set language = ? where user.id=?", [
			language,
			springSecurityService.principal.id
		])


		def request = org.codehaus.groovy.grails.plugins.springsecurity.SecurityRequestHolder.getRequest()
		def session = request.getSession(false)
		session[org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME] = new Locale(language)
	}
}
