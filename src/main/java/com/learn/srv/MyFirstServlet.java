package com.learn.srv;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class MyFirstServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. Inspect the incoming request metadata
        String clientBrowser = request.getHeader("User-Agent");
        String requestMethod = request.getMethod();
        String requestURI = request.getRequestURI();
        
        // 2. Add custom logic: If a specific query param is missing, change the HTTP status code
        String secretCode = request.getParameter("secret");
        
        if ("42".equals(secretCode)) {
            // Success State
            response.setStatus(HttpServletResponse.SC_OK); // Status 200
            response.setContentType("text/html;charset=UTF-8");
            
            PrintWriter out = response.getWriter();
            out.println("<html>");
            out.println("<body style='font-family: sans-serif; padding: 30px; background-color: #e8f8f5;'>");
            out.println("<h1 style='color: #117a65;'>🔒 Access Granted!</h1>");
            out.println("<p><strong>HTTP Method Used:</strong> " + requestMethod + "</p>");
            out.println("<p><strong>Target URI Path:</strong> " + requestURI + "</p>");
            out.println("<p><strong>Your Browser Details:</strong> " + clientBrowser + "</p>");
            out.println("</body></html>");
        } else {
            // Error State: Client didn't pass the right credentials
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing or invalid secret parameter (?secret=42)");
        }
    }
}
