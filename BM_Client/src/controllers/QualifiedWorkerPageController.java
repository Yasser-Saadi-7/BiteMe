package controllers;



import org.kordamp.ikonli.javafx.FontIcon;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Controller class for the Qualified Worker Page. Handles actions triggered
 * from the user interface on the Qualified Worker Page.
 * 
 * @author Yasser Saadi
 */

public class QualifiedWorkerPageController {

	/**
	 * Handles the action event when the user clicks on the "Edit Menu" button.
	 * Opens the Update Meals Type page.
	 * 
	 * @param event The ActionEvent that triggered this method.
	 */
	@FXML
	private Button editMenuBtn;

	@FXML
	private Button logOutBtn;

	public void initialize() {
	    
	}
	@FXML
	void editMenu(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		UpdateMealsTypeController aFrame = new UpdateMealsTypeController();
		try {
			aFrame.start(stage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Handles the action event when the user clicks on the "My Cart" button. Opens
	 * the Cart page where the user can view their cart.
	 * 
	 * @param event The ActionEvent that triggered this method.
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

	public void start(Stage primaryStage) throws Exception {
		Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("/boundry/QualifiedWorkerPage.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Qualified Worker Page");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
