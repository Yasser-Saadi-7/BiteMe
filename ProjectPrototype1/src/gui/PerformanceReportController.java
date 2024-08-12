package gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import logic.Message1;
import logic.MessageType;
import logic.PerformanceReport;
import client.ChatClient;
import client.ClientUI;
import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PerformanceReportController extends BaseReportController {

    @FXML
    private Button btnClose, btnSearch;
    @FXML
    private Label branchLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private TableView<PerformanceReport> performanceTable; // Custom class to hold report data
    @FXML
    private TextField searchField; // TextField for searching by Report ID

    @FXML
    private void initialize() {
        branchLabel.setText("Branch: " + branch);
        dateLabel.setText("Report Date: " + month + " " + year);

        // Load performance report data
        loadPerformanceReportData();
    }

    private void loadPerformanceReportData() {
        // Construct the request data string
        String requestData = String.join(" ", "Performance", String.valueOf(managerId), String.valueOf(branch), month, year);
        sendRequest(MessageType.requestPerformanceReport, requestData, this::updatePerformanceTableData);
    }

    // Generic method to send requests and handle responses
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

    // Method to wait for the server response
    private boolean waitForResponse() {
        long startTime = System.currentTimeMillis();
        long timeout = 5000; // 5 seconds timeout

        while (ChatClient.awaitResponse) {
            if (System.currentTimeMillis() - startTime > timeout) {
                Platform.runLater(() -> {
                    updatePerformanceTable(new ArrayList<>()); // Clear table on timeout
                    showAlert("Timeout", "Server response timed out. Please try again.");
                });
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

    // Update the performance table with received data
    private void updatePerformanceTableData() {
        ArrayList<PerformanceReport> reports = ChatClient.performanceReportsList;
        updatePerformanceTable(reports);
    }

    private void updatePerformanceTable(List<PerformanceReport> reports) {
        ObservableList<PerformanceReport> reportData = FXCollections.observableArrayList(reports);
        performanceTable.setItems(reportData);
    }

    // Method to handle Close button click (go back to ViewMonthlyReports.fxml)
    @FXML
    private void handleCloseButton(ActionEvent event) {
        Stage currentStage = (Stage) btnClose.getScene().getWindow();
        openFXML("/gui/ViewMonthlyReports.fxml", "View Monthly Report", currentStage);
    }

    // Method to handle Search button click
    @FXML
    private void handleSearch(ActionEvent event) {
        String reportId = searchField.getText().trim();
        if (!reportId.isEmpty()) {
            searchReportById(reportId);
        } else {
            showAlert("Invalid Input", "Please enter a Report ID to search.");
        }
    }

    // Method to search for a report by ID
    private void searchReportById(String reportId) {
        String requestData = String.join(" ", "SearchPerformanceReport", reportId);
        sendRequest(MessageType.requestSearchPerformanceReport, requestData, this::updateSearchResult);
    }

    // Update the performance table based on search results
    private void updateSearchResult() {
        PerformanceReport report = ChatClient.searchedPerformanceReport;
        if (report != null) {
            ObservableList<PerformanceReport> reportData = FXCollections.observableArrayList(report);
            performanceTable.setItems(reportData);
        } else {
            showAlert("Not Found", "No report found with the provided ID.");
        }
    }

    // Method to show an alert dialog
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Helper method to open FXML files
    private void openFXML(String fxmlFile, String title, Stage currentStage) {
        try {
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
            e.printStackTrace();
            showAlert("Error", "Failed to open the report page. Please try again.");
        }
    }
}
