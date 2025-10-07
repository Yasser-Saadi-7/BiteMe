package entities;

import java.io.Serializable;

public class OrderListSponser implements Serializable{
private static final long serialVersionUID = 1L;
	
	private String customerId,orderListNum,res,time,arrivalTime,status,approval,address,delivery,price,exTime;

	public OrderListSponser(String customerId, String orderListNum, String res, String time, String arrivalTime,
			String status, String approval, String address, String delivery, String price, String exTime) {
		super();
		this.customerId = customerId;
		this.orderListNum = orderListNum;
		this.res = res;
		this.time = time;
		this.arrivalTime = arrivalTime;
		this.status = status;
		this.approval = approval;
		this.address = address;
		this.delivery = delivery;
		this.price = price;
		this.exTime = exTime;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getOrderListNum() {
		return orderListNum;
	}

	public void setOrderListNum(String orderListNum) {
		this.orderListNum = orderListNum;
	}

	public String getRes() {
		return res;
	}

	public void setRes(String res) {
		this.res = res;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getApproval() {
		return approval;
	}

	public void setApproval(String approval) {
		this.approval = approval;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDelivery() {
		return delivery;
	}

	public void setDelivery(String delivery) {
		this.delivery = delivery;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getExTime() {
		return exTime;
	}

	public void setExTime(String exTime) {
		this.exTime = exTime;
	}

	
	


}
