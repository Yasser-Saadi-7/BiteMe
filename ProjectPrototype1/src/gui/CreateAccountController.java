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
import logic.UserType;
import client.ChatClient;
import client.ClientUI;
import javafx.application.Platform;

import java.io.IOException;

// Create Account PAGE
public class CreateAccountController {
	@FXML
	private TextField Fnametxt, Lnametxt, Phonetxt, IDtxt, Emailtxt, Credittxt, usernameTxt, passTxt;

	@FXML
	private Button btnclose, createBtn, clrFields;
	
	@FXML
	private Label txtmessage;
	
	 // Specify user type as an enum
    private UserType userType = UserType.CLIENT; // Default user type
    
    
	// Method to handle the Create button click
	@FXML
	private void handleCreateButton(ActionEvent event) {
		createAccount(); // Call the createAccount method
	}

	// Method to create a new account
	public void createAccount() {
		// Collect data from input fields
		String userID = IDtxt.getText().trim();
		String firstName = Fnametxt.getText().trim();
		String lastName = Lnametxt.getText().trim();
		String email = Emailtxt.getText().trim();
		String phone = Phonetxt.getText().trim();
		String creditCard = Credittxt.getText().trim();
		String username = usernameTxt.getText().trim();
		String password = passTxt.getText().trim();
		String userType = "Client" ;

		// Check if all fields are filled -except credit card which is for private accounts
		if (userID.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phone.isEmpty()
				 || username.isEmpty() || password.isEmpty()) {
			updateTextMessage(
					"All fields marked with * are required. Please fill them out to proceed with account creation.");
			return;
		}

		// Prepare the message string
		String str = String.join(" ", userID, firstName, lastName, email, phone, creditCard, username, password,
				userType);

		// Send message to the server
		ClientUI.chat.accept(new Message1(MessageType.createAccount, str));

		// Create the account on the server in a new thread to avoid blocking UI
		new Thread(() -> createAccountOnServer(userID, firstName, lastName, email, phone, creditCard, userType,
				username, password)).start();
	}

	private void createAccountOnServer(String userID, String firstName, String lastName, String email, String phone,
			String creditCard, String userType, String username, String password) {
		try {
			// Wait for response from the server
			waitForResponse(); // Custom method to wait for server response

			// After the response is received, update the message
			String createAccountResponse = ChatClient.checkUserIdResponse;

			// Update the UI based on the response
			updateResponseMessage(createAccountResponse);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Method to wait for the server response
	private void waitForResponse() {
		// Wait for the response by checking awaitResponse
		long startTime = System.currentTimeMillis();
		long timeout = 5000; // 5 seconds timeout

		while (ChatClient.awaitResponse) {
			if (System.currentTimeMillis() - startTime > timeout) {
				updateTextMessage("Server response timed out. Please try again.");
				return; // Exit if timeout occurs
			}
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
	            switch (createAccountResponse) {
	                case "Account created successfully":
	                    updateTextMessage("New Account has been created.");
	                    break;
	                case "User already exists":
	                    updateTextMessage("This User alreday has an account! Please re-check the user details.");
	                    break;
	                default:
	                    updateTextMessage("Failed to create the account. Please try again.");
	                    break;
	            }
	        } else {
	            updateTextMessage("No response from server. Please check the connection.");
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
