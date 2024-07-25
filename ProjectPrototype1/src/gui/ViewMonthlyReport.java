package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;

public class ViewMonthlyReport {

    @FXML
    private ComboBox<String> chooseBrcombo;

    @FXML
    private ComboBox<String> btnchooseMcombo;

    @FXML
    private Button viewInRepobtn;

    @FXML
    private Button viewOrderRepobtn;

    @FXML
    private Button viewPerfRepobtn;

    @FXML
    private Button btnclose;

    @FXML
    private void initialize() {
        // Initialize ComboBox items here, if needed
        chooseBrcombo.getItems().addAll("Branch 1", "Branch 2", "Branch 3");
        // Initialize months
        btnchooseMcombo.getItems().addAll(
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December");
    }

    // Method to handle the "View Performance Report" button click
    @FXML
    private void handleViewPerformanceReportButton(ActionEvent event) {
        // Implement your logic to view the performance report
        System.out.println("View Performance Report");
        ViewPerformanceReport();
    }

    // Method to handle the "View Income Report" button click
    @FXML
    private void handleViewIncomeReportButton(ActionEvent event) {
        // Implement your logic to view the income report
        System.out.println("View Income Report");
        ViewIncomeReport();
    }

    // Method to handle the "View Order Report" button click
    @FXML
    private void handleViewOrderReportButton(ActionEvent event) {
        // Implement your logic to view the order report
        System.out.println("View Order Report");
        ViewOrderReport();
    }

    // Method to handle the "Close" button click
    @FXML
    private void handleCloseButton(ActionEvent event) {
        // Close the current window
        Stage stage = (Stage) btnclose.getScene().getWindow();
        stage.close();
    }

    // Placeholder method for viewing performance report
    private void ViewPerformanceReport() {
        // Add your implementation here
        // Example: Load a new FXML file for the performance report
        System.out.println("Performance Report Logic");
    }

    // Placeholder method for viewing income report
    private void ViewIncomeReport() {
        // Add your implementation here
        // Example: Load a new FXML file for the income report
        System.out.println("Income Report Logic");
    }

    // Placeholder method for viewing order report
    private void ViewOrderReport() {
        // Add your implementation here
        // Example: Load a new FXML file for the order report
        System.out.println("Order Report Logic");
    }
    // Method to check if both ComboBoxes have a selected item
    private boolean isInputValid() {
        if (chooseBrcombo.getValue() == null || btnchooseMcombo.getValue() == null) {
            showAlert("Invalid Input", "Please select both a branch and a month.");
            return false;
        }
        return true;
    }

    // Method to show an alert dialog
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

