package com.akiong


import javax.servlet.http.HttpSession
import javax.servlet.http.HttpSessionEvent
import javax.servlet.http.HttpSessionListener


import com.akiong.security.LoginHistory;
import com.akiong.security.UserDetails;

class HttpSessionCollector implements HttpSessionListener {
    private static final Map<String, HttpSession> sessions = new HashMap<String, HttpSession>();

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        HttpSession session = event.getSession();
        sessions.put(session.getId(), session);
    }


    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        UserDetails.withTransaction { status ->
            def userDetails = UserDetails.findBySessionId(event.getSession().getId());
            if (userDetails) {
                userDetails.isLogin = "0"
                userDetails.sessionId = null
                userDetails.logoutTime = new Date()
                userDetails.save()
				
				def loginHs = LoginHistory.findAllByUserid(userDetails.user.username, [max:1, sort:"id", order:"desc", offset:0] )
				if(loginHs.size() > 0){
					loginHs[0].logoutTime = userDetails.logoutTime
					loginHs[0].save()
				}
            }
        }
        sessions.remove(event.getSession().getId());
    }

    public static void remove(String sessionId) {
        sessions.remove(sessionId);
    }

    public static HttpSession find(String sessionId) {
        return sessions.get(sessionId);
    }

}

