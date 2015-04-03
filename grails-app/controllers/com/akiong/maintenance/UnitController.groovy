package com.akiong.maintenance

import grails.converters.JSON

class UnitController {

	def UnitService
	
    def index={
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		
		if(!params.sort && !params.order) {
			params.sort = "name"
			params.order = "desc"
		}
		def c = Unit.createCriteria()
		def results = c.list(params) {
			if(params.name) {
				ilike("name", "%${params.name}%")
			}
			eq("deleteFlag", "N")
		}
		[unitList: results, unitTotal:results.totalCount, name: params.name?:"",description:params.description?:""]
	}
	
	def create={
		
	}
	
	def save={		
		def results = UnitService.createNewUnit(params)
		flash.message = "${message(code: 'default.created.message', args: [message(code: 'unit.label', default: 'Unit'),results.name])}"
		redirect(action: "show", id: results.id)
	}
	
	def show={
		def getUnit = Unit.get(params.id)
		[unitInstance:getUnit]
	}
	
	def edit={
		def getUnit = Unit.get(params.id)		
		[unitInstance:getUnit]		
	}
	
	def update={
		def results = UnitService.update(params)		
		flash.message = "${message(code: 'default.updated.message', args: [message(code: 'unit.label', default: 'Unit'),results.name])}"
		redirect(action:"show", id:results.id)
	}
	
	def delete={
		def results = UnitService.delete(params)
		flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'unit.label', default: 'Unit'),results.name])}"
		redirect(action:"index")
	}
	
	def replaceUnit={
		def results = UnitService.replace(params)
		flash.message = "${message(code: 'default.replaced.message', args: [message(code: 'unit.label', default: 'Unit'),results.name])}"
		
		redirect(action: "show", id: results.id)
	}
	
	def findUnitByName = {
		def unit = Unit.findByName(params.name)
		if (unit) {
			if (!params.id) {
				if (unit.deleteFlag.equals("N"))
					response.status = 405
				else
					response.status = 409
			}
			else {
				if (params.id.equals(unit.id.toString()))
					response.status = 200
				else{
					if (unit.deleteFlag.equals("N"))
						response.status = 405
					else
						response.status = 409
				}
			}
		}
		else {
			response.status = 200
		}

		if (params.callback) {
			render "${params.callback}(${params as JSON})"
		}
		else {
			render "${params as JSON}"
		}
	}
}
