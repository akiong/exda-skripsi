package com.akiong.maintenance
import grails.converters.JSON
import org.slf4j.Loggerimport com.akiong.BackOfficeController
import org.slf4j.LoggerFactory

class CityController extends BackOfficeController {
	def CityService
	static allowedMethods = [save: "POST", update: "POST", delete: "POST", saveTree: "POST"]
	def index= {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)

		if(!params.sort && !params.order) {
			params.sort = "city"
			params.order = "desc"
		}
		def c = City.createCriteria()
		def results = c.list(params) {
			if(params.city) {
				ilike("city", "%${params.city}%")
			}
			eq("deleteFlag", "N")
		}
		[citydetaillist: results,citydetaillisttotal:results.totalCount, city: params.city?:"",description:params.description?:""]
	}
	def create={		
	}

	def save={
		def cityInstance = new City()
		cityInstance.properties = params
		try{
			def results = CityService.savecity(cityInstance)
			flash.message = "${message(code: 'default.created.message', args: [message(code: 'city.label', default: 'City'),cityInstance.city])}"
			redirect(action: "show", id: results.id)
		}
		catch(Exception e) {
			cityInstance.validate()
			if(e.getMessage().equals("Host Duplicate")){
				cityInstance.errors.rejectValue("createdBy", "default.duplicate.label", [
					message(code: 'host.label')]
				as Object[], e.getMessage())
			}
			else{
				flash.message = "${message(code: 'default.null.message', args: [message(code: 'city.label', default: 'City'),cityInstance.city])}"
				render(view: "create",model :[city:params.city,description:params.description] )
			}
		}
	}
	def show={
		def lihateditinstance = City.get(params.id)
		[cityInstance:lihateditinstance]
	}
	def delete={
		CityService.hapus(params)
		flash.message = "${message(code: 'deleted.message.label', args: [message(code: 'city.label', default: 'City')])}"
		redirect(action:"index")
	}

	def edit={
		def editinstance = City.get(params.id)
		def provinceList = Province.findAllByDeleteFlag('N')
		[cityInstance:editinstance,provinceList:provinceList]
	}
	def update={
		def results = CityService.updatee(params)
		flash.message = "${message(code: 'default.updated.message', args: [message(code: 'city.label', default: 'City'),results.city])}"
		redirect(action:"show",id:results.id)
	}

	def cekavaiable(){
		def available
		if(City.findByCity(params.id)) {
			available = false
		}
		else {
			available = true
		}
		response.contentType = "application/json"
		render """{"available":${available}}"""
	}

	def findCityByName = {
		def city = City.findByCity(params.city)
		if (city) {
			if (!params.id) {
				if (city.deleteFlag.equals("N"))
					response.status = 405
				else
					response.status = 409
			}
			else {
				if (params.id.equals(city.id.toString()))
					response.status = 200
				else{
					if (city.deleteFlag.equals("N"))
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

	def replace={
		def instancer = City.findByCityAndDeleteFlag(params.city, "Y")
		def replacecity = City.get(instancer.id)
		replacecity.description = params.description
		replacecity.deleteFlag = "N"
		replacecity.save(failOnError:true)
		redirect (action:"show",id:replacecity.id)
	}
}
