package controllers;

import javafx.fxml.FXML;

import java.net.URL;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import entities.MealsType;
import entities.Message1;
import entities.MessageType;

/**
 * Controller class for the Sponsor Page. Handles user actions and interactions
 * for the Sponsor Page.
 * 
 * @authors Yasser Saadi
 */

public class SponserController {

	@FXML
	private Button logOutbtn;

	@FXML
	private Button ViewOrdersListbtn;

	@FXML
	private Button myCartbtn;

	@FXML
	private TextField scheduledDatetxt;

	/**
	 * Handles the action event when the user clicks on the "Log Out" button. Opens
	 * the Login page to allow the user to log out and return to the login screen.
	 *
	 * @param event The ActionEvent that triggered this method.
	 */
	@FXML
	void logOut(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		LogInController logInController = new LogInController();
		try {
			logInController.start(stage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Handles the action event when the user clicks on the "My Cart" button. Opens
	 * the Cart page where the user can view their cart.
	 *
	 * @param event The ActionEvent that triggered this method.
	 */
	@FXML
	void myCart(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		TheCartController aFrame = new TheCartController();
		try {
			aFrame.start(stage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Handles the action event when the user clicks on the "View Orders List"
	 * button. Opens the Update Status page to view and update the status of orders.
	 *
	 * @param event The ActionEvent that triggered this method.
	 */
	@FXML
	void viewOrdersList(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		UpdateStatusController aFrame = new UpdateStatusController();
		try {
			aFrame.start(stage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void start(Stage primaryStage) throws Exception {
		Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("/boundry/SponserPage.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Sponser Page");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
