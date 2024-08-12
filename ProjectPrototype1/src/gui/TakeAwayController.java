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

public class TakeAwayController implements Initializable {
    public static int orderNum;

    @FXML
    private TextField firstNametxt;

    @FXML
    private TextField lastNametxt;

    @FXML
    private TextField idtxt;

    @FXML
    private TextField reqTimetxt;

    @FXML
    private Label totalPricelbl;

    @FXML
    private DatePicker reqDate;

    @FXML
    void home(ActionEvent event) {
        navigateTo(event, new ClientPageController());
    }

    @FXML
    void back(ActionEvent event) {
        navigateTo(event, new DeliveryTypeController());
    }

    @FXML
    void confirm(ActionEvent event) {
        // Check if all the TextFields are filled
        if (areFieldsValid()) {
            String str, res;
            double totalPrice = calculateTotalPrice();
            res = buildRestaurantNames();

            // Prepare the message to send to the server
            str = createOrderMessage(totalPrice, res);
            ClientUI.chat.accept(new Message1(MessageType.takeAway, str));
            TheCartController.myCart.clear();

            // Open the next page
            navigateTo(event, new PaymentCompletedController());
        } else {
            showAlert("Input Error", "Please fill in all required fields.");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateTotalPriceLabel();
    }

    // Helper Methods
    private boolean areFieldsValid() {
        return !firstNametxt.getText().trim().isEmpty() && 
               !lastNametxt.getText().trim().isEmpty() && 
               !idtxt.getText().trim().isEmpty() && 
               !reqTimetxt.getText().trim().isEmpty() && 
               reqDate.getValue() != null; // Check if DatePicker value is selected
    }

    private double calculateTotalPrice() {
        double totalPrice = 0.0;
        for (int i = 0; i < TheCartController.myCart.size(); i++) {
            totalPrice += TheCartController.myCart.get(i).getTotalPrice();
        }
        totalPrice += DeliveryTypeController.deliveryType.getPrice(); // Add delivery type price
        return totalPrice;
    }

    private String buildRestaurantNames() {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < TheCartController.myCart.size(); i++) {
            res.append(TheCartController.myCart.get(i).getResName());
            if (i < TheCartController.myCart.size() - 1) {
                res.append(",");
            }
        }
        return res.toString();
    }

    private String createOrderMessage(double totalPrice, String res) {
        LocalDate currentDate = LocalDate.now();
        String dateString = currentDate.toString();
        String timeString = getFormattedTime();

        // Access the userID from the loggedInUser object
        return ChatClient.loggedInUser.getUserID() + " " + 
               ChatClient.listNumber + " " + 
               res + " " + 
               dateString + " " + 
               reqDate.getValue().toString() + " " + 
               String.format("%.2f", totalPrice) + " " + 
               "NoAddress" + " " + 
               DeliveryTypeController.deliveryType.getDeliveryType().replace(" ", "") + " " + 
               "Ready" + " " + 
               "WaitingForApproval" + " " + 
               timeString + " " + 
               reqTimetxt.getText();
    }


    private String getFormattedTime() {
        LocalTime currentTime = LocalTime.now().plusHours(2); // Add 2 hours
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return currentTime.format(formatter);
    }

    private void updateTotalPriceLabel() {
        double totalPrice = calculateTotalPrice();
        totalPricelbl.setText(String.format("%.2f", totalPrice)); // Format to 2 decimal places
    }

    private void navigateTo(ActionEvent event, Object controller) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // get stage
        try {
            if (controller instanceof ClientPageController) {
                ((ClientPageController) controller).start(stage);
            } else if (controller instanceof DeliveryTypeController) {
                ((DeliveryTypeController) controller).start(stage);
            } else if (controller instanceof PaymentCompletedController) {
                ((PaymentCompletedController) controller).start(stage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void start(Stage primaryStage) throws Exception {
		  Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("/gui/TakeAway.fxml"));
	      Scene scene = new Scene(root);
	      primaryStage.setTitle("Take Away");
	      primaryStage.setScene(scene);
	      primaryStage.show();
	}

}
