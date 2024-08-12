package Server;

import java.io.IOException;
import java.util.ArrayList;

import client.ChatClient;
import gui.ServerPortFrameController1;
import logic.ClientConnectionDetails;
import logic.CreateAccount;
import logic.DeliveryType1;
import logic.Dish;
import logic.IncomeReport;
import logic.LogIn;
import logic.MealType;
import logic.Message1;
import logic.MessageType;
import logic.Order;
import logic.OrderList;
import logic.PerformanceReport;
import logic.Restaurant;
import logic.Selection;
import logic.UserType;
import logic.AccountType;
import logic.Branches;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

/**
 * This class overrides some of the methods in the abstract superclass to
 * provide more functionality to the server.
 */
public class EchoServer extends AbstractServer {
    private mysqlConnection sqlConnection = new mysqlConnection();
    private ServerPortFrameController1 controller;

    // Constructors ****************************************************

    /**
     * Constructs an instance of the echo server.
     *
     * @param port The port number to connect on.
     */
    public EchoServer(int port, String user, String password) {
        super(port);
        sqlConnection.connectToDB(user, password);
    }

    // Instance methods ************************************************

    /**
     * This method handles any messages received from the client.
     *
     * @param msg    The message received from the client.
     * @param client The connection from which the message originated.
     */
    public void handleMessageFromClient(Object msg, ConnectionToClient client) {
        Message1 message = (Message1) msg;

        try {
            switch (message.getMessageType()) {
                case searchOrder:
                    handleSearchOrder(message, client);
                    break;
                case connect:
                case disconnect:
                    handleConnectDisconnect(message, client);
                    break;
                case viewOrdersList:
                    handleViewOrdersList(client);
                    break;
                case updateOrder:
                    handleUpdateOrder(message, client);
                    break;
                case logIn:
                    handleLogIn(message, client);
                    break;
                case showRestaurant:
                    handleShowRestaurant(client);
                    break;
                case mealType:
                    handleMealType(message, client);
                    break;
                case dishes:
                    handleDishes(message, client);
                    break;
                case selections:
                    handleSelections(message, client);
                    break;
                case createAccount:
                    handleCreateAccount(message, client);
                    break;
                case getPerformanceReports:
                    handleGetPerformanceReports(message, client);
                    break;
                case getIncomeReports:
                    handleGetIncomeReports(message, client);
                    break;
                case assignBranchToManager:
                    handleAssignBranchToManager(message, client);
                    break;
                case getBranchesForManager:
                    handleGetBranchesForManager(message, client);
                    break;
                case getOrdersForBranch:
                    handleGetOrdersForBranch(message, client);
                    break;
                default:
                    client.sendToClient(new Message1(null, "Unknown message type."));
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleSearchOrder(Message1 message, ConnectionToClient client) throws IOException {
        String orderId = (String) message.getObject();
        Order orderFromDb = sqlConnection.parseTheData(orderId);
        client.sendToClient(new Message1(MessageType.searchOrder, orderFromDb));
    }

    private void handleConnectDisconnect(Message1 message, ConnectionToClient client) throws IOException {
        String[] messageParts = ((String) message.getObject()).split(" ");
        ServerPortFrameController1.connectionData.add(new ClientConnectionDetails(messageParts[0], messageParts[1], messageParts[2]));
        client.sendToClient(new Message1(null, null));
    }

    private void handleViewOrdersList(ConnectionToClient client) throws IOException {
        ArrayList<Order> orders = sqlConnection.getOrdersFromDB();
        client.sendToClient(new Message1(MessageType.viewOrdersList, orders));
    }

    private void handleUpdateOrder(Message1 message, ConnectionToClient client) throws IOException {
        Order orderToDb = (Order) message.getObject();
        sqlConnection.updateOrderToDB(orderToDb);
        client.sendToClient(new Message1(null, null));
    }

    private void handleLogIn(Message1 message, ConnectionToClient client) throws IOException {
        String[] messageParts = ((String) message.getObject()).split(" ");
        LogIn loggedInUser = sqlConnection.userLogIn(messageParts[0], messageParts[1]);
        client.sendToClient(new Message1(MessageType.logIn, loggedInUser));
    }

    private void handleShowRestaurant(ConnectionToClient client) throws IOException {
        ArrayList<Restaurant> allRes = sqlConnection.getAllRes();
        client.sendToClient(new Message1(MessageType.showRestaurant, allRes));
    }

    private void handleMealType(Message1 message, ConnectionToClient client) throws IOException {
        String restaurantIdStr = ((String) message.getObject()).split(" ")[0];
        
        try {
            int restaurantId = Integer.parseInt(restaurantIdStr); // Convert String to int
            ArrayList<MealType> allMealsType = sqlConnection.getMealType(restaurantId); // Use int here
            client.sendToClient(new Message1(MessageType.mealType, allMealsType));
        } catch (NumberFormatException e) {
            client.sendToClient(new Message1(MessageType.mealType, "Invalid restaurant ID format."));
        }
    }


    private void handleDishes(Message1 message, ConnectionToClient client) throws IOException {
        String mealTypeId = ((String) message.getObject()).split(" ")[0];
        ArrayList<Dish> allDishes = sqlConnection.getDishes(mealTypeId);
        client.sendToClient(new Message1(MessageType.dishes, allDishes));
    }

    private void handleSelections(Message1 message, ConnectionToClient client) throws IOException {
        String dishId = ((String) message.getObject()).split(" ")[0];
        ArrayList<Selection> allSelections = sqlConnection.getSelections(dishId);
        client.sendToClient(new Message1(MessageType.selections, allSelections));
    }

    private void handleCreateAccount(Message1 message, ConnectionToClient client) throws IOException {
        CreateAccount accountDetails = (CreateAccount) message.getObject();
        
        if (sqlConnection.userExists(accountDetails.getUserID())) {
            client.sendToClient(new Message1(MessageType.createAccount, "User already exists"));
            return;
        }
        
        boolean success = sqlConnection.createAccount(
                accountDetails.getUserID(),
                accountDetails.getFirstName(),
                accountDetails.getLastName(),
                accountDetails.getEmail(),
                accountDetails.getPhone(),
                accountDetails.getCreditCard(),
                accountDetails.getUserType(),
                accountDetails.getUsername(),
                accountDetails.getPassword(),
                accountDetails.getHomeBranchId(),
                accountDetails.getAccountType()
        );
        
        String responseMessage = success ? "Account created successfully" : "Failed to create account, Try again!";
        client.sendToClient(new Message1(MessageType.createAccount, responseMessage));
    }

    private void handleGetPerformanceReports(Message1 message, ConnectionToClient client) throws IOException {
        String[] reportParams = ((String) message.getObject()).split(" ");
        if (reportParams.length < 1) {
            client.sendToClient(new Message1(MessageType.getPerformanceReports, "Branch ID is required."));
            return;
        }
        
        int branchId = Integer.parseInt(reportParams[0]);
        ArrayList<PerformanceReport> performanceReports = sqlConnection.getPerformanceReports(branchId);
        client.sendToClient(new Message1(MessageType.getPerformanceReports, performanceReports));
    }

    private void handleGetIncomeReports(Message1 message, ConnectionToClient client) throws IOException {
        String[] reportParams = ((String) message.getObject()).split(" ");
        if (reportParams.length < 1) {
            client.sendToClient(new Message1(MessageType.getIncomeReports, "Branch ID is required."));
            return;
        }

        String selectedBranch = reportParams[0];
        ArrayList<IncomeReport> incomeReports = sqlConnection.getIncomeReports(selectedBranch);
        client.sendToClient(new Message1(MessageType.getIncomeReports, incomeReports));
    }

    private void handleAssignBranchToManager(Message1 message, ConnectionToClient client) throws IOException {
        String[] messageParts = ((String) message.getObject()).split(" ");
        if (messageParts.length < 2) {
            client.sendToClient(new Message1(MessageType.assignBranchToManager, "Branch ID and manager ID are required."));
            return;
        }

        int branchId = Integer.parseInt(messageParts[0]);
        String managerId = messageParts[1];
        boolean isAssigned = sqlConnection.assignBranchToManager(branchId, managerId);
        
        String responseMessage = isAssigned ? "Branch assigned successfully." : "Failed to assign branch. Please check the branch ID and manager ID.";
        client.sendToClient(new Message1(MessageType.assignBranchToManager, responseMessage));
    }

    private void handleGetBranchesForManager(Message1 message, ConnectionToClient client) throws IOException {
        String managerIdForBranches = (String) message.getObject();
        ArrayList<Branches> branchesForManager = sqlConnection.getBranchesForManager(managerIdForBranches);
        client.sendToClient(new Message1(MessageType.getBranchesForManager, branchesForManager));
    }

    private void handleGetOrdersForBranch(Message1 message, ConnectionToClient client) throws IOException {
        String[] params = (String[]) message.getObject();
        
        if (params == null || params.length < 3) {
            client.sendToClient(new Message1(MessageType.getOrdersForBranch, "Manager ID, month, and year are required."));
            return;
        }
        
        String managerIdForOrders = params[0]; // Extracting manager ID
        String month = params[1]; // Extracting month
        String year = params[2]; // Extracting year
        
        ArrayList<Order> ordersForBranch = sqlConnection.getOrdersForBranch(managerIdForOrders, month, year);
        client.sendToClient(new Message1(MessageType.getOrdersForBranch, ordersForBranch));
    }

    // Method that handles when a client is disconnected from the server.
    public void clientDisconnected(ConnectionToClient client) {
        System.out.println("Client disconnected: " + client);
    }

    // Method that handles when a client is connected to the server.
    public void clientConnected(ConnectionToClient client) {
        System.out.println("Client connected: " + client);
    }

    // Setter for the controller
    public void setController(ServerPortFrameController1 controller) {
        this.controller = controller;
    }
}
