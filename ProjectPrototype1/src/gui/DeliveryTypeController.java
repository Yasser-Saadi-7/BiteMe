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
import logic.DeliveryType1;
import logic.Message1;
import logic.MessageType;



public class DeliveryTypeController implements Initializable{
	
	ObservableList<DeliveryType1> deliveryTypes;
	
	public static DeliveryType1 deliveryType;
	 
	
	@FXML
	public  TableView<DeliveryType1> delivertTypeTable;

	@FXML
	private TableColumn<DeliveryType1, String> deliveryTypeCol;
	
	@FXML
	private TableColumn<DeliveryType1, Double> priceCol;
	
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
			}else {
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
		deliveryTypeCol.setCellValueFactory(new PropertyValueFactory<DeliveryType1,String>("deliveryType"));
		priceCol.setCellValueFactory(new PropertyValueFactory<DeliveryType1,Double>("price"));
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
	
	public void start(Stage primaryStage) throws Exception {
		  Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("/gui/DeliveryType.fxml"));
	      Scene scene = new Scene(root);
	      primaryStage.setTitle("Delivery Type");
	      primaryStage.setScene(scene);
	      primaryStage.show();
	   }


}
