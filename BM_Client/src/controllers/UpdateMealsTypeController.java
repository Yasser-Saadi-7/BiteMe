package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import entities.MealsType;
import entities.Message1;
import entities.MessageType;

/**
 * Controller class for managing and displaying meal types.
 * Allows navigation to update dishes and handles logout functionality.
 * 
 * @author Yasser Saadi
 */
public class UpdateMealsTypeController implements Initializable{
	
    ObservableList<MealsType> mealsType;
	
	public static MealsType mealType1;
	
	@FXML
	public  TableView<MealsType> mealsTypeTable;

	@FXML
	private TableColumn<MealsType, String> mealTypeCol;
	
	
	
	
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
	
	/**
     * Handles the action event when the user clicks the "Next" button.
     * Navigates to the Update Dishes page if a meal type is selected.
     * Shows an error alert if no meal type is selected.
     *
     * @param event The ActionEvent that triggered this method.
     */
	@FXML
	void next(ActionEvent event) {
		if(mealsTypeTable.getSelectionModel().getSelectedItem()!=null) {
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();// get stage
			UpdateDishesController AFrame=new UpdateDishesController();
			try {
				AFrame.start(stage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			Alert a = new Alert(AlertType.ERROR);
			a.setContentText("Error");
			a.setHeaderText("should you Select a meal type:");
			a.showAndWait();
		}
	}
	
	
	 /**
     * Handles the action event when the user clicks the "Home" button.
     * Navigates back to the Qualified Worker Page.
     *
     * @param event The ActionEvent that triggered this method.
     */
	@FXML
	void home(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();// get stage
		QualifiedWorkerPageController AFrame=new QualifiedWorkerPageController();
		try {
			AFrame.start(stage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void start(Stage primaryStage) throws Exception {
		  Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("/boundry/UpdateMealsType.fxml"));
	      Scene scene = new Scene(root);
	      primaryStage.setTitle("Meals Type");
	      primaryStage.setScene(scene);
	      primaryStage.show();
	}
	
	/**
     * Initializes the Update Meals Type page by setting up the TableView columns and loading meal type data.
     *
     * @param location The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the resources are not available.
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		mealTypeCol.setCellValueFactory(new PropertyValueFactory<MealsType,String>("mealTypeName"));
		ClientUI.chat.accept(new Message1(MessageType.mealsType,ChatClient.logIn.getHomeBranch()));
		mealsType=FXCollections.observableArrayList(ChatClient.mealsTypeList);
		mealsTypeTable.setItems(mealsType);
		mealsTypeTable.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				mealType1=mealsTypeTable.getSelectionModel().getSelectedItem();
			}
		});
	}
}
