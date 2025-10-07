package controllers;

import java.io.IOException;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import entities.LogIn;
import entities.Message1;
import entities.MessageType;

public class LogInController {

	@FXML
	private Button btnclose;

	@FXML
	private Button btnlogIn;

	@FXML
	private TextField txtusername;

	@FXML
	private PasswordField txtpassword;

	@FXML
	private Label txtmessage;

	public void logIn(ActionEvent event) throws Exception {
		ArrayList<String> arr = new ArrayList<>();
		LogIn result;
		if (txtusername.getText().trim().isEmpty() || txtpassword.getText().trim().isEmpty()) {
			updateTextMessage("Please enter user name or password");
		} else {
			ClientUI.chat.accept(new Message1(MessageType.logIn, txtusername.getText() + " " + txtpassword.getText()));
			result = ChatClient.logIn;

			if (result.getUserType().equals("Branch Manager")) {
				Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // get stage
				BranchManagerController AFrame = new BranchManagerController();
				try {
					AFrame.start(stage);
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else if (result.getUserType().equals("CEO")) {
				Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // get stage
				CEOPageController AFrame = new CEOPageController();
				try {
					AFrame.start(stage);
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else if (result.getUserType().equals("Sponsor")) {
				Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // get stage
				SponserController AFrame = new SponserController();
				try {
					AFrame.start(stage);
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else if (result.getUserType().equals("Client")) {
				Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // get stage
				ClientPageController AFrame = new ClientPageController();
				try {
					AFrame.start(stage);
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else if (result.getUserType().equals("Qualified Worker")) {
				Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // get stage
				QualifiedWorkerPageController AFrame = new QualifiedWorkerPageController();
				try {
					AFrame.start(stage);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/boundry/LogIn.fxml"));
		Scene scene = new Scene(root);
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
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}

			ClientUI.chat.accept(new Message1(MessageType.disconnect,
					ip.getHostAddress() + " " + ip.getHostName() + " " + "Disconnected"));
			ClientController.client.quit();
			System.exit(0);
		}
	}
}
