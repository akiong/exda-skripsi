import grails.converters.JSON

import javax.servlet.http.HttpServletResponse

import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.AccountExpiredException
import org.springframework.security.authentication.CredentialsExpiredException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.LockedException
import org.springframework.security.core.context.SecurityContextHolder as SCH
import org.springframework.security.web.WebAttributes
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

import com.akiong.HttpSessionCollector
import com.akiong.security.User

class LoginController {

	/**
	 * Dependency injection for the authenticationTrustResolver.
	 */
	def authenticationTrustResolver

	/**
	 * Dependency injection for the springSecurityService.
	 */
	def springSecurityService
	def cifService
	
	private static final Logger logger = LoggerFactory.getLogger(this)
	
	/**
	 * Default action; redirects to 'defaultTargetUrl' if logged in, /login/auth otherwise.
	 */
	def index = {
		if (springSecurityService.isLoggedIn()) {
			redirect uri: SpringSecurityUtils.securityConfig.successHandler.defaultTargetUrl
		}
		else {
			redirect action: 'auth', params: params
		}
	}

	/**
	 * Show the login page.
	 */
	def auth = {

		def config = SpringSecurityUtils.securityConfig

		if (springSecurityService.isLoggedIn()) {
			redirect uri: config.successHandler.defaultTargetUrl
			return
		}

		String view = 'auth'
		String postUrl = "${request.contextPath}${config.apf.filterProcessesUrl}"
		render view: view, model: [postUrl: postUrl,
		                           rememberMeParameter: config.rememberMe.parameter]
	}

	/**
	 * The redirect action for Ajax requests.
	 */
	def authAjax = {
		response.setHeader 'Location', SpringSecurityUtils.securityConfig.auth.ajaxLoginFormUrl
		response.sendError HttpServletResponse.SC_UNAUTHORIZED
	}

	/**
	 * Show denied page.
	 */
	def denied = {
		if (springSecurityService.isLoggedIn() &&
				authenticationTrustResolver.isRememberMe(SCH.context?.authentication)) {
			// have cookie but the page is guarded with IS_AUTHENTICATED_FULLY
			redirect action: 'full', params: params
		}
	}

	/**
	 * Login page for users with a remember-me cookie but accessing a IS_AUTHENTICATED_FULLY page.
	 */
	def full = {
		def config = SpringSecurityUtils.securityConfig
		render view: 'auth', params: params,
			model: [hasCookie: authenticationTrustResolver.isRememberMe(SCH.context?.authentication),
			        postUrl: "${request.contextPath}${config.apf.filterProcessesUrl}"]
	}

	/**
	 * Callback after a failed login. Redirects to the auth page with a warning message.
	 */
	def authfail = {

		def username = session[UsernamePasswordAuthenticationFilter.SPRING_SECURITY_LAST_USERNAME_KEY]
		String msg = ''
		def exception = session[WebAttributes.AUTHENTICATION_EXCEPTION]
		if (exception) {
			if (exception instanceof AccountExpiredException) {
				msg = g.message(code: "springSecurity.errors.login.expired")
			}
			else if (exception instanceof CredentialsExpiredException) {
				msg = g.message(code: "springSecurity.errors.login.passwordExpired")
			}
			else if (exception instanceof DisabledException) {
				msg = g.message(code: "springSecurity.errors.login.disabled")
			}
			else if (exception instanceof LockedException) {
				msg = g.message(code: "springSecurity.errors.login.locked")
			}
			else {
				msg = g.message(code: "springSecurity.errors.login.fail")
			}
		}

		if (springSecurityService.isAjax(request)) {
			render([error: msg] as JSON)
		}
		else {
			flash.message = msg
			redirect action: 'auth', params: params
		}
	}

