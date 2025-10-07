# BiteMe
A client-server food ordering application with a MySQL database, featuring a role-based interface for customers, restaurants, and managers.

---

## 🍔 BiteMe Project
BiteMe is a distributed, full-stack restaurant management and food ordering system developed in the Java environment.  
The system was built as an academic project, demonstrating an advanced client-server architecture, database management, and a dynamic, role-based user interface.  
It allows complete management of business operations, from receiving a customer's order, through processing at the restaurant, to generating complex analytical reports for management.

---

## 🎯 Implemented Features

### 👤 For the Customer
- Guided Ordering Wizard: A step-by-step interface for selecting a restaurant, meal type, dishes, and customizing each dish.  
- Flexible Delivery Options: Full support for standard delivery or self-pickup (Take Away).  
- Order Tracking: View order history and the updated status of each order.  

### 🍴 For the Restaurant (Qualified Worker)
- Real-Time Order Management: Operational interface to view incoming orders and update their status (Approved, Ready, Shipped).  
- Full Menu Management: Ability to add, delete, and update the prices of dishes on the restaurant's menu.  

### 👔 For the Branch Manager
- Account Creation: An interface for registering new customers.  
- Monthly Report Generation: Access to detailed, view-ready reports for their branch:  
  - **Income Report:** Financial analysis of revenues.  
  - **Order Report:** Breakdown of orders by item type.  
  - **Performance Report:** Graphical analysis of adherence to delivery times.  

---

## 🏗️ System Architecture

### 🖥️ Server-Side
- **Communication Core (EchoServer.java):** Based on OCSF library, listens for connections and manages communication with multiple clients simultaneously. The central `handleMessageFromClient` method routes requests based on `MessageType`.  
- **Data Access Layer (mysqlConnection.java):** Manages all communication with the MySQL database. Executes queries and maps results to objects.  
- **Operational Interface (ServerUI.java):** JavaFX-based GUI for DB connection setup and server management.  

### 🖼️ Client-Side
- **Communication Core (ChatClient.java):** Manages connection to the server, sends requests, and processes responses.  
- **MVC Architecture:**  
  - *View:* FXML files define screen layouts.  
  - *Controller:* Java classes (e.g., `LogInController`) handle UI logic and communication.  
  - *Model:* Entity objects like `Order`, `Dish`, `User`.  
- **Role-Based Interface:** After login, users are routed to their home screen according to their role.  

---

## 🛠️ Technologies Used
- **Language:** Java  
- **User Interface:** JavaFX  
- **Client-Server Communication:** OCSF (Object Client-Server Framework)  
- **Database:** MySQL  

---

## 🚀 Getting Started

### Step 0: Prerequisites
- Java Development Kit (JDK) 8+  
- MySQL Server (WAMP, XAMPP, or standalone install)  

### Step 1: Database Setup
1. Start MySQL server.  
2. Connect using MySQL Workbench.  
3. Create schema:  
   ```sql
   CREATE SCHEMA project;
   ```  
4. Import `project.sql` to create tables and load sample data.  

### Step 2: Running the Server
1. Locate `ServerBiteMe.jar` and run it.  
2. Fill in:    
   - DB User: your MySQL username (e.g., root)  
   - DB Password: your password  
   - Port: e.g., 5555  
3. Click **Connect** to start server.  

### Step 3: Running the Client
1. Locate `ClientBiteMe.jar` and run it.  
2. Fill in:  
   - IP: `localhost` (if server is on same machine)  
   - Port: e.g., 5555  
3. Click **Connect**.  
4. Log in with an existing DB user and start using the system.  

---

✅ You're all set! The BiteMe system is now running.
