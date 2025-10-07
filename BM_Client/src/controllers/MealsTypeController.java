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
import entities.MealsType;
import entities.Message1;
import entities.MessageType;
import entities.Restaurant;

/**
 * Controller class for the Meals Type Page in the GUI application.
 * This class manages the meal type selection process and handles user interactions
 * such as moving to the next page to view dishes, navigating back to the previous page,
 * going to the home page, and logging out of the application.
 * The FXML file associated with this controller is "MealsType.fxml".
 * 
 * 
 * @author Yasser Saadi
 */

public class MealsTypeController implements Initializable{
	/** Observable list of meal types to be displayed in the table */
	ObservableList<MealsType> mealsType;
	/** Static reference to the selected meal type */
	public static MealsType mealType;
	/** TableView component for displaying the list of meal types */
	@FXML
	public  TableView<MealsType> mealsTypeTable;
	
	/** TableColumn for displaying the meal type names */
	@FXML
	private TableColumn<MealsType, String> mealTypeCol;
	
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
     * Navigates the user back to the Restaurant Page.
     * This method is triggered when the "Back" button is clicked.
     * 
     * @param event the ActionEvent triggered when the button is clicked
     */
	@FXML
	void back(ActionEvent event) {
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
     * Proceeds to the next page to view dishes of the selected meal type.
     * This method is triggered when the "Next" button is clicked.
     * If no meal type is selected, an error alert is shown.
     * 
     * @param event the ActionEvent triggered when the button is clicked
     */
	@FXML
	void next(ActionEvent event) {
		if(mealsTypeTable.getSelectionModel().getSelectedItem()!=null) {
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();// get stage
			DishesController AFrame=new DishesController();
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
     * Starts the Meals Type Page.
     * This method is used to load the MealsType.fxml file and display the Meals Type Page to the user.
     * 
     * @param primaryStage the primary stage on which the scene is set
     * @throws Exception if there is any error during loading the FXML file
     */
	public void start(Stage primaryStage) throws Exception {
		  Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("/boundry/MealsType.fxml"));
	      Scene scene = new Scene(root);
	      primaryStage.setTitle("Meals Type");
	      primaryStage.setScene(scene);
	      primaryStage.show();
	}
	/**
     * Initializes the controller class.
     * This method is automatically called after the FXML file has been loaded.
     * It sets up the TableView columns and populates the table with meal types retrieved from the server.
     * 
     * @param location the location used to resolve relative paths for the root object, or null if the location is not known
     * @param resources the resources used to localize the root object, or null if the root object was not localized
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		mealTypeCol.setCellValueFactory(new PropertyValueFactory<MealsType,String>("mealTypeName"));
		ClientUI.chat.accept(new Message1(MessageType.mealsType,RestaurantController.resturant.getResName()));
		mealsType=FXCollections.observableArrayList(ChatClient.mealsTypeList);
		mealsTypeTable.setItems(mealsType);
		mealsTypeTable.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				mealType=mealsTypeTable.getSelectionModel().getSelectedItem();

			}

		});
		
		
	}


}
