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
import logic.Message1;
import logic.MessageType;
import logic.OrderList;
import logic.Restaurant;


public class ViewOrderListController implements Initializable{
	
	ObservableList<OrderList> orderList;
	
	public static OrderList norderInOrderList;
	
	@FXML
    private TableColumn<OrderList, String> addressCol;

    @FXML
    private TableColumn<OrderList, String> approvalCol;

    @FXML
    private TableColumn<OrderList, String> arrivalCol;

    @FXML
    private TableColumn<OrderList, String> deliveryCol;

    @FXML
    private TableColumn<OrderList, String> orderLishNumCol;

    @FXML
    private TableView<OrderList> orderListTable;

    @FXML
    private TableColumn<OrderList, Double> priceCol;

    @FXML
    private TableColumn<OrderList, String> resCol;

    @FXML
    private TableColumn<OrderList, String> statusCol;

    @FXML
    private TableColumn<OrderList, String> timeCol;

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
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	orderLishNumCol.setCellValueFactory(new PropertyValueFactory<OrderList,String>("orderListNum"));
    	addressCol.setCellValueFactory(new PropertyValueFactory<OrderList,String>("address"));
    	approvalCol.setCellValueFactory(new PropertyValueFactory<OrderList,String>("approval"));
    	arrivalCol.setCellValueFactory(new PropertyValueFactory<OrderList,String>("arrivalTime"));
    	deliveryCol.setCellValueFactory(new PropertyValueFactory<OrderList,String>("delivery"));
    	priceCol.setCellValueFactory(new PropertyValueFactory<OrderList,Double>("price"));
    	resCol.setCellValueFactory(new PropertyValueFactory<OrderList,String>("res"));
    	statusCol.setCellValueFactory(new PropertyValueFactory<OrderList,String>("status"));
    	timeCol.setCellValueFactory(new PropertyValueFactory<OrderList,String>("time"));
    	ClientUI.chat.accept(new Message1(MessageType.orderList, ChatClient.loggedInUser.getUserID()));
		orderList=FXCollections.observableArrayList(ChatClient.orderList);
		orderListTable.setItems(orderList);
		orderListTable.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				norderInOrderList=orderListTable.getSelectionModel().getSelectedItem();

			}

		});
		
		
	}
    
    public void start(Stage primaryStage) throws Exception {
	      Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("/gui/ViewOrderList.fxml"));
	      Scene scene = new Scene(root);
	      primaryStage.setTitle("View OrderL ist");
	      primaryStage.setScene(scene);
	      primaryStage.show();
	   }
    
    

}
