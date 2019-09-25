package org.jasig.tomcat.valves;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.ServletException;

import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.realm.GenericPrincipal;
import org.apache.catalina.valves.ValveBase;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

/*
 * Interprets HTTP_REMOTE_USER as REMOTE_USER.
 * This is needed to support IIS+Shibboleth where IIS does not allow
 * Shibboleth to set USER_NAME.
 */
public class RemoteUserValve extends ValveBase {

    private static final Log log = LogFactory.getLog(RemoteUserValve.class);

    private String userHeader;

    public RemoteUserValve() {
        super(true);
    }

    @Override
    public void invoke(Request request, Response response) throws IOException, ServletException {
        String username = request.getHeader(userHeader);
        if (username != null) {
            if (log.isInfoEnabled()) {
                log.debug("header " + userHeader + " = \"" + username + "\"");
            }
            final Principal principal = new GenericPrincipal(username, null, null);
            request.setUserPrincipal(principal);
            request.setAttribute("USER_NAME", username);
        } else {
            if (log.isInfoEnabled()) {
                log.debug("header " + userHeader + " is missing");
            }
        }

        getNext().invoke(request, response);
    }

    public void setUserHeader(String header) {
        log.info("user header: " + header);
        this.userHeader = header;
    }
}
