package controllers;

import java.net.URL;
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
import entities.Dish;
import entities.MealsType;
import entities.Message1;
import entities.MessageType;

/**
 * Controller class for the Dishes Page in the GUI application.
 * This class manages the dish selection process and handles user interactions
 * such as moving to the next page for further selection, navigating back to the previous page,
 * going to the home page, and logging out of the application.
 * The FXML file associated with this controller is "Dishes.fxml".
 * 
 * 
 * @author Yasser Saadi
 */

public class DishesController implements Initializable{
	/** Observable list of dishes to be displayed in the table */
	ObservableList<Dish> dishes;
	/** Static reference to the selected dish */
	public static Dish dish;
	/** TableView component for displaying the list of dishes */
	@FXML
	public  TableView<Dish> dishesTable;
	/** TableColumn for displaying the dish names */
	@FXML
	private TableColumn<Dish, String> dishNameCol;
	/** TableColumn for displaying the dish prices */
	@FXML
	private TableColumn<Dish, String> priceCol;
	/**
     * Logs the user out and navigates them back to the login page.
     * This method is triggered when the "Log Out" button is clicked.
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
     * Navigates the user back to the Meals Type Page.
     * This method is triggered when the "Back" button is clicked.
     * 
     * @param event the ActionEvent triggered when the button is clicked
     */
	@FXML
	void back(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();// get stage
		MealsTypeController AFrame=new MealsTypeController();
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
     * Proceeds to the next page to make further selections based on the chosen dish.
     * This method is triggered when the "Next" button is clicked.
     * If no dish is selected, an error alert is shown.
     * 
     * @param event the ActionEvent triggered when the button is clicked
     */
	@FXML
	void next(ActionEvent event) {
		if(dishesTable.getSelectionModel().getSelectedItem()!=null) {
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();// get stage
			SelectionsController AFrame=new SelectionsController();
			try {
				AFrame.start(stage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			Alert a = new Alert(AlertType.ERROR);
			a.setContentText("Error");
			a.setHeaderText("should you Select a meal type:");
			a.showAndWait();
		}

	}
	/**
     * Starts the Dishes Page.
     * This method is used to load the Dishes.fxml file and display the Dishes Page to the user.
     * 
     * @param primaryStage the primary stage on which the scene is set
     * @throws Exception if there is any error during loading the FXML file
     */
	public void start(Stage primaryStage) throws Exception {
		  Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("/boundry/Dishes.fxml"));
	      Scene scene = new Scene(root);
	      primaryStage.setTitle("Meals Type");
	      primaryStage.setScene(scene);
	      primaryStage.show();
	}
	/**
     * Initializes the controller class.
     * This method is automatically called after the FXML file has been loaded.
     * It sets up the TableView columns and populates the table with dishes retrieved from the server.
     * 
     * @param location the location used to resolve relative paths for the root object, or null if the location is not known
     * @param resources the resources used to localize the root object, or null if the root object was not localized
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		dishNameCol.setCellValueFactory(new PropertyValueFactory<Dish,String>("dishName"));
		priceCol.setCellValueFactory(new PropertyValueFactory<Dish,String>("price"));
		ClientUI.chat.accept(new Message1(MessageType.dishes,MealsTypeController.mealType.getMealTypeId()));
		dishes=FXCollections.observableArrayList(ChatClient.dishesList);
		dishesTable.setItems(dishes);
		dishesTable.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				dish=dishesTable.getSelectionModel().getSelectedItem();

			}

		});
		
		
	}
	

}
