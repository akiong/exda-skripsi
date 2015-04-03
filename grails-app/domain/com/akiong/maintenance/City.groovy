package com.akiong.maintenance

class City {
	String city
	String description
	String deleteFlag = "N"
	
//	Province province
    static constraints = {
		city(blank:false,unique:false)
    }
	
	String toString() {
		"$city"
	}
}
