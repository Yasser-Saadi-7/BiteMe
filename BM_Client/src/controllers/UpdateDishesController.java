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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import entities.Dish;
import entities.MealsType;
import entities.Message1;
import entities.MessageType;


/**
 * Controller class for managing and updating dishes.
 * Provides functionalities to add, update, delete dishes, and navigate between different views.
 * Handles communication with the server for dish-related operations.
 * 
 * @author Yasser Saadi
 */
public class UpdateDishesController implements Initializable{
	
    ObservableList<Dish> dishes;
	
	public static Dish dish1;
	
	@FXML
	public  TableView<Dish> dishesTable;
	
	@FXML
	private TableColumn<Dish, String> dishNameCol;
	
	@FXML
	private TableColumn<Dish, String> priceCol;
	
	@FXML
    private TextField dishNametxt;

    @FXML
    private TextField newDishNametxt;

    @FXML
    private TextField newDishPricetxt;

    @FXML
    private TextField pricetxt;
    
    @FXML
	private Label txtmessage;
    
    private String getNewDishName() {
		return newDishNametxt.getText();
	}
	
    /**
     * Handles the action event for deleting a dish.
     * Removes the selected dish from the TableView and sends a request to delete the dish from the database.
     * Shows a success message if the dish is deleted or an error message if no dish is selected.
     *
     * @param event The ActionEvent that triggered this method.
     */
	@FXML
	void delete(ActionEvent event) {
		  Dish selectedDish = dishesTable.getSelectionModel().getSelectedItem();
		    
		    if (selectedDish != null) {
		        // Get the dish ID
		        String dishId = selectedDish.getDishId();
		        
		        // Remove the selected dish from the ObservableList
		        dishesTable.getItems().remove(selectedDish);
		        
		        // Send a request to delete the dish from the database
		        ClientUI.chat.accept(new Message1(MessageType.deleteDish, dishId));
		        
		        // Show success message
		        txtmessage.setText("Dish " + selectedDish.getDishName() + " deleted successfully.");
		    } else {
		        // Show error message if no dish is selected
		        Alert alert = new Alert(AlertType.ERROR);
		        alert.setContentText("Please select a dish to delete.");
		        alert.setHeaderText("No Dish Selected");
		        alert.showAndWait();
		    } }
		    
	
	  /**
     * Handles the action event for updating the dish name.
     * Updates the name of the selected dish in the TableView and sends a request to update the name in the database.
     * Shows an error message if no dish is selected or if the new dish name is empty.
     *
     * @param event The ActionEvent that triggered this method.
     */
	@FXML
	void updateDishName(ActionEvent event) {
	    if (dishesTable.getSelectionModel().getSelectedItem() != null) {
	        String newDishName = newDishNametxt.getText();
	        if (!newDishName.trim().isEmpty()) {
	            Dish selectedDish = dishesTable.getSelectionModel().getSelectedItem();
	            selectedDish.setDishName(newDishName);

	            // Update the dish name in the database
	            String str = selectedDish.getDishId() + " " + newDishName;
	            Message1 msg = new Message1(MessageType.updateDishName, str);
	            ClientUI.chat.accept(msg);

	            // Refresh the TableView to show the updated dish name
	            dishesTable.refresh();
	            txtmessage.setText("Dish name updated succesfully");
	        } else {
	            Alert a = new Alert(AlertType.ERROR);
	            a.setContentText("Error");
	            a.setHeaderText("Please enter a valid dish name.");
	            a.showAndWait();
	        }
	    } else {
	        Alert a = new Alert(AlertType.ERROR);
	        a.setContentText("Error");
	        a.setHeaderText("Please select a dish to update the name.");
	        a.showAndWait();
	    }
	}

	 /**
     * Handles the action event for updating the dish price.
     * Updates the price of the selected dish in the TableView and sends a request to update the price in the database.
     * Shows an error message if no dish is selected or if the new price is empty.
     *
     * @param event The ActionEvent that triggered this method.
     */
	@FXML
	void UpdateDishPrice(ActionEvent event) {
	    if (dishesTable.getSelectionModel().getSelectedItem() != null) {
	        String newDishPrice = newDishPricetxt.getText();
	        if (!newDishPrice.trim().isEmpty()) {
	            Dish selectedDish = dishesTable.getSelectionModel().getSelectedItem();
	            selectedDish.setPrice(newDishPrice);

	            // Update the dish price in the database
	            String str = selectedDish.getDishId() + " " + newDishPrice;
	            Message1 msg = new Message1(MessageType.updateDishPrice, str);
	            ClientUI.chat.accept(msg);

	            // Refresh the TableView to show the updated price
	            dishesTable.refresh();
	            txtmessage.setText("Dish price updated succesfully");

	        } else {
	            Alert a = new Alert(AlertType.ERROR);
	            a.setContentText("Error");
	            a.setHeaderText("Please enter a valid price.");
	            a.showAndWait();
	        }
	    } else {
	        Alert a = new Alert(AlertType.ERROR);
	        a.setContentText("Error");
	        a.setHeaderText("Please select a dish to update the price.");
	        a.showAndWait();
	    }
	}

