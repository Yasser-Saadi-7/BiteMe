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
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 * @version July 2000
 */
public class ChatClient extends AbstractClient
{
  //Instance variables **********************************************
  
/**
   * The interface type variable.  It allows the implementation of 
   * the display method in the client.
   */
  ChatIF clientUI; 
  public static Order  o1 = new Order(null,null,null,null,null);
  public static ArrayList<Order> list;
  public static boolean awaitResponse = false;
  public static String logIn = "error";
  public static String CreateAccount = "error1";

  

  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the chat client.
   *
   * @param host The server to connect to.
   * @param port The port number to connect on.
   * @param clientUI The interface type variable.
   */
	 
  public ChatClient(String host, int port, ChatIF clientUI) 
    throws IOException 
  {
    super(host, port); //Call the superclass constructor
    this.clientUI = clientUI;
    //openConnection();
  }

  //Instance methods ************************************************
    
  /**
   * This method handles all data that comes in from the server.
   *
   * @param msg The message from the server.
   */
  public void handleMessageFromServer(Object msg) 
  {
	  Order order;
	  System.out.println("--> handleMessageFromServer");
     
	  awaitResponse = false;
	  if(msg instanceof Order) {
		  order=(Order)msg;
		  o1.setListNumber(order.getListNumber());
		  o1.setOrderAddress(order.getOrderAddress());
		  o1.setOrederNumber(order.getOrederNumber());
		  o1.setRestaurant(order.getRestaurant());
		  o1.setTprice(order.getTprice());
	  }else if(msg instanceof ArrayList) {
		  list=(ArrayList)msg;
	  }
	  else {
		  String st;
		  st=msg.toString();
		  o1.setOrederNumber("Error");
	  }
	 
  }

  /**
   * This method handles all data coming from the UI            
   *
   * @param message The message from the UI.    
   */
  
  public void handleMessageFromClientUI(Object message)  
  {
    try
    {
    	openConnection();//in order to send more than one message
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
    }
    catch(IOException e)
    {
    	e.printStackTrace();
      clientUI.display("Could not send message to server: Terminating client."+ e);
      quit();
    }
  }

  
  /**
   * This method terminates the client.
   */
  public void quit()
  {
    try
    {
		//System.out.println("Client disconnected: " + client);
		//System.out.println("Client disconnected: " + client);
      closeConnection();
    }
    catch(IOException e) {}
    System.exit(0);
  }
}
//End of ChatClient class

