package com.learn.srv;

import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class SecurityFilter implements Filter {

    private String systemSecretKey;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Fetches the required key defined inside web.xml for this specific filter
        this.systemSecretKey = filterConfig.getInitParameter("requiredSecretKey");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        // 1. Check if the browser already has an active authenticated session container
        // [1.3]
        HttpSession currentSession = req.getSession(false); // false = don't create a new one yet

        if (currentSession != null && "authenticated".equals(currentSession.getAttribute("authStatus"))) {
            // User is already logged in! Advance down the pipeline smoothly
            chain.doFilter(request, response);
            return;
        }

        // Extract the authToken query parameter passed in the URL
        String userToken = req.getParameter("authToken");

        if (systemSecretKey != null && systemSecretKey.equals(userToken)) {
            // Token matches! Establish a brand new server session storage pool [1.3]
            HttpSession newSession = req.getSession(true); // true = build fresh session memory mapping
            newSession.setAttribute("authStatus", "authenticated");
            newSession.setAttribute("username", "Harish (Admin Profile)");
            // Secret matches! Pass execution cleanly forward to the next filter/servlet
            chain.doFilter(request, response);
        } else {
            // Block execution and immediately return a 401 Unauthorized status code
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                    "Authentication Gateway Required: Pass your validation credentials (?authToken=jboss-master-key)");
        }
    }

    @Override
    public void destroy() {
    }
}
