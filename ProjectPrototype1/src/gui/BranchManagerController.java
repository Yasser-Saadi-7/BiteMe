package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

// Branch Manager PAGE
public class BranchManagerController {

    @FXML
    private Button btnCreateOrder;

    @FXML
    private Button btnCreateAcc;

    @FXML
    private Button btnViewMR;

    @FXML
    private Button btnclose;

    // Method to handle Create Order button click
    @FXML
    private void handleCreateOrderButton(ActionEvent event) {
        Stage currentStage = (Stage) btnCreateOrder.getScene().getWindow(); // Get current stage
        openFXML("CreateOrder.fxml", "Create Order", currentStage); // Pass the current stage
    }

    // Method to handle Create Account button click
    @FXML
    private void handleCreateAccountButton(ActionEvent event) {
        Stage currentStage = (Stage) btnCreateAcc.getScene().getWindow(); // Get current stage
        openFXML("CreateAccount.fxml", "Create Account", currentStage); // Pass the current stage
    }

    // Method to handle View Monthly Report button click
    @FXML
    private void handleViewMonthlyReportButton(ActionEvent event) {
        Stage currentStage = (Stage) btnViewMR.getScene().getWindow(); // Get current stage
        openFXML("ViewMonthlyReport.fxml", "View Monthly Report", currentStage); // Pass the current stage
    }

    // Method to handle Close button click
    @FXML
    private void handleCloseButton(ActionEvent event) {
        Stage stage = (Stage) btnclose.getScene().getWindow();
        stage.close();
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

            // Close the current stage (if it's not null)
            if (currentStage != null) {
                currentStage.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/BranchManagerPage.fxml"));
        Scene scene = new Scene(root);
        // scene.getStylesheets().add(getClass().getResource("/gui/OrderTrack.css").toExternalForm());
        primaryStage.setTitle("Branch Manager Page");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Method to handle server disconnection and close/back button
    private void closePage() {
        System.out.println("Server Disconnected!");
        System.exit(0);
    }
}
