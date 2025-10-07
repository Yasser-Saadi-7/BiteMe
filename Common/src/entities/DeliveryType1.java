package entities;

import java.io.Serializable;

public class DeliveryType1 implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String deliveryType,price;

	public DeliveryType1(String deliveryType, String price) {
		super();
		this.deliveryType = deliveryType;
		this.price = price;
	}

	public String getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	
	

}
