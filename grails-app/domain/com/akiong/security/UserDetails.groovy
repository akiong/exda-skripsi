package com.akiong.security


class UserDetails implements Serializable {

	static auditable = [ignore:[
			'version',
			'lastUpdated',
			'password2',
			'isLogin',
			'ipAddress',
			'sessionId',
			'loginTime',
			'logoutTime'
		]]
	
	static searchable = {
		except = [
			"version",
			"userAlias",
			"forceChangePassword",
			"activateToken",
			"retry",
			"noOfWrongChangedPassword",
			"password2",
			"activationKey",
			"createdBy",
			"dateCreated",
			"updatedBy",
			"lastUpdated",
			"isDemo"
		]
		user component: true
	}

	String firstName
	String lastName
	User user
	String userAlias
	String email
	String mobilePhoneNo
	boolean forceChangePassword
	boolean activateToken = false
	int retry
	int noOfWrongChangedPassword
	int noOfWrongToken
	String status
	String deleteFlag = "N"
	String createdBy
	Date dateCreated
	String updatedBy = ""
	Date lastUpdated
	String password2
	String activationKey
	String newEmail

	String language
	String tid

	/*
	 * User type
	 * 1 - Back Office User
	 * 2 - Cif User
	 * */
	String userType = "1"
	/**
	 * isLogin
	 * 0 - Not Logged In
	 * 1 - Logged In
	 */
	String isLogin = "0"
	String ipAddress
	String sessionId
	Date loginTime
	Date logoutTime
	String isDemo = "N"

	static constraints = {
		firstName(blank: false, maxSize: 100)
		lastName(blank: false, maxSize: 100)
		user(blank: false)
		userAlias(blank: false, maxSize: 100)
		email(blank: false, matches: "[a-zA-Z0-9._%+-]+@(?:[a-zA-Z0-9-]+.)+[a-zA-Z]{2,4}")
		mobilePhoneNo(nullable: true, matches: "[0-9]{10,}")
		forceChangePassword(nullable: true)
		activateToken(nullable: true)
		retry(maxSize: 1, nullable: true)
		noOfWrongChangedPassword(maxSize: 1, nullable: true)
		status(maxSize: 1)
		createdBy(blank: false, maxSize: 50)
		dateCreated(blank: false)
		updatedBy(maxSize: 50, nullable: true)
		lastUpdated(nullable: true)
		password2(nullable: true)
		activationKey(nullable: true, blank: true, maxSize: 100)
		newEmail(nullable: true, blank: true)
		language(nullable: true, blank: true, maxSize: 5)
		userType(blank: true, nullable: true, maxSize: 1)
		isLogin(blank: true, nullable: true, maxSize: 1)
		ipAddress(blank: true, nullable: true, maxSize: 30)
		sessionId(blank: true, nullable: true, maxSize: 32)
		loginTime(blank: true, nullable: true)
		logoutTime(blank: true, nullable: true)
		isDemo(blank: true, nullable: true, maxSize: 1)
		tid(blank: true, nullable: true)
	}

	static mapping = { user cascade: "all-delete-orphan"; }

	String toString() {
		"$userAlias"
	}

	/**
	 *
	 */
	def beforeInsert = {
		//        createdBy = springSecurityService.principal.username
		dateCreated = new Date()
	}
	def beforeUpdate = {
		//        updatedBy = springSecurityService.principal.username
		lastUpdated = new Date()
	}

	/**
	 * Audit Logging
	 */
	def onSave = { println "new UserDetails inserted" // may optionally refer to newState map
	}
	def onDelete = { println "UserDetails was deleted" // may optionally refer to oldState map
	}
	def onChange = { oldMap, newMap ->
		println "UserDetails was changed ..."
		oldMap.each({ key, oldVal ->
			if (oldVal != newMap[key]) {
				println " * $key changed from $oldVal to " + newMap[key]
			}
		})
	}//*/
}
