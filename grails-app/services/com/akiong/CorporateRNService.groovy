package com.akiong

import grails.converters.JSON

import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

import com.akiong.app.cif.Cif
import com.akiong.app.cif.CifUser
import com.akiong.app.cif.GroupMember
import com.akiong.app.cif.Grup
//import com.akiong.maintenance.Terminal
import com.akiong.security.UserDetails
import com.akiong.security.UserRole
import com.akiong.helper.Common

class CorporateRNService {

	static transactional = true

	def passwordGeneratorService
	def springSecurityService
	def corporateAcitiviService
	def activitiService
	def cifService
	def corporateUserService
	def messageSource
	def sequenceGeneratorService
	def virtualAccountService

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	def approveCorporate(def taskId, def username, def variables = null){
		if(!variables){
			variables = corporateAcitiviService.checkPermision(taskId, username)
		}
		else{
			corporateAcitiviService.checkUserAccess(variables, username)
		}
		def cifInstance = Cif.get(variables["id"])

		if(variables["type"].equals("create")){
			cifInstance.updatedBy = username
			cifInstance.pending = null
			cifInstance.status = Common.STATUS_ACTIVE
			cifInstance.save(failOnError: true)

			//			copySPtoCif(cifInstance, username)

			def pic1 = UserDetails.executeQuery("SELECT ud FROM UserDetails ud WHERE ud.user.username = ?",[cifInstance.pic1])[0]
			def passwordList = passwordGeneratorService.generateRegistrationCode()
			pic1.user.enabled = 1
			pic1.user.password = springSecurityService.encodePassword(passwordList[0] + passwordList[1])
			if (pic1.email) {
				cifService.emailPasswordBO(pic1.user.username, pic1.email, pic1.firstName + " " + pic1.lastName, passwordList[0] + passwordList[1])
			}
			pic1.save(failOnError: true)

			def pic2 = UserDetails.executeQuery("SELECT ud FROM UserDetails ud WHERE ud.user.username = ?",[cifInstance.pic2])[0]
			passwordList = passwordGeneratorService.generateRegistrationCode()
			pic2.user.enabled = 1
			pic2.user.password = springSecurityService.encodePassword(passwordList[0] + passwordList[1])
			if (pic2.email) {
				cifService.emailPasswordBO(pic2.user.username, pic2.email, pic2.firstName + " " + pic2.lastName, passwordList[0] + passwordList[1])
			}
			pic2.save(failOnError: true)
		}

		activitiService.claimTask(taskId, username)
		def params = [:]
		params.approved = true
		params.enoughApproval = true
		params.releaser = false
		activitiService.completeTask(taskId, params)
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	def rejectCorporate(def taskId, def username, def variables = null){
		if(!variables){
			variables = corporateAcitiviService.checkPermision(taskId, username)
		}
		else{
			corporateAcitiviService.checkUserAccess(variables, username)
		}

		def cifInstance = Cif.get(variables["id"])
		if(variables["type"].equals("create")){
			def cifUsers = CifUser.findAllByCifAndDeleteFlag(cifInstance, "N")
			def user
			def det
			cifUsers.each {
				user = it.userDetails.user
				it.delete(failOnError: true)
				UserRole.removeAll(user)
				user.delete(failOnError: true)
			}
			cifInstance.delete(failOnError: true)
		}
		else{
			cifInstance.updatedBy = username
			cifInstance.pending = null
			cifInstance.status = Common.STATUS_ACTIVE
			cifInstance.save(failOnError: true)
		}

		activitiService.claimTask(taskId, username)
		def params = [:]
		params.approved = false
		params.enoughApproval = true
		params.releaser = false
		activitiService.completeTask(taskId, params)
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	def approveMember(def taskId, def username, def userLimit, def variables = null){
		if(!variables){
			variables = corporateAcitiviService.checkPermision(taskId, username)
		}
		else{
			corporateAcitiviService.checkUserAccess(variables, username)
		}
		def member = GroupMember.get(variables["id"])
		def groupId = member.group.id
		def cif = corporateUserService.getCurrentCifUser().cif

		if(member.cifUser.cif.id != cif.id){
			throw new SecurityException("Not allowed to approve this member")
		}

		if(variables["type"].equals("create")){
			member.updatedBy = username
			member.pending = null
			member.status = Common.STATUS_ACTIVE
			member.save(failOnError: true)

			//			member.memberLimit.userLimit = new BigDecimal(userLimit)
			member.memberLimit.userLimit = new BigDecimal(0)
			member.memberLimit.save(failOnError: true)

			def passwordList = passwordGeneratorService.generateRegistrationCode()
			def userdetail = member.cifUser.userDetails
			userdetail.user.enabled = 1
			userdetail.user.password = springSecurityService.encodePassword(passwordList[0] + passwordList[1])
			if (userdetail.email) {
				cifService.emailPasswordBO(userdetail.user.username, userdetail.email, userdetail.firstName + " " + userdetail.lastName, passwordList[0] + passwordList[1])
			}
			userdetail.save(failOnError: true)

			member.reindex()
		}

		activitiService.claimTask(taskId, username)
		def params = [:]
		params.approved = true
		params.enoughApproval = true
		params.releaser = false
		activitiService.completeTask(taskId, params)

		return groupId
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	def rejectMember(def taskId, def username, def variables = null){
		if(!variables){
			variables = corporateAcitiviService.checkPermision(taskId, username)
		}
		else{
			corporateAcitiviService.checkUserAccess(variables, username)
		}

		def member = GroupMember.get(variables["id"])
		def cif = corporateUserService.getCurrentCifUser().cif
		def groupId = member.group.id

		if(member.cifUser.cif.id != cif.id){
			throw new SecurityException("Not allowed to reject this member")
		}

		if(variables["type"].equals("create")){
			UserRole.removeAll(member.cifUser.userDetails.user)
			member.delete(failOnError: true)
		}

		activitiService.claimTask(taskId, username)
		def params = [:]
		params.approved = false
		params.enoughApproval = true
		params.releaser = false
		activitiService.completeTask(taskId, params)

		return groupId
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	def approveGroup(def taskId, def username, def variables = null){
		if(!variables){
			variables = corporateAcitiviService.checkPermision(taskId, username)
		}
		else{
			corporateAcitiviService.checkUserAccess(variables, username)
		}

		def group = Grup.get(variables["id"])
		def cif = corporateUserService.getCurrentCifUser().cif

		if(group.cif.id != cif.id){
			throw new SecurityException("Not allowed to approve this group")
		}

		if(variables["type"].equals("update")){
			def mod = JSON.parse(group.pending)
			group.properties = mod
			group.pending = null
			group.updatedBy = springSecurityService.principal.username
			group.save(failOnError: true)
		}

		activitiService.claimTask(taskId, username)
		def params = [:]
		params.approved = false
		params.enoughApproval = true
		params.releaser = false
		activitiService.completeTask(taskId, params)

		return group.id
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	def rejectGroup(def taskId, def username, def variables = null){
		if(!variables){
			variables = corporateAcitiviService.checkPermision(taskId, username)
		}
		else{
			corporateAcitiviService.checkUserAccess(variables, username)
		}

		def group = Grup.get(variables["id"])
		def cif = corporateUserService.getCurrentCifUser().cif

		if(group.cif.id != cif.id){
			throw new SecurityException("Not allowed to reject this group")
		}

		if(variables["type"].equals("update")){
			group.pending = null
			group.updatedBy = springSecurityService.principal.username
			group.save(failOnError: true)
		}

		activitiService.claimTask(taskId, username)
		def params = [:]
		params.approved = false
		params.enoughApproval = true
		params.releaser = false
		activitiService.completeTask(taskId, params)

		return group.id
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	def approveTerminal(def taskId, def username, def variables = null){
		if(!variables){
			variables = corporateAcitiviService.checkPermision(taskId, username)
		}
		else{
			corporateAcitiviService.checkUserAccess(variables, username)
		}

		def terminal = Terminal.get(variables["id"])

		if(variables["type"].equals("update")){
			def mod = JSON.parse(terminal.pending)
			terminal.properties = mod
			terminal.status = Common.STATUS_ACTIVE
			terminal.pending = null
			terminal.updatedBy = springSecurityService.principal.username
			terminal.save(failOnError: true)
		}
		else if(variables["type"].equals("create")){
			terminal.status = Common.STATUS_ACTIVE
			terminal.pending = null
			terminal.updatedBy = springSecurityService.principal.username
			terminal.registeredDate = new Date()
			terminal.save(failOnError: true)
		}
		else if(variables["type"].equals("delete")){
			terminal.status = Common.STATUS_ACTIVE
			terminal.pending = null
			terminal.deleteFlag = "Y";
			terminal.updatedBy = springSecurityService.principal.username
			terminal.unregisterdDate = new Date()
			terminal.save(failOnError: true)
		}

		activitiService.claimTask(taskId, username)
		def params = [:]
		params.approved = false
		params.enoughApproval = true
		params.releaser = false
		activitiService.completeTask(taskId, params)
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	def rejectTerminal(def taskId, def username, def variables = null){
		if(!variables){
			variables = corporateAcitiviService.checkPermision(taskId, username)
		}
		else{
			corporateAcitiviService.checkUserAccess(variables, username)
		}

		def terminal = Terminal.get(variables["id"])

		if(variables["type"].equals("update") || variables["type"].equals("delete")){
			terminal.pending = null
			terminal.status = Common.STATUS_ACTIVE
			terminal.updatedBy = springSecurityService.principal.username
			terminal.save(failOnError: true)
		}
		else if(variables["type"].equals("create")){
			terminal.delete(failOnError: true)
		}

		activitiService.claimTask(taskId, username)
		def params = [:]
		params.approved = false
		params.enoughApproval = true
		params.releaser = false
		activitiService.completeTask(taskId, params)
	}
}
