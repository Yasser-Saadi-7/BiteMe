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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import entities.DeliveryType1;
import entities.Message1;
import entities.MessageType;


/**
 * Controller class for the Delivery Type Page in the GUI application.
 * This class manages the selection of the delivery type for an order,
 * allowing the user to choose from available options and proceed to the next step
 * based on the selection. The FXML file associated with this controller is "DeliveryType.fxml".
 * 
 * 
 * This controller also handles the retrieval and display of available delivery types from the server.
 * 
 * @author Yasser Saadi
 */
public class DeliveryTypeController implements Initializable{
	 /** Observable list of delivery types to be displayed in the table */
	ObservableList<DeliveryType1> deliveryTypes;
	/** Static reference to the selected delivery type */
	public static DeliveryType1 deliveryType;
	 
	/** TableView component for displaying the list of delivery types */
	@FXML
	public  TableView<DeliveryType1> delivertTypeTable;
	/** TableColumn for displaying the delivery type name */
	@FXML
	private TableColumn<DeliveryType1, String> deliveryTypeCol;
	/** TableColumn for displaying the delivery price */
	@FXML
	private TableColumn<DeliveryType1, String> priceCol;
	/**
     * Navigates the user back to the Cart Page.
     * This method is triggered when the "Back" button is clicked.
     * 
     * @param event the ActionEvent triggered when the button is clicked
     */
	@FXML
	void back(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();// get stage
		TheCartController AFrame=new TheCartController();
		try {
			AFrame.start(stage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
     * Proceeds to the next page based on the selected delivery type.
     * If the user selects "Take Away", they are directed to the Take Away page.
     * If they select "Robot Delivery", an error message is shown as this feature is not available.
     * Otherwise, the user is directed to the standard Delivery page.
     * If no delivery type is selected, an error alert is shown.
     * This method is triggered when the "Next" button is clicked.
     * 
     * @param event the ActionEvent triggered when the button is clicked
     */
	@FXML
	void next(ActionEvent event) {
		if(delivertTypeTable.getSelectionModel().getSelectedItem()!=null) {
			if(deliveryType.getDeliveryType().equals(" Take Away")) {
				Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();// get stage
				TakeAwayController AFrame=new TakeAwayController();
				try {
					AFrame.start(stage);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else if(deliveryType.getDeliveryType().equals("Robot Delivery")) {
				Alert a = new Alert(AlertType.ERROR);
				a.setContentText("Error");
				a.setHeaderText("This feature is not working yet.");
				a.showAndWait();
			}
			else {
				Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();// get stage
				DeliveryController AFrame=new DeliveryController();
				try {
					AFrame.start(stage);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}else {
			Alert a = new Alert(AlertType.ERROR);
			a.setContentText("Error");
			a.setHeaderText("should you Select a delivery type:");
			a.showAndWait();
		}
	}
	/**
     * Navigates the user to the Home (Client) Page.
     * This method is triggered when the "Home" button is clicked.
     * 
     * @param event the ActionEvent triggered when the button is clicked
     */
	@FXML
	void home(ActionEvent event) {
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
     * Initializes the controller class.
     * This method is automatically called after the FXML file has been loaded.
     * It sets up the TableView columns and populates the table with available delivery types
     * retrieved from the server.
     * 
     * @param location the location used to resolve relative paths for the root object, or null if the location is not known
     * @param resources the resources used to localize the root object, or null if the root object was not localized
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		deliveryTypeCol.setCellValueFactory(new PropertyValueFactory<DeliveryType1,String>("deliveryType"));
		priceCol.setCellValueFactory(new PropertyValueFactory<DeliveryType1,String>("price"));
		ClientUI.chat.accept(new Message1(MessageType.deliveryType,null));
		deliveryTypes=FXCollections.observableArrayList(ChatClient.deliveryTypeList);
		delivertTypeTable.setItems(deliveryTypes);
		delivertTypeTable.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				deliveryType=delivertTypeTable.getSelectionModel().getSelectedItem();

			}

		});
		
		
	}
	/**
     * Starts the Delivery Type Page.
     * This method is used to load the DeliveryType.fxml file and display the Delivery Type Page to the user.
     * 
     * @param primaryStage the primary stage on which the scene is set
     * @throws Exception if there is any error during loading the FXML file
     */
	public void start(Stage primaryStage) throws Exception {
		  Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("/boundry/DeliveryType.fxml"));
	      Scene scene = new Scene(root);
	      primaryStage.setTitle("Delivery Type");
	      primaryStage.setScene(scene);
	      primaryStage.show();
	   }


}
