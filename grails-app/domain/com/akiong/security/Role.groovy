package com.akiong.security

import java.io.Serializable;

class Role implements Serializable {
	
	static searchable = {
		except = ['version', 'name', 'type']
		authority name: 'authority'
		authority name: 'sortableAuthority', index: 'not_analyzed', store: 'no'
	}

	String id
    String name
    String authority
    String type
    String isSystem = "N"

	static mapping = {
        cache true
        id generator: 'assigned'
//        roleMenus cascade: "all-delete-orphan"
    }

    static constraints = {
        authority blank: false, unique: true
        name blank: false
        type nullable: true
        isSystem nullable: false, maxSize: 1
    }

    String toString() {
        return authority
    }

    static hasMany = [roleMenus: RoleMenu]
}
