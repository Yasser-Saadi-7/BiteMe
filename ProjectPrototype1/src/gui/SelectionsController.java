package gui;

import java.net.URL;
import java.util.ArrayList;
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
import logic.Message1;
import logic.MessageType;
import logic.Selection;

public class SelectionsController implements Initializable {
    
    ObservableList<Selection> selections;
    
    public static Selection selection, yourSelection;
    public static ArrayList<Selection> allAddedSelections;
    private boolean addedDrink;
    public static double totalPrice; // Change type to double
    
    @FXML
    public TableView<Selection> selectionTable;
    
    @FXML
    private TableColumn<Selection, String> selectionNameCol;
    
    @FXML
    private TableColumn<Selection, Double> priceCol; // Change type to Double
    
    @FXML
    public TableView<Selection> yourSelectionTable;
    
    @FXML
    private TableColumn<Selection, String> yourSelectionNameCol;
    
    @FXML
    private TableColumn<Selection, Double> yourPriceCol; // Change type to Double
    
    @FXML
    void logOut(ActionEvent event) {
        allAddedSelections.clear();
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
        allAddedSelections.clear();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // get stage
        DishesController AFrame = new DishesController();
        try {
            AFrame.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    void home(ActionEvent event) {
        allAddedSelections.clear();
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
        totalPrice = DishesController.dish.getPrice(); // Assuming getPrice() returns a double
        for (Selection addedSelection : allAddedSelections) {
			totalPrice += addedSelection.getSelPrice(); // Assuming getSelPrice() returns a double
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // get stage
        OrderSummaryController AFrame = new OrderSummaryController();
        try {
            AFrame.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    void add(ActionEvent event) {
        if (selectionTable.getSelectionModel().getSelectedItem() != null) {
            if (!(allAddedSelections.contains(selection))) {
                if ((selection.getSelName().equals("Small") || selection.getSelName().equals("Big") || selection.getSelName().equals("Medium")) && addedDrink) {
                    Alert a = new Alert(AlertType.ERROR);
                    a.setContentText("Error");
                    a.setHeaderText("You already chose a drink size:");
                    a.showAndWait();
                } else {
                    allAddedSelections.add(selection);
                    ObservableList<Selection> yourSelections = FXCollections.observableArrayList(allAddedSelections);
                    yourSelectionTable.setItems(yourSelections);
                    addedDrink = true;
                }
            } else {
                Alert a = new Alert(AlertType.ERROR);
                a.setContentText("Error");
                a.setHeaderText("This selection is already added:");
                a.showAndWait();
            }
        } else {
            Alert a = new Alert(AlertType.ERROR);
            a.setContentText("Error");
            a.setHeaderText("You should select a selection:");
            a.showAndWait();
        }
    }
    
    @FXML
    void remove(ActionEvent event) {
        addedDrink = false;
        for (int i = 0; i < allAddedSelections.size(); i++) {
            if (allAddedSelections.get(i).equals(yourSelection)) {
                allAddedSelections.remove(i);
                ObservableList<Selection> yourSelections = FXCollections.observableArrayList(allAddedSelections);
                yourSelectionTable.setItems(yourSelections);
            } else if (allAddedSelections.get(i).getSelName().equals("Small") || allAddedSelections.get(i).getSelName().equals("Big") || allAddedSelections.get(i).getSelName().equals("Medium")) {
                addedDrink = true;
            }
        }
    }
    
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(this.getClass().getResource("/gui/Selections.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Meals Type");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addedDrink = false;
        allAddedSelections = new ArrayList<Selection>();
        totalPrice = 0.0; // Initialize to zero as a double
        selectionNameCol.setCellValueFactory(new PropertyValueFactory<Selection, String>("selName"));
        priceCol.setCellValueFactory(new PropertyValueFactory<Selection, Double>("selPrice")); // Ensure selPrice is Double
        yourSelectionNameCol.setCellValueFactory(new PropertyValueFactory<Selection, String>("selName"));
        yourPriceCol.setCellValueFactory(new PropertyValueFactory<Selection, Double>("selPrice")); // Ensure selPrice is Double
        ClientUI.chat.accept(new Message1(MessageType.selections, DishesController.dish.getDishId()));
        selections = FXCollections.observableArrayList(ChatClient.selectionsList);
        selectionTable.setItems(selections);
        selectionTable.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent arg0) {
                selection = selectionTable.getSelectionModel().getSelectedItem();
            }
        });
        
        yourSelectionTable.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent arg0) {
                yourSelection = yourSelectionTable.getSelectionModel().getSelectedItem();
            }
        });
    }
}
