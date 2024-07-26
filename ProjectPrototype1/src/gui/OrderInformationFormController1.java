package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ClientUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logic.Message1;
import logic.MessageType;
import logic.Order;

public class OrderInformationFormController1 implements Initializable {
	private Order o;
		
	@FXML
	private TextField txtrestaurant;
	@FXML
	private TextField txtordernumber;
	@FXML
	private TextField txttotalprice;
	@FXML
	private TextField txtorderlistnumber;
	@FXML
	private TextField txtorderaddress;
	
	
	private String getRes() {
		return txtrestaurant.getText();
	}
	private String getOrderNum() {
		return txtordernumber.getText();
	}
	private String getTotalP() {
		return txttotalprice.getText();
	}
	private String getListNum() {
		return txtorderlistnumber.getText();
	}
	private String getAddress() {
		return txtorderaddress.getText();
	}
		
	public void loadOrder(Order o1) {
		this.o=o1;
		this.txtrestaurant.setText(o.getRestaurant());
		this.txtordernumber.setText(o.getOrederNumber());
		this.txttotalprice.setText(o.getTprice());		
		this.txtorderlistnumber.setText(o.getListNumber());
		this.txtorderaddress.setText(o.getOrderAddress());
	}
	
	
	public void closePage(ActionEvent event) throws Exception{
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		OrderTrackFrameController1 orderTrackFrameController= new OrderTrackFrameController1();
		Stage primaryStage = new Stage();
		orderTrackFrameController.start(primaryStage);
	}
	
	public void updateOrder(ActionEvent event) throws Exception{
		String res = txtrestaurant.getText();
		String orderNum = txtordernumber.getText();
		String price = txttotalprice.getText();
	    String listNum = txtorderlistnumber.getText();
	    String address = txtorderaddress.getText();

		// Create a new Order object
		Order newOrder = new Order(orderNum,res,price,address,listNum);
		// Send the order object to the server
				try {
					ClientUI.chat.accept(new Message1(MessageType.updateOrder,newOrder));
				} catch (Exception e) {
					e.printStackTrace();
				}
	}
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {	
		//setFacultyComboBox();		
	}
	
}

