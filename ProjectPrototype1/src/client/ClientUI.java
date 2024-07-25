package client;

//yasser

import logic.Order;



import client.ClientController;
import gui.ClientConnectionController;
import gui.OrderTrackFrameController1;
import javafx.application.Application;
import javafx.stage.Stage;


public class ClientUI extends Application {
	public static ClientController chat; //only one instance
	private static String host="";

	public static void main( String args[] ) throws Exception
	   {
	
		    //host=args[0];
		    launch(args);  
	   } // end main
	 
	@Override
	public void start(Stage primaryStage) throws Exception {
		 //chat= new ClientController(host, 5555);
		// TODO Auto-generated method stub
						  		
		ClientConnectionController aFrame = new ClientConnectionController(); // create StudentFrame
		 aFrame.start(primaryStage);
	}
	
	
}

