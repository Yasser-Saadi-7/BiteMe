package client;

import ocsf.client.*;
import logic.Branches;
import logic.CreateAccount;
import logic.Dish;
import logic.IncomeReport;
import logic.MealType; 
import logic.Message1;
import logic.MessageType;
import logic.Order; 
import logic.PerformanceReport;
import logic.Restaurant;
import logic.Selection;
import logic.OrderReport;

import java.io.*;
import java.util.ArrayList;

import common.ChatIF;

public class ChatClient extends AbstractClient {
    // Instance variables **********************************************
    private ChatIF clientUI; 
    public static Order o1 = new Order(0, null, 0, 0.0, null, null, 0, null, null);
    public static boolean awaitResponse = true;
    public static String logIn = "error";
    public static String checkUserIdResponse = null; 
    public static String checkReportResponse = null; 
    public static String checkBranchResponse = null; 

    // Lists to hold various data from the server
    public static ArrayList<Order> list;
    public static ArrayList<Restaurant> resList;
    public static ArrayList<MealType> mealTypeList; 
    public static ArrayList<Dish> dishesList;
    public static ArrayList<Selection> selectionsList;
    public static ArrayList<CreateAccount> accountList;
    public static ArrayList<Branches> branchesList;
    public static ArrayList<Order> ordersForBranchList;

    // Report lists 
    public static ArrayList<PerformanceReport> performanceReportsList;
    public static ArrayList<IncomeReport> incomeReportsList;
    public static ArrayList<OrderReport> orderReportsList;

    // For handling searching method in reports
    public static PerformanceReport searchedPerformanceReport;
    public static IncomeReport searchedIncomeReport;
    public static OrderReport searchedOrderReport;
    
    // Constructors ****************************************************
    public ChatClient(String host, int port, ChatIF clientUI) throws IOException {
        super(host, port); // Call the superclass constructor
        this.clientUI = clientUI;
    }

    // Instance methods ************************************************
    public void handleMessageFromServer(Object msg) {
        System.out.println("--> handleMessageFromServer");
        awaitResponse = false; // Resetting the response flag
        Message1 m = (Message1) msg; // Cast the received message
        
        // Handle different types of messages based on their type
        switch (m.getMessageType()) {
            case searchOrder: 
                handleSearchOrderResponse(m);
                break;
            case viewOrdersList: 
                list = (ArrayList<Order>) m.getObject();
                break;
            case logIn: 
                logIn = (String) m.getObject();
                break;
            case showRestaurant: 
                resList = (ArrayList<Restaurant>) m.getObject();
                break;
            case mealType: 
                mealTypeList = (ArrayList<MealType>) m.getObject();
                break;
            case dishes: 
                dishesList = (ArrayList<Dish>) m.getObject();
                break;
            case selections: 
                selectionsList = (ArrayList<Selection>) m.getObject();
                break;
            case createAccount: 
                handleCreateAccountResponse(m);
                break;
            case ViewPerformanceReport: 
                performanceReportsList = (ArrayList<PerformanceReport>) m.getObject();
                checkReportResponse = "Report generated successfully"; // Example response for success
                break;
            case ViewIncomeReport: 
                incomeReportsList = (ArrayList<IncomeReport>) m.getObject();
                checkReportResponse = "Report generated successfully"; // Example response for success
                break;
            case ViewOrderReport: 
                ordersForBranchList = (ArrayList<Order>) m.getObject();
                checkReportResponse = "Report generated successfully"; // Example response for success
                break;
            case getBranchesForManager: 
                handleBranchesResponse(m);
                break;
            case getOrdersForBranch: 
                ordersForBranchList = (ArrayList<Order>) m.getObject();
                break;
            default:
                break;
        }
    }

    private void handleSearchOrderResponse(Message1 m) {
        Order order = (Order) m.getObject();
        if (order != null) {
            o1.setOrderId(order.getOrderId());
            o1.setUserID(order.getUserID());
            o1.setRestaurantId(order.getRestaurantId());
            o1.setTotalPrice(order.getTotalPrice());
            o1.setOrderAddress(order.getOrderAddress());
            o1.setOrderDate(order.getOrderDate()); // Return as String
            o1.setDeliveryTypeId(order.getDeliveryTypeId());
            o1.setExpectedDeliveryTime(order.getExpectedDeliveryTime()); // Return as String
            o1.setActualDeliveryTime(order.getActualDeliveryTime()); // Return as String
        } else {
            o1.setOrderId(-1); // Use -1 to indicate an error
        }
    }

    private void handleCreateAccountResponse(Message1 m) {
        checkUserIdResponse = (String) m.getObject();
        System.out.println("Server response: " + checkUserIdResponse); // DEBUG
        awaitResponse = false; // Signal that the response has been received
        clientUI.display(checkUserIdResponse); // Display the response to the user
    }

    private void handleBranchesResponse(Message1 m) {
        branchesList = (ArrayList<Branches>) m.getObject();
        // Convert branches to a simple String for ComboBox
        if (branchesList != null && !branchesList.isEmpty()) {
            StringBuilder branchNames = new StringBuilder();
            for (Branches branch : branchesList) {
                branchNames.append(branch.getBranchName()).append(","); 
            }
            checkBranchResponse = branchNames.toString(); // Set the response
        } else {
            checkBranchResponse = "No branches available";
        }
    }

    public void handleMessageFromClientUI(Object message) {
        try {
            openConnection(); // in order to send more than one message
            awaitResponse = true; // Set to wait for a response
            sendToServer(message); // Send the message to the server
            
            // Wait for response
            while (awaitResponse) {
                try {
                    Thread.sleep(100); // Sleep to prevent busy waiting
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            clientUI.display("Could not send message to server: Terminating client." + e);
            quit();
        }
    }

    public void createAccount(CreateAccount account) {
        handleMessageFromClientUI(new Message1(MessageType.createAccount, account)); // Send account creation request
    }

    // New method to request reports
    public void requestReport(String reportType, int managerId, String branch, String month, String year) {
        Message1 message = new Message1(MessageType.requestReport, new String[]{reportType, String.valueOf(managerId), branch, month, year});
        handleMessageFromClientUI(message); // Send report request
    }

    public void quit() {
        try {
            closeConnection(); // Close connection to the server
        } catch (IOException e) {
            e.printStackTrace(); // Log error if closing fails
        }
        System.exit(0); // Exit the application
    }
}
