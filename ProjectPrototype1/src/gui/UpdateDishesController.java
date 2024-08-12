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
import logic.Dish;
import logic.MealType;
import logic.Message1;
import logic.MessageType;

public class UpdateDishesController implements Initializable{
	
    ObservableList<Dish> dishes;
	
	public static Dish dish1;
	
	@FXML
	public  TableView<Dish> dishesTable;
	
	@FXML
	private TableColumn<Dish, String> dishNameCol;
	
	@FXML
	private TableColumn<Dish, Double> priceCol;
	
	@FXML
    private TextField dishNametxt;

    @FXML
    private TextField newDishNametxt;
    

    @FXML
    private TextField newDishPricetxt;

    @FXML
    private TextField pricetxt;
    
    private String getNewDishName() {
		return newDishNametxt.getText();
	}
	
	@FXML
	void delete(ActionEvent event) {
		
	}
	
	@FXML
	void updateDishName(ActionEvent event) {

		if(dishesTable.getSelectionModel().getSelectedItem()!=null) {
			String str =dish1.getDishId()+" "+getNewDishName();
			String newDishName=getNewDishName();
			if(!(newDishName.trim().isEmpty())) {
				ObservableList<Dish> currentTableData = dishesTable.getItems();
				String currentDishId = dish1.getDishId();
				for (Dish Dishes : currentTableData) {
					if (Dishes.getDishId().equals(currentDishId)) {
						Dishes.setDishName(newDishNametxt.getText());
						dishesTable.setItems(currentTableData);
						dishesTable.refresh();
						break;
						
					}
				}
			}else {
				Alert a = new Alert(AlertType.ERROR);
				a.setContentText("Error");
				a.setHeaderText("You should enter dish name:");
				a.showAndWait();
			}
		}else {
			Alert a = new Alert(AlertType.ERROR);
			a.setContentText("Error");
			a.setHeaderText("should you Select a dish:");
			a.showAndWait();
		}
		
	}
	
	@FXML
	void UpdateDishPrice(ActionEvent event) {
		
	}
	
	@FXML
	void add(ActionEvent event) {
		
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
	
	@FXML
	void back(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();// get stage
		UpdateMealTypeController AFrame=new UpdateMealTypeController();
		try {
			AFrame.start(stage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	
	@FXML
	void next(ActionEvent event) {
		if(dishesTable.getSelectionModel().getSelectedItem()!=null) {
			
		}else {
			Alert a = new Alert(AlertType.ERROR);
			a.setContentText("Error");
			a.setHeaderText("should you Select a meal type:");
			a.showAndWait();
		}

	}
	
	public void start(Stage primaryStage) throws Exception {
		  Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("/gui/UpdateDishes.fxml"));
	      Scene scene = new Scene(root);
	      primaryStage.setTitle("Update Dishes");
	      primaryStage.setScene(scene);
	      primaryStage.show();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		dishNameCol.setCellValueFactory(new PropertyValueFactory<Dish,String>("dishName"));
		priceCol.setCellValueFactory(new PropertyValueFactory<Dish,Double>("price"));
		ClientUI.chat.accept(new Message1(MessageType.dishes,UpdateMealTypeController.mealType1.getMealTypeId()));
		dishes=FXCollections.observableArrayList(ChatClient.dishesList);
		dishesTable.setItems(dishes);
		dishesTable.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				dish1=dishesTable.getSelectionModel().getSelectedItem();

			}

		});
		
		
	}


}
