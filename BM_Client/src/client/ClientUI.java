package client;

//yasser 

import entities.Order;



import client.ClientController;
import controllers.ClientConnectionController;
import javafx.application.Application;
import javafx.stage.Stage;


public class ClientUI extends Application {
	public static ClientController chat; //only one instance
	private static String host="";

	public static void main( String args[] ) throws Exception
	   {
	
		    
		    launch(args);  
	   } // end main
	 
	@Override
	public void start(Stage primaryStage) throws Exception {
		 
		// TODO Auto-generated method stub
		ClientConnectionController aFrame = new ClientConnectionController(); // create StudentFrame
		aFrame.start(primaryStage);
	}
	
	
}

