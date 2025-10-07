package controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class CEOPageController {
	@FXML
	private Button btnViewMR; // Button to view monthly reports

	@FXML
	private Button btnclose; // Button to close the application

	/**
	 * Logs the user out and navigates them back to the login page. This method is
	 * triggered when the "Log Out" button is clicked.
	 * 
	 * @param event the ActionEvent triggered when the button is clicked
	 */
	@FXML
	void logOut(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		LogInController logInController = new LogInController();
		try {
			logInController.start(stage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void viewMonthlyReports(ActionEvent event) {
		Stage currentStage = (Stage) btnViewMR.getScene().getWindow(); // Get current stage
		openFXML("/boundry/ViewMonthlyReportsCEO.fxml", "CEO View Monthly Report", currentStage); // Pass the current stage
	}

	@FXML
	void viewQuartlyReports(ActionEvent event) {

	}

	/**
	 * Opens a new FXML file in a new stage (window).
	 *
	 * @param fxmlFile     the path to the FXML file to load
	 * @param title        the title for the new stage
	 * @param currentStage the current stage to be closed after opening the new
	 *                     stage
	 * 
	 *                     This method attempts to load the specified FXML file and
	 *                     create a new stage with the loaded scene. If successful,
	 *                     it shows the new stage and closes the current stage.
	 */
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
			e.printStackTrace(); // Print the stack trace if loading fails
		}
	}
//	/**
//     * Starts the Dishes Page.
//     * This method is used to load the Dishes.fxml file and display the Dishes Page to the user.
//     * 
//     * @param primaryStage the primary stage on which the scene is set
//     * @throws Exception if there is any error during loading the FXML file
//     */
	public void start(Stage primaryStage) throws Exception {
		  Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("/boundry/CEOPage.fxml"));
	      Scene scene = new Scene(root);
	      primaryStage.setTitle("CEO Page");
	      primaryStage.setScene(scene);
	      primaryStage.show();
	}

}
