package com.akiong

import com.akiong.maintenance.Car

class CarService {

    def serviceMethod() {

    }
	
	def createNewCar(def params){
		def newCar = new Car(params)
		newCar.save(failOnError:true)
		return newCar
	}
	
	def update(def params){
		def updateCar = Car.get(params.id)
		updateCar.properties = params
		updateCar.save(failOnError:true)
		return updateCar
	}
	
	def hapus(def params){
		def deleteCar = Car.get(params.id)
		deleteCar.deleteFlag = 'Y'
		deleteCar.save(failOnError:true)
		return deleteCar
	}
	
	def replaced(def params){
		def car = Car.findByNoPlat(params.noPlat)
		car.deleteFlag = 'N'
		car.description = params.description
		car.save(failOnError:true)
		return car
	}
}
