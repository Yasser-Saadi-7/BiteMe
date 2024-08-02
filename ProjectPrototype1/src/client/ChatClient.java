package client;

import ocsf.client.AbstractClient;
import common.ChatIF;
import logic.MealsType;
import logic.Message1;
import logic.Order;
import logic.Restaurant;

import java.io.IOException;
import java.util.ArrayList;
import Server.ServerUI;
/**
 * This class overrides some of the methods defined in the abstract
 * superclass in order to give more functionality to the client.
 */
public class ChatClient extends AbstractClient {
  // Instance variables **********************************************
  
  ChatIF clientUI; 
  public static Order o1 = new Order(null, null, null, null, null);
  public static ArrayList<Order> list;
  public static boolean awaitResponse = false;
  public static String logIn = "error";

  public static String CreateAccount = "error1";
  public static String ViewMonthlyReport = "error2";
  public static ArrayList<Restaurant> resList;
  public static ArrayList<MealsType> mealsTypeList;
  public static String CheckUserIdResponse; // Field to hold the response

  // Constructors ****************************************************
  
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
          o1.setOrederNumber(order.getOrederNumber());
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
        logIn = (String) m.getObject();
        break;
      case createAccount: // New case for creating account
        CreateAccount = (String) m.getObject();
        break;
      case showRestaurant:
        resList = (ArrayList<Restaurant>) m.getObject();
        break;
      case mealsType:
        mealsTypeList = (ArrayList<MealsType>) m.getObject();
        break;
      default:
        break;
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
    } else if (response.startsWith("CreateAccount:")) { // Handle the create account response
      CreateAccount = response.split(":")[1]; // Store the response (Success or Failure)
    }
  }

  public void handleMessageFromClientUI(Object message) {
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
    } catch (IOException e) {
      e.printStackTrace();
      clientUI.display("Could not send message to server: Terminating client." + e);
      quit();
    }
  }

  public void quit() {
    try {
      closeConnection();
    } catch (IOException e) {}
    System.exit(0);
  }
}
// End of ChatClient class
