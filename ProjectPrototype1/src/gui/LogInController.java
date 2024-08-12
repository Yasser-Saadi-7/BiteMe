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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
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
        String result;
        if (txtusername.getText().trim().isEmpty() || txtpassword.getText().trim().isEmpty()) {
            updateTextMessage("Please enter user name or password");
        } else {
            ClientUI.chat.accept(new Message1(MessageType.logIn, txtusername.getText() + " " + txtpassword.getText()));
            result = ChatClient.logIn; // Assuming this returns the user type
            
            switch (result) {
                case "Branch Manager":
                    openManagerController();
                    break;
                case "CEO":
                    openCEOController();
                    break;
                case "Sponser":
                    openSponserController();
                    break;
                case "Qualified Worker":
                    openQualifiedWorkerController();
                    break;
                default:
                    updateTextMessage("Invalid user type or login failed.");
                    break;
            }
        }
    }
    
    private void openManagerController() throws Exception {
        // Load the Manager controller
        loadFXML("/gui/ManagerPage.fxml", "Manager Dashboard");
    }

    private void openCEOController() throws Exception {
        // Load the CEO controller
        loadFXML("/gui/CEOPage.fxml", "CEO Dashboard");
    }

    private void openSponserController() throws Exception {
        // Load the Sponser controller
        loadFXML("/gui/SponserPage.fxml", "Sponser Dashboard");
    }

    private void openQualifiedWorkerController() throws Exception {
        // Load the Qualified Worker controller
        loadFXML("/gui/QualifiedWorkerPage.fxml", "Qualified Worker Dashboard");
    }

    private void loadFXML(String fxmlFile, String title) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
        Stage stage = (Stage) btnlogIn.getScene().getWindow(); // Get the current stage
        stage.setTitle(title);
        stage.setScene(new Scene(root));
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
        Alert alert = new Alert(AlertType.WARNING, "Are you sure you want to exit?", ButtonType.YES, ButtonType.NO);
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
