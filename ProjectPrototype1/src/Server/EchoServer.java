// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 
package Server;

import java.io.*;
import java.util.ArrayList;
import java.util.Vector;

import client.ChatClient;
import client.ClientController;
import logic.ClientConnectionDetails;
import logic.CreateAccount;
import logic.Dish;
import logic.MealsType;
import logic.Message1;
import logic.MessageType;
import gui.ServerPortFrameController1;
import logic.Order;
import logic.Restaurant;
import logic.Selection;
import logic.UserType;
import ocsf.server.*;

/**
 * This class overrides some of the methods in the abstract 
 * superclass in order to give more functionality to the server.
 *
 */

public class EchoServer extends AbstractServer 
{
	private mysqlConnection Sqlconnection=new mysqlConnection();
	
	private ServerPortFrameController1 controller;
	private String ip,host;
	
  //Class variables *************************************************
  
  /**
   * The default port to listen on.
   */
  //final public static int DEFAULT_PORT = 5555;
  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the echo server.
   *
   * @param port The port number to connect on.
   * 
   */
// public static Student [] students=new Student[4];

  public EchoServer(int port,String user,String password) 
  {
    super(port);
    Sqlconnection.ConnectToDB(user,password);
    
  }

  //Instance methods ************************************************
  
  /**
   * This method handles any messages received from the client.
   *
   * @param msg The message received from the client.
   * @param client The connection from which the message originated.
   * @param 
   */
  public void handleMessageFromClient  (Object msg, ConnectionToClient client)
  {
	    Message1 m = (Message1) msg;
		String message[];
		ArrayList<Object> arr;
		try {
			switch (m.getMessageType()) {
			case searchOrder:
				message = ((String) m.getObject()).split(" ");
				Order orderFromDb=Sqlconnection.parseTheData(message[0]);
				client.sendToClient(new Message1(MessageType.searchOrder,orderFromDb));
				break;
			case connect:
				message = ((String) m.getObject()).split(" ");
				ServerPortFrameController1.connectionData.add(new ClientConnectionDetails(message[0], message[1], message[2]));
				client.sendToClient(new Message1(null, null));
				break;
			case disconnect:
				message = ((String) m.getObject()).split(" ");
				ServerPortFrameController1.connectionData.add(new ClientConnectionDetails(message[0], message[1], message[2]));
				client.sendToClient(new Message1(null, null));
				break;
			case viewOrdersList:
				ArrayList<Order> orders = mysqlConnection.getOrdersFromDB();
				client.sendToClient(new Message1(MessageType.viewOrdersList,orders));
				break;
			case updateOrder:
				Order orderToDb=(Order)m.getObject();
				Sqlconnection.updateOrderToDB(orderToDb);
				client.sendToClient(new Message1(null, null));
				break;
			case logIn:
				message = ((String) m.getObject()).split(" ");
				String userType =Sqlconnection.userLogIn(message[0],message[1]);
				client.sendToClient(new Message1(MessageType.logIn,userType));
				break;
			case showRestaurant:
				ArrayList<Restaurant> Allres=Sqlconnection.getAllRes();
				client.sendToClient(new Message1(MessageType.showRestaurant,Allres));
				break;
			case mealsType:
				message = ((String) m.getObject()).split(" ");
				ArrayList<MealsType> allMealsType=Sqlconnection.getMealsType(message[0]);
				client.sendToClient(new Message1(MessageType.mealsType,allMealsType));
				break;
			case dishes:
				message = ((String) m.getObject()).split(" ");
				ArrayList<Dish> allDishes=Sqlconnection.getDishes(message[0]);
				client.sendToClient(new Message1(MessageType.dishes,allDishes));
				break;
			case selections:
				message = ((String) m.getObject()).split(" ");
				ArrayList<Selection> allSelections=Sqlconnection.getSelections(message[0]);
				client.sendToClient(new Message1(MessageType.selections,allSelections));
				break;
			case createAccount:
			    message = ((String) m.getObject()).split(" ");

			    // Check if we have at least 8 elements
			    if (message.length < 8) {
			        client.sendToClient(new Message1(MessageType.createAccount, "All fields marked with * are required. Please fill them out to proceed with account creation."));
			        return;
			    }

			    // Extract parameters based on the expected format
			    String userID = message[0]; // User ID
			    String firstName = message[1]; // First Name
			    String lastName = message[2]; // Last Name
			    String email = message[3]; // Email
			    String phone = message[4]; // Phone
			    String creditCard = message[5]; // Credit Card
			    String username = message[6]; // Username
			    String password = message[7]; // Password
			    
			    
			 // Determine user type (you can add a mechanism to get this from the client if needed)
			    UserType userType1 = UserType.CLIENT; // Default to CLIENT or determine based on additional input


			    // Check if the user ID already exists
			    ArrayList<CreateAccount> accountList = Sqlconnection.getAccountsFromDB(userID);
			    boolean userExists = accountList.stream().anyMatch(account -> account.getUserID().equals(userID));

			    if (userExists) {
			        client.sendToClient(new Message1(MessageType.createAccount, "User already exists"));
			    } else {
			        boolean success = Sqlconnection.createAccount(userID, firstName, lastName, email, phone, creditCard, UserType.CLIENT, username, password); 
			        if (success) {
			            client.sendToClient(new Message1(MessageType.createAccount, "Account created successfully"));
			        } else {
			            client.sendToClient(new Message1(MessageType.createAccount, "Failed to create account, Try again!"));
			        }
			    }
			    break; // Add this break statement to ensure flow control is correct


			default:
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
  /**
   * This method overrides the one in the superclass.  Called
   * when the server starts listening for connections.
   */
  protected void serverStarted()
  {
    System.out.println ("Server listening for connections on port " + getPort()); 

  }
  
 
  //@Override
	//protected void clientConnected(ConnectionToClient client) 
	//{
	    //ip=client.getInetAddress().getHostAddress();
	    //host=client.getInetAddress().getHostName();
		//String ipAddress = client.getInetAddress().getHostAddress();
		//String hostName = client.getInetAddress().getHostName();
		//ServerUI.aFrame.addClientConnection(ipAddress, hostName, "Connected");
		//System.out.println("Client connected: " + client);
		//System.out.println("IP: " + ipAddress);
		//System.out.println("Host: " + hostName);
	//}
  
	
	//@Override
	//synchronized protected void clientDisconnected(ConnectionToClient client) 
	//{
		//System.out.println("yoyo");
		//String ipAddress = client.getInetAddress().getHostAddress();
		//String hostName = client.getInetAddress().getHostName();
		//ServerUI.aFrame.addClientConnection(ipAddress, hostName, "Disconnected");
		//System.out.println("Client disconnected: " + client);
	//}
	//@Override
	//synchronized protected void clientDisconnected(
		    //ConnectionToClient client) {
		//System.out.println("yoyo");
		//if (ServerUI.aFrame != null) {
			//System.out.println("yoyo");
			//String ipAddress = client.getInetAddress().getHostAddress();
			//String hostName = client.getInetAddress().getHostName();
			//ServerUI.aFrame.addClientConnection(client.getInetAddress().getHostAddress(), client.getInetAddress().getHostName(), "Disconnected");
			//System.out.println("Client disconnected: " + client);
			//System.out.println("Client disconnected: " + client);
		//}
		
	//}
	
	//@Override
	//synchronized protected void clientException(ConnectionToClient client, Throwable exception) 
	//{
		//ServerPortFrameController1 aa;
		//aa=ServerUI.aFrame;
		//if(aa==null)
		//{
			//System.out.println("null");
		//}
		//String ipAddress = client.getInetAddress().getHostAddress();
		//String hostName = client.getInetAddress().getHostName();
		 //ServerUI.aFrame.addClientConnection(ip,host, "Disconnected");
		//aa.addClientConnection(ip, host, "Disconnected");
		//System.out.println("Client disconnected: " + client);
		//System.out.println("Client disconnected: " + client);
	//}
	
	
	
}
//End of EchoServer class
