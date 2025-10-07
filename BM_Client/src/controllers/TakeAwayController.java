package controllers;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
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
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import entities.Message1;
import entities.MessageType;
/**
 * Controller class for the Take Away Page in the GUI application.
 * This class allows the user to fill in details for a take-away order,
 * such as the requested date and time, and confirm the order.
 * The FXML file associated with this controller is "TakeAway.fxml".
 * 
 * The controller also handles the communication with the server to submit the order
 * details and clear the cart once the order is confirmed.
 * 
 * The order number and other relevant information are generated and stored during the process.
 * 
 * This class includes validation to ensure all required fields are filled before the order can be confirmed.
 * 
 * @author Yasser Saadi
 */
public class TakeAwayController implements Initializable{
	/** Static variable to store the order number */
	public static int orderNum;
	
	/** TextField for the user to input the requested time for the take-away order */
	@FXML
	private TextField reqTimetxt;
	/** Label to display the total price of the order */
	@FXML
	private Label totalPricelbl;
	/** DatePicker for the user to select the requested date for the take-away order */
	@FXML
	private DatePicker reqDate;
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
     * Confirms the take-away order after validating the input fields.
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
	    if ( !reqTimetxt.getText().trim().isEmpty() && 
	        reqDate.getValue() != null) { // Assuming you also want to check if a DatePicker value is selected
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
			for(int i=0;i<TheCartController.myCart.size();i++) {
				totalPrice+=Integer.parseInt(TheCartController.myCart.get(i).getTotalPrice());
			}
			totalPrice+=Integer.parseInt(DeliveryTypeController.deliveryType.getPrice());
			
			//get all restaurant names in the order
	    	for(int i=0;i<TheCartController.myCart.size()-1;i++) {
	    		res+=TheCartController.myCart.get(i).getResName()+",";
	    	}
	    	res+=TheCartController.myCart.get(TheCartController.myCart.size()-1).getResName();
	    	//sending all the order list data to the server
	    	str=ChatClient.logIn.getUserID()+" "+ChatClient.listNumber+" "+res+" "+dateString+" "+reqDate.getValue().toString()+" "+Integer.toString(totalPrice)+" "+"NoAddress"+" "+deliveryTybeoutSpaces+" "+"Procressing"+" "+"WaitingForApproval"+" "+timeString+" "+reqTimetxt.getText();
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
     * It calculates and displays the total price for the take-away order.
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
     * Starts the Take Away Page.
     * This method is used to load the TakeAway.fxml file and display the Take Away Page to the user.
     * 
     * @param primaryStage the primary stage on which the scene is set
     * @throws Exception if there is any error during loading the FXML file
     */
	public void start(Stage primaryStage) throws Exception {
		  Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("/boundry/TakeAway.fxml"));
	      Scene scene = new Scene(root);
	      primaryStage.setTitle("Take Away");
	      primaryStage.setScene(scene);
	      primaryStage.show();
	}
	 

}
