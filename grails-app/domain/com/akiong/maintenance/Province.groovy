package com.akiong.maintenance

class Province {
	String province
	String description
	String deleteFlag = "N"
	static constraints = {
		province(blank:false,unique:false)
	}
	
	String toString() {
		"$province"
	}
}
