package com.akiong


import java.util.regex.Matcher
import java.util.regex.Pattern
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.codehaus.groovy.grails.commons.ConfigurationHolder

class BackOfficeController {


	private static final logger = LoggerFactory.getLogger(this)


	def springSecurityService

	def beforeInterceptor = {
		if (ConfigurationHolder.config.netbank.debug.action)
			logger.debug((!springSecurityService.loggedIn ? "Anonymous" : springSecurityService.principal?.username) + " execute action :  ${actionUri}");

		if (!springSecurityService.loggedIn) {
			redirect(controller: "login", action: "auth")
			return false
		}
		else if (session.getAttribute("change")) {
			if (!controllerUri.toString().equals("/changePassword")) {
				redirect(controller: "changePassword", action: "edit")
				return false
			}
		}
	}

	def checkShowUrl = {
		String numberExpression = "[0-9]*";
		CharSequence inputStr = params.id.toString()
		Pattern pattern = Pattern.compile(numberExpression)
		Matcher matcher = pattern.matcher(inputStr)
		if (!matcher.matches()) {
			redirect(controller: "error", action: "notFound")
			logger.debug("page not found");
			return false
		}
		else {
			return true
		}
	}
}
