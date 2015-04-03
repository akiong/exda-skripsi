package com.akiong

import org.activiti.engine.runtime.ProcessInstance
import org.activiti.engine.task.Task
import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH
import org.grails.activiti.ActivitiConstants

import com.akiong.app.cif.Grup

class CorporateAcitiviService {

	static transactional = true

	def springSecurityService
	def activitiService
	def runtimeService
	def taskService
	def messageSource

	private static final String BO_CO_MNGT = "BO Corporate Management";
	private static final String BO_MT_MNGT = "Maintenance Management";
	private static final String FO_CO_MNGT = "FO Corporate Management";
	private static final String MEMBER_CO_MNGT = "Member Corporate Management";
	private static final String BO_TRX = "BO Transaction";
	private static final String FOR_FUNC = "forFunc";
	private static final String SYSTEM = "system";
	private static final String NETBANK_APPROVAL_TASK = "NetBankApprovalRequest";
	private static final String sessionUsernameKey = CH.config.activiti.sessionUsernameKey ?: ActivitiConstants.DEFAULT_SESSION_USERNAME_KEY

	def startTransaction(def params) {
		params.controller = NETBANK_APPROVAL_TASK
		params[sessionUsernameKey] = springSecurityService.principal.username
		ProcessInstance pi = activitiService.startProcess(params)
		return pi.id
	}

	def startTransactionAnim(def params) {
		params.controller = NETBANK_APPROVAL_TASK
		params[sessionUsernameKey] = "Anonymous"
		ProcessInstance pi = activitiService.startProcess(params)
		return pi.id
	}

	def initiateCorporateManagement(def id, def corpName, def type, def objectType, def corpId){
		def params = [:]
		def piId = startTransaction(params)

		Task task = activitiService.getAssignedTask(params[sessionUsernameKey], piId)
		params.taskId = task.id
		params.id = id
		params.forFunc = BO_CO_MNGT
		params.type = type
		params.objectType = objectType
		params.changeDate = new Date()
		params.corpName = corpName
		params.corpId = corpId
		params.approver = true
		params.releaser = false
		params.dueDate = null
		params.userList = SYSTEM
		activitiService.completeTask(params.taskId, params)

		def taskQuery = taskService.createTaskQuery()
		taskQuery.processInstanceId(piId)
		if (taskQuery.count() != 0) {
			def result = taskQuery.singleResult()

			runtimeService.setVariable(result.executionId, "taskId", result.id)
		}
	}

	def initiateCorporateManagementAnim(def id, def corpName, def type, def objectType, def corpId){
		def params = [:]
		def piId = startTransactionAnim(params)

		Task task = activitiService.getAssignedTask(params[sessionUsernameKey], piId)
		params.taskId = task.id
		params.id = id
		params.forFunc = BO_CO_MNGT
		params.type = type
		params.objectType = objectType
		params.changeDate = new Date()
		params.corpName = corpName
		params.corpId = corpId
		params.approver = true
		params.releaser = false
		params.dueDate = null
		params.userList = SYSTEM
		activitiService.completeTask(params.taskId, params)

		def taskQuery = taskService.createTaskQuery()
		taskQuery.processInstanceId(piId)
		if (taskQuery.count() != 0) {
			def result = taskQuery.singleResult()

			runtimeService.setVariable(result.executionId, "taskId", result.id)
		}
	}

	def initiateCorporateManagement2(def id, def corpName, def type, def objectType, def corpId){
		def params = [:]
		def piId = startTransaction(params)

		Task task = activitiService.getAssignedTask(params[sessionUsernameKey], piId)
		params.taskId = task.id
		params.id = id
		params.forFunc = FO_CO_MNGT
		params.type = type
		params.objectType = objectType
		params.changeDate = new Date()
		params.corpName = corpName
		params.corpId = corpId
		params.approver = true
		params.releaser = false
		params.dueDate = null
		params.userList = SYSTEM
		activitiService.completeTask(params.taskId, params)

		def taskQuery = taskService.createTaskQuery()
		taskQuery.processInstanceId(piId)
		if (taskQuery.count() != 0) {
			def result = taskQuery.singleResult()

			runtimeService.setVariable(result.executionId, "taskId", result.id)
		}
	}

