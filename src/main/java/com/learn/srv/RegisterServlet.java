package com.learn.srv;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RegisterServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("====== [JDBC ENGINE] PostgreSQL Driver initialized natively! ======");
        } catch (ClassNotFoundException e) {
            throw new ServletException("Critical Error: Driver not found in classpath.", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/form.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        String usernameValue = request.getParameter("username");
        String emailValue = request.getParameter("email");

        // Input Validation
        if (emailValue == null || !emailValue.endsWith(".com")) {
            request.setAttribute("errorMessage", "Registration Blocked: Email must end in '.com'.");
            request.setAttribute("typedUser", usernameValue);
            request.setAttribute("typedEmail", emailValue);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/form.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // DYNAMIC GLOBAL SCOPE LOOKUP: Fetch database parameters securely out of ServletContext
        ServletContext context = this.getServletContext();
        String dbUrl = context.getInitParameter("dbUrlPath");
        String dbUser = context.getInitParameter("dbUsername");
        String dbPass = context.getInitParameter("dbPassword");

        String sqlInsertQuery = "INSERT INTO users (username, email) VALUES (?, ?)";
        
        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
             PreparedStatement preparedStatement = conn.prepareStatement(sqlInsertQuery)) {
            
            preparedStatement.setString(1, usernameValue);
            preparedStatement.setString(2, emailValue);
            
            int rowsAffected = preparedStatement.executeUpdate();
            
            PrintWriter out = response.getWriter();
            out.println("<html><body style='font-family:sans-serif; padding:40px;'>");
            out.println("<h2 style='color:green;'>✓ Registration Complete!</h2>");
            out.println("<p>Account '" + usernameValue + "' was successfully recorded inside PostgreSQL.</p>");
            out.println("<p><a href='register'>Register Another Profile Account</a> | <a href='exploreUsers'>Open Admin Explorer</a></p>");
            out.println("</body></html>");
            
        } catch (SQLException e) {
            if ("23505".equals(e.getSQLState())) {
                request.setAttribute("errorMessage", "Conflict: The email address is already in use!");
            } else {
                request.setAttribute("errorMessage", "Database System Error: " + e.getMessage());
            }
            request.setAttribute("typedUser", usernameValue);
            request.setAttribute("typedEmail", emailValue);
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("/form.jsp");
            dispatcher.forward(request, response);
        }
    }
}
