package com.akiong

import com.akiong.maintenance.City
import com.akiong.maintenance.Province;


class CityService {

	static transactional = true

	def savecity(def cityInstance ){
		cityInstance.save(failOnError : true)
		return cityInstance
	}
	def hapus(def paramss){
		def deleteinstance = City.get(paramss.id)
		deleteinstance.deleteFlag = "Y"
		deleteinstance.save(failOnError:true)
		return paramss
	}
	def updatee(def params){
		def instancer = City.get(params.id)
		instancer.city = params.city
		instancer.description = params.description
		instancer.save(failOnError:true)
		return params
	}
}