	def initiateMemberManagement(def member, def groupId, def type, def objectType, def corpId){
		def params = [:]
		def piId = startTransaction(params)

		Task task = activitiService.getAssignedTask(params[sessionUsernameKey], piId)
		params.taskId = task.id
		params.id = member.id
		params.forFunc = MEMBER_CO_MNGT
		params.type = type
		params.objectType = objectType
		params.changeDate = new Date()
		params.groupId = groupId
		params.corpId = corpId
		params.memberName = member.cifUser.firstName + " " + member.cifUser.lastName
		params.mobilePhoneNo = member.cifUser.userDetails.mobilePhoneNo
		params.email = member.cifUser.userDetails.email
		//		params.userLimit = member.memberLimit.userLimit
		params.approver = true
		params.releaser = false
		params.dueDate = null
		params.userList = SYSTEM
		activitiService.completeTask(params.taskId, params)

		def taskQuery = taskService.createTaskQuery()
		taskQuery.processInstanceId(piId)
		if (taskQuery.count() != 0) {
			def result = taskQuery.singleResult()

			runtimeService.setVariable(result.executionId, "taskId", result.id)
		}
	}

	def initiateTerminalManagement(def terminal, def corpName, def type, def objectType, def corpId){
		def params = [:]
		def piId = startTransaction(params)

		Task task = activitiService.getAssignedTask(params[sessionUsernameKey], piId)
		params.taskId = task.id
		params.id = terminal.id
		//		params.forFunc = MEMBER_CO_MNGT
		params.forFunc = BO_CO_MNGT
		params.type = type
		params.objectType = objectType
		params.tid = terminal.tid
		params.changeDate = new Date()
		params.corpName = corpName
		params.corpId = corpId
		params.approver = true
		params.releaser = false
		params.dueDate = null
		params.userList = SYSTEM
		activitiService.completeTask(params.taskId, params)

		def taskQuery = taskService.createTaskQuery()
		taskQuery.processInstanceId(piId)
		if (taskQuery.count() != 0) {
			def result = taskQuery.singleResult()

			runtimeService.setVariable(result.executionId, "taskId", result.id)
		}
	}

	def updateGroup(def group, def groupId, def type, def objectType, def corpId){
		def params = [:]
		def piId = startTransaction(params)

		Task task = activitiService.getAssignedTask(params[sessionUsernameKey], piId)
		params.taskId = task.id
		params.id = group.id
		params.forFunc = MEMBER_CO_MNGT
		params.type = type
		params.objectType = objectType
		params.changeDate = new Date()
		params.groupId = groupId
		params.corpId = corpId
		params.approver = true
		params.releaser = false
		params.dueDate = null
		params.userList = SYSTEM
		activitiService.completeTask(params.taskId, params)

		def taskQuery = taskService.createTaskQuery()
		taskQuery.processInstanceId(piId)
		if (taskQuery.count() != 0) {
			def result = taskQuery.singleResult()

			runtimeService.setVariable(result.executionId, "taskId", result.id)
		}
	}

	def initiateMaintenanceManagement(def id, def type, def objectType){
		def params = [:]
		def piId = startTransaction(params)

		Task task = activitiService.getAssignedTask(params[sessionUsernameKey], piId)
		params.taskId = task.id
		params.id = id
		params.forFunc = BO_MT_MNGT
		params.type = type
		params.objectType = objectType
		params.changeDate = new Date()
		params.approver = true
		params.releaser = false
		params.dueDate = null
		params.userList = SYSTEM
		activitiService.completeTask(params.taskId, params)

		def taskQuery = taskService.createTaskQuery()
		taskQuery.processInstanceId(piId)
		if (taskQuery.count() != 0) {
			def result = taskQuery.singleResult()

			runtimeService.setVariable(result.executionId, "taskId", result.id)
		}
	}

