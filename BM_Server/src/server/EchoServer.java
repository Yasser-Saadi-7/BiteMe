// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 
package server;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import entities.Branches;
import entities.ClientConnectionDetails;
import entities.CreateAccount;
import entities.DeliveryType1;
import entities.Dish;
import entities.IncomeReport;
import entities.LogIn;
import entities.MealsType;
import entities.Message1;
import entities.MessageType;
import controllers.ServerPortFrameController1;
import entities.Order;
import entities.OrderList;
import entities.OrderListSponser;
import entities.OrderReport;
import entities.PerformanceReport;
import entities.Restaurant;
import entities.Selection;
import ocsf.server.*;

/**
 * This class overrides some of the methods in the abstract superclass in order
 * to give more functionality to the server.
 *@author Yasser Saadi
 */

public class EchoServer extends AbstractServer {
	private mysqlConnection Sqlconnection = new mysqlConnection();

	private ServerPortFrameController1 controller;
	private String ip, host;

	// Class variables *************************************************

	/**
	 * The default port to listen on.
	 */
	// final public static int DEFAULT_PORT = 5555;

	// Constructors ****************************************************

	/**
	 * Constructs an instance of the echo server.
	 *
	 * @param port The port number to connect on.
	 * 
	 */
// public static Student [] students=new Student[4];

	public EchoServer(int port, String user, String password) {
		super(port);
		mysqlConnection.ConnectToDB(user, password);

	}

	// Instance methods ************************************************

