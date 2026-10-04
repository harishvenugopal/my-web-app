<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jakarta.servlet.ServletContext, jakarta.servlet.http.HttpSession" %>
<!DOCTYPE html>
<html>
<head>
    <title>Java Servlet Master Cheat Sheet</title>
    <style>
        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f4f6f9; color: #333; margin: 0; padding: 30px; }
        .container { max-width: 1000px; margin: auto; }
        .header { background: linear-gradient(135deg, #1e3c72, #2a5298); color: white; padding: 25px; border-radius: 8px; box-shadow: 0 4px 15px rgba(0,0,0,0.1); margin-bottom: 25px; text-align: center; }
        .grid { display: grid; grid-template-columns: 1fr 1fr; gap: 20px; }
        .card { background: white; padding: 20px; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.05); border-top: 4px solid #007ACC; }
        .card.global { border-top-color: #27ae60; }
        .card.session { border-top-color: #f39c12; }
        .card.metaphor { border-top-color: #9b59b6; }
        h3 { margin-top: 0; color: #2c3e50; border-bottom: 1px solid #eee; padding-bottom: 10px; }
        code { background: #f1f2f6; padding: 2px 6px; border-radius: 4px; font-family: 'Consolas', monospace; font-size: 14px; color: #c23616; }
        .metric-box { background: #f8f9fa; padding: 12px; border-radius: 6px; margin: 10px 0; border-left: 4px solid #ccc; }
        .btn { display: inline-block; background: #007ACC; color: white; padding: 8px 15px; text-decoration: none; border-radius: 4px; font-size: 14px; margin-top: 10px; font-weight: bold; }
        .btn-danger { background: #c0392b; }
        .badge { background: #e1b12c; color: white; padding: 3px 8px; border-radius: 12px; font-size: 11px; font-weight: bold; float: right; }
    </style>
</head>
<body>
<div class="container">
    <div class="header">
        <h1>🎰 WildFly Casino Architecture Explorer</h1>
        <p>Live Telemetry Probe & Interactive Master Cheat Sheet</p>
    </div>

    <div class="grid">
        <!-- Live System Context Scope Info -->
        <div class="card global">
            <h3>🏦 ServletContext <span class="badge">Global Scope</span></h3>
            <p><strong>Metaphor:</strong> The Entire Casino Building & Shared Air Ground Rules.</p>
            <div class="metric-box">
                <strong>Live Environment Label:</strong> <br>
                <code><%= application.getInitParameter("environmentLabel") %></code>
            </div>
            <div class="metric-box">
                <strong>Live Traffic Turnstile Count:</strong> <br>
                <code><%= application.getAttribute("globalHitCounter") != null ? application.getAttribute("globalHitCounter") : "0 (Visit /gateway first)" %></code>
            </div>
            <div class="metric-box">
                <strong>Server Container Specs:</strong> <br>
                <span style="font-size:12px;"><%= application.getServerInfo() %></span>
            </div>
            <p style="font-size: 12px; color:#7f8c8d;">Declared outside all servlets via <code>&lt;context-param&gt;</code> inside <code>web.xml</code>.</p>
        </div>

        <!-- Live Session State Tracker Info -->
        <div class="card session">
            <h3>🔑 HttpSession <span class="badge">Session Scope</span></h3>
            <p><strong>Metaphor:</strong> The Neon VIP Wristband clipped onto a specific Guest.</p>
            <% 
                HttpSession currentSession = request.getSession(false);
                if (currentSession != null) {
            %>
                <div class="metric-box" style="border-left-color: #f39c12;">
                    <strong>Your Assigned Tracking ID:</strong><br>
                    <span style="font-size:11px; font-family:monospace; font-weight:bold;"><%= currentSession.getId() %></span>
                </div>
                <div class="metric-box" style="border-left-color: #f39c12;">
                    <strong>Session User Identity:</strong><br>
                    <code><%= currentSession.getAttribute("username") != null ? currentSession.getAttribute("username") : "Guest Visitor" %></code>
                </div>
                <a href="gateway?action=logout" class="btn btn-danger">Cut Wristband (Logout)</a>
            <% } else { %>
                <div class="metric-box" style="border-left-color: #d63031; background: #fab1a0;">
                    <strong>Session State Status:</strong> Unauthenticated State.
                </div>
                <a href="gateway?authToken=jboss-master-key" class="btn">Get VIP Wristband (Login)</a>
            <% } %>
            <p style="font-size: 12px; color:#7f8c8d; margin-top:15px;">Maintained seamlessly by WildFly via <code>JSESSIONID</code> network tracking cookies.</p>
        </div>

        <!-- Order of Execution Guide -->
        <div class="card">
            <h3>⚡ The C-F-S-H-E Processing Pipeline</h3>
            <p>The exact sequential chain every incoming request network packet moves through:</p>
            <ol style="line-height: 1.8; font-size:14px;">
                <li><strong>C - Context Initialization:</strong> Web container initializes <code>ServletContext</code> variables at app deployment.</li>
                <li><strong>F - Filter Interception:</strong> Chained Interceptors trap matching URL routes sequentially (Top-to-Bottom in <code>web.xml</code>).</li>
                <li><strong>S - Servlet Logic:</strong> Target thread executes matching method blocks (<code>doGet</code> / <code>doPost</code>).</li>
                <li><strong>H - HttpSession Mapping:</strong> Server correlates incoming cookie markers to extract state memory maps.</li>
                <li><strong>E - Error Resolution:</strong> System catches anomalies and maps them cleanly via <code>&lt;error-page&gt;</code> parameters.</li>
            </ol>
        </div>

        <!-- RequestDispatcher Guide -->
        <div class="card metaphor">
            <h3>🚪 RequestDispatcher Mechanics</h3>
            <p><strong>Metaphor:</strong> The Secret Underground Bellhop Tunnels.</p>
            <ul style="line-height: 1.6; font-size:14px; padding-left:20px;">
                <li><code>forward(request, response)</code>: Completely shifts the execution handoff. The original servlet leaves the room, and the target view engine takes over. <strong style="color:#8e44ad;">Browser URL remains identical.</strong></li>
                <li><code>include(request, response)</code>: Pauses execution, brings in a content snippet from another file, merges it inline, and resumes.</li>
            </ul>
            <div style="background:#f1f2f6; padding:10px; border-radius:4px; font-size:11px; font-family:monospace;">
                // Code Pattern Example<br>
                RequestDispatcher rd = request.getRequestDispatcher("/target");<br>
                rd.forward(request, response);
            </div>
        </div>
    </div>
    
    <div style="text-align:center; margin-top:30px; font-size:12px; color:#95a5a6;">
        WildFly 🎰 Casino SME Masterclass Explorer Portfolio Node.
    </div>
</div>
</body>
</html>
