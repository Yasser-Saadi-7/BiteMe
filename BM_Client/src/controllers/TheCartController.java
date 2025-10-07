package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import entities.Cart;
import entities.MealsType;
import entities.Message1;
import entities.MessageType;
import entities.OrderReport;
import entities.Selection;
/**
 * Controller class for the My Cart Page in the GUI application.
 * This class manages the user's shopping cart, allowing them to view, remove items, 
 * and proceed to payment. The FXML file associated with this controller is "TheCart.fxml".
 * 
 * This controller also handles the display of the cart's contents, which includes details 
 * such as restaurant name, meal type, dish name, selections, quantity, and price.
 * 
 * @author Yasser Saadi
 */
public class TheCartController implements Initializable{
	/** Static reference to the currently selected cart item */
	private static Cart cart;
	
	public static ArrayList<OrderReport> orderReList = new ArrayList<OrderReport>();
	/** Static list to store all items in the user's cart */
	public static ArrayList<Cart> myCart=new ArrayList<Cart>();
	/** Observable list for displaying cart items in the TableView */
	ObservableList<Cart> cartItems;
	
	/** TableView component for displaying the user's cart */
	@FXML
	public  TableView<Cart> myCartTable;
	 /** TableColumn for displaying the restaurant name */
	@FXML
	private TableColumn<Cart, String> resNameCol;
	/** TableColumn for displaying the meal type */
	@FXML
	private TableColumn<Cart, String> MTypeCol;
	/** TableColumn for displaying the dish name */
	@FXML
	private TableColumn<Cart, String> dishNameCol;
	/** TableColumn for displaying the selections made for the dish */
	@FXML
	private TableColumn<Cart, String> selCol;
	/** TableColumn for displaying the quantity of the order */
	@FXML
	private TableColumn<Cart, String> QCol;
	/** TableColumn for displaying the total price of the order */
	@FXML
	private TableColumn<Cart, String> priceCol;
	
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
     * Proceeds to the payment page if there are items in the cart.
     * If the cart is empty, an error alert is shown.
     * This method is triggered when the "Pay" button is clicked.
     * 
     * @param event the ActionEvent triggered when the button is clicked
     */
	@FXML
	void pay(ActionEvent event) {
		if(myCart.size()!=0) {
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();// get stage
			DeliveryTypeController AFrame=new DeliveryTypeController();
			try {
				AFrame.start(stage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			Alert a = new Alert(AlertType.ERROR);
			a.setContentText("Error");
			a.setHeaderText("There is no orders in your cart");
			a.showAndWait();
		}
		
		
	}
	/**
     * Removes the selected order from the cart.
     * If no order is selected, an error alert is shown.
     * This method is triggered when the "Remove" button is clicked.
     * 
     * @param event the ActionEvent triggered when the button is clicked
     */
	@FXML
	void remove(ActionEvent event) {
		if(myCartTable.getSelectionModel().getSelectedItem()!=null) {
			for (int i=0;i<myCart.size();i++) {
				if(myCart.get(i).equals(cart)) {
					myCart.remove(i);
					ObservableList<Cart> newCart=FXCollections.observableArrayList(myCart);
					myCartTable.setItems(newCart);
				}
			}
			for(int i=0;i<orderReList.size();i++) {
				if(orderReList.get(i).getRestaurantName().equals(cart.getResName()) && orderReList.get(i).getItemName().equals(cart.getDishName())) {
					orderReList.remove(i);
				}
			}
		}else {
			Alert a = new Alert(AlertType.ERROR);
			a.setContentText("Error");
			a.setHeaderText("should you Select an order to remove:");
			a.showAndWait();
		}
		
	}
	/**
     * Starts the My Cart Page.
     * This method is used to load the TheCart.fxml file and display the My Cart Page to the user.
     * 
     * @param primaryStage the primary stage on which the scene is set
     * @throws Exception if there is any error during loading the FXML file
     */
	public void start(Stage primaryStage) throws Exception {
		  Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("/boundry/TheCart.fxml"));
	      Scene scene = new Scene(root);
	      primaryStage.setTitle("My Cart");
	      primaryStage.setScene(scene);
	      primaryStage.show();
	}
	/**
     * Initializes the controller class.
     * This method is automatically called after the FXML file has been loaded.
     * It sets up the TableView columns and populates the table with items currently in the user's cart.
     * 
     * @param location the location used to resolve relative paths for the root object, or null if the location is not known
     * @param resources the resources used to localize the root object, or null if the root object was not localized
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		resNameCol.setCellValueFactory(new PropertyValueFactory<Cart,String>("resName"));
		MTypeCol.setCellValueFactory(new PropertyValueFactory<Cart,String>("mealType"));
		dishNameCol.setCellValueFactory(new PropertyValueFactory<Cart,String>("dishName"));
		selCol.setCellValueFactory(new PropertyValueFactory<Cart,String>("sel"));
		QCol.setCellValueFactory(new PropertyValueFactory<Cart,String>("quantity"));
		priceCol.setCellValueFactory(new PropertyValueFactory<Cart,String>("totalPrice"));
		cartItems=FXCollections.observableArrayList(myCart);
		myCartTable.setItems(cartItems);
		myCartTable.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				cart=myCartTable.getSelectionModel().getSelectedItem();

			}

		});
		
		
	}

}