	/**
	 * This method handles any messages received from the client.
	 *
	 * @param msg    The message received from the client.
	 * @param client The connection from which the message originated.
	 * @param
	 */
	public void handleMessageFromClient(Object msg, ConnectionToClient client) {
		Message1 m = (Message1) msg;
		String message[];
		ArrayList<Object> arr;
		ArrayList<OrderReport> arrReport;
		try {
			switch (m.getMessageType()) {
			case searchOrder:
				message = ((String) m.getObject()).split(" ");
				Order orderFromDb = Sqlconnection.parseTheData(message[0]);
				client.sendToClient(new Message1(MessageType.searchOrder, orderFromDb));
				break;
			case connect:
				message = ((String) m.getObject()).split(" ");
				ServerPortFrameController1.connectionData
						.add(new ClientConnectionDetails(message[0], message[1], message[2]));
				client.sendToClient(new Message1(null, null));
				break;
			case disconnect:
				message = ((String) m.getObject()).split(" ");
				ServerPortFrameController1.connectionData
						.add(new ClientConnectionDetails(message[0], message[1], message[2]));
				client.sendToClient(new Message1(null, null));
			//case viewOrdersList:
				//ArrayList<Order> orders = mysqlConnection.getOrdersFromDB();
				//client.sendToClient(new Message1(MessageType.viewOrdersList, orders));
				//break;
			case updateOrder:
				Order orderToDb = (Order) m.getObject();
				Sqlconnection.updateOrderToDB(orderToDb);
				client.sendToClient(new Message1(null, null));
				break;
			case logIn:
				message = ((String) m.getObject()).split(" ");
				LogIn loggedInUser = Sqlconnection.userLogIn(message[0], message[1]);
				client.sendToClient(new Message1(MessageType.logIn, loggedInUser));
				break;
			case showRestaurant:
				ArrayList<Restaurant> Allres = Sqlconnection.getAllRes();
				client.sendToClient(new Message1(MessageType.showRestaurant, Allres));
				break;
			case mealsType:
				message = ((String) m.getObject()).split(" ");
				ArrayList<MealsType> allMealsType = Sqlconnection.getMealsType(message[0]);
				client.sendToClient(new Message1(MessageType.mealsType, allMealsType));
				break;
			case dishes:
				message = ((String) m.getObject()).split(" ");
				ArrayList<Dish> allDishes = Sqlconnection.getDishes(message[0]);
				client.sendToClient(new Message1(MessageType.dishes, allDishes));
				break;
			case selections:
				message = ((String) m.getObject()).split(" ");
				ArrayList<Selection> allSelections = Sqlconnection.getSelections(message[0]);
				client.sendToClient(new Message1(MessageType.selections, allSelections));
				break;
			case deliveryType:
				ArrayList<DeliveryType1> allDeliveryTypes = Sqlconnection.getAllDeliveryTypes();
				client.sendToClient(new Message1(MessageType.deliveryType, allDeliveryTypes));
				break;
			case takeAway:
				message = ((String) m.getObject()).split(" ");
				mysqlConnection.insertOrder(message[0], message[1], message[2], message[3], message[4], message[5],
						message[6], message[7], message[8], message[9], message[10], message[11]);
				
				client.sendToClient(new Message1(null, null));
				break;
			case getListNumber:
				String listNum = Sqlconnection.getListNumber();
				client.sendToClient(new Message1(MessageType.getListNumber, listNum));
				break;
			case setListNumber:
				message = ((String) m.getObject()).split(" ");
				mysqlConnection.updateListNumber(message[0]);
				client.sendToClient(new Message1(null, null));
				break;
			case orderList:
				message = ((String) m.getObject()).split(" ");
				ArrayList<OrderList> orderList = Sqlconnection.getOrderList(message[0]);
				client.sendToClient(new Message1(MessageType.orderList, orderList));
				break;
			case updateStatus:
				message = ((String) m.getObject()).split(" ");
				Sqlconnection.updateStatus(message[0]);
				client.sendToClient(new Message1(null, null));
				break;
			case createAccount:
				handleCreateAccount(m, client);
				break;
			case getPerformanceReports:
				// handleGetPerformanceReports(m, client);
				message = ((String) m.getObject()).split(" ");
				ArrayList<PerformanceReport> performanceReports = mysqlConnection.getPerformanceReports(message[0],
						message[1], message[2]);
				client.sendToClient(new Message1(MessageType.getPerformanceReports, performanceReports));
				break;

			case getIncomeReports:
				message = ((String) m.getObject()).split(" ");
				ArrayList<IncomeReport> IncomeReport = mysqlConnection.getIncomeReports(message[0], message[1],
						message[2]);
				client.sendToClient(new Message1(MessageType.getIncomeReports, IncomeReport));
				break;

			case getOrderReports:
				message = ((String) m.getObject()).split(" ");
				ArrayList<OrderReport> orderReportsList = Sqlconnection.getAllOrderReports(message[0], message[1],
						message[2]);
				client.sendToClient(new Message1(MessageType.getOrderReports, orderReportsList));
				break;

			case assignBranchToManager:
				handleAssignBranchToManager(m, client);
				break;
			case getBranchesForManager:
				handleGetBranchesForManager(m, client);
				break;
			case updateDishName:
				message = ((String) m.getObject()).split(" ");
				Sqlconnection.updateDishNameToDB(message[0], message[1]);
				client.sendToClient(new Message1(null, null));
				break;
			case updateDishPrice:
				message = ((String) m.getObject()).split(" ");
				Sqlconnection.updateDishPriceToDB(message[0], message[1]);
				client.sendToClient(new Message1(null, null));
				break;
			case deleteDish:
				message = ((String) m.getObject()).split(" ");
				Sqlconnection.deleteDishFromDB(message[0]);
				client.sendToClient(new Message1(null, null)); // Acknowledge deletion
				break;
			case addDish:
				message = ((String) m.getObject()).split(" ");
				Sqlconnection.addDishToDB(message[0], message[1], message[2], message[3], message[4], message[5]);
				client.sendToClient(new Message1(null, null)); // Acknowledge deletion
				break;
			case getDishNumber:
				String dishNum = Sqlconnection.getDishId();
				client.sendToClient(new Message1(MessageType.getDishNumber, dishNum));
				break;
			case setDishNumber:
				message = ((String) m.getObject()).split(" ");
				Sqlconnection.updateDishId(message[0]);
				client.sendToClient(new Message1(null, null));
				break;
			case OrderList:
				message = ((String) m.getObject()).split(" ");
				ArrayList<OrderListSponser> orderList1 = Sqlconnection.getAllOrders(message[0]);
				client.sendToClient(new Message1(MessageType.OrderList, orderList1));
				break;
			case updateOrderStatus:
				message = ((String) m.getObject()).split(" ");
				Sqlconnection.updateStatusReady(message[0]);
				client.sendToClient(new Message1(null, null));
				break;
			case updateOrderApproval:
				message = ((String) m.getObject()).split(" ");
				Sqlconnection.updateApproval(message[0], message[1]);
				client.sendToClient(new Message1(null, null));
				break;
			case addOrderToReports:
				arrReport=(ArrayList<OrderReport>) m.getObject();
				boolean success = mysqlConnection.insertOrderReportToDB(arrReport);
			    if (success) {
			        client.sendToClient(new Message1(null, null)); 
			    } else {
			        client.sendToClient(new Message1(MessageType.addOrderToReports, "DB_INSERT_FAILED")); 
			    }
			case getReportNum:
				String reportNum = Sqlconnection.getReportNumber();
				client.sendToClient(new Message1(MessageType.getReportNum, reportNum));
				break;
			case setReportNum:
				message = ((String) m.getObject()).split(" ");
				mysqlConnection.updateReportNumber(message[0]);
				client.sendToClient(new Message1(null, null));
				break;
			default:
				break;
			}

		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}

	}

