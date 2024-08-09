package gui;

import java.net.URL;
import java.util.ResourceBundle;

import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import logic.Message1;
import logic.MessageType;
import logic.Order;

public class OrderSummaryController implements Initializable {
    
    @FXML
    private Label resNameLbl;
    
    @FXML
    private Label mealTypeLbl;
    
    @FXML
    private Label dishNameLbl;
    
    @FXML
    private Label totalPriceLbl;
    
    @FXML
    private ListView<String> selectionsViewList;
    
    // Assuming you have a way to get the current order
    private Order currentOrder;

    @FXML
    void home(ActionEvent event) {
        SelectionsController.allAddedSelections.clear();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Get stage
        ClientPageController AFrame = new ClientPageController();
        try {
            AFrame.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    void addToCart(ActionEvent event) {
        // Logic to add the current order to the cart could go here
        SelectionsController.allAddedSelections.clear();
    }
    
    @FXML
    void newOrder(ActionEvent event) {
        SelectionsController.allAddedSelections.clear();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Get stage
        RestaurantController AFrame = new RestaurantController();
        try {
            AFrame.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(this.getClass().getResource("/gui/OrderSummary.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Order Summary");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Obtain the current order details here
        currentOrder = getCurrentOrder(); // Replace this with the actual method to retrieve the order

        if (currentOrder != null) {
            resNameLbl.setText(String.valueOf(currentOrder.getRestaurantId())); // Assuming you want to display restaurant ID
            // If you have a method to get restaurant name, use it here
            mealTypeLbl.setText(MealTypeController.mealType.getMealTypeName());
            dishNameLbl.setText(DishesController.dish.getDishName());
            totalPriceLbl.setText(String.valueOf(currentOrder.getTotalPrice())); // Updated to use Order's total price
            
            // Display added selections in the list view
            selectionsViewList.getItems().clear(); // Clear existing items
            for (var selection : SelectionsController.allAddedSelections) {
                selectionsViewList.getItems().add(selection.getSelName());
            }
        } else {
            // Handle the case where the order is null (e.g., show an error message or default values)
            resNameLbl.setText("No restaurant selected");
            totalPriceLbl.setText("0.00");
        }
    }
    
    // Method to obtain the current order (you need to implement this)
    private Order getCurrentOrder() {
        // TODO: Implement logic to retrieve the current Order object
        // This could involve accessing a shared state, a static method, or a service
        return null; // Placeholder; replace with actual order retrieval
    }
}
