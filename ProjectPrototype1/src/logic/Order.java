package logic;

import java.io.Serializable;
import java.sql.Date; // Import java.sql.Date for handling SQL DATE type
import java.sql.Timestamp;

public class Order implements Serializable {
    private static final long serialVersionUID = 1L;

    private int orderId; // Matches SQL orderId
    private String userID; // Matches SQL userID
    private int restaurantId; // Matches SQL restaurantId
    private double totalPrice; // Matches SQL totalPrice
    private String orderAddress; // Matches SQL orderAddress
    private Timestamp orderDate; // Changed to java.sql.Date to match SQL orderDate
    private int deliveryTypeId; // Matches SQL deliveryTypeId
    private String expectedDeliveryTime; // Matches SQL expectedDeliveryTime
    private String actualDeliveryTime; // Matches SQL actualDeliveryTime

    public Order(int orderId, String userID, int restaurantId, double totalPrice, String orderAddress, 
                 Timestamp timestamp, int deliveryTypeId, String expectedDeliveryTime, String actualDeliveryTime) {
        this.orderId = orderId;
        this.userID = userID;
        this.restaurantId = restaurantId;
        this.totalPrice = totalPrice;
        this.orderAddress = orderAddress;
        this.orderDate = timestamp; // Initialize orderDate as java.sql.Date
        this.deliveryTypeId = deliveryTypeId;
        this.expectedDeliveryTime = expectedDeliveryTime;
        this.actualDeliveryTime = actualDeliveryTime;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress;
    }

    public Timestamp getOrderDate() {
        return orderDate; // Return as java.sql.Date
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate; // Accept as java.sql.Date
    }

    public int getDeliveryTypeId() {
        return deliveryTypeId;
    }

    public void setDeliveryTypeId(int deliveryTypeId) {
        this.deliveryTypeId = deliveryTypeId;
    }

    public String getExpectedDeliveryTime() {
        return expectedDeliveryTime; // Return as String
    }

    public void setExpectedDeliveryTime(String expectedDeliveryTime) {
        this.expectedDeliveryTime = expectedDeliveryTime; // Accept as String
    }

    public String getActualDeliveryTime() {
        return actualDeliveryTime; // Return as String
    }

    public void setActualDeliveryTime(String actualDeliveryTime) {
        this.actualDeliveryTime = actualDeliveryTime; // Accept as String
    }
    
    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", userID='" + userID + '\'' +
                ", restaurantId=" + restaurantId +
                ", totalPrice=" + totalPrice +
                ", orderAddress='" + orderAddress + '\'' +
                ", orderDate=" + orderDate + // Include orderDate in toString
                ", deliveryTypeId=" + deliveryTypeId +
                ", expectedDeliveryTime='" + expectedDeliveryTime + '\'' +
                ", actualDeliveryTime='" + actualDeliveryTime + '\'' +
                '}';
    }
}
