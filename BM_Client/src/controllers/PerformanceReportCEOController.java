package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import client.ChatClient;
import client.ClientUI;
import entities.Dish;
import entities.Message1;
import entities.MessageType;
import entities.PerformanceReport;
import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller for managing the performance report view.
 * This class handles the loading, and displaying of performance reports
 * in the UI.
 */
public class PerformanceReportCEOController extends BaseReportController implements Initializable {
	/** Observable list of dishes to be displayed in the table */
	ObservableList<PerformanceReport> perfReports;
    @FXML
    private Button btnclose;
    
    @FXML
    private TableView<PerformanceReport> performanceTable; // Table to display performance report data
    @FXML
    private TableColumn<PerformanceReport, String> reportIdCol; // Column for Report ID
    @FXML
    private TableColumn<PerformanceReport, String> branchIdCol; // Column for Branch ID
    @FXML
    private TableColumn<PerformanceReport, String> reportDateCol; // Column for Report Date
    @FXML
    private TableColumn<PerformanceReport, String> totalOrdersCol; // Column for Total Orders
    @FXML
    private TableColumn<PerformanceReport, String> totalRevenueCol; // Column for Total Revenue
    @FXML
    private TableColumn<PerformanceReport, String> avgExpectedDeliveryCol; // Column for Avg Expected Delivery Time
    @FXML
    private TableColumn<PerformanceReport, String> avgActualDeliveryCol; // Column for Avg Actual Delivery Time
    @FXML
    private TableColumn<PerformanceReport, String> onTimeDeliveryRateCol; // Column for On-Time Delivery Rate
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Set up the cell value factories for the TableView columns
        reportIdCol.setCellValueFactory(new PropertyValueFactory<PerformanceReport, String>("reportId"));
        branchIdCol.setCellValueFactory(new PropertyValueFactory<PerformanceReport, String>("branchId"));
        totalOrdersCol.setCellValueFactory(new PropertyValueFactory<PerformanceReport, String>("totalOrders"));
        totalRevenueCol.setCellValueFactory(new PropertyValueFactory<PerformanceReport, String>("totalRevenue"));
        avgExpectedDeliveryCol.setCellValueFactory(new PropertyValueFactory<PerformanceReport, String>("averageExpectedDeliveryTime"));
        avgActualDeliveryCol.setCellValueFactory(new PropertyValueFactory<PerformanceReport, String>("averageActualDeliveryTime"));
        onTimeDeliveryRateCol.setCellValueFactory(new PropertyValueFactory<PerformanceReport, String>("onTimeDeliveryRate"));
    	perfReports=FXCollections.observableArrayList(ChatClient.performanceReportsList);
    	performanceTable.setItems(perfReports);
          }
//     
    /**
     * Sends a request to the server with the specified message type and data.
     *
     * @param messageType the type of message to send
     * @param requestData the data to send with the request
     * @param updateAction the action to perform upon receiving a response
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
     * @return true if the response is received, false if a timeout occurred
     */
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

    /**
     * Updates the performance table with the received report data.
     */
    private void updatePerformanceTableData() {
        ArrayList<PerformanceReport> reports = ChatClient.performanceReportsList;
        updatePerformanceTable(reports);
    }

    /**
     * Updates the performance table with the provided list of performance reports.
     *
     * @param reports the list of performance reports to display
     */
    private void updatePerformanceTable(ArrayList<PerformanceReport> reports) {
        ObservableList<PerformanceReport> reportData = FXCollections.observableArrayList(reports);
        performanceTable.setItems(reportData);
    }

    /**
     * Handles the Close button click event, which navigates back to the ViewMonthlyReports view.
     *
     * @param event the ActionEvent triggered by the button click
     */
    @FXML
    private void handleCloseButton(ActionEvent event) {
        Stage currentStage = (Stage) btnclose.getScene().getWindow();
        openFXML("/boundry/ViewMonthlyReports.fxml", "View Monthly Report", currentStage);
    }

    
  

    /**
     * Displays an alert dialog with the specified title and message.
     *
     * @param title   the title of the alert
     * @param message the message to display in the alert
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Opens a new FXML view.
     *
     * @param fxmlFile   the path to the FXML file to load
     * @param title      the title of the new stage
     * @param currentStage the current stage to close
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