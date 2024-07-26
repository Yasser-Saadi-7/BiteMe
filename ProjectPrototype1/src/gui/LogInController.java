package gui;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Optional;

import client.ChatClient;
import client.ClientController;
import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import logic.Message1;
import logic.MessageType;

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
	
	
	public void logIn(ActionEvent event) throws Exception{
		ArrayList<String> arr = new ArrayList<>();
		String result;
		if(txtusername.getText().trim().isEmpty() || txtpassword.getText().trim().isEmpty()) {
			updateTextMessage("Please enter user name or password");
		}else {
			ClientUI.chat.accept(new Message1(MessageType.logIn,txtusername.getText() + " "+ txtpassword.getText()));
			result=ChatClient.logIn;
			if(result.equals("Manager")) {
				System.out.println("");
				
			}else if(result.equals("CEO")) {
				
			}else if(result.equals("Sponser")){
				
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
	public void updateTextMessage(String message) {
		txtmessage.setText(message);
	}
	
	
	public void closePage(ActionEvent event) throws Exception {
		Alert alert = new Alert(AlertType.WARNING, "Are you sure you want to exit?", ButtonType.YES, ButtonType.NO);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.YES) {
			InetAddress ip = null;
			try {
				ip = InetAddress.getLocalHost();
				// System.out.println(ip.getCanonicalHostName());
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			ClientUI.chat.accept(new Message1(MessageType.disconnect,
					ip.getHostAddress() + " " + ip.getHostName() + " " + "Disconnected"));
			ClientController.client.quit();
			System.exit(0);

		}			
	}

}
