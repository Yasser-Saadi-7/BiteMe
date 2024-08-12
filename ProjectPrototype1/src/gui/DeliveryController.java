package gui;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import logic.Message1;
import logic.MessageType;

/**
 * Controller for managing delivery-related actions and UI updates.
 */
public class DeliveryController implements Initializable {

    @FXML
    private TextField addtxt;      // Text field for address input

    @FXML
    private TextField idtxt;       // Text field for ID input

    @FXML
    private DatePicker reqDate;    // Date picker for selecting request date

    @FXML
    private TextField reqTimetxt;  // Text field for time input

    @FXML
    private Label totalPricelbl;    // Label to display total price

    /**
     * Navigates to the home page.
     *
     * @param event the action event
     */
    @FXML
    void home(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Get stage
        ClientPageController AFrame = new ClientPageController();
        try {
            AFrame.start(stage); // Start the home page
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Navigates back to the delivery type selection page.
     *
     * @param event the action event
     */
    @FXML
    void back(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Get stage
        DeliveryTypeController AFrame = new DeliveryTypeController();
        try {
            AFrame.start(stage); // Start the delivery type selection page
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Confirms the delivery order and processes the request.
     *
     * @param event the action event
     */
    @FXML
    void confirm(ActionEvent event) {
        // Check if all the TextFields are filled
        if (!addtxt.getText().trim().isEmpty() &&
            !idtxt.getText().trim().isEmpty() &&
            !reqTimetxt.getText().trim().isEmpty() &&
            reqDate.getValue() != null) { // Check if a DatePicker value is selected
            
            String str, res = "";
            double totalPrice = 0.0; // Change totalPrice to double

            String originalString = DeliveryTypeController.deliveryType.getDeliveryType();
            String deliveryTypeOutSpaces = originalString.replace(" ", ""); // Remove spaces from delivery type

            // Set the order date
            LocalDate currentDate = LocalDate.now();
            String dateString = currentDate.toString();

            // Check what the order number
            ClientUI.chat.accept(new Message1(MessageType.getListNumber, null));

            // Set the order time
            LocalTime currentTime = LocalTime.now();
            LocalTime newTime = currentTime.plusHours(2); // Add 2 hours to the current time

            // Format the new time to a string
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            String timeString = newTime.format(formatter);

            // Calculate the total price
            for (int i = 0; i < TheCartController.myCart.size(); i++) {
                totalPrice += TheCartController.myCart.get(i).getTotalPrice(); // Ensure getTotalPrice returns a double
            }
            totalPrice += DeliveryTypeController.deliveryType.getPrice(); // Ensure getPrice returns a double

            // Get all restaurant names in the order
            for (int i = 0; i < TheCartController.myCart.size() - 1; i++) {
                res += TheCartController.myCart.get(i).getResName() + ",";
            }
            res += TheCartController.myCart.get(TheCartController.myCart.size() - 1).getResName();

            // Sending all the order list data to the server
            String address = addtxt.getText();
            String addressWithoutSpaces = address.replace(" ", "");
            // Use loggedInUser instead of logIn and ensure listNumber is defined in ChatClient
            str = ChatClient.loggedInUser.getUserID() + " " + ChatClient.listNumber + " " + res + " " + dateString + " " + reqDate.getValue().toString() + " " +
                  String.format("%.2f", totalPrice) + " " + addressWithoutSpaces + " " + deliveryTypeOutSpaces + " " + "Ready" + " " + "WaitingForApproval" + " " +
                  timeString + " " + reqTimetxt.getText();
            ClientUI.chat.accept(new Message1(MessageType.takeAway, str));
            TheCartController.myCart.clear(); // Clear the cart

            // Open the next page
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Get stage
            PaymentCompletedController AFrame = new PaymentCompletedController();
            try {
                AFrame.start(stage); // Start the payment completed page
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            // Display an alert dialog to inform the user
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all required fields.");
            alert.showAndWait();
        }
    }

    /**
     * Initializes the controller after its root element has been processed.
     *
     * @param location  the location used to resolve relative paths for the root object, or null
     * @param resources the resources used to localize the root object, or null
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        double totalPrice = 0.0; // Change totalPrice to double
        for (int i = 0; i < TheCartController.myCart.size(); i++) {
            totalPrice += TheCartController.myCart.get(i).getTotalPrice(); // Ensure getTotalPrice returns a double
        }
        totalPrice += DeliveryTypeController.deliveryType.getPrice(); // Ensure getPrice returns a double
        totalPricelbl.setText(String.format("%.2f", totalPrice)); // Update total price label
    }

    /**
     * Starts the delivery scene.
     *
     * @param primaryStage the primary stage for this scene
     * @throws Exception if an error occurs while loading the FXML
     */
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(this.getClass().getResource("/gui/Delivery.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Delivery");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
