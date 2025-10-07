package client;

import ocsf.client.*;
import client.*;
import common.ChatIF;
import entities.Branches;
import entities.CreateAccount;
import entities.DeliveryType1;
import entities.Dish;
import entities.IncomeReport;
import entities.LogIn;
import entities.MealsType;
import entities.Message1;
import entities.MessageType;
import entities.Order;
import entities.OrderList;
import entities.OrderListSponser;
import entities.OrderReport;
import entities.PerformanceReport;
import entities.Restaurant;
import entities.Selection;

import java.io.*;
import java.util.ArrayList;

//import server.ServerUI;

/**
 * This class overrides some of the methods defined in the abstract superclass
 * in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 * @version July 2000
 */
public class ChatClient extends AbstractClient {
	// Instance variables **********************************************

	public static final Object responseData = null;
	/**
	 * The interface type variable. It allows the implementation of the display
	 * method in the client.
	 */
	ChatIF clientUI;
	public static Order o1 = new Order(null, null, null, null, null);
	public static ArrayList<Order> list;
	public static boolean awaitResponse = true;
	public static ArrayList<Restaurant> resList;
	public static ArrayList<MealsType> mealsTypeList;
	public static ArrayList<Dish> dishesList;
	public static ArrayList<Selection> selectionsList;
	public static ArrayList<DeliveryType1> deliveryTypeList;
	public static String listNumber;
	public static String reportNumber;
	public static ArrayList<OrderList> orderList;

	public static String checkUserIdResponse = null; // Response for user ID checks
	public static String checkReportResponse = null; // Response for report checks
	public static String checkBranchResponse = null; // Response for branch checks

//Variable to store the currently logged-in user
	public static LogIn logIn;
	public static LogIn loggedInUser = null;
	public static ArrayList<CreateAccount> accountList; // List of accounts
	public static ArrayList<Branches> branchesList; // List of branches
//Report lists
	public static ArrayList<PerformanceReport> performanceReportsList;
	public static ArrayList<IncomeReport> incomeReportsList;
	public static ArrayList<OrderReport> orderReportsList;

	// For handling searching method in reports
	public static PerformanceReport searchedPerformanceReport;
	public static IncomeReport searchedIncomeReport;
	public static OrderReport searchedOrderReport;

	public static String dishNumber;
	public static ArrayList<OrderListSponser> orderList1;

	// Constructors ****************************************************

	/**
	 * Constructs an instance of the chat client.
	 *
	 * @param host     The server to connect to.
	 * @param port     The port number to connect on.
	 * @param clientUI The interface type variable.
	 */

	public ChatClient(String host, int port, ChatIF clientUI) throws IOException {
		super(host, port); // Call the superclass constructor
		this.clientUI = clientUI;
		
	}

	// Instance methods ************************************************

	/**
	 * This method handles all data that comes in from the server.
	 *
	 * @param msg The message from the server.
	 */
	public void handleMessageFromServer(Object msg) {

		System.out.println("--> handleMessageFromServer");
		awaitResponse = false;
		Message1 m = (Message1) msg;

		switch (m.getMessageType()) {
		case searchOrder:
			Order order = (Order) m.getObject();
			if (order != null) {
				o1.setListNumber(order.getListNumber());
				o1.setOrderAddress(order.getOrderAddress());
				o1.setOrederNumber(order.getOrderNumber());
				o1.setRestaurant(order.getRestaurant());
				o1.setTprice(order.getTprice());
			} else {
				o1.setOrederNumber("Error");
			}
			break;
		case viewOrdersList:
			list = (ArrayList<Order>) m.getObject();
			break;
		case logIn:
			// Handle the login message and store the logged-in user
			logIn = (LogIn) m.getObject();
			break;
		case showRestaurant:
			resList = (ArrayList<Restaurant>) m.getObject();
			break;
		case mealsType:
			mealsTypeList = (ArrayList<MealsType>) m.getObject();
			break;
		case dishes:
			dishesList = (ArrayList<Dish>) m.getObject();
			break;
		case selections:
			selectionsList = (ArrayList<Selection>) m.getObject();
			break;
		case deliveryType:
			deliveryTypeList = (ArrayList<DeliveryType1>) m.getObject();
			break;
		case getListNumber:
			listNumber = (String) m.getObject();
			break;
		case getReportNum:
			reportNumber = (String) m.getObject();
			break;
		case orderList:
			orderList = (ArrayList<OrderList>) m.getObject();
			break;
		case createAccount:
			handleCreateAccountResponse(m);
			break;
		case getPerformanceReports:
			performanceReportsList = (ArrayList<PerformanceReport>) m.getObject();
			checkReportResponse = "Report generated successfully"; 
			break;
		case getIncomeReports:
			incomeReportsList = (ArrayList<IncomeReport>) m.getObject();
			checkReportResponse = "Report generated successfully"; 
			break;
		case getOrderReports:
			orderReportsList = (ArrayList<OrderReport>) m.getObject();
			checkReportResponse = "Report generated successfully"; 
			break;
		case getDishNumber:
			dishNumber = (String) m.getObject();
			break;
		case OrderList:
			orderList1 = (ArrayList<OrderListSponser>) m.getObject();
			break;
		case getBranchesForManager:
			handleBranchesResponse(m);
			break;

		default:
			break;
		}
	}

	public void createAccount(CreateAccount account) {
		handleMessageFromClientUI(new Message1(MessageType.createAccount, account)); // Send account creation request
	}

	/**
	 * Requests a report from the server based on specified criteria.
	 *
	 * @param reportType the type of report to request
	 * @param managerId  the ID of the manager requesting the report
	 * @param branch     the branch to which the report applies
	 * @param month      the month of the report
	 * @param year       the year of the report
	 */
	public void requestReport(String reportType, int managerId, String branch, String month, String year) {
		Message1 message = new Message1(MessageType.requestReport,
				new String[] { reportType, String.valueOf(managerId), branch, month, year });
		handleMessageFromClientUI(message); // Send report request
	}

	/**
	 * Handles the response for account creation requests.
	 *
	 * @param m the message containing the account creation response
	 */
	private void handleCreateAccountResponse(Message1 m) {
		checkUserIdResponse = (String) m.getObject();
		System.out.println("Server response: " + checkUserIdResponse); // DEBUG
		awaitResponse = false; // Signal that the response has been received
		clientUI.display(checkUserIdResponse); // Display the response to the user
	}

	/**
	 * Handles the response for branch-related requests.
	 *
	 * @param m the message containing branch information
	 */
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
			checkBranchResponse = "No branches available"; // Indicate no branches found
		}
	}

	/**
	 * This method handles all data coming from the UI
	 *
	 * @param message The message from the UI.
	 */

	public void handleMessageFromClientUI(Object message) {
		try {
			openConnection();// in order to send more than one message
			awaitResponse = true;
			sendToServer(message);
			// wait for response
			while (awaitResponse) {
				try {
					Thread.sleep(100);
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

	/**
	 * This method terminates the client.
	 */
	public void quit() {
		try {
			
			closeConnection();
		} catch (IOException e) {
		}
		System.exit(0);
	}
}
//End of ChatClient class
