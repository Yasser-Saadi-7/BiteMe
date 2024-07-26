package gui;
import javafx.fxml.FXML;
import java.util.ArrayList;
import client.ChatClient;
import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
//Create Account PAGE
public class CreateAccountController {

    @FXML
    private TextField Fnametxt;

    @FXML
    private TextField Lnametxt;

    @FXML
    private TextField Phonetxt;

    @FXML
    private TextField IDtxt;

    @FXML
    private TextField Emailtxt;

    @FXML
    private TextField Credittxt;
    

	@FXML
	private Label txtmessage;
	

    public void createAccount(ActionEvent event) throws Exception{
        String firstName = Fnametxt.getText();
        String lastName = Lnametxt.getText();
        String phone = Phonetxt.getText();
        String id = IDtxt.getText();
        String email = Emailtxt.getText();
        String creditCard = Credittxt.getText();
        ArrayList<String> arr1 = new ArrayList<>();
    	String newAcc;
    	if(firstName.trim().isEmpty() || lastName.trim().isEmpty()
    			|| phone.trim().isEmpty() || id.trim().isEmpty()
    			|| email.trim().isEmpty() || creditCard.trim().isEmpty()) {
    			updateTextMessage("Please fill ALL Fields");
    		}else {
    			arr1.add("CreateAccount");
    			arr1.add(firstName);
    			arr1.add(lastName);
    			arr1.add(phone);
    			arr1.add(id);
    			arr1.add(email);
    			arr1.add(creditCard);
    			ClientUI.chat.accept(arr1);
    			newAcc=ChatClient.CreateAccount;
    		}

}
    
    public void updateTextMessage(String message) {
		txtmessage.setText(message);
	}
	
    
    private void clearFields() {
        Fnametxt.clear();
        Lnametxt.clear();
        Phonetxt.clear();
        IDtxt.clear();
        Emailtxt.clear();
        Credittxt.clear();
    }
}