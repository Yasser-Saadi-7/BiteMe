package gui;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import logic.Message1;
import logic.MessageType;
import client.ChatClient;
import client.ClientUI;
import client.AccountCreationCommand;
import javafx.application.Platform;

import java.io.IOException;

// Create Account PAGE
public class CreateAccountController {

    @FXML
    private TextField Fnametxt;

    @FXML
    private TextField Lnametxt;

    @FXML
    private TextField Phonetxt;

    @FXML
    private TextField IDtxt;

    @FXML
    private TextField Emailtxt;

    @FXML
    private TextField Credittxt;
    
    @FXML
    private TextField usernameTxt;
    
    @FXML
    private TextField passTxt;

    @FXML
    private Label txtmessage;

    @FXML
    private Label newAcctxt;

    @FXML
    private Button btnclose;

    @FXML
    private Button createBtn;

    @FXML
    private Button clrFields;

    // Method called when the controller is initialized
    @FXML
    public void initialize() {
        newAcctxt.setVisible(false); // Initially set newAcctxt to be invisible
    }

    // Method to handle the Create button click
    @FXML
    private void handleCreateButton(ActionEvent event) {
        //createAccount(); // Call the createAccount method when the create button is clicked
    	String userID = IDtxt.getText();
        String firstName = Fnametxt.getText();
        String lastName = Lnametxt.getText();
        String email = Emailtxt.getText();
        String phone = Phonetxt.getText();
        String creditCard = Credittxt.getText();
        String username = usernameTxt.getText();
        String password = passTxt.getText();
        String userType = "Client";
        String str =userID+" "+firstName+" "+lastName+" "+email+" "+phone+" "+creditCard+" "+userType+" "+username+" "+password;
        ClientUI.chat.accept(new Message1(MessageType.createAccount, str));
    }

    // Method to create a new account
    public void createAccount() {
        String userID = IDtxt.getText();
        String firstName = Fnametxt.getText();
        String lastName = Lnametxt.getText();
        String email = Emailtxt.getText();
        String phone = Phonetxt.getText();
        String creditCard = Credittxt.getText();
        String username = usernameTxt.getText();
        String password = passTxt.getText();
        String userType = "Client";
        String str =userID+" "+firstName+" "+lastName+" "+email+" "+phone+" "+creditCard+" "+username+" "+password+" "+userType;

        // Print out the values for debugging
        System.out.println("Creating account with the following details:");
        System.out.println("User ID: " + userID);
        System.out.println("First Name: " + firstName);
        System.out.println("Last Name: " + lastName);
        System.out.println("Email: " + email);
        System.out.println("Phone: " + phone);
        System.out.println("Credit Card: " + creditCard);
        System.out.println("User Type: " + userType);
        System.out.println("username: " + username);
        System.out.println("password: " + password);

        // Check if all fields are filled
        if (firstName.trim().isEmpty() || lastName.trim().isEmpty()
                || phone.trim().isEmpty() || userID.trim().isEmpty()
                || email.trim().isEmpty()|| username.trim().isEmpty() 
                || password.trim().isEmpty()) {
            updateTextMessage("All fields marked with * are required. Please fill them out to proceed with account creation.");
            newAcctxt.setVisible(false); // Ensure the label is hidden if fields are empty
            return;
        }

        // Create the account on the server in a new thread to avoid blocking UI
        new Thread(() -> createAccountOnServer(userID, firstName, lastName, email, phone, creditCard, userType, username, password)).start();
    }

    private void createAccountOnServer(String userID, String firstName, String lastName, String email, 
    		String phone, String creditCard, String userType, String username, String password) {
        try {
            // Prepare the account creation command
            String[] accountDetails = {
                    AccountCreationCommand.CREATE_ACCOUNT.getCommand(),
                    userID, firstName, lastName, phone, userID, email, creditCard, userType, username, password
            };

            // Send the create account request
            //ClientUI.chat.accept(new Message1(MessageType.createAccount, str)); // Send as Message1
            
            // Wait for response from the server
            waitForResponse(); // Custom method to wait for server response

            // After the response is received, update the message
            String createAccountResponse = ChatClient.CheckUserIdResponse;

            // Update the UI based on the response
            updateResponseMessage(createAccountResponse);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to wait for the server response
    private void waitForResponse() {
        // Wait for the response by checking awaitResponse
        while (ChatClient.awaitResponse) {
            try {
                Thread.sleep(100); // Sleep to prevent busy waiting
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateResponseMessage(String createAccountResponse) {
        // Use Platform.runLater to update the UI on the FX application thread
        Platform.runLater(() -> {
            // Handle response from the server
            if (createAccountResponse != null) {
                if ("Account created successfully".equals(createAccountResponse)) {
                    updateTextMessage("New Account has been created.");
                    newAcctxt.setVisible(true); // Show the label if account creation is successful
                } else {
                    updateTextMessage("Failed to create the account. Please try again.");
                    newAcctxt.setVisible(false); // Hide if account creation fails
                }
            } else {
                updateTextMessage("No response from server. Please check the connection.");
                newAcctxt.setVisible(false); // Hide if no response is received
            }
        });
    }

    public void updateTextMessage(String message) {
        txtmessage.setText(message);
    }

    // Method to handle Close button click (go back to BranchManagerPage.fxml)
    @FXML
    private void handleCloseButton(ActionEvent event) {
        Stage currentStage = (Stage) btnclose.getScene().getWindow();
        openFXML("BranchManagerPage.fxml", "Branch Manager", currentStage);
    }

    // Method to handle server disconnection (if needed)
    private void closePage() {
        System.out.println("Server Disconnected!");
        System.exit(0);
    }

    // Method to handle Clear Fields button click
    @FXML
    private void handleClearFieldsButton(ActionEvent event) {
        clearFields(); // Call the clearFields method
    }

    // Existing clearFields method
    private void clearFields() {
        Fnametxt.clear();
        Lnametxt.clear();
        Phonetxt.clear();
        IDtxt.clear();
        Emailtxt.clear();
        Credittxt.clear();
        usernameTxt.clear();
        passTxt.clear();
        updateTextMessage("Please Fill All Required Fields");
        newAcctxt.setVisible(false); // Hide the account created message
    }

    // Helper method to open FXML files
    private void openFXML(String fxmlFile, String title, Stage currentStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();

            // Create a new stage and scene
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.setTitle(title);

            // Show the new stage
            newStage.show();

            // Close the current stage
            if (currentStage != null) {
                currentStage.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
