package com.akiong.security

class LoginHistory implements Serializable{

	String userid
	Date loginTime
	Date logoutTime

	static constraints = {
		userid(blank: false)
		loginTime(blank: false, nullable: false)
		logoutTime(blank: true, nullable: true)
	}

	String toString() {
		"$userid"
	}

	static mapping = { cache true }
}
