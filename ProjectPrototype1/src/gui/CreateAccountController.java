package gui;

import javafx.fxml.FXML;

import java.io.IOException;
import java.util.ArrayList;
import client.ChatClient;
import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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

    // Method to create a new account
    public void createAccount(ActionEvent event) throws Exception {
        String firstName = Fnametxt.getText();
        String lastName = Lnametxt.getText();
        String phone = Phonetxt.getText();
        String id = IDtxt.getText();
        String email = Emailtxt.getText();
        String creditCard = Credittxt.getText();
        ArrayList<String> arr1 = new ArrayList<>();

        // Check if all fields are filled
        if (firstName.trim().isEmpty() || lastName.trim().isEmpty()
                || phone.trim().isEmpty() || id.trim().isEmpty()
                || email.trim().isEmpty() || creditCard.trim().isEmpty()) {
            updateTextMessage("Please fill ALL Fields");
            newAcctxt.setVisible(false); // Hide the label if not all fields are filled
        } else {
            // Check if the user ID is unique
            if (!isUserIdUnique(id)) {
                updateTextMessage("User ID already exists. Please choose a different ID.");
                newAcctxt.setVisible(false); // Hide the label if the user ID is not unique
                return; // Exit the method early
            }

            // Create account logic
            arr1.add("CreateAccount");
            arr1.add(firstName);
            arr1.add(lastName);
            arr1.add(phone);
            arr1.add(id);
            arr1.add(email);
            arr1.add(creditCard);
            ClientUI.chat.accept(arr1);
            String newAcc = ChatClient.CheckUserIdResponse;
            updateTextMessage("New Account has been created");
            newAcctxt.setVisible(true); // Show the label when the account is created successfully
            newAcctxt.setText("New Account has been created"); // Set success message
        }
    }

    // Method to check if the user ID is unique
    private boolean isUserIdUnique(String userId) {
        ArrayList<String> arr1 = new ArrayList<>();
        arr1.add("CheckUserId");
        arr1.add(userId);
        ClientUI.chat.accept(arr1);

        // Wait for the response (implement your response handling correctly)
        try {
            Thread.sleep(100); // Wait for a short moment for the response to arrive
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Return true if ID is unique
        return "Unique".equals(ChatClient.CheckUserIdResponse);
    }

    // Method to handle Close button click (go back to BranchManagerPage.fxml)
    @FXML
    private void handleCloseButton(ActionEvent event) {
        Stage currentStage = (Stage) btnclose.getScene().getWindow();
        openFXML("BranchManagerPage.fxml", "Branch Manager", currentStage);
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

    private void clearFields() {
        Fnametxt.clear();
        Lnametxt.clear();
        Phonetxt.clear();
        IDtxt.clear();
        Emailtxt.clear();
        Credittxt.clear();
    }

    // Method to handle server disconnection (if needed)
    private void closePage() {
        System.out.println("Server Disconnected!");
        System.exit(0);
    }
}
