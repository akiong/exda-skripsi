package com.akiong.maintenance


class SystemConfiguration implements Serializable {

    static auditable = true

    String name
    String description = ""
    int idx = 99
    String createdBy
    Date dateCreated
    String updatedBy = ""
    Date lastUpdated

    static constraints = {
        name(blank: false, maxSize: 100)
        description(blank: true, nullable: true, maxSize: 500)
        idx(maxSize: 3)
        createdBy(blank: false, maxSize: 50)
        dateCreated(blank: false)
        updatedBy(blank: true, nullable: true, maxSize: 50)
        lastUpdated(blank: true)
    }

    String toString() {
        "$name"
    }

    static mapping = { cache true }

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
        println "new SystemConfiguration inserted" // may optionally refer to newState map
    }
    def onDelete = {
        println "SystemConfiguration was deleted" // may optionally refer to oldState map
    }
    def onChange = { oldMap, newMap ->
        println "SystemConfiguration was changed ..."
        oldMap.each({ key, oldVal ->
            if (oldVal != newMap[key]) {
                println " * $key changed from $oldVal to " + newMap[key]
            }
        })
    }//*/
}
