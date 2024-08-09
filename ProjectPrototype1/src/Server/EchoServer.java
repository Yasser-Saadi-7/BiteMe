package Server;

import java.io.*;
import java.util.ArrayList;
import logic.ClientConnectionDetails;
import logic.CreateAccount;
import logic.Dish;
import logic.IncomeReport;
import logic.MealType;
import logic.Message1;
import logic.MessageType;
import gui.ServerPortFrameController1;
import logic.Order;
import logic.PerformanceReport;
import logic.Restaurant;
import logic.Selection;
import logic.UserType;
import logic.Branches; // Ensure to import the Branches class
import ocsf.server.*;

/**
 * This class overrides some of the methods in the abstract superclass to 
 * provide more functionality to the server.
 */
public class EchoServer extends AbstractServer {
    private mysqlConnection sqlConnection = new mysqlConnection();
    private ServerPortFrameController1 controller;
    private String ip, host;

    // Constructors ****************************************************

    /**
     * Constructs an instance of the echo server.
     *
     * @param port The port number to connect on.
     */
    public EchoServer(int port, String user, String password) {
        super(port);
        sqlConnection.ConnectToDB(user, password);
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
                case searchOrder: {
                    String[] messageParts = ((String) message.getObject()).split(" ");
                    Order orderFromDb = sqlConnection.parseTheData(messageParts[0]);
                    client.sendToClient(new Message1(MessageType.searchOrder, orderFromDb));
                    break;
                }
                case connect:
                case disconnect: {
                    String[] messageParts = ((String) message.getObject()).split(" ");
                    ServerPortFrameController1.connectionData.add(new ClientConnectionDetails(messageParts[0], messageParts[1], messageParts[2]));
                    client.sendToClient(new Message1(null, null));
                    break;
                }
                case viewOrdersList: {
                    ArrayList<Order> orders = sqlConnection.getOrdersFromDB();
                    client.sendToClient(new Message1(MessageType.viewOrdersList, orders));
                    break;
                }
                case updateOrder: {
                    Order orderToDb = (Order) message.getObject();
                    sqlConnection.updateOrderToDB(orderToDb);
                    client.sendToClient(new Message1(null, null));
                    break;
                }
                case logIn: {
                    String[] messageParts = ((String) message.getObject()).split(" ");
                    String userType = sqlConnection.userLogIn(messageParts[0], messageParts[1]);
                    client.sendToClient(new Message1(MessageType.logIn, userType));
                    break;
                }
                case showRestaurant: {
                    ArrayList<Restaurant> allRes = sqlConnection.getAllRes();
                    client.sendToClient(new Message1(MessageType.showRestaurant, allRes));
                    break;
                }
                case mealType: {
                    String[] messageParts = ((String) message.getObject()).split(" ");
                    ArrayList<MealType> allMealsType = sqlConnection.getMealsType(messageParts[0]);
                    client.sendToClient(new Message1(MessageType.mealType, allMealsType));
                    break;
                }
                case dishes: {
                    String[] messageParts = ((String) message.getObject()).split(" ");
                    ArrayList<Dish> allDishes = sqlConnection.getDishes(messageParts[0]);
                    client.sendToClient(new Message1(MessageType.dishes, allDishes));
                    break;
                }
                case selections: {
                    String[] messageParts = ((String) message.getObject()).split(" ");
                    ArrayList<Selection> allSelections = sqlConnection.getSelections(messageParts[0]);
                    client.sendToClient(new Message1(MessageType.selections, allSelections));
                    break;
                }
                case createAccount: {
                    String[] messageParts = ((String) message.getObject()).split(" ");
                    
                    // Validate message length
                    if (messageParts.length < 8) {
                        client.sendToClient(new Message1(MessageType.createAccount,
                                "All fields marked with * are required. Please fill them out to proceed with account creation."));
                        break;
                    }

                    // Extract parameters
                    String userID = messageParts[0]; 
                    String firstName = messageParts[1]; 
                    String lastName = messageParts[2]; 
                    String email = messageParts[3]; 
                    String phone = messageParts[4]; 
                    String creditCard = messageParts[5]; 
                    String username = messageParts[6]; 
                    String password = messageParts[7]; 

                    // Default user type
                    UserType userType1 = UserType.CLIENT; 

                    // Check for existing user
                    if (sqlConnection.userExists(userID)) {
                        client.sendToClient(new Message1(MessageType.createAccount, "User already exists"));
                    } else {
                        boolean success = sqlConnection.createAccount(userID, firstName, lastName, email, phone, creditCard,
                                userType1, username, password);
                        String responseMessage = success ? "Account created successfully" : "Failed to create account, Try again!";
                        client.sendToClient(new Message1(MessageType.createAccount, responseMessage));
                    }
                    break;
                }
                
                case getPerformanceReports: {
                    String[] reportParams = ((String) message.getObject()).split(" ");
                    
                    // Validate input
                    if (reportParams.length < 1) {
                        client.sendToClient(new Message1(MessageType.getPerformanceReports, 
                            "Branch ID is required."));
                        break;
                    }
                    
                    int branchId = Integer.parseInt(reportParams[0]); // Expecting branchId
                    ArrayList<PerformanceReport> performanceReports = sqlConnection.getPerformanceReports(branchId);
                    client.sendToClient(new Message1(MessageType.getPerformanceReports, performanceReports));
                    break;
                }

                case getIncomeReports: {
                    String[] reportParams = ((String) message.getObject()).split(" ");
                    
                    // Validate input
                    if (reportParams.length < 1) {
                        client.sendToClient(new Message1(MessageType.getIncomeReports, 
                            "Branch ID is required."));
                        break;
                    }
                    
                    String selectedBranch = reportParams[0]; // Expecting selectedBranch
                    ArrayList<IncomeReport> incomeReports = sqlConnection.getIncomeReports(selectedBranch);
                    client.sendToClient(new Message1(MessageType.getIncomeReports, incomeReports));
                    break;
                }
                
                case assignBranchToManager: {
                    String[] messageParts = ((String) message.getObject()).split(" ");
                    
                    // Validate input
                    if (messageParts.length < 2) {
                        client.sendToClient(new Message1(MessageType.assignBranchToManager,
                                "Branch ID and manager ID are required."));
                        break;
                    }
                    
                    int branchId = Integer.parseInt(messageParts[0]); // Changed to int for branchId
                    String managerId = messageParts[1];
                    boolean isAssigned = sqlConnection.assignBranchToManager(branchId, managerId);
                    
                    String responseMessage = isAssigned ? "Branch assigned successfully." : "Failed to assign branch. Please check the branch ID and manager ID.";
                    client.sendToClient(new Message1(MessageType.assignBranchToManager, responseMessage));
                    break;
                }
                case getBranchesForManager: {
                    String managerIdForBranches = (String) (message.getObject());
                    ArrayList<Branches> branches = sqlConnection.getBranchesForManager(managerIdForBranches); // Fetching list of Branches
                    client.sendToClient(new Message1(MessageType.getBranchesForManager, branches)); // Send branches as a List of Branches
                    break;
                }
                case getOrdersForBranch: {
                    String[] orderParams = ((String) message.getObject()).split(" ");
                    
                    // Validate input
                    if (orderParams.length < 3) {
                        client.sendToClient(new Message1(MessageType.getOrdersForBranch, "Manager ID, month, and year are required."));
                        break;
                    }
                    
                    String managerIdForOrders = orderParams[0];
                    String month = orderParams[1];
                    String year = orderParams[2];
                    ArrayList<Order> ordersForBranch = sqlConnection.getOrdersForBranch(managerIdForOrders, month, year);
                    client.sendToClient(new Message1(MessageType.getOrdersForBranch, ordersForBranch));
                    break;
                }
                default:
                    // Handle unknown message type
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            try {
                client.sendToClient(new Message1(null, "Invalid input format. Please check your inputs and try again."));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * This method overrides the one in the superclass. Called when the server
     * starts listening for connections.
     */
    protected void serverStarted() {
        System.out.println("Server listening for connections on port " + getPort());
    }
}
// End of EchoServer class
