package com.akiong.app.cif

import com.akiong.maintenance.City
import com.akiong.maintenance.Province
import com.akiong.security.UserDetails;
import com.akiong.*
class Cif implements Serializable {

	static auditable = true
	static searchable = {
		except = [
			"city",
			"version",
			"pic1",
			"pic2",
			"createdBy",
			"dateCreated",
			"updatedBy",
			"lastUpdated",
			"pending"
		
		]
		corpName index: 'not_analyzed'
//		corpName name: 'corpName'
//		corpName name: 'sortableName', index: 'not_analyzed', store: 'no'
//		cifId name: 'cifId'
//		cifId name: 'sortableCorpId', index: 'not_analyzed', store: 'no'
	}
	
	String cifId
	String corpName
	String address1
	String address2
	String pic1
	String pic2
	City city
	Date joinDate
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

	//temp
	UserDetails picc1
	UserDetails picc2
	String demo = "N"
	
//	RateType rateType

	static constraints = {
		corpName(blank: false, maxSize: 200)
		address1(blank: false)
		pic1(blank: false)
		pic2(blank: false)
		joinDate(blank: false)
		createdBy(blank: false, maxSize: 50)
		dateCreated(blank: false)
		updatedBy(maxSize: 50, nullable: true)
		lastUpdated(nullable: true)
		pending(nullable: true, blank: true)
		status(blank: false)
		deleteFlag(maxSize: 1)
		demo(nullable: true, maxSize: 1)
	}

	static transients = ['picc1', 'picc2']

	String toString() {
		"${corpName}"
	}

	static mapping = {
		cache true
		pending type: "text"
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
	def onSave = { println "new Cif inserted" // may optionally refer to newState map
	}
	def onDelete = { println "Cif was deleted" // may optionally refer to oldState map
	}
	def onChange = { oldMap, newMap ->
		println "Cif was changed ..."
		oldMap.each({ key, oldVal ->
			if (oldVal != newMap[key]) {
				println " * $key changed from $oldVal to " + newMap[key]
			}
		})
	}//*/
}
