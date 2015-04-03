// Place your Spring DSL code here
import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH
import org.springframework.security.core.session.SessionRegistryImpl
import org.springframework.security.web.authentication.session.ConcurrentSessionControlStrategy
import org.springframework.security.web.session.ConcurrentSessionFilter

import com.akiong.authentication.UsernamePasswordAuthenticationFilter
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils

beans = {
	
	sessionRegistry(SessionRegistryImpl)
	
	concurrencyFilter(ConcurrentSessionFilter) {
		sessionRegistry = sessionRegistry
		logoutHandlers = [ref("rememberMeServices"), ref("securityContextLogoutHandler")]
		expiredUrl='/login/concurrentSession'
	}
	concurrentSessionControlStrategy(ConcurrentSessionControlStrategy, sessionRegistry) {
		alwaysCreateSession = true
		exceptionIfMaximumExceeded = false
		maximumSessions = 1
	}
	
	authenticationProcessingFilter(UsernamePasswordAuthenticationFilter) {
		def conf = SpringSecurityUtils.securityConfig
		authenticationManager = ref("authenticationManager")
		sessionAuthenticationStrategy = ref('concurrentSessionControlStrategy')
		authenticationSuccessHandler = ref('authenticationSuccessHandler')
		authenticationFailureHandler = ref('authenticationFailureHandler')
		rememberMeServices = ref('rememberMeServices')
		authenticationDetailsSource = ref('authenticationDetailsSource')
		filterProcessesUrl = conf.apf.filterProcessesUrl
		usernameParameter = conf.apf.usernameParameter
		passwordParameter = conf.apf.passwordParameter
		continueChainBeforeSuccessfulAuthentication = conf.apf.continueChainBeforeSuccessfulAuthentication
		allowSessionCreation = conf.apf.allowSessionCreation
		postOnly = conf.apf.postOnly
	}
	
	
	//Enable this when need embedded ActiveMQ
//	xmlns amq: 'http://activemq.apache.org/schema/core'
//
//	def brokerName = 'myEmbeddedBroker2'
//
//	amq.'broker'(brokerName: brokerName, useJmx: true, persistent: false) {
//		amq.'managementContext' {
//			amq.'managementContext'(connectorPort: 2011, jmxDomainName: 'embeddedBroker')
//		}
//
//		amq.'plugins' { amq.'loggingBrokerPlugin' }
//
//		amq.'transportConnectors' {
//			amq.'transportConnector'(name: 'openwire', uri: 'tcp://localhost:62626')
//		}
//	}
//	jmsConnectionFactory(org.apache.activemq.ActiveMQConnectionFactory) { brokerURL = "vm://${brokerName}" }
	
	jmsConnectionFactory(org.apache.activemq.ActiveMQConnectionFactory) { brokerURL = CH.config.notif.jms.connection }
}
