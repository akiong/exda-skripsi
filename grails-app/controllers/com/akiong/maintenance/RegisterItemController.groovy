package com.akiong.maintenance

class RegisterItemController {

    def index= {
		
	}
	
	def create={
		[unitList:Unit.findAllByDeleteFlag('N')]
	}
}
