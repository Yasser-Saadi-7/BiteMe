package controllers;

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
import entities.Dish;
import entities.Message1;
import entities.MessageType;
import entities.Selection;
/**
 * Controller class for the Selections Page in the GUI application.
 * This class manages the selection of additional options or customizations for a dish,
 * and handles user interactions such as adding and removing selections, navigating to the next page,
 * going back to the previous page, going to the home page, and logging out of the application.
 * The FXML file associated with this controller is "Selections.fxml".
 * 
 * 
 * @author Yasser Saadi
 */
public class SelectionsController implements Initializable{
	/** Observable list of selections to be displayed in the table */
	ObservableList<Selection> selections;
	/** Static reference to the selected selection */
	public static Selection selection,yourSelection;
	/** Static list of all selections added by the user */
	public static ArrayList<Selection> allAddedSelections;
	/** Boolean to track if a drink size has been added */
	private boolean addedDrink;
	/** Static variable to track the total price of the selected dish and additional options */
	public static int totalPrice;
	
	/** TableView component for displaying the list of available selections */
	@FXML
	public  TableView<Selection> selectionTable;
	/** TableColumn for displaying the selection names */
	@FXML
	private TableColumn<Selection, String> selectionNameCol;
	/** TableColumn for displaying the selection prices */
	@FXML
	private TableColumn<Selection, String> priceCol;
	/** TableView component for displaying the user's selected options */
	@FXML
	public  TableView<Selection> yourSelectionTable;
	/** TableColumn for displaying the names of the user's selected options */
	@FXML
	private TableColumn<Selection, String> yourSelectionNameCol;
	/** TableColumn for displaying the prices of the user's selected options */
	@FXML
	private TableColumn<Selection, String> yourPriceCol;
	
	/**
     * Logs the user out, clears the current selections, and navigates back to the login page.
     * This method is triggered when the "Log Out" button is clicked.
     * 
     * @param event the ActionEvent triggered when the button is clicked
     */
	
