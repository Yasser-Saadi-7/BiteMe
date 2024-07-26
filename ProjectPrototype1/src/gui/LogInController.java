package gui;

import java.util.ArrayList;

import client.ChatClient;
import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LogInController {
	
	@FXML
	private Button btnclose;
	
	@FXML
	private Button btnlogIn;
	
	@FXML
	private TextField txtusername;
	
	@FXML
	private TextField txtpassword;
	
	@FXML
	private Label txtmessage;
	
	@FXML
	private Label txterrormsg;
	
	public void logIn(ActionEvent event) throws Exception{
		ArrayList<String> arr = new ArrayList<>();
		String result;
		if(txtusername.getText().trim().isEmpty() || txtpassword.getText().trim().isEmpty()) {
			updateTextMessage("Please Re-enter user name or password");
		}else {
			arr.add("LogIn");
			arr.add(txtusername.getText());
			arr.add(txtpassword.getText());
			ClientUI.chat.accept(arr);
			result=ChatClient.logIn;
			if(result.equals("Manager")) {
				System.out.println("");
				
			}else if(result.equals("CEO")) {
				
			}else if(result.equals("Sponser")){
				
			}else if(result.equals("BranchManger")){
				
			}else if(result.equals("Sponser")){
				
			}else if(result.equals("QualifiedWorker")){
				
			}
		}
	}
	
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/gui/LogIn.fxml"));
		Scene scene = new Scene(root);
		//scene.getStylesheets().add(getClass().getResource("/gui/OrderTrack.css").toExternalForm());
		primaryStage.setTitle("LogIn Page");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	//showing the user that user name/password is incorrect
	public void updateTextMessage(String message) {
		txterrormsg.setText(message);
	}
	
	
	public void closePage(ActionEvent event) throws Exception {
		System.out.println("Server Diconnected!");
		System.exit(0);			
	}

}
