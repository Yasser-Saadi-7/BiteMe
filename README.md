# BiteMe
A client-server food ordering application with a MySQL database, featuring a role-based interface for customers, restaurants, and managers.

🍔 BiteMe Project
BiteMe is a distributed, full-stack restaurant management and food ordering system developed in the Java environment. The system was built as a academic project, demonstrating an advanced client-server architecture, database management, and a dynamic, role-based user interface. The system allows for the complete management of business operations, from receiving a customer's order, through operational processing at the restaurant, to the generation of complex analytical reports for management.
---
🎯 Implemented Features
The system provides a tailored user experience for each of its user types, with an emphasis on the following fully developed functionalities:
👤 For the Customer
Guided Ordering Wizard: A step-by-step interface for selecting a restaurant, meal type, dishes, and customizing each dish.
Flexible Delivery Options: Full support for standard delivery or self-pickup (Take Away).
Order Tracking: View order history and the updated status of each order.

🍴 For the Restaurant (Qualified Worker)
Real-Time Order Management: An operational interface to view incoming orders and update their status (Approved, Ready, Shipped).
Full Menu Management: The ability to add, delete, and update the prices of dishes on the restaurant's menu.

👔 For the Branch Manager
Account Creation: An interface for registering new customers in the system.
Monthly Report Generation: Access to detailed, view-ready reports for their branch:
Income Report: Financial analysis of restaurant revenues in the branch.
Order Report: Breakdown of orders by item type.
Performance Report: Graphical analysis of adherence to delivery times.

---


🏗️ System Architecture
The system is built on a Three-Tier Client-Server architecture, which clearly separates presentation, logic, and data.

🖥️ Server-Side:
The server is the brain of the system. It manages all business logic, communication with the database, and handles requests from all connected clients.

Communication Core (EchoServer.java): Based on the OCSF (Object Client-Server Framework) library, this component listens for connections and manages communication with multiple clients simultaneously. The central handleMessageFromClient method acts as a router, receiving every request from the client, identifying its type via MessageType, and triggering the appropriate logic.

Data Access Layer (mysqlConnection.java): This component centralizes all communication with the MySQL database. It is responsible for creating the connection, executing all queries (SELECT, UPDATE, INSERT), and mapping the results to the system's objects.

Operational Interface (ServerUI.java): The server includes a simple graphical user interface based on JavaFX, allowing the system operator to easily enter database connection details and start the server.

🖼️ Client-Side:
The client-side is a rich desktop application built with JavaFX, providing a dynamic and user-tailored interface.

Communication Core (ChatClient.java): Also based on OCSF, this component manages the connection to the server. It is responsible for sending requests (as Message objects) to the server and receiving responses. The central handleMessageFromServer method receives the responses and updates the global data within the application.

MVC Architecture (Model-View-Controller):

View: The FXML files define the visual structure of each screen in the application.

Controller: Dedicated classes (like LogInController, OrderSummaryController, etc.) contain all the UI logic. They respond to user actions (button clicks), send requests to the server via ChatClient, and update the view according to the returned information.

Model: Objects representing the system's entities (like Order, Dish, User), located in a shared package (common).

Role-Based Interface: After login, the LogInController routes each user to their appropriate home screen (ClientPage, BranchManagerPage, etc.), ensuring that each user is exposed only to the functionality relevant to them.

---


🛠️ Technologies Used
Language: Java

User Interface (UI): JavaFX

Client-Server Communication: OCSF (Object Client-Server Framework)

Database: MySQL

---
🚀 Getting Started (Installation & Usage)
This guide will explain step-by-step how to run the project on your machine.

Step 0: Prerequisites
Ensure the following software is installed on your computer:

Java Development Kit (JDK) - Version 8 or higher.

MySQL Server (you can use WAMP, XAMPP, or a direct installation).

Step 1: Database Setup
This is the most critical step. The server will not work without it.

Start your MySQL server and ensure it is running.

Connect to the database using a management tool like MySQL Workbench or phpMyAdmin.

Create a new schema for the project. It is recommended to name it biteme. You can do this with the command:

SQL

CREATE SCHEMA biteme;
Load the data: Select the biteme schema you just created and import the BiteMe.sql file included in the project. This action will create all the necessary tables and load initial data.

Step 2: Running the Server
Locate the ServerBiteMe.jar file in the project directory.

Run the file (usually by double-clicking it).

The server settings window will open. Fill in the following details:

DB Name: The name of the schema you created (e.g., biteme).

DB User: Your database username (usually root).

DB Password: The user's password.

Port: The port number on which the server will listen for clients (e.g., 5555).

Click "Connect". If the details are correct, you will see a confirmation message, and the server will start listening. Do not close this window!

Step 3: Running the Client
Locate the ClientBiteMe.jar file in the project directory.

Run the file (by double-clicking it).

The server connection window will open. Fill in the following details:

IP: The IP address of the computer where the server is running. If it's the same machine, type localhost.

Port: The port you set when starting the server (e.g., 5555).

Click "Connect".

If the connection is successful, you will be taken to the login screen. You can now use one of the existing users in the database to log into the system.

You're all set! The system is running.

