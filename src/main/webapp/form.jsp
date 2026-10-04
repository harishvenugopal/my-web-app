<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Dynamic Servlet Registration</title>
</head>
<body style="font-family: Arial, sans-serif; padding: 40px; background-color: #f8f9fa;">
    <div style="max-width: 400px; margin: auto; background: white; padding: 30px; border-radius: 8px; box-shadow: 0px 0px 10px rgba(0,0,0,0.1);">
        <h2 style="color: #2c3e50; margin-top: 0;">Register Profile</h2>

        <!-- 1. Dynamically read and display database or format validation errors -->
        <% 
            String error = (String) request.getAttribute("errorMessage");
            if (error != null) { 
        %>
            <div style="color: #721c24; background-color: #f8d7da; padding: 10px; border-radius: 4px; margin-bottom: 15px; font-size: 14px; border-left: 4px solid #dc3545;">
                <%= error %>
            </div>
        <% } %>

        <!-- 2. Read previously typed attributes to keep form inputs sticky -->
        <%
            String prevUser = (String) request.getAttribute("typedUser");
            String prevEmail = (String) request.getAttribute("typedEmail");
            if (prevUser == null) prevUser = "";
            if (prevEmail == null) prevEmail = "";
        %>

        <form action="register" method="POST">
            <label style="font-weight: bold;">Username:</label><br>
            <input type="text" name="username" value="<%= prevUser %>" style="width: 100%; padding: 8px; margin: 8px 0 15px 0; border: 1px solid #ccc; border-radius: 4px;" required><br>
            
            <label style="font-weight: bold;">Email Address:</label><br>
            <input type="email" name="email" value="<%= prevEmail %>" style="width: 100%; padding: 8px; margin: 8px 0 20px 0; border: 1px solid #ccc; border-radius: 4px;" required><br>
            
            <button type="submit" style="width: 100%; background: #007ACC; color: white; padding: 10px; border: none; border-radius: 4px; font-size: 16px; cursor: pointer;">Submit Registration</button>
        </form>
    </div>
</body>
</html>
