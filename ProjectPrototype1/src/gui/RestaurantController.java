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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import logic.Message1;
import logic.MessageType;
import logic.Restaurant;

public class RestaurantController implements Initializable{
	
	ObservableList<Restaurant> resturants;
	
	public static Restaurant resturant;
	
	
	@FXML
	public  TableView<Restaurant> resTable;

	@FXML
	private TableColumn<Restaurant, String> restaurantNameCol;


	@FXML
	private TableColumn<Restaurant, String> phoneCol;
	
	@FXML
	private TableColumn<Restaurant, String> locationCol;
	
	
	@FXML
	private Button btnshowmenu;
	
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
	void back(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();// get stage
		ClientPageController AFrame=new ClientPageController();
		try {
			AFrame.start(stage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	void showMenu(ActionEvent event) {
		if(resTable.getSelectionModel().getSelectedItem()!=null) {
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();// get stage
			MealTypeController AFrame=new MealTypeController();
			try {
				AFrame.start(stage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			Alert a = new Alert(AlertType.ERROR);
			a.setContentText("Error");
			a.setHeaderText("should you Select a resturant:");
			a.showAndWait();
		}
		
		
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		restaurantNameCol.setCellValueFactory(new PropertyValueFactory<Restaurant,String>("resName"));
		phoneCol.setCellValueFactory(new PropertyValueFactory<Restaurant,String>("resPhoneNum"));
		locationCol.setCellValueFactory(new PropertyValueFactory<Restaurant,String>("resLocation"));
		ClientUI.chat.accept(new Message1(MessageType.showRestaurant,null));
		resturants=FXCollections.observableArrayList(ChatClient.resList);
		resTable.setItems(resturants);
		resTable.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				resturant=resTable.getSelectionModel().getSelectedItem();

			}

		});
		
		
	}
	
	
	public void start(Stage primaryStage) throws Exception {
		  Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("/gui/Restaurant.fxml"));
	      Scene scene = new Scene(root);
	      primaryStage.setTitle("Restaurant");
	      primaryStage.setScene(scene);
	      primaryStage.show();
	   }

}
