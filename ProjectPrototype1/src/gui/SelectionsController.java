package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import logic.Message1;
import logic.MessageType;
import logic.Selection;

public class SelectionsController implements Initializable {

    ObservableList<Selection> selections;

    public static Selection selection, yourSelection;
    public static ArrayList<Selection> allAddedSelections = new ArrayList<>();
    private boolean addedDrink;
    public static double totalPrice; // Change to double

    @FXML
    public TableView<Selection> selectionTable;

    @FXML
    private TableColumn<Selection, String> selectionNameCol;

    @FXML
    private TableColumn<Selection, Double> priceCol; // Change to Double

    @FXML
    public TableView<Selection> yourSelectionTable;

    @FXML
    private TableColumn<Selection, String> yourSelectionNameCol;

    @FXML
    private TableColumn<Selection, Double> yourPriceCol; // Change to Double

    @FXML
    void logOut(ActionEvent event) {
        clearSelections();
        navigateTo(new LogInController(), event);
    }

    @FXML
    void back(ActionEvent event) {
        clearSelections();
        navigateTo(new DishesController(), event);
    }

    @FXML
    void home(ActionEvent event) {
        clearSelections();
        navigateTo(new ClientPageController(), event);
    }

    @FXML
    void next(ActionEvent event) {
        totalPrice = DishesController.dish.getPrice(); // Keep it as a double
        for (Selection addedSelection : allAddedSelections) {
            totalPrice += addedSelection.getSelectionPrice(); // Keep it as a double
        }
        navigateTo(new OrderSummaryController(), event);
    }

    @FXML
    void add(ActionEvent event) {
        if (selectionTable.getSelectionModel().getSelectedItem() != null) {
            if (!allAddedSelections.contains(selection)) {
                if (isDrinkSize(selection) && addedDrink) {
                    showAlert("You already chose a drink size.");
                } else {
                    allAddedSelections.add(selection);
                    updateYourSelectionTable();
                    addedDrink = isDrinkSize(selection);
                }
            } else {
                showAlert("This selection has already been added.");
            }
        } else {
            showAlert("Please select a selection.");
        }
    }

    @FXML
    void remove(ActionEvent event) {
        if (yourSelection != null) {
            allAddedSelections.remove(yourSelection);
            updateYourSelectionTable();
            addedDrink = allAddedSelections.stream()
                    .anyMatch(sel -> isDrinkSize(sel));
        }
    }

    private void updateYourSelectionTable() {
        ObservableList<Selection> yourSelections = FXCollections.observableArrayList(allAddedSelections);
        yourSelectionTable.setItems(yourSelections);
    }

    private boolean isDrinkSize(Selection selection) {
        return selection.getSelectionName().equalsIgnoreCase("Small") || 
               selection.getSelectionName().equalsIgnoreCase("Medium") || 
               selection.getSelectionName().equalsIgnoreCase("Big");
    }

    private void showAlert(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setContentText("Error: " + message);
        alert.showAndWait();
    }

    private void navigateTo(Object controller, ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/" + controller.getClass().getSimpleName() + ".fxml"));
            Parent root = loader.load();
            stage.setTitle(controller.getClass().getSimpleName());
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearSelections() {
        allAddedSelections.clear();
    }

    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/Selections.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Meals Type");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addedDrink = false;
        totalPrice = 0.0; // Initialize as double
        
        selectionNameCol.setCellValueFactory(new PropertyValueFactory<>("selectionName"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("selectionPrice")); // This must match the data type
        yourSelectionNameCol.setCellValueFactory(new PropertyValueFactory<>("selectionName"));
        yourPriceCol.setCellValueFactory(new PropertyValueFactory<>("selectionPrice")); // This must match the data type

        ClientUI.chat.accept(new Message1(MessageType.selections, DishesController.dish.getDishId()));
        selections = FXCollections.observableArrayList(ChatClient.selectionsList);
        selectionTable.setItems(selections);
        selectionTable.setOnMousePressed(this::handleSelectionTableMousePress);

        yourSelectionTable.setOnMousePressed(event -> yourSelection = yourSelectionTable.getSelectionModel().getSelectedItem());
    }

    private void handleSelectionTableMousePress(MouseEvent event) {
        selection = selectionTable.getSelectionModel().getSelectedItem();
    }
}
