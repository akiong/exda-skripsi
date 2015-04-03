package com.akiong

import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.slf4j.Logger;
import org.slf4j.LoggerFactory

class EmailService {

	static exposes = ['jms']
	static destination = "queue.sendEmailCC"

	private static final Logger logger = LoggerFactory.getLogger(this)
	private static final String from = ConfigurationHolder.config.netbank.email.from;

	def onMessage(message) {
		try {
			sendMail(message.emailTo, message.emailSubject, message.content)
			return null
		}
		catch (RuntimeException e) {
			logger.error(e.getMessage(), e)
		}
	}

	def sendMail(String emailTo, String emailSubject, String content) {
		logger.info("Send Email")
		//		logger.info("To : " + emailTo + "\nSubject: " + emailSubject + "\n" + content)
		sendMail {
			to emailTo
			from "aaaa"
			subject emailSubject
			body content
		}
	}
}
