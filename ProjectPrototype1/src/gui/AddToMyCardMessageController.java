package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AddToMyCardMessageController {
    
    /**
     * Handles the action when the "New Order" button is clicked.
     *
     * @param event the ActionEvent triggered by the button click
     */
    @FXML
    void newOrder(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Get the current stage
        RestaurantController AFrame = new RestaurantController(); // Create a new instance of RestaurantController
        try {
            AFrame.start(stage); // Start the RestaurantController scene
        } catch (Exception e) {
            e.printStackTrace(); // Print the stack trace for any exceptions
        }
    }

    /**
     * Handles the action when the "Home" button is clicked.
     *
     * @param event the ActionEvent triggered by the button click
     */
    @FXML
    void home(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Get the current stage
        ClientPageController AFrame = new ClientPageController(); // Create a new instance of ClientPageController
        try {
            AFrame.start(stage); // Start the ClientPageController scene
        } catch (Exception e) {
            e.printStackTrace(); // Print the stack trace for any exceptions
        }
    }

    /**
     * Handles the action when the "My Cart" button is clicked.
     *
     * @param event the ActionEvent triggered by the button click
     */
    @FXML
    void myCart(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Get the current stage
        TheCartController aFrame = new TheCartController(); // Create a new instance of TheCartController
        try {
            aFrame.start(stage); // Start the TheCartController scene
        } catch (Exception e) {
            e.printStackTrace(); // Print the stack trace for any exceptions
        }
    }

    /**
     * Initializes the AddToMyCardMessageController scene.
     *
     * @param primaryStage the primary stage for this application
     * @throws Exception if the FXML file cannot be loaded
     */
    public void start(Stage primaryStage) throws Exception {
        Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("/gui/AddToMyCardMessage.fxml")); // Load FXML
        Scene scene = new Scene(root); // Create a new scene
        primaryStage.setTitle("Add To My Card Message"); // Set the title of the stage
        primaryStage.setScene(scene); // Set the scene for the stage
        primaryStage.show(); // Show the stage
    }
}
