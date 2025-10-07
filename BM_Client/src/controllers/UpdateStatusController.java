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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import entities.Order;
import entities.OrderList;
import entities.OrderListSponser;
import entities.Dish;
import entities.Message1;
import entities.MessageType;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Controller class for the Update Status Page. Manages the display and update
 * of order statuses for the sponsor.
 * 
 * @author Yasser Saadi
 */

public class UpdateStatusController implements Initializable {

	public static OrderListSponser order1;

	ObservableList<OrderListSponser> Orders;

	@FXML
	private TableView<OrderListSponser> OrdersTable;

	@FXML
	private TableColumn<OrderListSponser, String> UserIdCol;

	@FXML
	private TableColumn<OrderListSponser, String> OrderListNumCol;

	@FXML
	private TableColumn<OrderListSponser, String> ResturantCol;

	@FXML
	private TableColumn<OrderListSponser, String> OrderDateCol;

	@FXML
	private TableColumn<OrderListSponser, String> TotalPriceCol;

	@FXML
	private TableColumn<OrderListSponser, String> AddressCol;

	@FXML
	private TableColumn<OrderListSponser, String> DeliveryServCol;

	@FXML
	private TableColumn<OrderListSponser, String> StatusCol;

	@FXML
	private TableColumn<OrderListSponser, String> ApprovalCol;

	@FXML
	private TableColumn<OrderListSponser, String> ArrivalTimeCol;

	@FXML
	private TableColumn<OrderListSponser, String> RequestedTimeCol;

	@FXML
	private Button LogOutbtn;

	@FXML
	private Button Homebtn;

	@FXML
	private TextField expectedTimetxt;

	@FXML
	private Button Approvebtn;

	@FXML
	private Button Readybtn;

	

	private LocalDateTime approvalTime;

	/**
	 * Initializes the Update Status page by setting up the TableView columns and
	 * loading the order data. Also configures the TableView to handle row
	 * selection.
	 *
	 * @param location  The location used to resolve relative paths for the root
	 *                  object, or null if the location is not known.
	 * @param resources The resources used to localize the root object, or null if
	 *                  the resources are not available.
	 */

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		UserIdCol.setCellValueFactory(new PropertyValueFactory<OrderListSponser, String>("customerId"));
		OrderListNumCol.setCellValueFactory(new PropertyValueFactory<OrderListSponser, String>("orderListNum"));
		ResturantCol.setCellValueFactory(new PropertyValueFactory<OrderListSponser, String>("res"));
		OrderDateCol.setCellValueFactory(new PropertyValueFactory<OrderListSponser, String>("time"));
		ArrivalTimeCol.setCellValueFactory(new PropertyValueFactory<OrderListSponser, String>("arrivalTime"));
		StatusCol.setCellValueFactory(new PropertyValueFactory<OrderListSponser, String>("status"));
		ApprovalCol.setCellValueFactory(new PropertyValueFactory<OrderListSponser, String>("approval"));
		AddressCol.setCellValueFactory(new PropertyValueFactory<OrderListSponser, String>("address"));
		DeliveryServCol.setCellValueFactory(new PropertyValueFactory<OrderListSponser, String>("delivery"));
		TotalPriceCol.setCellValueFactory(new PropertyValueFactory<OrderListSponser, String>("price"));
		RequestedTimeCol.setCellValueFactory(new PropertyValueFactory<OrderListSponser, String>("exTime"));
		ClientUI.chat.accept(new Message1(MessageType.OrderList, ChatClient.logIn.getHomeBranch()));

		Orders = FXCollections.observableArrayList(ChatClient.orderList1);
		OrdersTable.setItems(Orders);
		OrdersTable.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				order1 = OrdersTable.getSelectionModel().getSelectedItem();

			}

		});
	}

	/**
	 * Handles the action event when the user clicks on the "Approve" button.
	 * Updates the selected order's approval status and arrival time.
	 *
	 * @param event The ActionEvent that triggered this method.
	 */
	@FXML
	void Approve(ActionEvent event) {
		if (OrdersTable.getSelectionModel().getSelectedItem() != null
				&& !(expectedTimetxt.getText().trim().isEmpty())) {
			ObservableList<OrderListSponser> currentTableData = OrdersTable.getItems();
			String currentOrderNumber = order1.getOrderListNum();
			for (OrderListSponser orderList : currentTableData) {
				if (orderList.getOrderListNum().equals(currentOrderNumber)) {
					orderList.setApproval("Approved");
					orderList.setArrivalTime(expectedTimetxt.getText());
					OrdersTable.setItems(currentTableData);
					OrdersTable.refresh();
					if (!orderList.getApproval().equals("Approval")) {
						ClientUI.chat.accept(new Message1(MessageType.updateOrderApproval,
								currentOrderNumber + " " + expectedTimetxt.getText()));
					} else {
						Alert a = new Alert(AlertType.ERROR);
						a.setContentText("Error");
						a.setHeaderText("Order already approved");
						a.showAndWait();
					}

					break;

				}
			}
		} else {
			Alert a = new Alert(AlertType.ERROR);
			a.setContentText("Error");
			a.setHeaderText("should you Select order or add expected time:");
			a.showAndWait();
		}

	}

	/**
	 * Handles the action event when the user clicks on the "Ready" button. Updates
	 * the selected order's status to "Ready".
	 *
	 * @param event The ActionEvent that triggered this method.
	 */
	@FXML
	void Ready(ActionEvent event) {
		if (OrdersTable.getSelectionModel().getSelectedItem() != null) {
			ObservableList<OrderListSponser> currentTableData = OrdersTable.getItems();
			String currentOrderNumber = order1.getOrderListNum();
			for (OrderListSponser orderList : currentTableData) {
				if (orderList.getOrderListNum().equals(currentOrderNumber)) {
					orderList.setStatus("Ready");
					OrdersTable.setItems(currentTableData);
					OrdersTable.refresh();
					ClientUI.chat.accept(new Message1(MessageType.updateOrderStatus, currentOrderNumber));
					break;

				}
			}

		} else {
			Alert a = new Alert(AlertType.ERROR);
			a.setContentText("Error");
			a.setHeaderText("should you Select order:");
			a.showAndWait();
		}

	}

	/**
	 * Initializes and displays the Update Status page.
	 * 
	 * @param primaryStage The primary stage for this scene.
	 * @throws Exception if the FXML file cannot be loaded.
	 */
	public void start(Stage primaryStage) throws Exception {
		Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("/boundry/UpdateStatus.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Update Status");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * Handles the action event when the user clicks on the "Log Out" button. Opens
	 * the Login page to allow the user to log out and return to the login screen.
	 *
	 * @param event The ActionEvent that triggered this method.
	 */
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

	/**
	 * Handles the action event when the user clicks on the "Home" button. Opens the
	 * Qualified Worker Page.
	 *
	 * @param event The ActionEvent that triggered this method.
	 */
	@FXML
	void home(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();// get stage
		SponserController AFrame = new SponserController();
		try {
			AFrame.start(stage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
