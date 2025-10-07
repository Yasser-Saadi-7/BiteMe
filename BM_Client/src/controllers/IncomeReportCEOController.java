package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import client.ChatClient;
import client.ClientUI;
import entities.IncomeReport;
import entities.Message1;
import entities.MessageType;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Controller for managing income reports within the application.
 */
public class IncomeReportCEOController extends BaseReportController implements Initializable {

    @FXML
    private Button btnClose;       // Button to close the current view
    
    @FXML
    private TableView<IncomeReport> incomeTable; // TableView to display income reports

    @FXML
    private TableColumn<IncomeReport, String> reportIdCol; // Column for Report ID
    @FXML
    private TableColumn<IncomeReport, String> branchIdCol; // Column for Branch ID
    @FXML
    private TableColumn<IncomeReport, String> monthCol; // Column for Month
    @FXML
    private TableColumn<IncomeReport, String> yearCol; // Column for Year
    @FXML
    private TableColumn<IncomeReport, String> totalIncomeCol; // Column for Total Income

    /** Observable list of income reports to be displayed in the table */
    private ObservableList<IncomeReport> incomeReports;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set up the cell value factories for the TableView columns
        reportIdCol.setCellValueFactory(new PropertyValueFactory<IncomeReport, String>("reportId"));
        branchIdCol.setCellValueFactory(new PropertyValueFactory<IncomeReport, String>("branchId"));
        totalIncomeCol.setCellValueFactory(new PropertyValueFactory<IncomeReport, String>("totalIncome"));

       
        incomeReports = FXCollections.observableArrayList(ChatClient.incomeReportsList);
        incomeTable.setItems(incomeReports);
    }

    /**
     * Sends a request to the server and updates the UI upon receiving a response.
     *
     * @param messageType The type of message to send.
     * @param requestData The data to include in the request.
     * @param updateAction The action to perform upon receiving a response.
     */
    private void sendRequest(MessageType messageType, String requestData, Runnable updateAction) {
        // Create a new message
        Message1 requestMessage = new Message1(messageType, requestData);
        ClientUI.chat.accept(requestMessage); // Send the request

        // Create a thread to wait for the response
        new Thread(() -> {
            if (waitForResponse()) { // Wait for response
                Platform.runLater(updateAction); // Run the update action on the FX application thread
            }
        }).start();
    }

    /**
     * Waits for a response from the server with a timeout mechanism.
     *
     * @return true if a response was received, false if timed out.
     */
    private boolean waitForResponse() {
        long startTime = System.currentTimeMillis();
        long timeout = 5000; // 5 seconds timeout

        while (ChatClient.awaitResponse) {
            if (System.currentTimeMillis() - startTime > timeout) {
                showAlert("Timeout", "Server response timed out. Please try again.");
                return false; // Timeout occurred
            }
            try {
                Thread.sleep(100); // Sleep to prevent busy waiting
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return true; // Response received
    }

    /**
     * Updates the income table with the received report data.
     */
    private void updateIncomeTable() {
        ArrayList<IncomeReport> reports = ChatClient.incomeReportsList;
        ObservableList<IncomeReport> reportData = FXCollections.observableArrayList(reports);
        incomeTable.setItems(reportData);
    }

    /**
     * Handles the Close button click event to navigate back to the previous view.
     *
     * @param event The event triggered by the button click.
     */
    @FXML
    private void handleCloseButton(ActionEvent event) {
        Stage currentStage = (Stage) btnClose.getScene().getWindow();
        openFXML("/boundry/ViewMonthlyReports.fxml", "View Monthly Report", currentStage);
    }

    /**
     * Displays an alert dialog with a specified title and message.
     *
     * @param title   The title of the alert.
     * @param message The message content of the alert.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Opens a specified FXML file and sets it as the current scene.
     *
     * @param fxmlFile The path to the FXML file to open.
     * @param title    The title of the new window.
     * @param currentStage The current stage to close if not null.
     */
	private void openFXML(String fxmlFile, String title, Stage currentStage) {
		try {
			System.out.println("Loading FXML: " + fxmlFile);
			FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
			Parent root = loader.load();

			Stage newStage = new Stage();
			newStage.setScene(new Scene(root));
			newStage.setTitle(title);

			newStage.show();

			if (currentStage != null) {
				currentStage.close();
			}
		} catch (IOException e) {
			System.err.println("Failed to load FXML: " + fxmlFile);
			e.printStackTrace();
		}
	}
}
