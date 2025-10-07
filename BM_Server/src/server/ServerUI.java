package server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.util.Vector;
import controllers.ServerPortFrameController1;
import server.EchoServer;
import controllers.ServerPortFrameController1;

public class ServerUI extends Application {
	final public static int DEFAULT_PORT = 5555;
	public static Stage primaryStage1;
	public static ServerPortFrameController1 aFrame;

	public static void main( String args[] ) throws Exception
	   {   
		 launch(args);
	  } // end main
	
	
	public void start(Stage primaryStage) throws Exception {
		 primaryStage1 = primaryStage;				  		
		 aFrame = new ServerPortFrameController1(); // create ServerFrame
		 aFrame.start(primaryStage);
	}
	

	
	public static void runServer(String p,String user,String password)
	{
		 int port = 0; //Port to listen on

	        try
	        {
	        	port = Integer.parseInt(p); //Set port to 5555
	          
	        }
	        catch(Throwable t)
	        {
	        	System.out.println("ERROR - Could not connect!");
	        }
	    	
	        EchoServer sv = new EchoServer(port,user,password);
	        
	        try 
	        {
	        		sv.listen();
	        	                 //Start listening for connections
	        } 
	        catch (Exception ex) 
	        {
	          System.out.println("ERROR - Could not listen for clients!");
	        }
	}
	

}

