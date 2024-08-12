package gui;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Optional;

import client.ChatClient;
import client.ClientController;
import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import logic.LogIn;
import logic.Message1;
import logic.MessageType;

public class LogInController {

    @FXML
    private Button btnclose;

    @FXML
    private Button btnlogIn;

    @FXML
    private TextField txtusername;

    @FXML
    private TextField txtpassword;

    @FXML
    private Label txtmessage;

    public void logIn(ActionEvent event) throws Exception {
        ArrayList<String> arr = new ArrayList<>();
        LogIn result;
        if (txtusername.getText().trim().isEmpty() || txtpassword.getText().trim().isEmpty()) {
            updateTextMessage("Please enter username and password");
        } else {
            ClientUI.chat.accept(new Message1(MessageType.logIn, txtusername.getText() + " " + txtpassword.getText()));
            result = ChatClient.logIn;

            // Check user type and navigate to the appropriate view
            if (result.getUserType().equals("Branch Manager")) {
                openMonthlyReportPage(event, result.getUserID()); //getUserID() gets the manager ID
            } else if (result.getUserType().equals("CEO")) {
                // Handle CEO view
            } else if (result.getUserType().equals("Sponsor")) {
                // Handle Sponsor view
            } else if (result.getUserType().equals("Client")) {
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // get stage
                ClientPageController AFrame = new ClientPageController();
                try {
                    AFrame.start(stage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void openMonthlyReportPage(ActionEvent event, String managerId) throws Exception {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // get the current stage
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ViewMonthlyReport.fxml")); // Load the FXML for the report view
        Parent root = loader.load();

        // Get the controller and set the manager ID
        ViewMonthlyReportController reportController = loader.getController();
        reportController.setCurrentManagerId(Integer.parseInt(managerId)); // Assuming managerId is a String representation of an Integer

        // Create a new scene and show it
        stage.setScene(new Scene(root));
        stage.setTitle("Monthly Report");
        stage.show();
    }

    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/LogIn.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("LogIn Page");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void updateTextMessage(String message) {
        txtmessage.setText(message);
    }

    public void closePage(ActionEvent event) throws Exception {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Are you sure you want to exit?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.YES) {
            InetAddress ip = null;
            try {
                ip = InetAddress.getLocalHost();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }

            ClientUI.chat.accept(new Message1(MessageType.disconnect,
                    ip.getHostAddress() + " " + ip.getHostName() + " " + "Disconnected"));
            ClientController.client.quit();
            System.exit(0);
        }
    }
}
