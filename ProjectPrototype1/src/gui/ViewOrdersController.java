package gui;

import client.ChatClient;
import client.ClientUI;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import logic.Message1;
import logic.MessageType;
import logic.Order;

public class ViewOrdersController implements Initializable {
   ObservableList<Order> orders;
   @FXML
   private TableView<Order> Orders_tbl;
   @FXML
   private TableColumn<Order, String> res;
   @FXML
   private TableColumn<Order, Integer> ordernum;
   @FXML
   private TableColumn<Order, String> totalprice;
   @FXML
   private TableColumn<Order, String> listnumber;
   @FXML
   private TableColumn<Order, String> address;

   @FXML
   void closepage(ActionEvent event) {
	   ((Node)event.getSource()).getScene().getWindow().hide();
	    OrderTrackFrameController1 orderTrackFrameController= new OrderTrackFrameController1();
		Stage primaryStage = new Stage();
		try {
			orderTrackFrameController.start(primaryStage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

   }

   public void initialize(URL arg0, ResourceBundle arg1) {
	  this.res.setCellValueFactory(new PropertyValueFactory<>("restaurant"));
	  this.ordernum.setCellValueFactory(new PropertyValueFactory<>("orederNumber"));
	  this.listnumber.setCellValueFactory(new PropertyValueFactory<>("listNumber"));
	  this.address.setCellValueFactory(new PropertyValueFactory<>("orderAddress"));
	  this.totalprice.setCellValueFactory(new PropertyValueFactory<>("tprice"));
      //ClientUI.chat.accept("view");
	  ClientUI.chat.accept(new Message1(MessageType.viewOrdersList,"view"));
      this.orders = FXCollections.observableArrayList(ChatClient.list);
      this.Orders_tbl.setItems(this.orders);
   }

   public void start(Stage primaryStage) throws Exception {
      Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("/gui/ViewOrders.fxml"));
      Scene scene = new Scene(root);
      scene.getStylesheets().add(getClass().getResource("/gui/ViewOrder.css").toExternalForm());
      primaryStage.setTitle("All Orders");
      primaryStage.setScene(scene);
      primaryStage.show();
   }
}
