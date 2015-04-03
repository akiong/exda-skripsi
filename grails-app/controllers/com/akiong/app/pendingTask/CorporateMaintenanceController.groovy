package com.akiong.app.pendingTask

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.akiong.BackOfficeController;

class CorporateMaintenanceController extends BackOfficeController{

	static allowedMethods = [approveBulk: "POST", rejectBulk: "POST", approveMtnBulk: "POST", rejectMtnBulk: "POST"]

	def corporateAcitiviService
	def corporateMaintenanceService
	def springSecurityService

	private static final Logger logger = LoggerFactory.getLogger(this)

	def index = { redirect(action: "list", params: params) }

	def list = {
		def results = corporateAcitiviService.getCorpMaintenanceList(params)
		[taskList: results[0], total: results[1], name: params.name?:""]
	}

	def approveBulk = {
		withForm {
			if (params.id) {
				params.username = springSecurityService.principal.username
				try {
					println "params = "+params
					def error = corporateMaintenanceService.approveBulk(params)
					if (!error) {
						flash.message = "${message(code: 'default.data.approve.msg')}"
					}
					else {
						flash.error = "${message(code: 'default.data.cannot.approve.msg')}"
					}
				}
				catch (MissingResourceException e) {
					flash.error = e.getMessage()
					logger.error(e.getMessage(), e)
				}
				catch (SecurityException e) {
					flash.error = e.getMessage()
					logger.error(e.getMessage(), e)
				}
				catch (RuntimeException e) {
					logger.error(e.getMessage(), e)
					redirect(controller: "error", action: "serverError")
					return
				}
			}
			else {
				flash.message = "${message(code: 'default.no.data.approveOnly.msg')}"
			}

			redirect(action: "list")
		}.invalidToken {
			redirect(controller: "error", action: "forbidden")
		}
	}

	def rejectBulk = {
		withForm {
			if (params.id) {
				params.username = springSecurityService.principal.username
				try {
					def error = corporateMaintenanceService.rejectBulk(params)
					if (!error) {
						flash.message = "${message(code: 'default.data.reject.msg')}"
					}
					else {
						flash.error = "${message(code: 'default.data.cannot.reject.msg')}"
					}
				}
				catch (MissingResourceException e) {
					flash.error = e.getMessage()
					logger.error(e.getMessage(), e)
				}
				catch (SecurityException e) {
					flash.error = e.getMessage()
					logger.error(e.getMessage(), e)
				}
				catch (RuntimeException e) {
					logger.error(e.getMessage(), e)
					redirect(controller: "error", action: "serverError")
					return
				}
			}
			else {
				flash.message = "${message(code: 'default.no.data.reject.message')}"
			}

			redirect(action: "list")
		}.invalidToken {
			redirect(controller: "error", action: "forbidden")
		}
	}
	
	def layoutMailApproved={
		
	}
	
	def layoutMailCorporate={
	
	}
	
	def layoutMailRequestUpgradePlan={
		
	}
	
	def layoutMailApprovePlan={
		
	}
}
