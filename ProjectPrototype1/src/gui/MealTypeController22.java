//package gui;
//
//import java.net.URL;
//import java.util.ResourceBundle;
//
//import client.ChatClient;
//import client.ClientUI;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.fxml.Initializable;
//import javafx.scene.Node;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.Alert;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.control.Alert.AlertType;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.input.MouseEvent;
//import javafx.stage.Stage;
//import logic.MealType;
//import logic.Message1;
//import logic.MessageType;
//import logic.Restaurant;
//
//public class MealTypeController implements Initializable {
//	
//	ObservableList<MealType> mealTypeList;
//	public static MealType mealType;
//
//	@FXML
//	public TableView<MealType> mealTypeTable;
//
//	@FXML
//	private TableColumn<MealType, String> mealTypeCol;
//	
//	@FXML
//	void logOut(ActionEvent event) {
//		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//		LogInController logInController = new LogInController(); 
//		try {
//			logInController.start(stage);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	@FXML
//	void back(ActionEvent event) {
//		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//		RestaurantController AFrame = new RestaurantController();
//		try {
//			AFrame.start(stage);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	@FXML
//	void next(ActionEvent event) {
//		if (mealTypeTable.getSelectionModel().getSelectedItem() != null) {
//			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//			DishesController AFrame = new DishesController();
//			try {
//				AFrame.start(stage);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		} else {
//			Alert a = new Alert(AlertType.ERROR);
//			a.setContentText("Error");
//			a.setHeaderText("You should select a meal type:");
//			a.showAndWait();
//		}
//	}
//	
//	@FXML
//	void home(ActionEvent event) {
//		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//		ClientPageController AFrame = new ClientPageController();
//		try {
//			AFrame.start(stage);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public void start(Stage primaryStage) throws Exception {
//		Parent root = FXMLLoader.load(this.getClass().getResource("/gui/MealType.fxml")); 
//		Scene scene = new Scene(root);
//		primaryStage.setTitle("Meal Type");
//		primaryStage.setScene(scene);
//		primaryStage.show();
//	}
//	
//	@Override
//	public void initialize(URL location, ResourceBundle resources) {
//		mealTypeCol.setCellValueFactory(new PropertyValueFactory<MealType, String>("mealTypeName")); 
//		ClientUI.chat.accept(new Message1(MessageType.mealType, RestaurantController.resturant.getRestaurantName())); 
//		mealTypeList = FXCollections.observableArrayList(ChatClient.mealTypeList); 
//		mealTypeTable.setItems(mealTypeList); 
//		mealTypeTable.setOnMousePressed(new EventHandler<MouseEvent>() {
//			@Override
//			public void handle(MouseEvent arg0) {
//				mealType = mealTypeTable.getSelectionModel().getSelectedItem(); 
//			}
//		});
//	}
//}
package gui;


