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
import entities.Dish;
import entities.Message1;
import entities.MessageType;
import entities.OrderList;
import entities.Restaurant;

/**
 * Controller class for the View Order List Page in the GUI application.
 * This class manages the display and interaction with the user's order list, allowing
 * them to view details of their orders and accept orders that are ready. The FXML file
 * associated with this controller is "ViewOrderList.fxml".
 * 
 * @author Yasser Saadi
 */
public class ViewOrderListController implements Initializable{
	/** Observable list to hold the user's order list */
	ObservableList<OrderList> orderList;
	/** Static reference to the currently selected order in the order list */
	public static OrderList norderInOrderList;
	/** TableColumn for displaying the address associated with the order */
	@FXML
    private TableColumn<OrderList, String> addressCol;
	/** TableColumn for displaying the approval status of the order */
    @FXML
    private TableColumn<OrderList, String> approvalCol;
    /** TableColumn for displaying the arrival time of the order */
    @FXML
    private TableColumn<OrderList, String> arrivalCol;
    /** TableColumn for displaying the delivery service used for the order */
    @FXML
    private TableColumn<OrderList, String> deliveryCol;
    /** TableColumn for displaying the order list number */
    @FXML
    private TableColumn<OrderList, String> orderLishNumCol;
    /** TableView to display the list of orders */
    @FXML
    private TableView<OrderList> orderListTable;
    /** TableColumn for displaying the price of the order */
    @FXML
    private TableColumn<OrderList, String> priceCol;
    /** TableColumn for displaying the restaurant name associated with the order */
    @FXML
    private TableColumn<OrderList, String> resCol;
    /** TableColumn for displaying the status of the order */
    @FXML
    private TableColumn<OrderList, String> statusCol;
    /** TableColumn for displaying the time of the order */
    @FXML
    private TableColumn<OrderList, String> timeCol;
    /**
     * Accepts the selected order and updates its status to "Take It".
     * If no order is selected, an error alert is shown.
     * This method is triggered when the "Accept Order" button is clicked.
     * 
     * @param event the ActionEvent triggered when the button is clicked
     */
    @FXML
    void acceptOrder(ActionEvent event) {
    	if(orderListTable.getSelectionModel().getSelectedItem()!=null) {
    		ObservableList<OrderList> currentTableData = orderListTable.getItems();
			String currentOrderNumber = norderInOrderList.getOrderListNum();
			for (OrderList orderList : currentTableData) {
				if (orderList.getOrderListNum().equals(currentOrderNumber)) {
					orderList.setStatus("Take It");
					orderListTable.setItems(currentTableData);
					orderListTable.refresh();
					ClientUI.chat.accept(new Message1(MessageType.updateStatus,currentOrderNumber));
					break;
					
				}
			}
    	}else {
    		Alert a = new Alert(AlertType.ERROR);
			a.setContentText("Error");
			a.setHeaderText("should you Select order:");
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
     * It sets up the TableView columns and populates the table with the user's orders
     * retrieved from the server.
     * 
     * @param location the location used to resolve relative paths for the root object, or null if the location is not known
     * @param resources the resources used to localize the root object, or null if the root object was not localized
     */
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	orderLishNumCol.setCellValueFactory(new PropertyValueFactory<OrderList,String>("orderListNum"));
    	addressCol.setCellValueFactory(new PropertyValueFactory<OrderList,String>("address"));
    	approvalCol.setCellValueFactory(new PropertyValueFactory<OrderList,String>("approval"));
    	arrivalCol.setCellValueFactory(new PropertyValueFactory<OrderList,String>("arrivalTime"));
    	deliveryCol.setCellValueFactory(new PropertyValueFactory<OrderList,String>("delivery"));
    	priceCol.setCellValueFactory(new PropertyValueFactory<OrderList,String>("price"));
    	resCol.setCellValueFactory(new PropertyValueFactory<OrderList,String>("res"));
    	statusCol.setCellValueFactory(new PropertyValueFactory<OrderList,String>("status"));
    	timeCol.setCellValueFactory(new PropertyValueFactory<OrderList,String>("time"));
		ClientUI.chat.accept(new Message1(MessageType.orderList,ChatClient.logIn.getUserID()));
		orderList=FXCollections.observableArrayList(ChatClient.orderList);
		orderListTable.setItems(orderList);
		orderListTable.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				norderInOrderList=orderListTable.getSelectionModel().getSelectedItem();

			}

		});
		
		
	}
    /**
     * Starts the View Order List Page.
     * This method is used to load the ViewOrderList.fxml file and display the View Order List Page to the user.
     * 
     * @param primaryStage the primary stage on which the scene is set
     * @throws Exception if there is any error during loading the FXML file
     */
    public void start(Stage primaryStage) throws Exception {
	      Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("/boundry/ViewOrderList.fxml"));
	      Scene scene = new Scene(root);
	      primaryStage.setTitle("View OrderL ist");
	      primaryStage.setScene(scene);
	      primaryStage.show();
	   }
    
    

}
