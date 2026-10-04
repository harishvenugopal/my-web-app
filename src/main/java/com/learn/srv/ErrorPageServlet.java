package com.learn.srv;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ErrorPageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // 1. Extract error metadata injected natively by WildFly
        Integer statusCode = (Integer) request.getAttribute("jakarta.servlet.error.status_code");
        String requestUri = (String) request.getAttribute("jakarta.servlet.error.request_uri");
        String errorMessage = (String) request.getAttribute("jakarta.servlet.error.message");

        if (errorMessage == null) {
            errorMessage = "No explicit system message provided.";
        }

        // 2. Render a professional, clean error screen
        out.println("<html>");
        out.println("<body style='font-family:sans-serif; text-align:center; padding-top:50px; background-color:#fdf2f2;'>");
        out.println("<div style='max-width:500px; margin:auto; background:white; padding:30px; border-radius:8px; box-shadow:0 4px 10px rgba(0,0,0,0.05); border-top: 5px solid #ec7063;'>");
        out.println("<h2 style='color:#cb4335;'>⚠️ Secure Environment Alert</h2>");
        out.println("<hr style='border:0; border-top:1px solid #eee; margin:20px 0;'>");
        out.println("<p style='text-align:left;'><strong>HTTP Status Code:</strong> <span style='color:#cb4335; font-weight:bold;'>" + statusCode + "</span></p>");
        out.println("<p style='text-align:left;'><strong>Blocked Resource Path:</strong> <code>" + requestUri + "</code></p>");
        out.println("<p style='text-align:left; background:#f9f9f9; padding:10px; border-left:3px solid #ccc; font-size:14px;'><strong>Container Message:</strong> " + errorMessage + "</p>");
        out.println("<br><a href='gateway?authToken=jboss-master-key' style='background:#2471a3; color:white; padding:10px 20px; text-decoration:none; border-radius:4px;'>Return to Secure Login</a>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
}
