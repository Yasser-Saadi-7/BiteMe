package gui;

import java.net.URL;
import java.util.ResourceBundle;

import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import logic.Message1;
import logic.MessageType;
import logic.Order;

public class OrderInformationFormController1 implements Initializable {
    private Order o;

    @FXML
    private TextField txtRestaurant; // Updated variable naming to follow Java conventions
    @FXML
    private TextField txtOrderId; // Updated to reflect new field
    @FXML
    private TextField txtTotalPrice; // Updated to reflect new field
    @FXML
    private TextField txtOrderAddress; // Updated to reflect new field
    @FXML
    private TextField txtUserID; // Added for user ID
    @FXML
    private TextField txtDeliveryTypeId; // Added for delivery type ID
    @FXML
    private TextField txtExpectedDeliveryTime; // Added for expected delivery time
    @FXML
    private TextField txtActualDeliveryTime; // Added for actual delivery time

    private String getRes() {
        return txtRestaurant.getText();
    }

    private int getOrderId() {
        return Integer.parseInt(txtOrderId.getText()); // Updated to return int
    }

    private double getTotalPrice() {
        return Double.parseDouble(txtTotalPrice.getText()); // Updated to return double
    }

    private String getAddress() {
        return txtOrderAddress.getText();
    }

    private String getUserId() {
        return txtUserID.getText(); // Added to get user ID
    }

    private int getDeliveryTypeId() {
        return Integer.parseInt(txtDeliveryTypeId.getText()); // Added to return int for delivery type ID
    }

    private String getExpectedDeliveryTime() {
        return txtExpectedDeliveryTime.getText(); // Changed to return String
    }

    private String getActualDeliveryTime() {
        return txtActualDeliveryTime.getText(); // Changed to return String
    }

    public void loadOrder(Order o1) {
        this.o = o1;
        this.txtRestaurant.setText(String.valueOf(o.getRestaurantId())); // Display restaurant ID
        this.txtOrderId.setText(String.valueOf(o.getOrderId()));
        this.txtTotalPrice.setText(String.valueOf(o.getTotalPrice()));
        this.txtOrderAddress.setText(o.getOrderAddress());
        this.txtUserID.setText(o.getUserID()); // Set user ID
        this.txtDeliveryTypeId.setText(String.valueOf(o.getDeliveryTypeId())); // Set delivery type ID
        this.txtExpectedDeliveryTime.setText(o.getExpectedDeliveryTime()); // Set expected delivery time
        this.txtActualDeliveryTime.setText(o.getActualDeliveryTime()); // Set actual delivery time
    }

    public void closePage(ActionEvent event) throws Exception {
        ((Node) event.getSource()).getScene().getWindow().hide(); // Hiding primary window
        OrderTrackFrameController1 orderTrackFrameController = new OrderTrackFrameController1();
        Stage primaryStage = new Stage();
        orderTrackFrameController.start(primaryStage);
    }

    public void updateOrder(ActionEvent event) throws Exception {
        String res = getRes();
        int orderId = getOrderId(); // Get order ID
        double totalPrice = getTotalPrice(); // Get total price
        String address = getAddress(); // Get order address
        String userId = getUserId(); // Get user ID
        int deliveryTypeId = getDeliveryTypeId(); // Get delivery type ID
        String expectedDeliveryTime = getExpectedDeliveryTime(); // Get expected delivery time
        String actualDeliveryTime = getActualDeliveryTime(); // Get actual delivery time

        // Create a new Order object
        Order newOrder = new Order(orderId, userId, Integer.parseInt(res), totalPrice, address, 
                                    null, // Set orderDate as null or the appropriate value
                                    deliveryTypeId, expectedDeliveryTime, actualDeliveryTime); // Updated constructor call

        // Send the order object to the server
        try {
            ClientUI.chat.accept(new Message1(MessageType.updateOrder, newOrder));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {	
        // Initialize any required data here
    }
}