	private void handleCreateAccount(Message1 message, ConnectionToClient client) throws IOException {
		CreateAccount accountDetails = (CreateAccount) message.getObject();

		if (Sqlconnection.userExists(accountDetails.getUserID())) {
			client.sendToClient(new Message1(MessageType.createAccount, "User already exists"));
			return;
		}

		boolean success = Sqlconnection.createAccount(accountDetails.getUserID(), accountDetails.getFirstName(),
				accountDetails.getLastName(), accountDetails.getEmail(), accountDetails.getPhone(),
				accountDetails.getCreditCard(), accountDetails.getUserType(), accountDetails.getUsername(),
				accountDetails.getPassword(), accountDetails.getHomeBranchId(), accountDetails.getAccountType(),
				accountDetails.getHomeBranchName());

		String responseMessage = success ? "Account created successfully" : "Failed to create account, Try again!";
		client.sendToClient(new Message1(MessageType.createAccount, responseMessage));
	}

	private void handleAssignBranchToManager(Message1 message, ConnectionToClient client) throws IOException {
		String[] messageParts = ((String) message.getObject()).split(" ");
		if (messageParts.length < 2) {
			client.sendToClient(
					new Message1(MessageType.assignBranchToManager, "Branch ID and manager ID are required."));
			return;
		}

		String branchId = messageParts[0];
		String managerId = messageParts[1];
		boolean isAssigned = Sqlconnection.assignBranchToManager(branchId, managerId);

		String responseMessage = isAssigned ? "Branch assigned successfully."
				: "Failed to assign branch. Please check the branch ID and manager ID.";
		client.sendToClient(new Message1(MessageType.assignBranchToManager, responseMessage));
	}

	private void handleGetBranchesForManager(Message1 message, ConnectionToClient client)
			throws IOException, SQLException {
		String managerIdForBranches = (String) message.getObject();
		ArrayList<Branches> branchesForManager = mysqlConnection.getBranchesForManager(managerIdForBranches);
		client.sendToClient(new Message1(MessageType.getBranchesForManager, branchesForManager));
	}

	/**
	 * This method overrides the one in the superclass. Called when the server
	 * starts listening for connections.
	 */
	protected void serverStarted() {
		System.out.println("Server listening for connections on port " + getPort());

	}

	// @Override
	// protected void clientConnected(ConnectionToClient client)
	// {
	// ip=client.getInetAddress().getHostAddress();
	// host=client.getInetAddress().getHostName();
	// String ipAddress = client.getInetAddress().getHostAddress();
	// String hostName = client.getInetAddress().getHostName();
	// ServerUI.aFrame.addClientConnection(ipAddress, hostName, "Connected");
	// System.out.println("Client connected: " + client);
	// System.out.println("IP: " + ipAddress);
	// System.out.println("Host: " + hostName);
	// }

	// @Override
	// synchronized protected void clientDisconnected(ConnectionToClient client)
	// {
	// System.out.println("yoyo");
	// String ipAddress = client.getInetAddress().getHostAddress();
	// String hostName = client.getInetAddress().getHostName();
	// ServerUI.aFrame.addClientConnection(ipAddress, hostName, "Disconnected");
	// System.out.println("Client disconnected: " + client);
	// }
	// @Override
	// synchronized protected void clientDisconnected(
	// ConnectionToClient client) {
	// System.out.println("yoyo");
	// if (ServerUI.aFrame != null) {
	// System.out.println("yoyo");
	// String ipAddress = client.getInetAddress().getHostAddress();
	// String hostName = client.getInetAddress().getHostName();
	// ServerUI.aFrame.addClientConnection(client.getInetAddress().getHostAddress(),
	// client.getInetAddress().getHostName(), "Disconnected");
	// System.out.println("Client disconnected: " + client);
	// System.out.println("Client disconnected: " + client);
	// }

	// }

	// @Override
	// synchronized protected void clientException(ConnectionToClient client,
	// Throwable exception)
	// {
	// ServerPortFrameController1 aa;
	// aa=ServerUI.aFrame;
	// if(aa==null)
	// {
	// System.out.println("null");
	// }
	// String ipAddress = client.getInetAddress().getHostAddress();
	// String hostName = client.getInetAddress().getHostName();
	// ServerUI.aFrame.addClientConnection(ip,host, "Disconnected");
	// aa.addClientConnection(ip, host, "Disconnected");
	// System.out.println("Client disconnected: " + client);
	// System.out.println("Client disconnected: " + client);
	// }

}
//End of EchoServer class
