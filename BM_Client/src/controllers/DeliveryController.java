package controllers;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import entities.Message1;
import entities.MessageType;
import entities.OrderReport;
/**
 * Controller class for the Delivery Page in the GUI application.
 * This class allows the user to fill in delivery details, including the requested date, time, and address,
 * and confirm the order. The FXML file associated with this controller is "Delivery.fxml".
 * 
 * The controller handles the validation of input fields to ensure that all required information is provided
 * before the order can be confirmed. It also manages communication with the server to submit the order details
 * and clear the cart once the order is confirmed.
 * 
 * The order details, including the total price and selected delivery type, are processed and stored during the confirmation process.
 * If any required fields are left blank, the user is alerted to complete the form before proceeding.
 * 
 * 
 * @author Yasser Saadi
 */
public class DeliveryController implements Initializable{
	public static OrderReport order;
	
	/** TextField for the user to input the delivery address */
	 @FXML
	 private TextField addtxt;
	 /** DatePicker for the user to select the requested delivery date */
	 @FXML
	 private DatePicker reqDate;
	 
	 /** TextField for the user to input the requested delivery time */
	 @FXML
	 private TextField reqTimetxt;
	 /** Label to display the total price of the order */
	 @FXML
	 private Label totalPricelbl;
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
     * Navigates the user back to the Delivery Type Page.
     * This method is triggered when the "Back" button is clicked.
     * 
     * @param event the ActionEvent triggered when the button is clicked
     */
	@FXML
	void back(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();// get stage
		DeliveryTypeController AFrame=new DeliveryTypeController();
		try {
			AFrame.start(stage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
     * Confirms the delivery order after validating the input fields.
     * If all required fields are filled, the order details are sent to the server,
     * the cart is cleared, and the user is navigated to the Payment Completed Page.
     * If any required fields are missing, an alert is shown.
     * This method is triggered when the "Confirm" button is clicked.
     * 
     * @param event the ActionEvent triggered when the button is clicked
     */
	@FXML
	void confirm(ActionEvent event) {
		
		// Check if all the TextFields are filled
		order = new OrderReport();
	    if ( !addtxt.getText().trim().isEmpty() &&
	        !reqTimetxt.getText().trim().isEmpty() && 
	        reqDate.getValue() != null) { // check if a DatePicker value is selected
	    	String str,res="";
	    	int totalPrice=0;
	    	
	    	String originalString = DeliveryTypeController.deliveryType.getDeliveryType();
	        // Remove all spaces
	        String deliveryTybeoutSpaces = originalString.replace(" ", "");
	        //set the order date
	    	LocalDate currentDate = LocalDate.now();
	        String dateString = currentDate.toString();
	        //check what the order number
	        ClientUI.chat.accept(new Message1(MessageType.getListNumber,null));
	        //set the order time
	        LocalTime currentTime = LocalTime.now();
	        // Add 2 hours to the current time
	        LocalTime newTime = currentTime.plusHours(2);
	        // Format the new time to a string
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
	        String timeString = newTime.format(formatter);

	        //calculate the total price		
			totalPrice+=Integer.parseInt(DeliveryTypeController.deliveryType.getPrice());
			//ClientUI.chat.accept(new Message1(MessageType.addOrderToReports,arrReport));
			//get all restaurant names in the order
	    	for(int i=0;i<TheCartController.myCart.size()-1;i++) {
	    		res+=TheCartController.myCart.get(i).getResName()+",";
	    	}
	    	res+=TheCartController.myCart.get(TheCartController.myCart.size()-1).getResName();
	    	//sending all the order list data to the server
	    	String address=addtxt.getText();
	    	String addreesWithoutSpaces = address.replace(" ", "");
	    	str=ChatClient.logIn.getUserID()+" "+ChatClient.listNumber+" "+res+" "+dateString+" "+reqDate.getValue().toString()+" "+Integer.toString(totalPrice)+" "+addreesWithoutSpaces+" "+deliveryTybeoutSpaces+" "+"Procressing"+" "+"WaitingForApproval"+" "+timeString+" "+reqTimetxt.getText();
	    	ClientUI.chat.accept(new Message1(MessageType.takeAway,str));
	    	
	    	
	    	
	    	
    		

	    	TheCartController.myCart.clear();
	        //open the next page
	        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();// get stage
			PaymentCompletedController AFrame=new PaymentCompletedController();
			try {
				AFrame.start(stage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	    } else {
	        
	        //display an alert dialog to inform the user
	        Alert alert = new Alert(Alert.AlertType.WARNING);
	        alert.setTitle("Input Error");
	        alert.setHeaderText(null);
	        alert.setContentText("Please fill in all required fields.");
	        alert.showAndWait();
	    }
	}
	/**
     * Initializes the controller class.
     * This method is automatically called after the FXML file has been loaded.
     * It calculates and displays the total price for the delivery order.
     * 
     * @param location the location used to resolve relative paths for the root object, or null if the location is not known
     * @param resources the resources used to localize the root object, or null if the root object was not localized
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		int totalPrice=0;
		for(int i=0;i<TheCartController.myCart.size();i++) {
			totalPrice+=Integer.parseInt(TheCartController.myCart.get(i).getTotalPrice());
		}
		totalPrice+=Integer.parseInt(DeliveryTypeController.deliveryType.getPrice());
		totalPricelbl.setText(Integer.toString(totalPrice));
		
	}
	/**
     * Starts the Delivery Page.
     * This method is used to load the Delivery.fxml file and display the Delivery Page to the user.
     * 
     * @param primaryStage the primary stage on which the scene is set
     * @throws Exception if there is any error during loading the FXML file
     */
	public void start(Stage primaryStage) throws Exception {
		  Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("/boundry/Delivery.fxml"));
	      Scene scene = new Scene(root);
	      primaryStage.setTitle("Delivery");
	      primaryStage.setScene(scene);
	      primaryStage.show();
	}
	 

}
