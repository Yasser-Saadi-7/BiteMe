package Server;

import java.util.ArrayList;
import client.ChatClient;
import gui.ServerPortFrameController1;
import logic.Order;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

/**
 * This class overrides some of the methods in the abstract 
 * superclass in order to give more functionality to the server.
 */
public class EchoServer extends AbstractServer {
    private mysqlConnection sqlConnection = new mysqlConnection();
    private ServerPortFrameController1 controller;

    // Constructors
    public EchoServer(int port, String user, String password) {
        super(port);
        sqlConnection.ConnectToDB(user, password);
    }

    // Instance methods

    /**
     * This method handles any messages received from the client.
     *
     * @param msg The message received from the client.
     * @param client The connection from which the message originated.
     */
    public void handleMessageFromClient(Object msg, ConnectionToClient client) {
        if (msg instanceof Order) {
            handleOrderUpdate((Order) msg);
        } else if (msg instanceof String && ((String) msg).equals("view")) {
            handleViewOrders();
        } else if (msg instanceof ArrayList) {
            ArrayList<String> arr = (ArrayList<String>) msg;
            switch (arr.get(0)) {
                case "LogIn":
                    handleLogin(arr);
                    break;
                case "CreateAccount":
                    handleCreateAccount(arr, client); // Pass the client connection
                    break;
                case "CheckUserId":
                    handleCheckUserId(arr, client); // Pass the client connection
                    break;
                default:
                    sendToClient(client, "Failed: Unknown command."); // Use the client object
                    break;
            }
        } else {
            handleUnknownMessage(msg, client);
        }
    }

    private void handleCreateAccount(ArrayList<String> arr, ConnectionToClient client) {
        // Check if the minimum required fields are provided
        if (arr.size() < 6) {
            sendToClient(client, "Failed: Incomplete account information.");
            return;
        }

        String firstName = arr.get(1);
        String lastName = arr.get(2);
        String phone = arr.get(3);
        String id = arr.get(4);
        String email = arr.get(5);
        String creditCard = arr.size() > 6 ? arr.get(6) : ""; // Assign empty string if credit card info is not provided

        boolean isUnique = sqlConnection.isUserIdUnique(id);
        if (!isUnique) {
            sendToClient(client, "Failed: User ID already exists. Please choose a different ID.");
            return;
        }

        // Create the account in the database
        boolean accountCreated = sqlConnection.createAccount(firstName, lastName, phone, id, email, creditCard);
        if (accountCreated) {
            sendToClient(client, "Success: New Account has been created.");
        } else {
            sendToClient(client, "Failed: Unable to create account. Please try again.");
        }
    }


    private void handleOrderUpdate(Order order) {
        sqlConnection.updateOrderToDB(order);
        this.sendToAllClients("Order Updated!");
    }

    private void handleViewOrders() {
        ArrayList<Order> orders = mysqlConnection.getOrdersFromDB();
        this.sendToAllClients(orders);
    }

    private void handleLogin(ArrayList<String> arr) {
        if (arr.get(0).equals("LogIn")) {
            ChatClient.logIn = sqlConnection.userLogIn(arr.get(1), arr.get(2));
        }
    }

    private void handleCheckUserId(ArrayList<String> arr, ConnectionToClient client) { // Accept client as a parameter
        String userId = arr.get(1);
        boolean isUnique = sqlConnection.isUserIdUnique(userId);
        String response = isUnique ? "CheckUserId:Unique" : "CheckUserId:Exists";
        sendToClient(client, response); // Use the client object
    }

    // Change the sendToClient method to use ConnectionToClient
    private void sendToClient(ConnectionToClient client, String message) {
        try {
            client.sendToClient(message); // Send message to the specific client
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleUnknownMessage(Object msg, ConnectionToClient client) {
        System.out.println("Message received: " + msg + " from " + client);
        Order orderFromDb = sqlConnection.parseTheData(msg);

        if (orderFromDb != null) {
            System.out.println("Order found!");
            this.sendToAllClients(orderFromDb);
        } else {
            System.out.println("Order Not Found");
            this.sendToAllClients("Error");
        }
    }

    /**
     * This method overrides the one in the superclass. Called
     * when the server starts listening for connections.
     */
    protected void serverStarted() {
        System.out.println("Server listening for connections on port " + getPort());
    }

    @Override
    protected void clientConnected(ConnectionToClient client) {
        String ipAddress = client.getInetAddress().getHostAddress();
        String hostName = client.getInetAddress().getHostName();
        ServerUI.aFrame.addClientConnection(ipAddress, hostName, "Connected");
        System.out.println("Client connected: " + client);
        System.out.println("IP: " + ipAddress);
        System.out.println("Host: " + hostName);
    }

    @Override
    synchronized protected void clientDisconnected(ConnectionToClient client) {
        String ipAddress = client.getInetAddress().getHostAddress();
        String hostName = client.getInetAddress().getHostName();
        ServerUI.aFrame.addClientConnection(ipAddress, hostName, "Disconnected");
        System.out.println("Client disconnected: " + client);
    }

    @Override
    synchronized protected void clientException(ConnectionToClient client, Throwable exception) {
        String ipAddress = client.getInetAddress().getHostAddress();
        String hostName = client.getInetAddress().getHostName();
        ServerUI.aFrame.addClientConnection(ipAddress, hostName, "Disconnected");
        System.out.println("Exception occurred for client: " + client);
    }
}
// End of EchoServer class
