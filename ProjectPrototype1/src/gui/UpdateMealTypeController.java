package gui;

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
import logic.MealType;
import logic.Message1;
import logic.MessageType;

public class UpdateMealTypeController implements Initializable{
	
    ObservableList<MealType> mealsType;
	
	public static MealType mealType1;
	
	@FXML
	public  TableView<MealType> mealsTypeTable;

	@FXML
	private TableColumn<MealType, String> mealTypeCol;
	
	
	
	
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
		  Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("/gui/UpdateMealsType.fxml"));
	      Scene scene = new Scene(root);
	      primaryStage.setTitle("Meals Type");
	      primaryStage.setScene(scene);
	      primaryStage.show();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		mealTypeCol.setCellValueFactory(new PropertyValueFactory<MealType,String>("mealTypeName"));
		ClientUI.chat.accept(new Message1(MessageType.mealType,"Alena"));
		mealsType=FXCollections.observableArrayList(ChatClient.mealTypeList);
		mealsTypeTable.setItems(mealsType);
		mealsTypeTable.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				mealType1=mealsTypeTable.getSelectionModel().getSelectedItem();

			}

		});
		
		
	}


}
