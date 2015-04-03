package com.akiong

import com.akiong.maintenance.Unit

class UnitService {

    def serviceMethod() {

    }
	
	def createNewUnit(def params){
		def newUnit = new Unit(params)
		newUnit.save(failOnError:true)
		return newUnit
	}
	
	def update(def params){
		def getUnit = Unit.get(params.id)
		getUnit.name = params.name
		getUnit.description = params.description
		getUnit.save(failOnError:true)
		return getUnit
	}
	
	def delete(def params){
		def deleteUnit = Unit.get(params.id)
		deleteUnit.deleteFlag = "Y"
		deleteUnit.save(failOnError:true)
		return deleteUnit
	}
	
	def replace(def params){
		def unit = Unit.findByName(params.name)
		unit.deleteFlag = 'N'
		unit.description = params.description
		unit.save(failOnError:true)
		return unit
	}
}
