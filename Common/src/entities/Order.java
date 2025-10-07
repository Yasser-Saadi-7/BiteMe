package entities;

import java.io.Serializable;

public class Order implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String orderNumber,restaurant,orderAddress,listNumber ,tprice;

	public Order(String orderNumber, String restaurant, String tPrice, String orderAddress, String listNumber) {
		this.orderNumber = orderNumber;
		this.restaurant = restaurant;
		this.tprice = tPrice;
		this.orderAddress = orderAddress;
		this.listNumber = listNumber;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrederNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(String restaurant) {
		this.restaurant = restaurant;
	}

	
	public String getTprice() {
		return tprice;
	}

	public void setTprice(String tprice) {
		this.tprice = tprice;
	}

	public String getOrderAddress() {
		return orderAddress;
	}

	public void setOrderAddress(String orderAddress) {
		this.orderAddress = orderAddress;
	}

	public String getListNumber() {
		return listNumber;
	}

	public void setListNumber(String listNumber) {
		this.listNumber = listNumber;
	}
	
}

