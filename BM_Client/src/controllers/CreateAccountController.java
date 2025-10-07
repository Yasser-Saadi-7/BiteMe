package controllers;

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
import client.ChatClient;
import client.ClientUI;
import entities.AccountType;
import entities.CreateAccount;
import entities.LogIn;
import entities.Message1;
import entities.MessageType;
import javafx.application.Platform;

import java.io.IOException;

public class CreateAccountController {

	@FXML
	private TextField Fnametxt, Lnametxt, Phonetxt, IDtxt, Emailtxt, Credittxt, usernameTxt, passTxt;

	@FXML
	private Button btnclose, createBtn, clrFields;

	@FXML
	private Label txtmessage;

	@FXML
	private ComboBox<String> userTypecombo;

	private LogIn loggedInUser;

	@FXML
	private void initialize() {
		initializeUserTypeComboBox();
		// This is a placeholder for a more robust solution.
		// Ideally, the client should have a listener for server messages.
		// When a response for "createAccount" arrives, it should call a method here.
		// For now, we assume ChatClient will update a field and we can check it.
	}

	private void initializeUserTypeComboBox() {
		userTypecombo.getItems().addAll("Client", "CEO", "Branch Manager", "Qualified Worker", "Sponsor");
	}

	public void setLoggedInUser(LogIn loggedInUser) {
		this.loggedInUser = loggedInUser;
	}

	@FXML
	private void handleCreateButton(ActionEvent event) {
		createAccount();
	}

	public void createAccount() {
		String userID = IDtxt.getText().trim();
		String firstName = Fnametxt.getText().trim();
		String lastName = Lnametxt.getText().trim();
		String email = Emailtxt.getText().trim();
		String phone = Phonetxt.getText().trim();
		String creditCard = Credittxt.getText().trim();
		String username = usernameTxt.getText().trim();
		String password = passTxt.getText().trim();
		String userType = userTypecombo.getValue();

		if (userID.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phone.isEmpty()
				|| username.isEmpty() || password.isEmpty() || userType == null) {
			updateTextMessage("All fields are required. Please fill them out to proceed.");
			return;
		}

		String homeBranchId = (ChatClient.logIn != null) ? ChatClient.logIn.getHomeBranchId() : "-1";
		String homeBranchName = (ChatClient.logIn != null) ? ChatClient.logIn.getHomeBranch() : "-1";

		AccountType accountType = creditCard.isEmpty() ? AccountType.BUSINESS : AccountType.PRIVATE;

		CreateAccount newAccount = new CreateAccount(userID, firstName, lastName, email, phone, creditCard, userType,
				username, password, homeBranchId, accountType, homeBranchName);

		// Send message to the server
		// The response should be handled by a central listener in ChatClient,
		// which then calls a method like `handleServerResponse` on this controller.
		ClientUI.chat.accept(new Message1(MessageType.createAccount, newAccount));

		// For demonstration, we will simulate waiting and then handling a response.
		// In a real application, a callback mechanism is needed.
		updateTextMessage("Sending creation request to server...");
	}
    
    // This method should be called by the client's message handler when a response arrives.
	public void handleServerResponse(String createAccountResponse) {
		Platform.runLater(() -> {
			if (createAccountResponse != null) {
				switch (createAccountResponse) {
					case "Account created successfully":
						updateTextMessage("New Account has been created successfully.");
						clearFields();
						break;
					case "User already exists":
						updateTextMessage("This User already has an account! Please re-check the user details.");
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
		// Ensure UI updates are on the JavaFX Application Thread
		Platform.runLater(() -> txtmessage.setText(message));
	}

	@FXML
	private void handleCloseButton(ActionEvent event) {
		openFXML("/boundry/BranchManagerPage.fxml", "Branch Manager", (Stage) btnclose.getScene().getWindow());
	}

	@FXML
	private void handleClearFieldsButton(ActionEvent event) {
		clearFields();
	}

	private void clearFields() {
		Fnametxt.clear();
		Lnametxt.clear();
		Phonetxt.clear();
		IDtxt.clear();
		Emailtxt.clear();
		Credittxt.clear();
		usernameTxt.clear();
		passTxt.clear();
		userTypecombo.getSelectionModel().clearSelection();
		updateTextMessage("Please Fill All Required Fields");
	}

	private void openFXML(String fxmlFile, String title, Stage currentStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
			Stage newStage = new Stage();
			newStage.setScene(new Scene(root));
			newStage.setTitle(title);
			newStage.show();
			if (currentStage != null) {
				currentStage.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}