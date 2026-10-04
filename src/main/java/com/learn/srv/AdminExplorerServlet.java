package com.learn.srv;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AdminExplorerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // 1. Fetch database configuration credentials from the global application scope
        ServletContext context = this.getServletContext();
        String dbUrl = context.getInitParameter("dbUrlPath");
        String dbUser = context.getInitParameter("dbUsername");
        String dbPass = context.getInitParameter("dbPassword");

        out.println("<html>");
        out.println("<head><title>Admin Data Explorer</title>");
        out.println("<style>");
        out.println("body { font-family:sans-serif; background:#f4f6f9; padding:40px; }");
        out.println("table { width:100%; border-collapse:collapse; background:white; border-radius:6px; overflow:hidden; box-shadow:0 2px 8px rgba(0,0,0,0.05); }");
        out.println("th, td { padding:12px 15px; text-align:left; border-bottom:1px solid #eee; }");
        out.println("th { background:#2c3e50; color:white; }");
        out.println("tr:hover { background:#f9f9f9; }");
        out.println(".btn { display:inline-block; padding:6px 12px; background:#007ACC; color:white; text-decoration:none; border-radius:4px; font-size:14px; }");
        out.println("</style></head>");
        out.println("<body>");
        out.println("<h2>📋 Registered Accounts Master Directory</h2>");
        out.println("<p style='margin-bottom:20px;'><a href='register' class='btn'>+ Add New Profile Account</a></p>");
        out.println("<table>");
        out.println("<tr><th>ID Row</th><th>Username Profile</th><th>Registered Email Address</th><th>Timestamp Created</th></tr>");

        String sqlSelectQuery = "SELECT id, username, email, created_at FROM users ORDER BY id ASC";

        // 2. Open data channel and read the results loop matrix
        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
             PreparedStatement preparedStatement = conn.prepareStatement(sqlSelectQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            
            // Loop sequentially through each record row inside the database response payload
            while (resultSet.next()) {
                int userId = resultSet.getInt("id");
                String name = resultSet.getString("username");
                String userEmail = resultSet.getString("email");
                String timestamp = resultSet.getTimestamp("created_at").toString();

                out.println("<tr>");
                out.println("<td><code>#" + userId + "</code></td>");
                out.println("<td><strong>" + name + "</strong></td>");
                out.println("<td>" + userEmail + "</td>");
                out.println("<td style='color:#7f8c8d; font-size:13px;'>" + timestamp + "</td>");
                out.println("</tr>");
            }
            
        } catch (SQLException e) {
            out.println("<tr><td colspan='4' style='color:red; text-align:center;'>Database Read Failure Alert: " + e.getMessage() + "</td></tr>");
        }

        out.println("</table>");
        out.println("</body></html>");
    }
}
