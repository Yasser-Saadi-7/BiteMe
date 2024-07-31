package gui;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ClientPageController {
	
	@FXML
	private Button btncreateorder;
	
	@FXML
	private Button btnlogout;
	
	@FXML
	private Button btnvieworderslist;
	
	@FXML
	private Label txtwelcome;
	
	
	@FXML
	void createNewOrder(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		RestaurantController resController = new RestaurantController(); 
		try {
			resController.start(stage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	@FXML
	void viewOrdersList(ActionEvent event) {
		
	}
	
	
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
	      Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("/gui/ClientPage.fxml"));
	      Scene scene = new Scene(root);
	      primaryStage.setTitle("Client Page");
	      primaryStage.setScene(scene);
	      primaryStage.show();
	   }

}
