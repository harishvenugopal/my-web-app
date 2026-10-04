package com.learn.srv;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ControllerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. Check if a logout action parameter was requested by the user
        String action = request.getParameter("action");
        if ("logout".equals(action)) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate(); // Completely destroys the session memory pool inside WildFly [1.3]
            }
            response.setContentType("text/html");
            response.getWriter().println("<html><body><h2>✓ Successfully Logged Out Safely!</h2><a href='gateway'>Return to Gateway</a></body></html>");
            return;
        }

        // 2. Read parameters from scopes
        ServletContext globalContext = this.getServletContext();
        String environmentName = globalContext.getInitParameter("environmentLabel");
        Integer totalTrafficHits = (Integer) globalContext.getAttribute("globalHitCounter");

        // 3. Extract the active user metadata straight out of Session Storage memory maps
        HttpSession session = request.getSession(false);
        String sessionUser = (session != null) ? (String) session.getAttribute("username") : "Guest Node";
        String sessionID = (session != null) ? session.getId() : "None";

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        out.println("<html><body style='font-family:sans-serif; padding:40px; background-color:#ebf5fb;'>");
        out.println("<h2 style='color:#2471a3;'>🛡️ Session Architecture Portal Dashboard</h2>");
        out.println("<div style='background:white; padding:25px; border-radius:6px; box-shadow:0 2px 5px rgba(0,0,0,0.05);'>");
        out.println("<p><strong>Target Environment Scope:</strong> " + environmentName + "</p>");
        out.println("<p style='color:#27ae60;'><strong>Active User Session Identity:</strong> " + sessionUser + "</p>");
        out.println("<p><strong>Implicit Server Tracking Cookie ID (JSESSIONID):</strong> <code>" + sessionID + "</code></p>");
        out.println("<h3 style='color:#e74c3c;'>Pipeline Metrics Tracker:</h3>");
        out.println("<p>Total Global Hits Intercepted by Web Filters: <strong>" + totalTrafficHits + "</strong></p>");
        out.println("<br><br><a href='gateway?action=logout' style='background:#c0392b; color:white; padding:8px 15px; text-decoration:none; border-radius:4px;'>Secure Logout System</a>");
        out.println("</div></body></html>");
    }
}
