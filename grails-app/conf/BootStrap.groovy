import grails.util.GrailsUtil

import com.akiong.maintenance.SystemConfiguration
import com.akiong.security.Menu
import com.akiong.security.RequestMap
import com.akiong.security.UserRole
import com.akiong.security.Role
import com.akiong.security.RoleMenu
import com.akiong.security.User
import com.akiong.security.UserDetails

import org.codehaus.groovy.grails.plugins.springsecurity.SecurityFilterPosition
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils


class BootStrap {
	def springSecurityService
	def searchableService
    def init = { servletContext ->
		////////////////////////////////////ini  buat reindex, proses ulang
		Thread.start {
			println "forked bulk index thread"
			searchableService.index()
			println "bulk index thread finished"
		}/////////////////////////////////////
		
		SpringSecurityUtils.clientRegisterFilter('concurrencyFilter', SecurityFilterPosition.CONCURRENT_SESSION_FILTER)
		
		Object.metaClass.s = {
			def o = delegate.save()
			if (!o) {
				delegate.errors.allErrors.each { println it }
			}
			o
		}
		
		/////ini buat add record ke dalam requestMap
		if ( User.count() == 0 ) {
			new RequestMap(url: "/api/userDetails/**",
			configAttribute: "IS_AUTHENTICATED_ANONYMOUSLY").save()

			new RequestMap(url: "/login/**",
			configAttribute: "IS_AUTHENTICATED_ANONYMOUSLY").save()

			new RequestMap(url: "/activation/index",
			configAttribute: "IS_AUTHENTICATED_ANONYMOUSLY").save()

			new RequestMap(url: "/css/**",
			configAttribute: "IS_AUTHENTICATED_ANONYMOUSLY").save()

			new RequestMap(url: "/images/**",
			configAttribute: "IS_AUTHENTICATED_ANONYMOUSLY").save()

			new RequestMap(url: "/js/**",
			configAttribute: "IS_AUTHENTICATED_ANONYMOUSLY").save()

			new RequestMap(url: "/",
			configAttribute: "IS_AUTHENTICATED_FULLY").save()

			new RequestMap(url: "/**",
			configAttribute: "IS_AUTHENTICATED_FULLY").save()

			new RequestMap(url: "/api/**",
			configAttribute: "IS_AUTHENTICATED_ANONYMOUSLY").save()

			new RequestMap(url: "/assets/**",
			configAttribute: "IS_AUTHENTICATED_ANONYMOUSLY").save()
		}
		
		def role_admin = Role.findByAuthority('ROLE_ADMIN')
		def role_corp_admin = Role.findByAuthority('ROLE_CORP_ADMIN')
		if (!role_admin) {
			role_admin = new Role(name: "Admin Role", authority: "ROLE_ADMIN")
			role_admin.id = "ROLE_ADMIN"
			role_admin.save(failOnError: true)
		}
		if (!role_corp_admin) {
			role_corp_admin = new Role(name: "Corporate Admin Role", authority: "ROLE_CORP_ADMIN")
			role_corp_admin.id = "ROLE_CORP_ADMIN"
			role_corp_admin.save(failOnError: true)
		}
		
		def mnu_m001 = Menu.findByName('My Task') ?: new Menu(
			name: "My Task",
			description: "",
			url: "/corporateMaintenance",
			sequence: 1,
			createdBy: 'system',
			dateCreated: new Date()).save(failOnError: true)

		def mnu_m002 = Menu.findByName('Corporate') ?: new Menu(
				name: "Corporate",
				description: "",
				url: "/",
				sequence: 2,
				createdBy: 'system',
				dateCreated: new Date()).save(failOnError: true)
	
		def mnu_m021 = Menu.findByName('Registration') ?: new Menu(
				name: "Registration",
				description: "",
				url: "/cif/create",
				sequence: 1,
				parentMenu : mnu_m002,
				createdBy: 'system',
				dateCreated: new Date()).save(failOnError: true)
	
		def mnu_m022 = Menu.findByName('Management') ?: new Menu(
				name: "Management",
				description: "",
				url: "/cif",
				sequence: 2,
				parentMenu : mnu_m002,
				createdBy: 'system',
				dateCreated: new Date()).save(failOnError: true)
	
		def mnu_m003 = Menu.findByName('Maintenance') ?: new Menu(
				name: "Maintenance",
				description: "",
				url: "/",
				sequence: 3,
				createdBy: 'system',
				dateCreated: new Date()).save(failOnError: true)
	
		def mnu_m031 = Menu.findByName('City') ?: new Menu(
				name: "City",
				description: "",
				url: "/city",
				sequence: 1,
				parentMenu : mnu_m003,
				createdBy: 'system',
				dateCreated: new Date()).save(failOnError: true)
	
		def mnu_m033 = Menu.findByName('Bank') ?: new Menu(
				name: "Bank",
				description: "",
				url: "/bank",
				sequence: 3,
				parentMenu : mnu_m003,
				createdBy: 'system',
				dateCreated: new Date()).save(failOnError: true)
	
		def mnu_m034 = Menu.findByName('Financial Institution') ?: new Menu(
				name: "Financial Institution",
				description: "",
				url: "/financial",
				sequence: 4,
				parentMenu : mnu_m003,
				createdBy: 'system',
				dateCreated: new Date()).save(failOnError: true)
	
		def mnu_m036 = Menu.findByName('Service Plan') ?: new Menu(
				name: "Service Plan",
				description: "",
				url: "/servicePackage",
				sequence: 6,
				parentMenu : mnu_m003,
				createdBy: 'system',
				dateCreated: new Date()).save(failOnError: true)
	
		def mnu_m004 = Menu.findByName('Security') ?: new Menu(
				name: "Security",
				description: "",
				url: "/",
				sequence: 4,
				createdBy: 'system',
				dateCreated: new Date()).save(failOnError: true)
	
		def mnu_m041 = Menu.findByName('User') ?: new Menu(
				name: "User",
				description: "",
				url: "/userDetails",
				sequence: 1,
				parentMenu : mnu_m004,
				createdBy: 'system',
				dateCreated: new Date()).save(failOnError: true)
	
		def mnu_m042 = Menu.findByName('Role') ?: new Menu(
				name: "Role",
				description: "",
				url: "/role",
				sequence: 2,
				parentMenu : mnu_m004,
				createdBy: 'system',
				dateCreated: new Date()).save(failOnError: true)
	
		def role_admin_mnu_m001 = RoleMenu.findByRoleAndMenu(role_admin, mnu_m001) ?: new RoleMenu(
				role: role_admin,
				menu: mnu_m001,
				createdBy: 'system',
				dateCreated: new Date()).save(failOnError: true)
	
		def role_admin_mnu_m002 = RoleMenu.findByRoleAndMenu(role_admin, mnu_m002) ?: new RoleMenu(
				role: role_admin,
				menu: mnu_m002,
				createdBy: 'system',
				dateCreated: new Date()).save(failOnError: true)
	
		def role_admin_mnu_m021 = RoleMenu.findByRoleAndMenu(role_admin, mnu_m021) ?: new RoleMenu(
				role: role_admin,
				menu: mnu_m021,
				createdBy: 'system',
				dateCreated: new Date()).save(failOnError: true)
	
		def role_admin_mnu_m022 = RoleMenu.findByRoleAndMenu(role_admin, mnu_m022) ?: new RoleMenu(
				role: role_admin,
				menu: mnu_m022,
				createdBy: 'system',
				dateCreated: new Date()).save(failOnError: true)
	
		def role_admin_mnu_m003 = RoleMenu.findByRoleAndMenu(role_admin, mnu_m003) ?: new RoleMenu(
				role: role_admin,
				menu: mnu_m003,
				createdBy: 'system',
				dateCreated: new Date()).save(failOnError: true)
	
		def role_admin_mnu_m031 = RoleMenu.findByRoleAndMenu(role_admin, mnu_m031) ?: new RoleMenu(
				role: role_admin,
				menu: mnu_m031,
				createdBy: 'system',
				dateCreated: new Date()).save(failOnError: true)
	
		def role_admin_mnu_m033 = RoleMenu.findByRoleAndMenu(role_admin, mnu_m033) ?: new RoleMenu(
				role: role_admin,
				menu: mnu_m033,
				createdBy: 'system',
				dateCreated: new Date()).save(failOnError: true)
	
		def role_admin_mnu_m034 = RoleMenu.findByRoleAndMenu(role_admin, mnu_m034) ?: new RoleMenu(
				role: role_admin,
				menu: mnu_m034,
				createdBy: 'system',
				dateCreated: new Date()).save(failOnError: true)
	
		def role_admin_mnu_m036 = RoleMenu.findByRoleAndMenu(role_admin, mnu_m036) ?: new RoleMenu(
				role: role_admin,
				menu: mnu_m036,
				createdBy: 'system',
				dateCreated: new Date()).save(failOnError: true)
	
		def role_admin_mnu_m004 = RoleMenu.findByRoleAndMenu(role_admin, mnu_m004) ?: new RoleMenu(
				role: role_admin,
				menu: mnu_m004,
				createdBy: 'system',
				dateCreated: new Date()).save(failOnError: true)
	
		def role_admin_mnu_m041 = RoleMenu.findByRoleAndMenu(role_admin, mnu_m041) ?: new RoleMenu(
				role: role_admin,
				menu: mnu_m041,
				createdBy: 'system',
				dateCreated: new Date()).save(failOnError: true)
	
		def role_admin_mnu_m042 = RoleMenu.findByRoleAndMenu(role_admin, mnu_m042) ?: new RoleMenu(
				role: role_admin,
				menu: mnu_m042,
				createdBy: 'system',
				dateCreated: new Date()).save(failOnError: true)

		
		def md5pass = springSecurityService.encodePassword('password')
		def adm1 = User.findByUsername('bobby') ?: new User(username: 'bobby',
		password: md5pass,
		enabled: true).save(failOnError: true)
		
		def bankadm1Details = UserDetails.findByUser(adm1) ?: new UserDetails(
			firstName: "bobby",
			lastName: "bobby",
			user: adm1,
			userAlias: 'bobby',
			email: 'ang.kionglam_aries@yahoo.com',
			mobilePhoneNo: '6281285839900',
			forceChangePassword: false,
			activateToken: false,
			retry: 0,
			noOfWrongChangedPassword: 0,
			status: '2',
			userType: '1',
			createdBy: 'system',
			dateCreated: new Date()).save(failOnError: true, flush: true)
			
		if (!adm1.authorities.contains(role_admin)) {
			def userRole = UserRole.create(adm1, role_admin)
			if (userRole.hasErrors()) println "Failed to assign user role to ${adm1.username}: ${userRole.errors}"
		}
		def passConfig = SystemConfiguration.findByName('Password') ?: new SystemConfiguration(name: 'Password',
			description: ".*(?=.{6,})(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).*",
			createdBy: 'system',
			dateCreated: new Date()).save(failOnError: true, flush: true)
		
    }
    def destroy = {
    }
}
