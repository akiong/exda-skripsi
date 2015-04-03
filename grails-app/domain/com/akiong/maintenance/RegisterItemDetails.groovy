package com.akiong.maintenance

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

class RegisterItemDetails implements Serializable{

    RegisterItem registerItem
	
	int muchItem 
	Unit unit 
	BigDecimal itemAmount 
	String conditionItem
	
	Date dateCreated
	Date lastUpdated
	
    static constraints = {
		conditionItem(nullable:true,blank:true)
		itemAmount(nullable: false, blank: false, min: new BigDecimal('0.0'), max: new BigDecimal('99999999999999999.99'), scale: 2)
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
