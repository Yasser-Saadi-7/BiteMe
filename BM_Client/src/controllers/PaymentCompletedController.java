package controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import entities.Message1;
import entities.MessageType;
import entities.OrderReport;
/**
 * Controller class for the Payment Completed Page in the GUI application.
 * This class displays a confirmation message to the user indicating that the payment
 * was successful, and it shows the order number. The FXML file associated with this controller
 * is "PaymentCompleted.fxml".
 * 
 * 
 * @author Yasser Saadi
 */
public class PaymentCompletedController implements Initializable{
	/** Label to display the order number of the user's completed order */
	@FXML
    private Label orderNumberlbl;
	
	
	
	//public OrderReport order;
	/**
     * Navigates the user to the Home (Client) Page.
     * This method is triggered when the "Home" button is clicked.
     * 
     * @param event the ActionEvent triggered when the button is clicked
     */
	@FXML
	void home(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();// get stage
		ClientPageController AFrame=new ClientPageController();
		try {
			AFrame.start(stage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
     * Starts the Payment Completed Page.
     * This method is used to load the PaymentCompleted.fxml file and display the Payment Completed Page to the user.
     * 
     * @param primaryStage the primary stage on which the scene is set
     * @throws Exception if there is any error during loading the FXML file
     */
	public void start(Stage primaryStage) throws Exception {
		  Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("/boundry/PaymentCompleted.fxml"));
	      Scene scene = new Scene(root);
	      primaryStage.setTitle("Payment Completed");
	      primaryStage.setScene(scene);
	      primaryStage.show();
	}
	/**
     * Initializes the controller class.
     * This method is automatically called after the FXML file has been loaded.
     * It sets the order number label to display the current order number and updates the system
     * to increment the order number for the next order.
     * 
     * @param location the location used to resolve relative paths for the root object, or null if the location is not known
     * @param resources the resources used to localize the root object, or null if the root object was not localized
     */
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		orderNumberlbl.setText(ChatClient.listNumber);
		int listNum=Integer.parseInt(ChatClient.listNumber)+1;
		ClientUI.chat.accept(new Message1(MessageType.setListNumber,Integer.toString(listNum)));
		ClientUI.chat.accept(new Message1(MessageType.addOrderToReports,TheCartController.orderReList));
		TheCartController.orderReList.clear();

		
		
		
	}


}
