# 🎰 Enterprise Java Web Architecture Sandbox

An intensive, hands-on masterclass portfolio exploring the low-level mechanics of **Jakarta EE Servlets**, multi-layered request processing pipelines, stateful session boundaries, and standalone relational database integration.

---

## 🔬 Core Learning Principle: "Mechanics Over Magic"

The absolute core engineering philosophy of this repository is **zero annotations (`@WebServlet`), zero framework magic (Spring Boot), and zero heavy abstractions.** 

Modern frameworks hide the low-level plumbing of web applications. To truly master enterprise systems engineering, every single component in this project has been built, configured, and bound from the ground up using **pure Java source code** and declarative, deterministic **Deployment Descriptor XML structures (`web.xml`)**. By manually configuring thread lifecycles, memory scopes, and request routing paths, we understand exactly *how* a web container handles network data streams under heavy load.

---

## 🏢 Completed Architecture Overview

Every request hitting this system moves through a strict, multi-tiered pipeline governed by the **C-F-S-H-E Processing Matrix**:

```text
[ Browser Network Packet ] 
           │
           ▼
┌────────────────────────────────────────────────────────────────────────┐
│ WildFly 33.0.2 Standalone Enterprise Container Environment             │
│                                                                        │
│  🌐 ServletContext (Global Scope): Encapsulates central DB URL paths  │
│                                                                        │
│    🛡️ Chained WebFilter Interceptors (Pipeline Barriers)              │
│        ├── 1. AuditingFilter: Atomically updates a global hit counter  │
│        └── 2. SecurityFilter: Intercepts parameters & validates session│
│                                                                        │
│    🔀 MVC Request Controller Nodes                                     │
│        ├── GET  /register  ──> RequestDispatcher.forward() ─> form.jsp │
│        ├── POST /register ──> Production-grade PostgreSQL JDBC Write   │
│        └── GET  /exploreUsers ──> SQL ResultSet Data Directory Table   │
│                                                                        │
│    🚨 Declarative Global Error Intercept Capture                       │
│        └── Gracefully handles 404/500 faults via internal forwards    │
└────────────────────────────────────────────────────────────────────────┘
```

---

## ⚙️ Key Technical Milestones Met

*   **Production Module Architecture:** Shifted away from temporary, embedded container engines to a permanent, static database module layout inside WildFly (`modules/org/postgresql/main/`).
*   **State & Session Management:** Implemented secure user-state retention utilizing WildFly-generated alphanumeric cookie indices (`JSESSIONID`) via the `HttpSession` API.
*   **Decoupled Infrastructure (IaC):** Optimized the local build structure (`pom.xml`) to keep the development `target/` directory lightweight, fast, and free of server configuration pollution.
*   **Automated Verification Loop:** Integrated automated integration test suites running offline memory mocks using **JUnit 5** and **Mockito**.

---

## 🛠️ Operational Commands (Local Sandbox Reference)

### 1. Boot up the Standalone Container Server Natively
```bash
~/wildfly-server/bin/standalone.sh
```

### 2. Compile and Assemble the Distribution Web Archive
```bash
mvn clean package
```

### 3. Deploy the Application Archive Live to the Server Zone
```bash
cp target/my-web-app.war ~/wildfly-server/standalone/deployments/
```

---
### 📅 Roadmap: Up Next...
When we resume on **next Saturday**, we will finalize the **Admin Data Explorer View Component** to map, query, and loop database record sets cleanly down the network channel.
