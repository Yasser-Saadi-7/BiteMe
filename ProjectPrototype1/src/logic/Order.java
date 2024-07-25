package logic;

import java.io.Serializable;

public class Order implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String orederNumber,restaurant,orderAddress,listNumber ,tprice;

	public Order(String orederNumber, String restaurant, String tPrice, String orderAddress, String listNumber) {
		this.orederNumber = orederNumber;
		this.restaurant = restaurant;
		this.tprice = tPrice;
		this.orderAddress = orderAddress;
		this.listNumber = listNumber;
	}

	public String getOrederNumber() {
		return orederNumber;
	}

	public void setOrederNumber(String orederNumber) {
		this.orederNumber = orederNumber;
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

