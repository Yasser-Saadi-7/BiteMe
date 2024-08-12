package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.application.Platform;
import logic.Branches;
import logic.Message1;
import logic.MessageType;
import client.ChatClient;
import client.ClientUI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import Server.mysqlConnection;

public class ViewMonthlyReportController {

    @FXML
    private ComboBox<String> chooseMocombo, chooseYrcombo;
    
    @FXML
    private ComboBox<Branches> chooseBrcombo; // ComboBox for branches 
    
    @FXML
    private Button viewInRepobtn, viewOrderRepobtn, viewPerfRepobtn, btnclose;

    private int currentManagerId;

    public void setCurrentManagerId(int managerId) {
        this.currentManagerId = managerId;
    }

    @FXML
    private void initialize() {
        populateMonthComboBox();
        populateYearComboBox();
        if (currentManagerId != 0) {
            populateBranches(String.valueOf(currentManagerId)); 
        } else {
            showAlert("Error", "Current Manager ID is not set.");
        }
    }

    private void populateBranches(String managerId) {
        if (managerId == null || managerId.isEmpty()) {
            showAlert("Error", "Manager ID is not valid.");
            return;
        }
        
        ArrayList<Branches> branches = mysqlConnection.getBranchesForManager(managerId);
        
        if (branches == null) { 
            showAlert("Error", "Failed to retrieve branches. Please check your database connection.");
            return;
        }

        chooseBrcombo.getItems().clear(); 
        chooseBrcombo.getItems().addAll(branches);
        
        if (branches.isEmpty()) {
            chooseBrcombo.getItems().add(new Branches(0, "No branches available", ""));
        }
    }


    private void populateMonthComboBox() {
        chooseMocombo.getItems().addAll(
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        );
    }

    private void populateYearComboBox() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int year = 2010; year <= currentYear; year++) {
            chooseYrcombo.getItems().add(String.valueOf(year));
        }
    }

    @FXML
    private void handleViewPerformanceReportButton(ActionEvent event) {
        if (isInputValid()) {
            openReportPage("Performance");
        }
    }

    @FXML
    private void handleViewIncomeReportButton(ActionEvent event) {
        if (isInputValid()) {
            openReportPage("Income");
        }
    }

    @FXML
    private void handleViewOrderReportButton(ActionEvent event) {
        if (isInputValid()) {
            openReportPage("Order");
        }
    }

    private void openReportPage(String reportType) {
        try {
            String fxmlFile = reportType + "Report.fxml"; // Determine the correct FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/" + fxmlFile)); // Ensure the path is correct
            Parent root = loader.load();

            // Get the controller and set the relevant data
            BaseReportController reportController = loader.getController();
            reportController.setManagerId(currentManagerId);
            reportController.setBranch(chooseBrcombo.getValue());
            reportController.setMonth(chooseMocombo.getValue());
            reportController.setYear(chooseYrcombo.getValue());

            // Create a new stage and scene
            Stage reportStage = new Stage();
            reportStage.setScene(new Scene(root));
            reportStage.setTitle(reportType + " Report");

            // Show the new stage
            reportStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to open the report page. Please try again.");
        }
    }

    private boolean isInputValid() {
        if (chooseBrcombo.getValue() == null || chooseMocombo.getValue() == null || chooseYrcombo.getValue() == null) {
            showAlert("Invalid Input", "Please select a branch, month, and year.");
            return false;
        }
        return true;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleCloseButton(ActionEvent event) {
        Stage currentStage = (Stage) btnclose.getScene().getWindow();
        openFXML("/gui/BranchManagerPage.fxml", "Branch Manager", currentStage);
    }

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
