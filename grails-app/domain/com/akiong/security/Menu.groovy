package com.akiong.security


class Menu implements Serializable {

    static auditable = true

    String name
    String description = ""
    String url
    int sequence
    int idx = 99
    String createdBy
    Date dateCreated
    String updatedBy = ""
    Date lastUpdated
    Menu parentMenu

    static constraints = {
        name(blank: false, maxSize: 100)
        description(maxSize: 500)
        url(blank: true, maxSize: 500)
        parentMenu(blank: true)
        sequence(maxSize: 3)
        idx(maxSize: 3)
        createdBy(blank: false, maxSize: 50)
        dateCreated(blank: false)
        updatedBy(maxSize: 50)
        lastUpdated()
    }
	
	String toString() {
		"$name"
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
    def onSave = {
        println "new Menu inserted"
        // may optionally refer to newState map
    }
    def onDelete = {
        println "Menu was deleted"
        // may optionally refer to oldState map
    }
    def onChange = { oldMap, newMap ->
        println "Menu was changed ..."
        oldMap.each({ key, oldVal ->
            if (oldVal != newMap[key]) {
                println " * $key changed from $oldVal to " + newMap[key]
            }
        })
    }//*/
}
