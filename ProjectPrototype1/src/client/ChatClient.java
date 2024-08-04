package client;

import ocsf.client.AbstractClient;
import common.ChatIF;
import logic.CreateAccount;
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
  
  public static boolean awaitResponse = false;
  public static String CheckUserIdResponse; // Added 
  public static String logIn = "error";
  public static String ViewMonthlyReport = "error2";
  public static ArrayList<Order> list;
  public static ArrayList<Restaurant> resList;
  public static ArrayList<MealsType> mealsTypeList;
  public static  ArrayList<CreateAccount> CreateAccountList;
  
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
        
      case createAccount: 
    	  CreateAccountList = (ArrayList<CreateAccount>) m.getObject();
    	  CheckUserIdResponse = (String) m.getObject(); // Store the server response here
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
