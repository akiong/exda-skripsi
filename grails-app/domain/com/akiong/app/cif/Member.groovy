package com.akiong.app.cif

import java.util.Date;


class Member implements Serializable {

	String firstName
	String lastName
	String mobile
	String email
	String religion
	Date dob
	String imsi
	String tempimsi
	String imei
	String devideId
	String tokenId
	String regId
	String deleteFlag = "N"

	static constraints = {
		imsi(nullable: true, blank: true)
		tempimsi(nullable: true, blank: true)
		imei(nullable: true, blank: true)
		devideId(nullable: true, blank: true)
		tokenId(nullable: true, blank: true)
		deleteFlag(maxSize: 1)
		regId(nullable: true, blank: true)
	}
}
