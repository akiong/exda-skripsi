package com.akiong.security


class User implements Serializable {

	static searchable = [only: ['username']]
	
	String username
	String password
	boolean enabled
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired

	static constraints = {
		username blank: false, unique: true, matches: "[a-zA-Z0-9]+", maxSize: 20
		password blank: false
	}

	static mapping = {
		password column: '`password`'
	}

	Set<Role> getAuthorities() {
		UserRole.findAllByUser(this).collect { it.role } as Set
	}
	
	String toString() {
		return username
	}
}
