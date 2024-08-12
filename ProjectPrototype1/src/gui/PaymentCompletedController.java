package gui;

import java.net.URL;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import logic.Message1;
import logic.MessageType;

public class PaymentCompletedController implements Initializable{
	
	@FXML
    private Label orderNumberlbl;

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
	
	public void start(Stage primaryStage) throws Exception {
		  Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("/gui/PaymentCompleted.fxml"));
	      Scene scene = new Scene(root);
	      primaryStage.setTitle("Payment Completed");
	      primaryStage.setScene(scene);
	      primaryStage.show();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		orderNumberlbl.setText(ChatClient.listNumber);
		int listNum=Integer.parseInt(ChatClient.listNumber)+1;
		ClientUI.chat.accept(new Message1(MessageType.setListNumber,Integer.toString(listNum)));
		
	}


}
