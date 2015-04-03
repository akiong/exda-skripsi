package com.akiong.app.cif

class Grup implements Serializable {

	static auditable = true

	static searchable = {
		except = [
			"version",
			"createdBy",
			"dateCreated",
			"updatedBy",
			"lastUpdated",
			"location",
			"pending"
		]
		name name: 'name'
		name name: 'sortableCorpName', index: 'not_analyzed', store: 'no'
		leader component: true
		cif component: true
	}
	
	Cif cif
	String grupId
	String name
	String dbaName
	CifUser owner
	GroupMember leader
	String location

	String deleteFlag = "N"
	String createdBy
	Date dateCreated
	String updatedBy = ""
	Date lastUpdated

	String pending

	static constraints = {
		grupId(blank: false)
		name(blank: false, maxSize: 200)
		dbaName(blank: false, maxSize: 200)
		location(blank: false)
		createdBy(blank: false, maxSize: 50)
		dateCreated(blank: false)
		updatedBy(maxSize: 50, nullable: true)
		lastUpdated(nullable: true)
		deleteFlag(maxSize: 1)
		pending(nullable: true, blank: true)
	}

	String toString() {
		"${name}"
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
	def onSave = { println "new Grup inserted" // may optionally refer to newState map
	}
	def onDelete = { println "Grup was deleted" // may optionally refer to oldState map
	}
	def onChange = { oldMap, newMap ->
		println "Grup was changed ..."
		oldMap.each({ key, oldVal ->
			if (oldVal != newMap[key]) {
				println " * $key changed from $oldVal to " + newMap[key]
			}
		})
	}//*/
}
