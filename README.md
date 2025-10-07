# BiteMe
A client-server food ordering application with a MySQL database, featuring a role-based interface for customers, restaurants, and managers.

🍔 BiteMe Project
BiteMe is a distributed, full-stack restaurant management and food ordering system developed in the Java environment. The system was built as a academic project, demonstrating an advanced client-server architecture, database management, and a dynamic, role-based user interface. The system allows for the complete management of business operations, from receiving a customer's order, through operational processing at the restaurant, to the generation of complex analytical reports for management.

---

🎯 Implemented Features
The system provides a tailored user experience for each of its user types, with an emphasis on the following fully developed functionalities:

👤 For the Customer  
- Guided Ordering Wizard: A step-by-step interface for selecting a restaurant, meal type, dishes, and customizing each dish.  
- Flexible Delivery Options: Full support for standard delivery or self-pickup (Take Away).  
- Order Tracking: View order history and the updated status of each order.  

🍴 For the Restaurant (Qualified Worker)  
- Real-Time Order Management: An operational interface to view incoming orders and update their status (Approved, Ready, Shipped).  
- Full Menu Management: The ability to add, delete, and update the prices of dishes on the restaurant's menu.  

👔 For the Branch Manager  
- Account Creation: An interface for registering new customers in the system.  
- Monthly Report Generation: Access to detailed, view-ready reports for their branch:  
  - Income Report: Financial analysis of restaurant revenues in the branch.  
  - Order Report: Breakdown of orders by item type.  
  - Performance Report: Graphical analysis of adherence to delivery times.  

---

🏗️ System Architecture  
The system is built on a Three-Tier Client-Server architecture, which clearly separates presentation, logic, and data.

🖥️ Server-Side:  
- **Communication Core (EchoServer.java):** Based on the OCSF library, this component listens for connections and manages communication with multiple clients simultaneously. The central `handleMessageFromClient` method acts as a router, receiving every request from the client, identifying its type via `MessageType`, and triggering the appropriate logic.  
- **Data Access Layer (mysqlConnection.java):** Centralizes all communication with the MySQL database. Responsible for creating the connection, executing queries (SELECT, UPDATE, INSERT), and mapping results to system objects.  
- **Operational Interface (ServerUI.java):** Simple JavaFX interface for entering DB connection details and starting the server.  

🖼️ Client-Side:  
- **Communication Core (ChatClient.java):** Manages connection to the server. Responsible for sending requests and receiving responses. The central `handleMessageFromServer` updates global data in the app.  
- **MVC Architecture:**  
  - *View:* FXML files define the screens.  
  - *Controller:* Java classes handle UI logic and communication with server.  
  - *Model:* Objects representing entities like Order, Dish, User.  
- **Role-Based Interface:** After login, each user is routed to their home screen based on their role.  

---

🛠️ Technologies Used  
- **Language:** Java  
- **User Interface:** JavaFX  
- **Client-Server Communication:** OCSF (Object Client-Server Framework)  
- **Database:** MySQL  

---

🚀 Getting Started (Installation & Usage)  

### Step 0: Prerequisites  
- Java Development Kit (JDK) 8+  
- MySQL Server (WAMP/XAMPP/direct install)  

### Step 1: Database Setup  
1. Start MySQL server.  
2. Connect with MySQL Workbench or phpMyAdmin.  
3. Create schema:  
   ```sql
   CREATE SCHEMA biteme;
   ```  
4. Import `BiteMe.sql` into the schema to create tables and load data.  

### Step 2: Running the Server  
1. Locate `ServerBiteMe.jar` and run it.  
2. Fill in:  
   - DB Name: `biteme`  
   - DB User: your MySQL username (e.g., root)  
   - DB Password: your password  
   - Port: e.g., 5555  
3. Click **Connect** → server will start listening.  

### Step 3: Running the Client  
1. Locate `ClientBiteMe.jar` and run it.  
2. Fill in:  
   - IP: `localhost` (if server is on same machine)  
   - Port: e.g., 5555  
3. Click **Connect**.  
4. Login with existing DB users and start using the system.  

---

✅ You're all set! The BiteMe system is now running.
