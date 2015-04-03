package com.akiong.maintenance

import java.io.Serializable;
import java.util.Date;

class RegisterItem implements Serializable{

	String noNota
	
	Date dateReceivedItem
		
	String senderName
	String noTelpSender
	String noHpSender
	String emailSender
	String addressSender
	String descriptionSender
	
	String receiverName
	String noTlpReceiver
	String noHpReceiver
	String emailReceiver
	String addressReceiver
	String descriptionReceiver
	
	BigDecimal totalAmount
	
	String isPay = "Y" 
	/*
	 * Y = sudah bayar
	 * N = belum bayar
	 * */
	
	String useDefault = "Y" //jika ya. maka diambil dari satuan/unit per harganya jika N, brrt hitung sendiri
	/*
	 * Y = hitungannya /harganya diambil dari satuan/unit
	 * N = harganya isi sendiri
	 * */
	
	Date dateCreated
	Date lastUpdated
	
	String createdBy
	
    static constraints = {
		noTelpSender(nullable:true,blank:true)
		noHpSender(nullable:true,blank:true)
		emailSender(nullable:true,blank:true)
		addressSender(nullable:true,blank:true)
		descriptionSender(nullable:true,blank:true)
		
		noTlpReceiver(nullable:true,blank:true)
		noHpReceiver(nullable:true,blank:true)
		emailReceiver(nullable:true,blank:true)
		addressReceiver(nullable:true,blank:true)
		descriptionReceiver(nullable:true,blank:true)
		
		createdBy(blank: false, maxSize: 50)
		lastUpdated(nullable: true)
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
