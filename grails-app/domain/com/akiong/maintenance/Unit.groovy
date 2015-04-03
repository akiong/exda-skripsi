package com.akiong.maintenance

import java.util.Date;

class Unit {
	String name
	String description
	String deleteFlag = "N"
	
	Date dateCreated
	Date lastUpdated
	
    static constraints = {
		description(nullable:true,blank:true)
    }
	
	def beforeInsert = {
		//        createdBy = springSecurityService.principal.username
		dateCreated = new Date()
	}
	def beforeUpdate = {
		//        updatedBy = springSecurityService.principal.username
		lastUpdated = new Date()
	}
}
