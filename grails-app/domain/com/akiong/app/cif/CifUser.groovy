package com.akiong.app.cif

import com.akiong.security.UserDetails;

import java.math.BigDecimal;

class CifUser implements Serializable {

	static auditable = true
	static searchable = {
		except = [
			"version",
			"deleted",
			"createdBy",
			"dateCreated",
			"updatedBy",
			"lastUpdated"
		]
		userDetails component: true
		cif component: true
		firstName name: 'firstName'
		firstName name: 'cuFirstName', index: 'not_analyzed', store: 'no'
	}
	
	Cif cif
	String firstName
	String lastName
	UserDetails userDetails
	String deleteFlag = "N"
	String createdBy
	Date dateCreated
	String updatedBy = ""
	Date lastUpdated
	String sysAdmin = "N"

	String finance = "N"
	String bo = "N"

	boolean deleted
	BigDecimal userLimit
	String position
	String accoutnNo

	static constraints = {
		cif(blank: false, nullable: false)
		firstName(blank: false, maxSize: 100)
		lastName(blank: false, maxSize: 100)
		userDetails(blank: false, maxSize: 100)
		createdBy(blank: false, maxSize: 50)
		dateCreated(blank: false)
		updatedBy(maxSize: 50, nullable: true)
		lastUpdated(nullable: true)
		sysAdmin(maxSize: 1, nullable: true)
		finance(maxSize: 1, nullable: true)
		bo(maxSize: 1, nullable: true)
		deleteFlag(maxSize: 1)
		accoutnNo(nullable: true, blank: true)
	}

	static transients = [
		'deleted',
		'userLimit',
		'position'
	]

	static mapping = {
		userDetails cascade: "all-delete-orphan";
		cache true
	}

	String toString() {
		"$userDetails"
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
	def onSave = { println "new CifUser inserted" // may optionally refer to newState map
	}
	def onDelete = { println "CifUser was deleted" // may optionally refer to oldState map
	}
	def onChange = { oldMap, newMap ->
		println "CifUser was changed ..."
		oldMap.each({ key, oldVal ->
			if (oldVal != newMap[key]) {
				println " * $key changed from $oldVal to " + newMap[key]
			}
		})
	}//*/
}