	/**
	 * The Ajax success redirect url.
	 */
	def ajaxSuccess = {
		def user = com.akiong.security.User.findByUsername(springSecurityService.authentication.name)
		def userDetails = com.akiong.security.UserDetails.findByUser(user)
		try {
			if (userDetails.isLogin.equals("1")) {
				def sessionx = HttpSessionCollector.find(userDetails.sessionId)
				if (sessionx) {
					sessionx.invalidate()
					HttpSessionCollector.remove(userDetails.sessionId)
				}
			}

			def ipAddress = request.getHeader("Client-IP")
			if (!ipAddress) {
				ipAddress = request.getHeader("X-Forwarded-For")
			}
			if (!ipAddress) {
				ipAddress = request.getRemoteAddr()
			}
			cifService.resetTryLoginAddInfo(userDetails, ipAddress, session.id, session.tid)

			session.setAttribute("alias", userDetails.userAlias)
			session.setAttribute("fullName", userDetails.firstName + " " + userDetails.lastName)
			session.setAttribute("change", userDetails.forceChangePassword)
			session.setAttribute("userType", userDetails.userType)

			if(userDetails.language != null){
				session[org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME] = new Locale(userDetails.language)
			}
			else{
				session[org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME] = new Locale("id")
			}
			buildMenuList()

			if (params.callback) {
				render"${params.callback} (${[success: true, change: userDetails.forceChangePassword, username: springSecurityService.authentication.name, fullName: (userDetails.firstName + " " + userDetails.lastName)] as JSON})"
			}
			else {
				render([success: true, change: userDetails.forceChangePassword, username: springSecurityService.authentication.name, fullName: (userDetails.firstName + " " + userDetails.lastName)] as JSON)
			}
		}
		//catch unknown RuntimeException, redirect to Error 500 server Error page
		catch (RuntimeException e) {
			logger.error(e.getMessage(), e)
			redirect(controller: "error", action: "serverError")
			return
		}
	
//		render([success: true, username: springSecurityService.authentication.name] as JSON)
	}
	
	private void buildMenuList() {
		String appName = grailsApplication.metadata['app.name']
		StringBuffer contentSB = new StringBuffer();
		def roles = User.get(springSecurityService.getPrincipal().id).getAuthorities()*.getAuthority()
		if (roles.size() > 0) {
			def menus = com.akiong.security.RoleMenu.createCriteria().list{
				projections { distinct("menu") }
				role{ 'in'("authority", roles) }
			}

			def parentMenus = menus.findAll {it.parentMenu}*.parentMenu
			parentMenus.addAll(menus.findAll {it.parentMenu == null})
			parentMenus.unique()
			parentMenus = parentMenus.sort {it.sequence}
			def childMenu
			def root = grailsAttributes.getApplicationUri(request)

			parentMenus.each{ x->
				childMenu = menus.findAll {it.parentMenu && it.parentMenu.equals(x)}.sort {it.sequence}
				if(childMenu){
					if(!x.url.equals("") && !x.url.equals("/")){
						contentSB.append("<li class='dropdown'><a class='dropdown-tonggle' data-toggle='dropdown' id='mnImg"+x.id+"' onclick=\"window.location='"+root+x.url+"'\" href='").append(root).append(x.url).append("' class='listMChld'>").append(x.name)
					}
					else{
						contentSB.append("<li class='dropdown' id='mnImg"+x.id+"'><a class='dropdown-tonggle' data-toggle='dropdown'>").append(x.name)
					}
					contentSB.append("<b class='caret'></b></a><ul class='dropdown-menu'>")
					childMenu.each {
						contentSB.append("<li><a onclick=\"window.location='"+root+it.url+"'\" href='").append(root).append(it.url).append("' class='listMChld'>")
						contentSB.append(it.name).append("</a></li>")
					}
					contentSB.append("</ul></li>")
				}
				else{
					contentSB.append("<li class='dropdown'><a class='dropdown-tonggle' data-toggle='dropdown' id='mnImg"+x.id+"' onclick=\"window.location='"+root+x.url+"'\" href='").append(root).append(x.url).append("' class='listMChld'>").append(x.name).append("</a></li>")
				}
			}

		}

		session.setAttribute("menuList", contentSB.toString())
	}

	/**
	 * The Ajax denied redirect url.
	 */
	def ajaxDenied = {
		render([error: 'access denied'] as JSON)
	}
	
	def concurrentSession = {
		
				def msg = "Your account is logged in from another browser or location."
		
				if (springSecurityService.isAjax(request)) {
					render([error: msg] as JSON)
				}
				else {
					flash.message = msg
					redirect action: 'auth', params: params
				}
		
			}
}
