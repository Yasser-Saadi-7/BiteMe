package gui;

import client.ClientController;
import client.ClientUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import logic.Message1;
import logic.MessageType;

public class ClientConnectionController implements Initializable {
   @FXML
   private Button connect_btn;
   @FXML
   private TextField ip_txt;

   @FXML
   void connect(ActionEvent event) {
      try {
		ClientUI.chat = new ClientController(this.ip_txt.getText(), ClientController.DEFAULT_PORT);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
   
      //ClientPageController aFrame =new ClientPageController();
      
      BranchManagerController aFrame =new BranchManagerController();
     BranchManagerController BranchMangerController = new BranchManagerController();
      LogInController logInController = new LogInController();
      try {
      	BranchMangerController.start(stage);
    	//logInController.start(stage);
    	 aFrame.start(stage);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

      

   }

   public void initialize(URL arg0, ResourceBundle arg1) {
      this.ip_txt.setText("localhost");
   }

   public void start(Stage primaryStage) throws Exception {
      Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("/gui/ClientConnection.fxml"));
      Scene scene = new Scene(root);
      primaryStage.setTitle("Client");
      primaryStage.setScene(scene);
      primaryStage.show();
   }
}
