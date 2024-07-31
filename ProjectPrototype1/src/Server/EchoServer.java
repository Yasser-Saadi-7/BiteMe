package Server;

import java.util.ArrayList;
import client.ChatClient;
import gui.ServerPortFrameController1;
import logic.ClientConnectionDetails;
import logic.MealsType;
import logic.Message1;
import logic.MessageType;
import logic.Order;

import logic.Restaurant;
import ocsf.server.*;






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
			default:
				break;
			}
			
			
	} catch (IOException e) {
		// TODO Auto-generated catch block
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
    synchronized protected void clientException(ConnectionToClient client, Throwable exception) {
        String ipAddress = client.getInetAddress().getHostAddress();
        String hostName = client.getInetAddress().getHostName();
        ServerUI.aFrame.addClientConnection(ipAddress, hostName, "Disconnected");
        System.out.println("Exception occurred for client: " + client);
    }

}
// End of EchoServer class
