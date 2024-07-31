package gui;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import client.ChatClient;
import client.ClientUI;
import java.io.IOException;
import client.AccountCreationCommand; // Import the enum

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
    private Label txtmessage;

    @FXML
    private Label newAcctxt;

    @FXML
    private Button btnclose;

    @FXML
    private Button createBtn;

    @FXML
    private Button clrFields;

    // Method to handle the Create button click
    @FXML
    private void handleCreateButton(ActionEvent event) {
        createAccount(event); // Call the createAccount method when the create button is clicked
    }

    // Method to create a new account
    public void createAccount(ActionEvent event) {
        String firstName = Fnametxt.getText();
        String lastName = Lnametxt.getText();
        String phone = Phonetxt.getText();
        String id = IDtxt.getText();
        String email = Emailtxt.getText();
        String creditCard = Credittxt.getText();

        // Check if all fields are filled
        if (firstName.trim().isEmpty() || lastName.trim().isEmpty()
                || phone.trim().isEmpty() || id.trim().isEmpty()
                || email.trim().isEmpty()) {
            updateTextMessage("All fields marked with * are required. Please fill them out to proceed with account creation.");
            newAcctxt.setVisible(false); // Hide the label if not all fields are filled
            return;
        }

        // Create the account on the server
        createAccountOnServer(firstName, lastName, phone, id, email, creditCard);
    }

    private void createAccountOnServer(String firstName, String lastName, String phone, String id, String email, String creditCard) {
        // Prepare the account creation command using enum
        String[] accountDetails = {
            AccountCreationCommand.CREATE_ACCOUNT.getCommand(),
            firstName,
            lastName,
            phone,
            id,
            email,
            creditCard
        };

        // Send the create account request
        ClientUI.chat.accept(accountDetails); // Send command to server

        // Update response message based on server reply
        updateResponseMessage();
    }

    private void updateResponseMessage() {
        // Assuming that ChatClient has a way to store responses
        String createAccountResponse = ChatClient.CheckUserIdResponse; // This variable should be updated based on server response
        System.out.println("Received response from server: " + createAccountResponse); // Debugging line

        // Handle response from the server
        if (createAccountResponse != null) {
            if ("Success: New Account has been created.".equals(createAccountResponse)) {
                updateTextMessage("New Account has been created.");
                newAcctxt.setVisible(true);
            } else {
                updateTextMessage("Failed to create the account. Please try again.");
                newAcctxt.setVisible(false);
            }
        } else {
            updateTextMessage("No response from server. Please check the connection.");
        }
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

    public void updateTextMessage(String message) {
        txtmessage.setText(message);
    }
}
