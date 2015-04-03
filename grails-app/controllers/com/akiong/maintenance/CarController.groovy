package com.akiong.maintenance

import grails.converters.JSON

class CarController {
	
	def CarService
	
    def index={
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		
		if(!params.sort && !params.order) {
			params.sort = "noPlat"
			params.order = "desc"
		}
		def c = Car.createCriteria()
		def results = c.list(params) {
			if(params.noPlat) {
				ilike("noPlat", "%${params.noPlat}%")
			}
			eq("deleteFlag", "N")
		}
		[carList: results, carTotal:results.totalCount, noPlat: params.noPlat?:"",description:params.description?:""]
	}
	
	def create={
		
	}
	
	def save={		
		def results = CarService.createNewCar(params)
		flash.message = "${message(code: 'default.created.message', args: [message(code: 'car.label', default: 'Car'),results.noPlat])}"
		redirect(action: "show", id: results.id)
	}
	
	def show={
		def getCar = Car.get(params.id)
		println "getCar = "+getCar
		[carInstance:getCar]
	}
	
	def edit={
		def getCar = Car.get(params.id)
		[carInstance:getCar]
		
	}
	def update={
		println "params = "+params
		def results = CarService.update(params)		
		flash.message = "${message(code: 'default.updated.message', args: [message(code: 'car.label', default: 'Car'),results.noPlat])}"
		redirect(action: "show", id: results.id)
	}
	
	def delete={
		def results = CarService.hapus(params)
		flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'car.label', default: 'Car'),results.noPlat])}"		
		redirect(action: "index")
	}
	
	def replaceCar={
		def results = CarService.replaced(params)
		flash.message = "${message(code: 'default.replaced.message', args: [message(code: 'car.label', default: 'Car'),results.noPlat])}"		
		redirect(action: "show", id: results.id)
	}
	
	def findNoPlatByName = {
		def car = Car.findByNoPlat(params.noPlat)
		if (car) {
			if (!params.id) {
				if (car.deleteFlag.equals("N"))
					response.status = 405
				else
					response.status = 409
			}
			else {
				if (params.id.equals(car.id.toString()))
					response.status = 200
				else{
					if (car.deleteFlag.equals("N"))
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