	/**
     * Handles the action event for adding a new dish.
     * Validates the input fields, creates a new Dish object, and sends a request to add it to the database.
     * Updates the TableView with the new dish and shows a confirmation message.
     * @param event The ActionEvent that triggered this method.
     */
	@FXML
	void add(ActionEvent event) {
		    // Retrieve the values from the text fields
		    String newDishName = dishNametxt.getText();
		    String newDishPrice = pricetxt.getText();
		    String mealTypeId = UpdateMealsTypeController.mealType1.getMealTypeId();  // Current meal type ID
		    String mealTypeName = UpdateMealsTypeController.mealType1.getMealTypeName();  // Current meal type name
		    String restaurantName = UpdateMealsTypeController.mealType1.getResName(); // Current restaurant name

		 // Debugging: Print the values to verify they are being retrieved correctly
		    System.out.println("Dish Name: '" + newDishName + "'");
		    System.out.println("Dish Price: '" + newDishPrice + "'");
		    
		    // Validation: Ensure that none of the fields are empty
		    if (newDishName.isEmpty() || newDishPrice.isEmpty()) {
		        Alert a = new Alert(AlertType.ERROR);
		        a.setContentText("Please fill in both the dish name and price.");
		        a.setHeaderText("Error");
		        a.showAndWait();
		        return;
		    }

		    
		    // Check if the dish already exists in the menu
		    dishes = dishesTable.getItems();
		    for (Dish dish : dishes) {
		        if (dish.getDishName().equalsIgnoreCase(newDishName)) {
		            Alert alert = new Alert(AlertType.ERROR);
		            alert.setContentText("The dish '" + newDishName + "' already exists in the menu.");
		            alert.setHeaderText("Duplicate Dish");
		            alert.showAndWait();
		            return;
		        }
		    }
		    
		    
		    // Create a new Dish object with the generated dish ID and the provided values
		    Dish newDish = new Dish("44", restaurantName, newDishName, mealTypeId, mealTypeName, newDishPrice);
		    
		    // Send the new dish to the server to add it to the database
		 // Remove all spaces
		    ClientUI.chat.accept(new Message1(MessageType.getDishNumber, null));
	        String dishNameWithoutSpaces = newDishName.replace(" ", "");
		    String str = ChatClient.dishNumber + " " + dishNameWithoutSpaces + " " + newDishPrice + " " + mealTypeId + " " + mealTypeName + " " + restaurantName;
		    Message1 msg = new Message1(MessageType.addDish, str);
		    ClientUI.chat.accept(msg);
		    ClientUI.chat.accept(new Message1(MessageType.setDishNumber,Integer.toString(Integer.parseInt(ChatClient.dishNumber)+1)));

		    // Update the ObservableList with the new dish
		    
		    dishes = dishesTable.getItems();
		    dishes.add(newDish); // Ensure that dishesList is the ObservableList bound to the TableView
		    // Refresh the TableView
			dishesTable.refresh();

		    // Clear the input fields
		    newDishNametxt.clear();
		    newDishPricetxt.clear();

		    // Show confirmation message
		    txtmessage.setText("Dish added successfully!");
		}
	
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
     * Handles the action event for going back to the previous page (Update Meals Type page).
     * Opens the Update Meals Type page and closes the current stage.
     * @param event The ActionEvent that triggered this method.
     */
	@FXML
	void back(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();// get stage
		UpdateMealsTypeController AFrame=new UpdateMealsTypeController();
		try {
			AFrame.start(stage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	  /**
     * Handles the action event for navigating to the home page (Qualified Worker Page).
     * Opens the Qualified Worker Page and closes the current stage.
     *
     * @param event The ActionEvent that triggered this method.
     */
	@FXML
	void home(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();// get stage
		QualifiedWorkerPageController AFrame=new QualifiedWorkerPageController();
		try {
			AFrame.start(stage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public void start(Stage primaryStage) throws Exception {
		  Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("/boundry/UpdateDishes.fxml"));
	      Scene scene = new Scene(root);
	      primaryStage.setTitle("Update Dishes");
	      primaryStage.setScene(scene);
	      primaryStage.show();
	}
	
    /**
     * Initializes the Update Dishes page by setting up the TableView columns and loading dish data.
     * @param location The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the resources are not available.
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		dishNameCol.setCellValueFactory(new PropertyValueFactory<Dish,String>("dishName"));
		priceCol.setCellValueFactory(new PropertyValueFactory<Dish,String>("price"));
		ClientUI.chat.accept(new Message1(MessageType.dishes,UpdateMealsTypeController.mealType1.getMealTypeId()));
		dishes=FXCollections.observableArrayList(ChatClient.dishesList);
		dishesTable.setItems(dishes);
		dishesTable.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				dish1=dishesTable.getSelectionModel().getSelectedItem();

			}

		});
		
		
	}


}
