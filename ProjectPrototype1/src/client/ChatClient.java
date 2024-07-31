package client;

import ocsf.client.*;
import client.*;
import common.ChatIF;
import logic.Order;

import java.io.*;
import java.util.ArrayList;

import Server.ServerUI;

/**
 * This class overrides some of the methods defined in the abstract
 * superclass in order to give more functionality to the client.
 */
public class ChatClient extends AbstractClient {
  // Instance variables **********************************************
  
  ChatIF clientUI; 
  public static Order  o1 = new Order(null,null,null,null,null);
  public static ArrayList<Order> list;
  public static boolean awaitResponse = false;
  public static String logIn = "error";
  public static String CheckUserIdResponse; // Field to hold the response

  // Constructors ****************************************************
  
  public ChatClient(String host, int port, ChatIF clientUI) throws IOException {
    super(host, port); // Call the superclass constructor
    this.clientUI = clientUI;
  }

  // Instance methods ************************************************
    
  public void handleMessageFromServer(Object msg) {
	    System.out.println("--> handleMessageFromServer");
	    awaitResponse = false; // Resetting awaitResponse flag
	    if (msg instanceof Order) {
	        Order order = (Order) msg;
	        o1.setListNumber(order.getListNumber());
	        o1.setOrderAddress(order.getOrderAddress());
	        o1.setOrederNumber(order.getOrederNumber());
	        o1.setRestaurant(order.getRestaurant());
	        o1.setTprice(order.getTprice());
	    } else if (msg instanceof ArrayList) {
	        list = (ArrayList) msg; // Assuming list is defined as an instance variable
	    } else if (msg instanceof String) { // Handle String responses from the server
	        handleServerResponse((String) msg); // Call the method to handle response
	    } else {
	        o1.setOrederNumber("Error");
	    }
	}

	// Method to handle server responses
	public static void handleServerResponse(String response) {
	    // Logic to handle various responses from the server
	    if (response.startsWith("CheckUserId:")) {
	        // Extract the response message after the command
	        CheckUserIdResponse = response.split(":")[1]; // Assuming response format is "CheckUserId:Exists" or "CheckUserId:Unique"
	    } else if (response.startsWith("Success:") || response.startsWith("Failed:")) {
	        CheckUserIdResponse = response; // Store the response for account creation
	    }
	}


  public void handleMessageFromClientUI(Object message)  {
    try {
        openConnection(); // in order to send more than one message
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
    } catch(IOException e) {
        e.printStackTrace();
        clientUI.display("Could not send message to server: Terminating client." + e);
        quit();
    }
  }

  public void quit() {
    try {
      closeConnection();
    } catch(IOException e) {}
    System.exit(0);
  }
}
// End of ChatClient class
