package controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import entities.Cart;
import entities.Dish;
import entities.MealsType;
import entities.Message1;
import entities.MessageType;
import entities.OrderReport;
/**
 * Controller class for the Order Summary Page in the GUI application.
 * This class displays the details of the user's order, including the selected restaurant,
 * meal type, dish, selections, and total price. It also allows the user to adjust the quantity
 * of the order and add it to the cart. The FXML file associated with this controller is "OrderSummary.fxml".
 * 
 * This controller also manages the transition to the "Add to Cart" confirmation page and handles
 * clearing the selections if the user navigates away.
 * 
 * @author Yasser Saadi
 */
public class OrderSummaryController implements Initializable{
	/** Static variable to track the quantity of the order */
	public static int quantity;
	/** Static variable to store the total price of the order as a string */
	public static  String totalPrice1;
	/** Label to display the selected restaurant name */
	@FXML
	private  Label resNameLbl;
	/** Label to display the selected meal type */
	@FXML
	private  Label mealTypeLbl;
	/** Label to display the selected dish name */
	@FXML
	private  Label dishNameLbl;
	/** Label to display the total price of the order */
	@FXML
	private  Label totalPriceLbl;
	/** TextField to input the quantity of the order */
	@FXML
	private  TextField qtxt;
	/** ListView to display the selections made by the user */
	@FXML
	private  ListView<String> selectionsViewList;
	/**
     * Navigates the user to the Home (Client) Page and clears the current selections.
     * This method is triggered when the "Home" button is clicked.
     * 
     * @param event the ActionEvent triggered when the button is clicked
     */
	@FXML
	void home(ActionEvent event) {
		SelectionsController.allAddedSelections.clear();
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();// get stage
		ClientPageController AFrame=new ClientPageController();
		try {
			AFrame.start(stage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public String getCurrentMonthStringAlternative() {
	    int month = LocalDate.now().getMonthValue();
	    return String.valueOf(month); 
	}
	public String getCurrentYearString() {
	    int currentYear = LocalDate.now().getYear();
	    
	    return String.valueOf(currentYear); 
	}
	/**
     * Adds the current order to the cart, including the selected dish, meal type, restaurant,
     * and any additional selections. The total price is calculated based on the quantity and 
     * selection prices. After adding the order to the cart, the user is navigated to the "Add to Cart" confirmation page.
     * This method is triggered when the "Add To Cart" button is clicked.
     * 
     * @param event the ActionEvent triggered when the button is clicked
     */
	@FXML
	void addToCart(ActionEvent event) {
		String selections="";
		int totalPrice;
		int ReporNum;
		for(int i=0;i<SelectionsController.allAddedSelections.size()-1;i++) {
			selections+=SelectionsController.allAddedSelections.get(i).getSelName()+",";
		}
		//selections+=SelectionsController.allAddedSelections.get(SelectionsController.allAddedSelections.size()-1).getSelName();
		if(SelectionsController.allAddedSelections.size()!=0) {
			selections+=SelectionsController.allAddedSelections.get(SelectionsController.allAddedSelections.size()-1).getSelName();
		}
		totalPrice=Integer.parseInt(qtxt.getText())*SelectionsController.totalPrice;
		TheCartController.myCart.add(new Cart(RestaurantController.resturant.getResName(),MealsTypeController.mealType.getMealTypeName(),DishesController.dish.getDishName(),selections,qtxt.getText(),Integer.toString(totalPrice)));
		ClientUI.chat.accept(new Message1(MessageType.getReportNum,null));
        int quan = Integer.parseInt(qtxt.getText());
        int itemPrice = Integer.parseInt(DishesController.dish.getPrice());
        int totalPriceForItem = quan * itemPrice;
        		
		TheCartController.orderReList.add(new OrderReport(ChatClient.reportNumber,RestaurantController.resturant.getBranchId(),getCurrentMonthStringAlternative(),getCurrentYearString(),RestaurantController.resturant.getResName(),DishesController.dish.getDishId(),DishesController.dish.getDishName(),MealsTypeController.mealType.getMealTypeName(),qtxt.getText(),DishesController.dish.getPrice(),Integer.toString(totalPriceForItem)));
		ReporNum=Integer.parseInt(ChatClient.reportNumber);
		ReporNum++;
		ClientUI.chat.accept(new Message1(MessageType.setReportNum,Integer.toString(ReporNum)));
		SelectionsController.allAddedSelections.clear();
		totalPrice1=Integer.toString(totalPrice);
		
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();// get stage
		AddToMyCardMessageController AFrame=new AddToMyCardMessageController();
		try {
			AFrame.start(stage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
     * Navigates the user back to the Selections Page and clears the current selections.
     * This method is triggered when the "Back" button is clicked.
     * 
     * @param event the ActionEvent triggered when the button is clicked
     */
	@FXML
	void back(ActionEvent event) {
		SelectionsController.allAddedSelections.clear();
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();// get stage
		SelectionsController AFrame=new SelectionsController();
		try {
			AFrame.start(stage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	 /**
     * Starts the Order Summary Page.
     * This method is used to load the OrderSummary.fxml file and display the Order Summary Page to the user.
     * 
     * @param primaryStage the primary stage on which the scene is set
     * @throws Exception if there is any error during loading the FXML file
     */
	
	public void start(Stage primaryStage) throws Exception {
		  Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("/boundry/OrderSummary.fxml"));
	      Scene scene = new Scene(root);
	      primaryStage.setTitle("Meals Type");
	      primaryStage.setScene(scene);
	      primaryStage.show();
	}
	/**
     * Initializes the controller class.
     * This method is automatically called after the FXML file has been loaded.
     * It sets up the labels and ListView with the selected restaurant, meal type, dish, selections, 
     * and total price. It also sets the default quantity to 1.
     * 
     * @param location the location used to resolve relative paths for the root object, or null if the location is not known
     * @param resources the resources used to localize the root object, or null if the root object was not localized
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		qtxt.setText("1");
		resNameLbl.setText(RestaurantController.resturant.getResName());	
		mealTypeLbl.setText(MealsTypeController.mealType.getMealTypeName());
		dishNameLbl.setText(DishesController.dish.getDishName());
		totalPriceLbl.setText(Integer.toString(SelectionsController.totalPrice));
		for(int i =0;i<SelectionsController.allAddedSelections.size();i++) {
			selectionsViewList.getItems().add(SelectionsController.allAddedSelections.get(i).getSelName());
		}
		
	}

}
