package gui;

import java.io.IOException;

import client.ChatClient;
import client.ClientController;
import client.ClientUI;
import common.ChatIF;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logic.Order;
import java.io.IOException;

public class OrderTrackFrameController1 {

	@FXML
	private Button btnSearch = null;

	@FXML
	private Button btnExit = null;

	@FXML
	private TextField txtordernumber;

	@FXML
	private Label txtmessage;

	private String getOrderNum() {
		return txtordernumber.getText();
	}

	public void Search(ActionEvent event) throws Exception {
		String num;
		num = getOrderNum();
		FXMLLoader loader = new FXMLLoader();
		if (num.trim().isEmpty()) {
			System.out.println("You must enter an order number");
			updateTextMessage("You must enter an order number");
		} else {
			ClientUI.chat.accept(num);
			if (ChatClient.o1.getOrederNumber().equals("Error")) {
				System.out.println("Order Number Not Found");
				updateTextMessage("Order Number Not Found");
			} else {
				System.out.println("Order Number Found");
				((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
				Stage primaryStage = new Stage();
				Pane root = loader.load(getClass().getResource("/gui/OrderInformation1.fxml").openStream());
				OrderInformationFormController1 orderFormController = loader.getController();		
				orderFormController.loadOrder(ChatClient.o1);
				Scene scene = new Scene(root);	
				scene.getStylesheets().add(getClass().getResource("/gui/OrderInformation.css").toExternalForm());
				primaryStage.setTitle("Order Informations");
				primaryStage.setScene(scene);		
				primaryStage.show();
			}

		}

	}

	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/gui/OrderTrack1.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/gui/OrderTrack.css").toExternalForm());
		primaryStage.setTitle("Order Track");
		primaryStage.setScene(scene);
		primaryStage.show();
		//ClientUI.chat.accept("e");
	}

	public void updateTextMessage(String message) {
		txtmessage.setText(message);
	}

	public void getExitBtn(ActionEvent event) throws Exception {
		//ClientUI.chat.accept("exit");
		//System.out.println("dis");
	    //System.exit(0);
		System.out.println("exit orders Tool");
		ClientController.client.quit();
		//System.exit(0);
	}
	
	@FXML
	   void viewOrders(ActionEvent event) {
		  ((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
	      Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	      ViewOrdersController viewOrdersController = new ViewOrdersController();

	      try {
	         viewOrdersController.start(stage);
	      } catch (Exception var5) {
	         var5.printStackTrace();
	      }

	   }


	public void display(String message) {
		System.out.println("message");

	}

}
