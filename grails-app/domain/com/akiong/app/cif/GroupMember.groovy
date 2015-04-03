package com.akiong.app.cif

import java.util.Date;

import com.akiong.security.UserDetails;

class GroupMember implements Serializable {

	static auditable = true

	static searchable = {
		except = [
			"version",
			"createdBy",
			"dateCreated",
			"updatedBy",
			"lastUpdated",
			"pending"
		]
		cifUser component: true
		group component: true
	}
	
	CifUser cifUser
	Grup group
	String deviceId
//	MemberLimit memberLimit

	/*
	 * 1 - leader, 2 - member
	 */
	String position
	String deleteFlag = "N"
	String createdBy
	Date dateCreated
	String updatedBy = ""
	Date lastUpdated

	String pending

	/*
	 * Status
	 * 1 - Pending Create
	 * 2 - Pending Update
	 * 3 - Pending Delete
	 * 4 - Active
	 * */
	String status

	static constraints = {
		group(nullable: true, blank: true)
		deviceId(nullable: true, blank: true)
		position(nullable: true, blank: true)
		createdBy(blank: false, maxSize: 50)
		dateCreated(blank: false)
		updatedBy(maxSize: 50, nullable: true)
		lastUpdated(nullable: true)
		deleteFlag(maxSize: 1)
		pending(nullable: true, blank: true)
		status(blank: false)
	}

	static transients = ['userLimit']

	static mapping = {
		cifUser cascade: "all-delete-orphan";
		cache true
		pending type: "text"
	}

	String toString() {
		"$cifUser"
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
	def onSave = { println "new GroupMember inserted" // may optionally refer to newState map
	}
	def onDelete = { println "GroupMember was deleted" // may optionally refer to oldState map
	}
	def onChange = { oldMap, newMap ->
		println "GroupMember was changed ..."
		oldMap.each({ key, oldVal ->
			if (oldVal != newMap[key]) {
				println " * $key changed from $oldVal to " + newMap[key]
			}
		})
	}//*/
}
