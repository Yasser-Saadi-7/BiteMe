package gui;

import java.net.URL;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import logic.Cart;
import logic.MealType;
import logic.Restaurant;

public class OrderSummaryController implements Initializable {

    public static int quantity;
    public static double totalPrice1; // Store total price as double

    @FXML
    private Label resNameLbl;

    @FXML
    private Label mealTypeLbl;

    @FXML
    private Label dishNameLbl;

    @FXML
    private Label totalPriceLbl;

    @FXML
    private TextField qtxt;

    @FXML
    private ListView<String> selectionsViewList;

    @FXML
    void home(ActionEvent event) {
        SelectionsController.allAddedSelections.clear();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Get stage
        ClientPageController AFrame = new ClientPageController();
        try {
            AFrame.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void addToCart(ActionEvent event) {
        String selections = "";
        double totalPrice; // Store the total price as double

        // Construct selections string
        for (int i = 0; i < SelectionsController.allAddedSelections.size() - 1; i++) {
            selections += SelectionsController.allAddedSelections.get(i).getSelectionName() + ",";
        }
        selections += SelectionsController.allAddedSelections.get(SelectionsController.allAddedSelections.size() - 1).getSelectionName();

        // Calculate total price
        totalPrice = Integer.parseInt(qtxt.getText()) * SelectionsController.totalPrice;

        // Create new Cart object
        TheCartController.myCart.add(new Cart(
                RestaurantController.resturant.getRestaurantName(),
                MealTypeController.mealType.getMealTypeName(),
                DishesController.dish.getDishName(),
                selections,
                qtxt.getText(),
                totalPrice // Pass totalPrice as a Double
        ));
        
        SelectionsController.allAddedSelections.clear();
        totalPrice1 = totalPrice; // Store total price
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Get stage
        AddToMyCardMessageController AFrame = new AddToMyCardMessageController();
        try {
            AFrame.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void back(ActionEvent event) {
        SelectionsController.allAddedSelections.clear();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Get stage
        SelectionsController AFrame = new SelectionsController();
        try {
            AFrame.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(this.getClass().getResource("/gui/OrderSummary.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Meals Type");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        qtxt.setText("1");
        resNameLbl.setText(RestaurantController.resturant.getRestaurantName());
        mealTypeLbl.setText(MealTypeController.mealType.getMealTypeName());
        dishNameLbl.setText(DishesController.dish.getDishName());
        totalPriceLbl.setText(String.format("%.2f", SelectionsController.totalPrice)); // Format total price
        for (int i = 0; i < SelectionsController.allAddedSelections.size(); i++) {
            selectionsViewList.getItems().add(SelectionsController.allAddedSelections.get(i).getSelectionName());
        }
    }
}
