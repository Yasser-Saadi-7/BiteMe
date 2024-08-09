package gui;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import client.ChatClient;
import client.ClientController;
import client.ClientUI;
import logic.Message1;
import logic.MessageType;
import logic.Order;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class OrderTrackFrameController1 {

    @FXML
    private Button btnSearch = null;

    @FXML
    private Button btnExit = null;

    @FXML
    private TextField txtordernumber;

    @FXML
    private Label txtmessage;

    private String getOrderNum() {
        return txtordernumber.getText();
    }

    public void Search(ActionEvent event) throws Exception {
        String num = getOrderNum();
        FXMLLoader loader = new FXMLLoader();
        
        if (num.trim().isEmpty()) {
            System.out.println("You must enter an order number");
            updateTextMessage("You must enter an order number");
        } else {
            // Sending a search order request to the server
            ClientUI.chat.accept(new Message1(MessageType.searchOrder, num));

            // Check if the order was found using the new structure
            Order foundOrder = ChatClient.o1; // Assuming o1 is now an Order object
            if (foundOrder.getOrderId() == -1) { // Assuming -1 indicates an error
                System.out.println("Order Number Not Found");
                updateTextMessage("Order Number Not Found");
            } else {
                System.out.println("Order Number Found");
                ((Node) event.getSource()).getScene().getWindow().hide(); // Hide primary window
                Stage primaryStage = new Stage();
                
                Pane root = loader.load(getClass().getResource("/gui/OrderInformation1.fxml").openStream());
                OrderInformationFormController1 orderFormController = loader.getController();
                orderFormController.loadOrder(foundOrder); // Load the found order
                Scene scene = new Scene(root);
                scene.getStylesheets().add(getClass().getResource("/gui/OrderInformation.css").toExternalForm());
                primaryStage.setTitle("Order Information");
                primaryStage.setScene(scene);
                primaryStage.show();
            }
        }
    }

    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/OrderTrack1.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/gui/OrderTrack.css").toExternalForm());
        primaryStage.setTitle("Order Track");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void updateTextMessage(String message) {
        txtmessage.setText(message);
    }

    public void getExitBtn(ActionEvent event) throws Exception {
        ClientController.client.quit();
        System.exit(0);
    }

    @FXML
    void viewOrders(ActionEvent event) {
        ((Node) event.getSource()).getScene().getWindow().hide(); // Hide primary window
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        ViewOrdersController viewOrdersController = new ViewOrdersController();

        try {
            viewOrdersController.start(stage);
        } catch (Exception var5) {
            var5.printStackTrace();
        }
    }

    public void display(String message) {
        System.out.println(message);
    }
}
