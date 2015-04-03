package com.akiong.authentication;


import java.security.KeyPair

import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

import org.codehaus.groovy.grails.plugins.springsecurity.SecurityRequestHolder
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.TextEscapeUtils
import org.springframework.util.Assert

import com.akiong.app.cif.CifUser
import com.akiong.app.cif.GroupMember
import com.akiong.app.cif.Grup
import com.akiong.helper.Common
import com.akiong.helper.JCryptionUtil
import com.akiong.HttpSessionCollector

/**
 * Processes an authentication form submission. Called {@code AuthenticationProcessingFilter} prior to Spring Security
 * 3.0.
 * <p>
 * Login forms must present two parameters to this filter: a username and
 * password. The default parameter names to use are contained in the
 * static fields {@link #SPRING_SECURITY_FORM_USERNAME_KEY} and {@link #SPRING_SECURITY_FORM_PASSWORD_KEY}.
 * The parameter names can also be changed by setting the {@code usernameParameter} and {@code passwordParameter}
 * properties.
 * <p>
 * This filter by default responds to the URL {@code /j_spring_security_check}.
 *
 * @author Ben Alex
 * @author Colin Sampaleanu
 * @author Luke Taylor
 * @since 3.0
 */

public class UsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	//~ Static fields/initializers =====================================================================================

	public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "j_username";
	public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "j_password";
	public static final String SPRING_SECURITY_LAST_USERNAME_KEY = "SPRING_SECURITY_LAST_USERNAME";

	private String usernameParameter = SPRING_SECURITY_FORM_USERNAME_KEY;
	private String passwordParameter = SPRING_SECURITY_FORM_PASSWORD_KEY;
	private boolean postOnly = true;

	//~ Constructors ===================================================================================================

	public UsernamePasswordAuthenticationFilter() {

		super("/j_spring_security_check");

	}

	//~ Methods ========================================================================================================

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

		if (postOnly && !request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}

		String username = obtainUsername(request);
		String password = obtainPassword(request);
//		String tid = request.getParameter("tid");
		println "assss"
		if (username == null) {
			username = "";
		}

		if (password == null) {
			password = "";
		}

		// Place the last username attempted into HttpSession for views
		HttpSession session = request.getSession(false);
		if(session == null && getAllowSessionCreation()){
			session = request.getSession();
		}

		if (session != null) {

			session.setAttribute(SPRING_SECURITY_LAST_USERNAME_KEY, TextEscapeUtils.escapeEntities(username));

			KeyPair keys = (KeyPair) session.getAttribute("keys");
			if(keys != null){
				JCryptionUtil jCryptionUtil = new JCryptionUtil();
				if(password != null && !password.equals("")){
					password = jCryptionUtil.decrypt(password, keys);
				}
			}
		}

		username = username.trim();
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);

		// Allow subclasses to set the "details" property
		setDetails(request, authRequest);

		return this.getAuthenticationManager().authenticate(authRequest);
	}

	/**
	 * Enables subclasses to override the composition of the password, such as by including additional values
	 * and a separator.<p>This might be used for example if a postcode/zipcode was required in addition to the
	 * password. A delimiter such as a pipe (|) should be used to separate the password and extended value(s). The
	 * <code>AuthenticationDao</code> will need to generate the expected password in a corresponding manner.</p>
	 *
	 * @param request so that request attributes can be retrieved
	 *
	 * @return the password that will be presented in the <code>Authentication</code> request token to the
	 *         <code>AuthenticationManager</code>
	 */
	protected String obtainPassword(HttpServletRequest request) {

		return request.getParameter(passwordParameter);
	}

	/**
	 * Enables subclasses to override the composition of the username, such as by including additional values
	 * and a separator.
	 *
	 * @param request so that request attributes can be retrieved
	 *
	 * @return the username that will be presented in the <code>Authentication</code> request token to the
	 *         <code>AuthenticationManager</code>
	 */
	protected String obtainUsername(HttpServletRequest request) {

		return request.getParameter(usernameParameter);
	}

	/**
	 * Provided so that subclasses may configure what is put into the authentication request's details
	 * property.
	 *
	 * @param request that an authentication request is being created for
	 * @param authRequest the authentication request object that should have its details set
	 */
	protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {

		authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
	}

	/**
	 * Sets the parameter name which will be used to obtain the username from the login request.
	 *
	 * @param usernameParameter the parameter name. Defaults to "j_username".
	 */
	public void setUsernameParameter(String usernameParameter) {

		Assert.hasText(usernameParameter, "Username parameter must not be empty or null");

		this.usernameParameter = usernameParameter;
	}

	/**
	 * Sets the parameter name which will be used to obtain the password from the login request..
	 *
	 * @param passwordParameter the parameter name. Defaults to "j_password".
	 */
	public void setPasswordParameter(String passwordParameter) {

		Assert.hasText(passwordParameter, "Password parameter must not be empty or null");

		this.passwordParameter = passwordParameter;
	}

	/**
	 * Defines whether only HTTP POST requests will be allowed by this filter.
	 * If set to true, and an authentication request is received which is not a POST request, an exception will
	 * be raised immediately and authentication will not be attempted. The <tt>unsuccessfulAuthentication()</tt> method
	 * will be called as if handling a failed authentication.
	 * <p>
	 * Defaults to <tt>true</tt> but may be overridden by subclasses.
	 */
	public void setPostOnly(boolean postOnly) {

		this.postOnly = postOnly;
	}



	public final String getUsernameParameter() {

		return usernameParameter;
	}



	public final String getPasswordParameter() {

		return passwordParameter;
	}

	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
		SecurityRequestHolder.set((HttpServletRequest) request, (HttpServletResponse) response);
		try {
			super.doFilter(request, response, chain);
		}
		finally {
			SecurityRequestHolder.reset();
		}
	}
}

