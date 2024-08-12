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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import logic.Cart;
import logic.MealType;
import logic.Message1;
import logic.MessageType;
import logic.Selection;

public class TheCartController implements Initializable{
	
	private static Cart cart;
	
	public static ArrayList<Cart> myCart=new ArrayList<Cart>();
	
	ObservableList<Cart> cartItems;
	
	
	@FXML
	public  TableView<Cart> myCartTable;
	
	@FXML
	private TableColumn<Cart, String> resNameCol;
	
	@FXML
	private TableColumn<Cart, String> MTypeCol;
	
	@FXML
	private TableColumn<Cart, String> dishNameCol;
	
	@FXML
	private TableColumn<Cart, String> selCol;
	
	@FXML
	private TableColumn<Cart, String> QCol;
	
	@FXML
	private TableColumn<Cart, Double> priceCol;
	
	
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
	
	@FXML
	void pay(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();// get stage
		DeliveryTypeController AFrame=new DeliveryTypeController();
		try {
			AFrame.start(stage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@FXML
	void remove(ActionEvent event) {
		if(myCartTable.getSelectionModel().getSelectedItem()!=null) {
			for (int i=0;i<myCart.size();i++) {
				if(myCart.get(i).equals(cart)) {
					myCart.remove(i);
					ObservableList<Cart> newCart=FXCollections.observableArrayList(myCart);
					myCartTable.setItems(newCart);
				}
			}
		}
		
	}
	
	public void start(Stage primaryStage) throws Exception {
		  Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("/gui/TheCart.fxml"));
	      Scene scene = new Scene(root);
	      primaryStage.setTitle("My Cart");
	      primaryStage.setScene(scene);
	      primaryStage.show();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		resNameCol.setCellValueFactory(new PropertyValueFactory<Cart,String>("resName"));
		MTypeCol.setCellValueFactory(new PropertyValueFactory<Cart,String>("mealType"));
		dishNameCol.setCellValueFactory(new PropertyValueFactory<Cart,String>("dishName"));
		selCol.setCellValueFactory(new PropertyValueFactory<Cart,String>("sel"));
		QCol.setCellValueFactory(new PropertyValueFactory<Cart,String>("quantity"));
		priceCol.setCellValueFactory(new PropertyValueFactory<Cart,Double>("totalPrice"));
		cartItems=FXCollections.observableArrayList(myCart);
		myCartTable.setItems(cartItems);
		myCartTable.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				cart=myCartTable.getSelectionModel().getSelectedItem();

			}

		});
		
	}
	

}