	@FXML
	void logOut(ActionEvent event) {
		allAddedSelections.clear();
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
     * Navigates the user back to the Dishes Page and clears the current selections.
     * This method is triggered when the "Back" button is clicked.
     * 
     * @param event the ActionEvent triggered when the button is clicked
     */
	@FXML
	void back(ActionEvent event) {
		allAddedSelections.clear();
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();// get stage
		DishesController AFrame=new DishesController();
		try {
			AFrame.start(stage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
     * Navigates the user to the Home (Client) Page and clears the current selections.
     * This method is triggered when the "Home" button is clicked.
     * 
     * @param event the ActionEvent triggered when the button is clicked
     */
	@FXML
	void home(ActionEvent event) {
		allAddedSelections.clear();
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();// get stage
		ClientPageController AFrame=new ClientPageController();
		try {
			AFrame.start(stage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
     * Proceeds to the Order Summary page after calculating the total price for the selected dish and additional options.
     * This method is triggered when the "Next" button is clicked.
     * 
     * @param event the ActionEvent triggered when the button is clicked
     */
	@FXML
	void next(ActionEvent event) {
		totalPrice=Integer.parseInt(DishesController.dish.getPrice());
		for(int i=0;i<allAddedSelections.size();i++) {
			totalPrice+=Integer.parseInt(allAddedSelections.get(i).getSelPrice());
		}
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();// get stage
		OrderSummaryController AFrame=new OrderSummaryController();
		try {
			AFrame.start(stage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
     * Adds the selected option from the selection table to the user's selection list, if it has not already been added.
     * This method also manages drink size selections to ensure only one size is added.
     * An error alert is shown if the user tries to add a duplicate selection or if no selection is made.
     * This method is triggered when the "Add" button is clicked.
     * 
     * @param event the ActionEvent triggered when the button is clicked
     */
	@FXML
	void add(ActionEvent event) {
		if(selectionTable.getSelectionModel().getSelectedItem()!=null) {
			if(!(allAddedSelections.contains(selection))) {
				if((selection.getSelName().equals("Small")||selection.getSelName().equals("Big")||selection.getSelName().equals("Medium"))&& addedDrink==true) {
					Alert a = new Alert(AlertType.ERROR);
					a.setContentText("Error");
					a.setHeaderText("You already choosed a drink size:");
					a.showAndWait();
				}else {
					allAddedSelections.add(selection);
					ObservableList<Selection> yourSelections=FXCollections.observableArrayList(allAddedSelections);
					yourSelectionTable.setItems(yourSelections);
					addedDrink=true;
				}
				
			}else {
				Alert a = new Alert(AlertType.ERROR);
				a.setContentText("Error");
				a.setHeaderText("This selection already added:");
				a.showAndWait();
			}
		}else {
			Alert a = new Alert(AlertType.ERROR);
			a.setContentText("Error");
			a.setHeaderText("should you Select a selection:");
			a.showAndWait();
		}
		
	}
	/**
     * Removes the selected option from the user's selection list.
     * If the removed option is a drink size, the boolean tracking drink size selections is reset.
     * An error alert is shown if no selection is made.
     * This method is triggered when the "Remove" button is clicked.
     * 
     * @param event the ActionEvent triggered when the button is clicked
     */
	@FXML
	void remove(ActionEvent event) {
		if(yourSelectionTable.getSelectionModel().getSelectedItem()!=null) {
			addedDrink=false;
			for(int i=0;i<allAddedSelections.size();i++) {
				if(allAddedSelections.get(i).equals(yourSelection)) {
					allAddedSelections.remove(i);
					ObservableList<Selection> yourSelections=FXCollections.observableArrayList(allAddedSelections);
					yourSelectionTable.setItems(yourSelections);
				}else if(allAddedSelections.get(i).getSelName().equals("Small")||allAddedSelections.get(i).getSelName().equals("Big")||allAddedSelections.get(i).getSelName().equals("Medium")) {
					addedDrink=true;
				}
			}
		}else {
			Alert a = new Alert(AlertType.ERROR);
			a.setContentText("Error");
			a.setHeaderText("should you Select a selection:");
			a.showAndWait();
		}
		
		
	}
	/**
     * Starts the Selections Page.
     * This method is used to load the Selections.fxml file and display the Selections Page to the user.
     * 
     * @param primaryStage the primary stage on which the scene is set
     * @throws Exception if there is any error during loading the FXML file
     */
	public void start(Stage primaryStage) throws Exception {
		  Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("/boundry/Selections.fxml"));
	      Scene scene = new Scene(root);
	      primaryStage.setTitle("Meals Type");
	      primaryStage.setScene(scene);
	      primaryStage.show();
	}
	/**
     * Initializes the controller class.
     * This method is automatically called after the FXML file has been loaded.
     * It sets up the TableView columns, initializes selection tracking variables,
     * and populates the tables with selections retrieved from the server.
     * 
     * @param location the location used to resolve relative paths for the root object, or null if the location is not known
     * @param resources the resources used to localize the root object, or null if the root object was not localized
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		addedDrink=false;
		allAddedSelections = new ArrayList<Selection>();
		totalPrice=0;
		selectionNameCol.setCellValueFactory(new PropertyValueFactory<Selection,String>("selName"));
		priceCol.setCellValueFactory(new PropertyValueFactory<Selection,String>("selPrice"));
		yourSelectionNameCol.setCellValueFactory(new PropertyValueFactory<Selection,String>("selName"));
		yourPriceCol.setCellValueFactory(new PropertyValueFactory<Selection,String>("selPrice"));
		ClientUI.chat.accept(new Message1(MessageType.selections,DishesController.dish.getDishId()));
		selections=FXCollections.observableArrayList(ChatClient.selectionsList);
		selectionTable.setItems(selections);
		selectionTable.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				selection=selectionTable.getSelectionModel().getSelectedItem();

			}

		});
		
		yourSelectionTable.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				yourSelection=yourSelectionTable.getSelectionModel().getSelectedItem();

			}

		});
		
		
	}
	
	

}
