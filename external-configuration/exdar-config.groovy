import org.apache.log4j.Appender;
import org.apache.log4j.DailyRollingFileAppender;

//netbank.send.sms=false
//netbank.demo=true
//netbank.sms.need.proxy=false
//netbank.sms.provider="sprint"
//netbank.sms.masking.user="TERAVIN"
//netbank.sms.masking.password="t3r4viN"
//netbank.sms.url="https://smsgw.sprint.web.id"
//netbank.sms.proxy="192.168.7.208"
//netbank.sms.proxyPort="808"
//netbank.sms.validity=600
//token.length=6
//token.max.invalid=3
//netbank.barcode.validity=900


//siera.remote.url="http://localhost:8084/remoteLoyalty"
//siera.pentahoServer.url="http://192.168.1.72:9088/pentaho/content/reporting"

max.autocomplete=30
akiong.email.from="ang.kionglam.aries@gmail.com"
notif.jms.connection="tcp://localhost:61616"
netbank.debug.action=true

//collection.file.location="/app/loyalty"
//searchable {
//	compassConnection = new File(
//			"/app/loyalty-index"
//		).absolutePath
//}
/*
log4j = {
    appenders {
        appender new DailyRollingFileAppender(
                name: 'dailyNotification',
                datePattern: "'.'yyyy-MM-dd",  // See the API for all patterns.
                fileName: "netbank-notification.log",
                layout: pattern(conversionPattern: '%d [%t] %-5p %c %x - %m%n')
        )
		appender new DailyRollingFileAppender(
			name: 'dailyMobile',
			datePattern: "'.'yyyy-MM-dd",  // See the API for all patterns.
			fileName: "netbank-mobile.log",
			layout: pattern(conversionPattern: '%d [%t] %-5p %c %x - %m%n')
		)
        appender new DailyRollingFileAppender(
                name: 'dailyDebug',
                datePattern: "'.'yyyy-MM-dd",  // See the API for all patterns.
                fileName: "netbank-debug.log",
                layout: pattern(conversionPattern: '%d [%t] %-5p %c %x - %m%n')
        )
        console name: 'stdout', layout: pattern(conversionPattern: "%d [%t] %-5p %c %x - %m%n")
    }

    error 'org.codehaus.groovy.grails.web.servlet',  //  controllers
            'org.codehaus.groovy.grails.web.pages', //  GSP
            'org.codehaus.groovy.grails.web.sitemesh', //  layouts
            'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
            'org.codehaus.groovy.grails.web.mapping', // URL mapping
            'org.codehaus.groovy.grails.commons', // core / classloading
            'org.codehaus.groovy.grails.plugins', // plugins
            'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
            'org.springframework',
            'org.hibernate',
            'org.codehaus.groovy.grails.scaffolding.view.ScaffoldingViewResolver',
            'org.apache.activemq.broker',
            'org.quartz',
            'net.sf.ehcache',
            'org.compass',
            'org.apache.tomcat',
            'org.apache.commons',
            'org.apache.catalina',
            'org.apache',
            'java.sql'




    warn 'org.mortbay.log'

    error dailyDebug: ['grails.app',
            'com.teravin', additivity = false]

    info dailyNotification: ['com.akiong.EmailService',
            'com.teravin.SmsService',
            additivity = false]
	
	info dailyMobile: ['com.teravin.ApiController',
			additivity = false]

    root {
        error 'file', 'stdout'
        additivity = true

    }
}
*/