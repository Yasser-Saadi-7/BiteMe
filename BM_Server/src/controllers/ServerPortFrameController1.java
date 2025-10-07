package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import entities.ClientConnectionDetails;
import server.EchoServer;
import server.ServerUI;

public class ServerPortFrameController1 implements Initializable {	
	public static ObservableList<ClientConnectionDetails> connectionData = FXCollections.observableArrayList();
	
	@FXML
	private Button btnConnect = null;
	@FXML
	private Button btnDisconnect = null;

	@FXML
	private TextField txtport;
	
	@FXML
	private TextField txtuser;
	
	@FXML
	private PasswordField txtpassword;
	
	@FXML
	private Label textmessage;
	
	@FXML
	private TableView<ClientConnectionDetails> connectionTable;
	
	@FXML
	private TableColumn<ClientConnectionDetails, String> ipColumn;
	
	@FXML
	private TableColumn<ClientConnectionDetails, String> hostColumn;
	
	@FXML
	private TableColumn<ClientConnectionDetails, String> statusColumn;

	
		
	
	private String getport() { return txtport.getText(); }
	private String getuser() { return txtuser.getText(); }
	private String getpassword() { return txtpassword.getText(); }
	 
	
	  

	public void start(Stage primaryStage) throws Exception {	
		Parent root = FXMLLoader.load(getClass().getResource("/boundry/ServerPort.fxml"));		
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/css/ServerPort1.css").toExternalForm());
		primaryStage.setTitle("Server");
		primaryStage.setScene(scene);
		primaryStage.show();		
	}
	public void updateTextMessage(String message) {
	    textmessage.setText(message);
	}
	public void serverConnection(ActionEvent event) throws Exception {
		String port,password,user;
		
		port=getport();
		password=getpassword();
		user=getuser();
		if(port.trim().isEmpty()) {
			System.out.println("Enter port number");
			updateTextMessage("Enter port number");
		}
		else if(user.trim().isEmpty() || password.trim().isEmpty()) {
			System.out.println("Enter DB user and password");
			updateTextMessage("Enter DB user and password");
			
		}
		else {
			ServerUI.runServer(port,user,password);
			updateTextMessage("Server listening for connections on port"+port);
			
		}
		
					
	}
	public void getExitBtn(ActionEvent event) throws Exception {
		System.out.println("Server Diconnected!");
		System.exit(0);			
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ipColumn.setCellValueFactory(new PropertyValueFactory<>("ipAddress"));
		hostColumn.setCellValueFactory(new PropertyValueFactory<>("hostName"));
		statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
		connectionTable.setItems(connectionData);
	}
	
	public void addClientConnection(String ipAddress, String hostName, String status) {
		connectionData.add(new ClientConnectionDetails(ipAddress, hostName, status));
		//connectionTable.refresh();
	}

}
