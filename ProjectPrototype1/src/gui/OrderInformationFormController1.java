package gui;

import java.net.URL;
import java.util.ResourceBundle;

import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import logic.Message1;
import logic.MessageType;
import logic.Order;

public class OrderInformationFormController1 implements Initializable {
	private Order o;

	@FXML
	private TextField txtrestaurant; // Restaurant name or ID
	@FXML
	private TextField txtordernumber; // Order number
	@FXML
	private TextField txttotalprice; // Total price
	@FXML
	private TextField txtorderlistnumber; // Order list number (if applicable)
	@FXML
	private TextField txtorderaddress; // Delivery address

	/**
	 * Retrieves the restaurant name or ID from the text field.
	 *
	 * @return the restaurant name or ID as a String
	 */
	private String getRes() {
		return txtrestaurant.getText();
	}

	/**
	 * Retrieves the order number from the text field.
	 *
	 * @return the order number as a String
	 */
	private String getOrderNum() {
		return txtordernumber.getText();
	}

	/**
	 * Retrieves the total price from the text field.
	 *
	 * @return the total price as a String
	 */
	private String getTotalP() {
		return txttotalprice.getText();
	}

	/**
	 * Retrieves the order list number from the text field.
	 *
	 * @return the order list number as a String
	 */
	private String getListNum() {
		return txtorderlistnumber.getText();
	}

	/**
	 * Retrieves the delivery address from the text field.
	 *
	 * @return the delivery address as a String
	 */
	private String getAddress() {
		return txtorderaddress.getText();
	}

	/**
	 * Loads the specified order into the form fields.
	 *
	 * @param o1 the Order object to load
	 */
	public void loadOrder(Order o1) {
		this.o = o1;
		// Use appropriate getters to fetch values from Order object
		this.txtrestaurant.setText(String.valueOf(o.getRestaurantId())); // Assuming it's the ID
		this.txtordernumber.setText(String.valueOf(o.getOrderId())); // Correct method
		this.txttotalprice.setText(String.valueOf(o.getTotalPrice())); // Correct method
		this.txtorderlistnumber.setText(o.getListNumber()); // Update to get the list number correctly
		this.txtorderaddress.setText(o.getOrderAddress());
	}

	/**
	 * Closes the current page and opens the Order Track Frame.
	 *
	 * @param event the ActionEvent triggered by the user
	 * @throws Exception if there is an error in closing the page or opening a new
	 *                   stage
	 */
	public void closePage(ActionEvent event) throws Exception {
		((Node) event.getSource()).getScene().getWindow().hide(); // Hiding primary window
		OrderTrackFrameController1 orderTrackFrameController = new OrderTrackFrameController1();
		Stage primaryStage = new Stage();
		orderTrackFrameController.start(primaryStage);
	}

	/**
	 * Updates the order based on the values entered in the form fields.
	 *
	 * @param event the ActionEvent triggered by the user
	 * @throws Exception if there is an error during the update process
	 */
	public void updateOrder(ActionEvent event) throws Exception {
		String res = txtrestaurant.getText();
		int orderNum = Integer.parseInt(txtordernumber.getText());
		double price = Double.parseDouble(txttotalprice.getText());
		String listNum = txtorderlistnumber.getText();
		String address = txtorderaddress.getText();

		// Assuming restaurant ID is entered in the text field
		int restaurantId = Integer.parseInt(res);
		int branchId = 0; // Update this as per your application logic
		int deliveryTypeId = 0; // Update this as per your application logic

		// Create a new Order object
		Order newOrder = new Order(orderNum, null, restaurantId, price, address, null, deliveryTypeId, null, null,
				listNum, branchId, String.valueOf(orderNum));

		// Send the order object to the server
		try {
			ClientUI.chat.accept(new Message1(MessageType.updateOrder, newOrder));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

}
