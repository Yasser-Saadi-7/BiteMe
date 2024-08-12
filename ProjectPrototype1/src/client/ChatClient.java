package client;

import ocsf.client.AbstractClient;
import common.ChatIF;
import logic.*;

import java.io.IOException;
import java.util.ArrayList;

/**
 * ChatClient handles the communication between the client and the server,
 * processing messages sent and received, including user authentication and
 * order management.
 */
public class ChatClient extends AbstractClient {
	// Instance variables **********************************************
	private ChatIF clientUI; // UI component for client interaction

	// Unified Order object to hold order details
	public static Order o1 = new Order(0, null, 0, 0.0, null, null, 0, null, null, null, 0, null);
	public static boolean awaitResponse = true; // Flag to wait for server response

	// Log-in and response variables
	public static String logIn = "error"; // Stores login status
	
	public static String checkUserIdResponse = null; // Response for user ID checks
	public static String checkReportResponse = null; // Response for report checks
	public static String checkBranchResponse = null; // Response for branch checks

	// Variable to store the currently logged-in user
	public static LogIn loggedInUser = null; // Changed from CreateAccount to LogIn

	// Lists to hold various data from the server
	public static ArrayList<Order> list; // List of orders
	public static ArrayList<Restaurant> resList; // List of restaurants
	public static ArrayList<MealType> mealTypeList; // List of meal types
	public static ArrayList<Dish> dishesList; // List of dishes
	public static ArrayList<Selection> selectionsList; // List of selections
	public static ArrayList<CreateAccount> accountList; // List of accounts
	public static ArrayList<Branches> branchesList; // List of branches
	public static ArrayList<Order> ordersForBranchList; // List of orders for a specific branch
	public static ArrayList<DeliveryType1> deliveryTypeList;
	public static ArrayList<OrderList> orderList;

	public static String listNumber;

	// Report lists
	public static ArrayList<PerformanceReport> performanceReportsList;
	public static ArrayList<IncomeReport> incomeReportsList;
	public static ArrayList<OrderReport> orderReportsList;

	// For handling searching method in reports
	public static PerformanceReport searchedPerformanceReport;
	public static IncomeReport searchedIncomeReport;
	public static OrderReport searchedOrderReport;

	// Constructors ****************************************************
	/**
	 * Constructs a ChatClient instance with the specified host, port, and UI
	 * component.
	 *
	 * @param host     the server host address
	 * @param port     the server port number
	 * @param clientUI the UI component for client interaction
	 * @throws IOException if an I/O error occurs during connection
	 */
	public ChatClient(String host, int port, ChatIF clientUI) throws IOException {
		super(host, port); // Call the superclass constructor
		this.clientUI = clientUI; // Initialize client UI
	}

	// Instance methods ************************************************
	/**
	 * Handles messages received from the server, processing them based on their
	 * type.
	 *
	 * @param msg the message object received from the server
	 */
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
			handleLogInResponse(m);
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
		case deliveryType:
			deliveryTypeList = (ArrayList<DeliveryType1>) m.getObject();
			break;
		case getListNumber:
			listNumber = (String) m.getObject();
			break;
		case orderList:
			orderList = (ArrayList<OrderList>) m.getObject();
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

	/**
	 * Handles the response for user login.
	 *
	 * @param m the message containing the login response
	 */
	private void handleLogInResponse(Message1 m) {
		logIn = (String) m.getObject();

		// Assuming the message contains user details upon successful login
		if (!logIn.equals("error")) {
			String[] userDetails = logIn.split(","); // Example format:
														// "userId,username,firstName,lastName,email,phone,creditCard,userType,homeBranchId,accountType"
			String userId = userDetails[0];
			String firstName = userDetails[1];
			String lastName = userDetails[2];
			String email = userDetails[3];
			String phone = userDetails[4];
			String creditCard = userDetails[5];
			UserType userType = UserType.valueOf(userDetails[6]);
			String username = userDetails[7];
			String password = userDetails[8];
			Integer homeBranchId = Integer.valueOf(userDetails[9]);
			AccountType accountType = AccountType.valueOf(userDetails[10]);

			// Store the logged-in user details using LogIn class
			loggedInUser = new LogIn(userId, firstName, lastName, email, phone, creditCard, userType, username,
					password, homeBranchId, accountType);
		}
	}

	/**
	 * Handles the response for searching an order.
	 *
	 * @param m the message containing the order search result
	 */
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
	 * Sends a message from the client UI to the server.
	 *
	 * @param message the message object to send
	 */
	public void handleMessageFromClientUI(Object message) {
		try {
			openConnection(); // Establish connection to send messages
			awaitResponse = true; // Set to wait for a response
			sendToServer(message); // Send the message to the server

			// Wait for response
			while (awaitResponse) {
				try {
					Thread.sleep(100); // Sleep to prevent busy waiting
				} catch (InterruptedException e) {
					e.printStackTrace(); // Log any interruption
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			clientUI.display("Could not send message to server: Terminating client." + e);
			quit(); // Terminate client on error
		}
	}

	/**
	 * Creates a new account with the provided account details.
	 *
	 * @param account the CreateAccount object containing account details
	 */
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
	 * Closes the connection to the server and exits the application.
	 */
	public void quit() {
		try {
			closeConnection(); // Close connection to the server
		} catch (IOException e) {
			e.printStackTrace(); // Log error if closing fails
		}
		System.exit(0); // Exit the application
	}
}