	def getCorpMaintenanceListFO(def params){
		params.max = Math.min(params.max ? params.int('max') : 10, 100)

		def taskQuery = runtimeService.createProcessInstanceQuery()
		taskQuery.variableValueEquals(FOR_FUNC, FO_CO_MNGT)
		//		taskQuery.variableValueNotEquals(sessionUsernameKey, springSecurityService.principal.username)
		taskQuery.variableValueEquals("corpId", params.cifId)
		taskQuery.orderByProcessInstanceId().asc()

		def total = taskQuery.count()

		def tasks = taskQuery.listPage(Integer.parseInt(params.offset ?: "0"), params.max)

		def taskRs = new ArrayList()
		def rs
		tasks.each{
			rs = runtimeService.getVariables(it.id, [
				"objectType",
				"type",
				"corpName",
				"changeDate",
				"taskId"
			])
			taskRs.add(rs)
		}

		def results = new ArrayList()
		results.add(taskRs)
		results.add(total)

		return results
	}

	def getCorpMaintenanceList(def params){
		params.max = Math.min(params.max ? params.int('max') : 10, 100)

		def taskQuery = runtimeService.createProcessInstanceQuery()
		taskQuery.variableValueEquals(FOR_FUNC, BO_CO_MNGT)
		//		taskQuery.variableValueNotEquals(sessionUsernameKey, springSecurityService.principal.username)
		if(params.name){
			taskQuery.variableValueLike("corpName", "%"+params.name+"%")
		}
		taskQuery.orderByProcessInstanceId().asc()

		def total = taskQuery.count()

		def tasks = taskQuery.listPage(Integer.parseInt(params.offset ?: "0"), params.max)

		def taskRs = new ArrayList()
		def rs
		tasks.each{
			rs = runtimeService.getVariables(it.id, [
				"objectType",
				"type",
				"corpName",
				"changeDate",
				"taskId"
			])
			taskRs.add(rs)
		}

		def results = new ArrayList()
		results.add(taskRs)
		results.add(total)

		return results
	}

	def getMemberMaintenanceList(def params){
		params.max = Math.min(params.max ? params.int('max') : 10, 100)

		def taskQuery = runtimeService.createProcessInstanceQuery()
		taskQuery.variableValueEquals(FOR_FUNC, MEMBER_CO_MNGT)
		taskQuery.variableValueEquals("groupId", Long.parseLong(params.id))
		taskQuery.orderByProcessInstanceId().asc()

		def total = taskQuery.count()

		def tasks = taskQuery.listPage(Integer.parseInt(params.offset ?: "0"), params.max)

		def taskRs = new ArrayList()
		def rs
		tasks.each{
			rs = runtimeService.getVariables(it.id, [
				"objectType",
				"type",
				"changeDate",
				"taskId",
				"memberName",
				"mobilePhoneNo",
				"email"
				//				"userLimit"
			])
			taskRs.add(rs)
		}

		def results = new ArrayList()
		results.add(taskRs)
		results.add(total)

		return results
	}

	def getAdminTaskList(def params){
		params.max = Math.min(params.max ? params.int('max') : 10, 100)

		def taskQuery = runtimeService.createProcessInstanceQuery()
		taskQuery.variableValueEquals(FOR_FUNC, MEMBER_CO_MNGT)
		taskQuery.variableValueEquals("corpId", params.id)
		if(params.group){
			taskQuery.variableValueEquals("groupId", Long.parseLong(params.group))
		}
		taskQuery.orderByProcessInstanceId().asc()

		def total = taskQuery.count()

		def tasks = taskQuery.listPage(Integer.parseInt(params.offset ?: "0"), params.max)

		def taskRs = new ArrayList()
		def rs
		def group
		tasks.each{
			rs = runtimeService.getVariables(it.id, [
				"objectType",
				"type",
				"changeDate",
				"taskId",
				"memberName",
				"groupId",
				"tid"
			])
			group = Grup.get(rs["groupId"])
			rs["groupName"] = group?.name
			taskRs.add(rs)
		}

		def results = new ArrayList()
		results.add(taskRs)
		results.add(total)

		return results
	}

