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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import logic.Dish;
import logic.MealType;
import logic.Message1;
import logic.MessageType;

public class DishesController implements Initializable {
    
    ObservableList<Dish> dishes;
    
    public static Dish dish;
    
    @FXML
    public TableView<Dish> dishesTable;
    
    @FXML
    private TableColumn<Dish, String> dishNameCol;
    
    @FXML
    private TableColumn<Dish, Double> priceCol; // Change type to Double
    
    @FXML
    void logOut(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        LogInController logInController = new LogInController(); 
        try {
            logInController.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    void back(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // get stage
        MealTypeController AFrame = new MealTypeController();
        try {
            AFrame.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    void home(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // get stage
        ClientPageController AFrame = new ClientPageController();
        try {
            AFrame.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    void next(ActionEvent event) {
        if (dishesTable.getSelectionModel().getSelectedItem() != null) {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // get stage
            SelectionsController AFrame = new SelectionsController();
            try {
                AFrame.start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Alert a = new Alert(AlertType.ERROR);
            a.setContentText("Error");
            a.setHeaderText("Please select a meal type:");
            a.showAndWait();
        }
    }
    
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(this.getClass().getResource("/gui/Dishes.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Meals Type");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dishNameCol.setCellValueFactory(new PropertyValueFactory<Dish, String>("dishName"));
        priceCol.setCellValueFactory(new PropertyValueFactory<Dish, Double>("price")); // Ensure price is Double
        ClientUI.chat.accept(new Message1(MessageType.dishes, MealTypeController.mealType.getMealTypeId()));
        dishes = FXCollections.observableArrayList(ChatClient.dishesList);
        dishesTable.setItems(dishes);
        dishesTable.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent arg0) {
                dish = dishesTable.getSelectionModel().getSelectedItem();
            }
        });
    }
}
