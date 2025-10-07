package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class BranchManagerController {

    @FXML
    private Button btnCreateAcc;

    @FXML
    private Button btnViewMR;

    @FXML
    private Button btnclose;

    @FXML
    private void handleCreateAccountButton(ActionEvent event) {
        openFXML("/boundry/CreateAccount.fxml", "Create Account", (Stage) btnCreateAcc.getScene().getWindow());
    }

    @FXML
    private void handleViewMonthlyReportButton(ActionEvent event) {
        openFXML("/boundry/ViewMonthlyReports.fxml", "View Monthly Report", (Stage) btnViewMR.getScene().getWindow());
    }

    @FXML
    private void handleCloseButton(ActionEvent event) {
        // Correct way to "log out" and return to the login screen
        openFXML("/boundry/LogIn.fxml", "Login", (Stage) ((Node) event.getSource()).getScene().getWindow());
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

    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/boundry/BranchManagerPage.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Branch Manager Page");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}