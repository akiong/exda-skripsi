package com.akiong.security


class RoleMenu implements Serializable {

    static auditable = true

    Role role
    Menu menu
    String createdBy
    Date dateCreated
    String updatedBy = ""
    Date lastUpdated

    static constraints = {
        role(blank: false)
        menu(blank: false)
        createdBy(blank: false, maxSize: 50)
        dateCreated(blank: false)
        updatedBy(maxSize: 50)
        lastUpdated()
    }

    String toString() {
        "$role" + " " + "$menu"
    }

    static belongsTo = [role: Role]

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
        println "new RoleMenu inserted"
        // may optionally refer to newState map
    }
    def onDelete = {
        println "RoleMenu was deleted"
        // may optionally refer to oldState map
    }
    def onChange = { oldMap, newMap ->
        println "RoleMenu was changed ..."
        oldMap.each({ key, oldVal ->
            if (oldVal != newMap[key]) {
                println " * $key changed from $oldVal to " + newMap[key]
            }
        })
    }//*/
}
