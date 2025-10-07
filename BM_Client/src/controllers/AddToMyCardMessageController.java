package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 * Controller class for the "Add To My Cart Message" Page in the GUI application.
 * This class provides the user with options after successfully adding an order to the cart.
 * The options include placing a new order, returning to the home page, or viewing the cart.
 * The FXML file associated with this controller is "AddToMyCardMessage.fxml".
 * 
 * This controller also manages the transitions to these different pages based on the user's selection.
 * 
 * @author Yasser Saadi
 */
public class AddToMyCardMessageController {
	
	/**
     * Navigates the user to the Restaurant Page to place a new order.
     * This method is triggered when the "New Order" button is clicked.
     * 
     * @param event the ActionEvent triggered when the button is clicked
     */
	@FXML
	void newOrder(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();// get stage
		RestaurantController AFrame=new RestaurantController();
		try {
			AFrame.start(stage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
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
     * Navigates the user to the Cart Page to view the contents of their cart.
     * This method is triggered when the "My Cart" button is clicked.
     * 
     * @param event the ActionEvent triggered when the button is clicked
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
     * Starts the "Add To My Cart Message" Page.
     * This method is used to load the AddToMyCardMessage.fxml file and display the page to the user.
     * 
     * @param primaryStage the primary stage on which the scene is set
     * @throws Exception if there is any error during loading the FXML file
     */
	public void start(Stage primaryStage) throws Exception {
	      Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("/boundry/AddToMyCardMessage.fxml"));
	      Scene scene = new Scene(root);
	      primaryStage.setTitle("Add To My Card Message");
	      primaryStage.setScene(scene);
	      primaryStage.show();
	   }

}
