package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import client.ChatClient;
import client.ClientUI;
import entities.Message1;
import entities.MessageType;
import entities.OrderReport;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;

/**
 * Controller for managing the Order Reports view. This class handles loading,
 * and displaying order reports in the UI.
 * 
 * 
 */
public class OrderReportCEOController extends BaseReportController  implements Initializable{

	@FXML
	private Button btnClose; // Buttons for closing reports
	@FXML
	private TableView<OrderReport> orderTable; // Table to display order report data
	@FXML
	private TableColumn<OrderReport, String> reportIdColumn, branchIdColumn, reportDateColumn, restaurantNameColumn,
			itemIdColumn, itemNameColumn, itemCategoryColumn, quantityColumn, itemPriceColumn, totalRevenueColumn; // Table
																													// columns

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	    // Set up the cell value factories for the TableView columns
	    reportIdColumn.setCellValueFactory(new PropertyValueFactory<OrderReport, String>("reportId"));
	    branchIdColumn.setCellValueFactory(new PropertyValueFactory<OrderReport, String>("branchId"));
	    restaurantNameColumn.setCellValueFactory(new PropertyValueFactory<OrderReport, String>("restaurantName"));
	    itemIdColumn.setCellValueFactory(new PropertyValueFactory<OrderReport, String>("itemId"));
	    itemNameColumn.setCellValueFactory(new PropertyValueFactory<OrderReport, String>("itemName"));
	    itemCategoryColumn.setCellValueFactory(new PropertyValueFactory<OrderReport, String>("itemCategory"));
	    quantityColumn.setCellValueFactory(new PropertyValueFactory<OrderReport, String>("quantity"));
	    itemPriceColumn.setCellValueFactory(new PropertyValueFactory<OrderReport, String>("itemPrice"));
	    totalRevenueColumn.setCellValueFactory(new PropertyValueFactory<OrderReport, String>("totalRevenue"));

	    // Populate the TableView with data
	    ObservableList<OrderReport> incomeReports = FXCollections.observableArrayList(ChatClient.orderReportsList);
	    orderTable.setItems(incomeReports);
	}

	/**
	 * Updates the order table with the list of order reports.
	 *
	 * @param reports the list of order reports to display
	 */
	private void updateOrderTable(List<OrderReport> reports) {
		ObservableList<OrderReport> reportData = FXCollections.observableArrayList(reports);
		orderTable.setItems(reportData);
	}

	/**
	 * Handles the Close button action, navigating back to the ViewMonthlyReports
	 * view.
	 *
	 * @param event the ActionEvent triggered by the button click
	 */
	@FXML
	private void handleCloseButton(ActionEvent event) {
		Stage currentStage = (Stage) btnClose.getScene().getWindow();
		openFXML("/boundry/ViewMonthlyReports.fxml", "View Monthly Report", currentStage);
	}

	/**
	 * Sends a request to the server with the specified message type and data.
	 *
	 * @param messageType     the type of message to send
	 * @param requestData     the data to send with the request
	 * @param responseHandler the action to perform upon receiving a response
	 */
	private void sendRequest(MessageType messageType, String requestData,
			Consumer<ArrayList<OrderReport>> responseHandler) {
		Message1 msg = new Message1(messageType, requestData);
		ClientUI.chat.accept(msg); // Send request to server

		// Create a thread to wait for the response
		new Thread(() -> {
			if (waitForResponse()) { // Wait for response
				Platform.runLater(() -> responseHandler.accept((ArrayList<OrderReport>) ChatClient.responseData));
			}
		}).start();
	}

	/**
	 * Waits for a response from the server with a timeout mechanism.
	 *
	 * @return true if the response is received, false if a timeout occurred
	 */
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
					updateOrderTable(new ArrayList<>()); // Clear table on timeout
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
	 * Displays an alert dialog with the specified title and message.
	 *
	 * @param title   the title of the alert
	 * @param message the message to display in the alert
	 */
	private void showAlert(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	/**
	 * Opens a new FXML view and closes the current stage.
	 *
	 * @param fxmlFile     the path to the FXML file to load
	 * @param title        the title of the new stage
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