	def getBOMaintenanceList(def params){
		params.max = Math.min(params.max ? params.int('max') : 10, 100)

		def taskQuery = runtimeService.createProcessInstanceQuery()
		taskQuery.variableValueEquals(FOR_FUNC,BO_MT_MNGT)
		//		taskQuery.variableValueNotEquals(sessionUsernameKey, springSecurityService.principal.username)
		taskQuery.orderByProcessInstanceId().asc()

		def total = taskQuery.count()

		def tasks = taskQuery.listPage(Integer.parseInt(params.offset ?: "0"), params.max)

		def taskRs = new ArrayList()
		def rs
		tasks.each{
			rs = runtimeService.getVariables(it.id, [
				"objectType",
				"type",
				"changeDate",
				"taskId"
			])
			taskRs.add(rs)
		}

		def results = new ArrayList()
		results.add(taskRs)
		results.add(total)

		return results
	}

	def getBOTransactionList(def params){
		params.max = Math.min(params.max ? params.int('max') : 10, 100)

		def taskQuery = runtimeService.createProcessInstanceQuery()
		taskQuery.variableValueEquals(FOR_FUNC,BO_TRX)
		//		taskQuery.variableValueNotEquals(sessionUsernameKey, springSecurityService.principal.username)
		taskQuery.orderByProcessInstanceId().asc()

		def total = taskQuery.count()

		def tasks = taskQuery.listPage(Integer.parseInt(params.offset ?: "0"), params.max)

		def taskRs = new ArrayList()
		def rs
		tasks.each{
			rs = runtimeService.getVariables(it.id, [
				"objectType",
				"type",
				"changeDate",
				"taskId"
			])
			taskRs.add(rs)
		}

		def results = new ArrayList()
		results.add(taskRs)
		results.add(total)

		return results
	}

	def checkPermision(def taskId, def username){
		def variables = taskService.getVariables(taskId, [
			"id",
			sessionUsernameKey,
			"type"
		])
		checkUserAccess(variables, username)

		return variables
	}

	def checkPermision2(def taskId, def username){
		def variables = taskService.getVariables(taskId, [
			"id",
			sessionUsernameKey,
			"type",
			"corpId"
		])
		checkUserAccess2(variables, username)

		return variables
	}

	def checkUserAccess(def variables, def username){
		//		if(variables[sessionUsernameKey].equals(username)){
		//			def request = org.codehaus.groovy.grails.plugins.springsecurity.SecurityRequestHolder.getRequest()
		//			def session = request.getSession(false)
		//			def locale = session[org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME]
		//
		//			throw new SecurityException(messageSource.getMessage("not.allowed.label", null, locale))
		//		}

		//TODO: check whether user has role to approve/reject it, throw SecurityException if not
	}

	def checkUserAccess2(def variables, def username){
		//		if(variables[sessionUsernameKey].equals(username)){
		//			def request = org.codehaus.groovy.grails.plugins.springsecurity.SecurityRequestHolder.getRequest()
		//			def session = request.getSession(false)
		//			def locale = session[org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME]
		//
		//			throw new SecurityException(messageSource.getMessage("not.allowed.label", null, locale))
		//		}

		//check whether user has role to approve/reject it, throw SecurityException if not
		def role = springSecurityService.getPrincipal().getAuthorities().find{it.authority.equals("ROLE_CO_SYS_ADMIN")}
		if(role == null) {
			def request = org.codehaus.groovy.grails.plugins.springsecurity.SecurityRequestHolder.getRequest()
			def session = request.getSession(false)
			def locale = session[org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME]

			throw new SecurityException(messageSource.getMessage("not.allowed.label", null, locale))
		}
	}

	def initiateCardProfileManagement(def id, def corpName, def type, def objectType, def corpId){
		def params = [:]
		def piId = startTransaction(params)

		Task task = activitiService.getAssignedTask(params[sessionUsernameKey], piId)
		params.taskId = task.id
		params.id = id
		params.forFunc = BO_CO_MNGT
		params.type = type
		params.objectType = objectType
		params.changeDate = new Date()
		params.corpName = corpName
		params.corpId = corpId
		params.approver = true
		params.releaser = false
		params.dueDate = null
		params.userList = SYSTEM
		activitiService.completeTask(params.taskId, params)

		def taskQuery = taskService.createTaskQuery()
		taskQuery.processInstanceId(piId)
		if (taskQuery.count() != 0) {
			def result = taskQuery.singleResult()

			runtimeService.setVariable(result.executionId, "taskId", result.id)
		}
	}
}
