package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import entities.BranchManager;
import entities.LogIn;
import entities.Message1;
import entities.MessageType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import client.ChatClient;
import client.ClientUI;

/**
 * Controller for viewing monthly reports related to branches.
 * The logged-in branch manager can see reports for their specific branch.
 */
public class ViewMonthlyReportsCEOController {

    @FXML
    private ComboBox<String> chooseMocombo; // ComboBox for selecting the month
    @FXML
    private ComboBox<String> chooseYrcombo;  // ComboBox for selecting the year
    @FXML
    private Button viewInRepobtn;             // Button to view income report
    @FXML
    private Button viewOrderRepobtn;          // Button to view order report
    @FXML
    private Button viewPerfRepobtn;           // Button to view performance report
    @FXML
    private Button btnclose;                   // Button to close the view

    private String currentManagerId;           // Current logged-in manager ID
    private String managerBranch;              // Branch managed by the current manager
    private ArrayList<LogIn> users;                 // List of users (LogIn objects)

    /**
     * Sets the current manager ID for the controller.
     *
     * @param managerId The ID of the currently logged-in manager.
     */
    public void setCurrentManagerId(String managerId) {
        this.currentManagerId = managerId;
        fetchBranchByManagerId(managerId); // Fetch the branch based on the manager ID
    }

    /**
     * Sets the list of users.
     *
     * @param users The list of users.
     */
    public void setUsers(ArrayList<LogIn> users) {
        this.users = users;
    }

    /**
     * Initializes the controller after its root element has been processed.
     * Populates the month and year combo boxes.
     */
    @FXML
    private void initialize() {
        populateMonthComboBox(); // Populate the month ComboBox
        populateYearComboBox();  // Populate the year ComboBox
    }

    /**
     * Populates the month ComboBox with the names of the months.
     */
    private void populateMonthComboBox() {
        chooseMocombo.getItems().addAll(
            "1", "2", "3", "4", "5", "6",
            "7", "8", "9", "10", "11", "12"
        ); // Populate months
    }

    /**
     * Populates the year ComboBox with the years from 1900 to the current year.
     */
    private void populateYearComboBox() {
        int startYear = 2010; // Starting year
        int currentYear = Calendar.getInstance().get(Calendar.YEAR); // Get the current year
        for (int year = startYear; year <= currentYear; year++) {
            chooseYrcombo.getItems().add(String.valueOf(year)); // Add years to ComboBox
        }
        chooseYrcombo.setVisibleRowCount(currentYear - startYear + 1); // Set the visible row count to display all years
    }

    /**
     * Fetches the branch associated with the given manager ID.
     *
     * @param managerId The ID of the manager.
     */
    private void fetchBranchByManagerId(String managerId) {
        // Find the user with the given manager ID and userType as "Branch Manager"
        for (LogIn user : users) {
            if (user.getUserID().equals(managerId) && "Branch Manager".equals(user.getUserType())) {
                this.managerBranch = user.getHomeBranch(); // Assign the branch to managerBranch
                break;
            }
        }
    }

    /**
     * Handles the action of viewing the performance report.
     *
     * @param event The action event triggered by clicking the button.
     */
    @FXML
    private void handleViewPerformanceReportButton(ActionEvent event) {
        if (isInputValid()) {
        	ClientUI.chat.accept(new Message1(MessageType.getPerformanceReports,
        			ChatClient.logIn.getHomeBranchId() + " " +  chooseMocombo.getValue() + " "
                    + chooseYrcombo.getValue() ));
        	
            openReportPage("Performance"); // Open performance report
        }
    	

    }

    /**
     * Handles the action of viewing the income report.
     *
     * @param event The action event triggered by clicking the button.
     */
    @FXML
    private void handleViewIncomeReportButton(ActionEvent event) {
        if (isInputValid()) {
        	 ClientUI.chat.accept(new Message1(MessageType.getIncomeReports,
         			ChatClient.logIn.getHomeBranchId() + " " +  chooseMocombo.getValue() + " "
                     + chooseYrcombo.getValue() ));
        	 
            openReportPage("Income"); // Open income report
           
        }
    }

    /**
     * Handles the action of viewing the order report.
     *
     * @param event The action event triggered by clicking the button.
     */
    @FXML
    private void handleViewOrderReportButton(ActionEvent event) {
        if (isInputValid()) {
        	 ClientUI.chat.accept(new Message1(MessageType.getOrderReports,
          			ChatClient.logIn.getHomeBranchId() + " " +  chooseMocombo.getValue() + " "
                      + chooseYrcombo.getValue() ));
            openReportPage("Order"); // Open order report
        }
    }

    /**
     * Opens the report page for the specified report type.
     *
     * @param reportType The type of report to open (e.g., Income, Performance, Order).
     */
    private void openReportPage(String reportType) {
        try {
            String fxmlFile = reportType + "ReportCEO.fxml"; // Determine the correct FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/boundry/" + fxmlFile)); // Load the FXML file
            Parent root = loader.load();

            String managerBranchString = String.valueOf(managerBranch);  // Convert branch to string

            // Get the controller and set the relevant data
            BaseReportController reportController = loader.getController();
            reportController.setManagerId(currentManagerId); // Set manager ID in the report controller
            reportController.setBranchName(managerBranchString); // Set manager's branch
            reportController.setMonth(chooseMocombo.getValue()); // Set selected month
            reportController.setYear(chooseYrcombo.getValue()); // Set selected year
           
            // Create a new stage and scene
            Stage reportStage = new Stage();
            reportStage.setScene(new Scene(root)); // Set the scene with the loaded FXML
            reportStage.setTitle(reportType + " Report"); // Set title

            // Show the new stage
            reportStage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Print stack trace on error
            showAlert("Error", "Failed to open the report page. Please try again."); // Alert if report page fails to open
        }
    }

    public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/boundry/ViewMonthlyReportsCEO.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("View Reports CEO Page");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
    
    /**
     * Validates the user input for month and year selection.
     *
     * @return true if the input is valid; false otherwise.
     */
    private boolean isInputValid() {
        // Check if month and year are selected
        if (chooseMocombo.getValue() == null || chooseYrcombo.getValue() == null) {
            showAlert("Invalid Input", "Please select both month and year."); // Alert if input is invalid
            return false;
        }
        return true; // Return true if input is valid
    }

    /**
     * Shows an alert dialog with the specified title and content.
     *
     * @param title   The title of the alert dialog.
     * @param content The content message of the alert dialog.
     */
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING); // Create a warning alert
        alert.setTitle(title); // Set the title
        alert.setHeaderText(null); // No header
        alert.setContentText(content); // Set the content
        alert.showAndWait(); // Show the alert and wait for response
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
    /**
     * Handles the Close button click event, which navigates back to the ViewMonthlyReports view.
     *
     * @param event the ActionEvent triggered by the button click
     */
    @FXML
    private void handleCloseButton(ActionEvent event) {
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // get stage
    	BranchManagerController AFrame = new BranchManagerController();
		try {
			AFrame.start(stage);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
