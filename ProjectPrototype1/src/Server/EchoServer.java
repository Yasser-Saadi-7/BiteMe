// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 
package Server;

import java.io.*;
import java.util.ArrayList;
import java.util.Vector;

import client.ChatClient;
import client.ClientController;
import gui.ServerPortFrameController1;
import logic.Order;
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
	  Order orderFromDb;
	  Order orderToDb;
	  if(msg instanceof Order) {
		  orderToDb=(Order)msg;
		  Sqlconnection.updateOrderToDB(orderToDb);
		  this.sendToAllClients("Order Updated!");
		  
	  }else if(((String)msg).equals("view")) {
		  ArrayList<Order> orders = mysqlConnection.getOrdersFromDB();
	      this.sendToAllClients(orders);
	  }else if(msg instanceof ArrayList) {
		  ArrayList<String> arr ;
		  arr=(ArrayList)msg;
		  if(arr.get(0).equals("LogIn")) {
			  ChatClient.logIn=Sqlconnection.userLogIn(arr.get(1), arr.get(2));
		  }
	  }
	  else{
		  int flag=0;
		  System.out.println("Message received: " + msg + " from " + client);
		  orderFromDb=Sqlconnection.parseTheData(msg);
		  if(orderFromDb!=null) {
			  System.out.println("Order found!");
			  this.sendToAllClients(orderFromDb);
		  }else {
			  System.out.println("Not Found");
			  this.sendToAllClients("Error");
		  }
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
  
 
  @Override
	protected void clientConnected(ConnectionToClient client) 
	{
	    ip=client.getInetAddress().getHostAddress();
	    host=client.getInetAddress().getHostName();
		String ipAddress = client.getInetAddress().getHostAddress();
		String hostName = client.getInetAddress().getHostName();
		ServerUI.aFrame.addClientConnection(ipAddress, hostName, "Connected");
		System.out.println("Client connected: " + client);
		System.out.println("IP: " + ipAddress);
		System.out.println("Host: " + hostName);
	}
  
	
	//@Override
	//synchronized protected void clientDisconnected(ConnectionToClient client) 
	//{
		//System.out.println("yoyo");
		//String ipAddress = client.getInetAddress().getHostAddress();
		//String hostName = client.getInetAddress().getHostName();
		//ServerUI.aFrame.addClientConnection(ipAddress, hostName, "Disconnected");
		//System.out.println("Client disconnected: " + client);
	//}
	@Override
	synchronized protected void clientDisconnected(
		    ConnectionToClient client) {
		System.out.println("yoyo");
		if (ServerUI.aFrame != null) {
			System.out.println("yoyo");
			//String ipAddress = client.getInetAddress().getHostAddress();
			//String hostName = client.getInetAddress().getHostName();
			ServerUI.aFrame.addClientConnection(client.getInetAddress().getHostAddress(), client.getInetAddress().getHostName(), "Disconnected");
			System.out.println("Client disconnected: " + client);
			//System.out.println("Client disconnected: " + client);
		}
		
	}
	
	@Override
	synchronized protected void clientException(ConnectionToClient client, Throwable exception) 
	{
		ServerPortFrameController1 aa;
		aa=ServerUI.aFrame;
		if(aa==null)
		{
			System.out.println("null");
		}
		//String ipAddress = client.getInetAddress().getHostAddress();
		//String hostName = client.getInetAddress().getHostName();
		aa.addClientConnection(ip, host, "Disconnected");
		//System.out.println("Client disconnected: " + client);
		//System.out.println("Client disconnected: " + client);
	}
	
	
	
}
//End of EchoServer class
