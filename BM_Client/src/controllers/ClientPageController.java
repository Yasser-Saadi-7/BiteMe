package controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
/**
 * Controller class for the Client Page in the GUI application.
 * This class manages the user interactions on the Client Page, such as creating a new order,
 * viewing the order list, accessing the cart, and logging out.
 * The FXML file associated with this controller is "ClientPage.fxml".
 * 
 * 
 * @author Yasser Saadi
 */
public class ClientPageController {
	
	@FXML
	private Button btncreateorder;
	
	@FXML
	private Button btnlogout;
	
	@FXML
	private Button btnvieworderslist;
	
	@FXML
	private Label txtwelcome;
	
	/**
     * Handles the action when the "Create New Order" button is clicked.
     * This method navigates the user to the restaurant page where they can create a new order.
     * 
     * @param event the ActionEvent triggered when the button is clicked
     */
	
	@FXML
	void createNewOrder(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		RestaurantController resController = new RestaurantController(); 
		try {
			resController.start(stage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	/**
     * Handles the action when the "My Cart" button is clicked.
     * This method navigates the user to the cart page where they can view the orders they have made and are waiting for payment.
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
     * Handles the action when the "View Orders List" button is clicked.
     * This method navigates the user to the page displaying the list of orders they have made previously.
     * 
     * @param event the ActionEvent triggered when the button is clicked
     */
	
	@FXML
	void viewOrdersList(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		ViewOrderListController aFrame = new ViewOrderListController(); 
		try {
			aFrame.start(stage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
     * Handles the action when the "Log Out" button is clicked.
     * This method logs the user out and navigates them back to the login page.
     * 
     * @param event the ActionEvent triggered when the button is clicked
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
     * Starts the Client Page.
     * This method is used to load the ClientPage.fxml file and display the Client Page to the user.
     * 
     * @param primaryStage the primary stage on which the scene is set
     * @throws Exception if there is any error during loading the FXML file
     */
	public void start(Stage primaryStage) throws Exception {
	      Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("/boundry/ClientPage.fxml"));
	      Scene scene = new Scene(root);
	      primaryStage.setTitle("Client Page");
	      primaryStage.setScene(scene);
	      primaryStage.show();
	   }

}
