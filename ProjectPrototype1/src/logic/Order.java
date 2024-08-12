package logic;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * Represents an order placed by a user in the system.
 */
public class Order implements Serializable {
    private static final long serialVersionUID = 1L;

    private int orderId; // Unique identifier for the order
    private String userID; // ID of the user who placed the order
    private int restaurantId; // ID of the restaurant for the order
    private Double totalPrice; // Total price of the order
    private String orderAddress; // Address where the order is to be delivered
    private Timestamp orderDate; // Date and time when the order was placed
    private int deliveryTypeId; // ID of the delivery type for the order
    private String expectedDeliveryTime; // Expected delivery time for the order
    private String actualDeliveryTime; // Actual delivery time for the order
    private String listNumber; // List number associated with the order
    private int branchId; // ID of the branch for the order
    private String orderNum; // Order number

    /**
     * Constructor to initialize all fields of the Order class.
     *
     * @param orderId              the unique identifier for the order
     * @param userID               the ID of the user who placed the order
     * @param restaurantId         the ID of the restaurant for the order
     * @param totalPrice           the total price of the order
     * @param orderAddress         the address where the order is to be delivered
     * @param orderDate            the date and time when the order was placed
     * @param deliveryTypeId       the ID of the delivery type for the order
     * @param expectedDeliveryTime  the expected delivery time for the order
     * @param actualDeliveryTime    the actual delivery time for the order
     * @param listNumber           the list number associated with the order
     * @param branchId             the ID of the branch for the order
     * @param orderNum             the order number
     */
    public Order(int orderId, String userID, int restaurantId, Double totalPrice, String orderAddress, 
                 Timestamp orderDate, int deliveryTypeId, String expectedDeliveryTime, String actualDeliveryTime,
                 String listNumber, int branchId, String orderNum) {
        this.orderId = orderId; // Initialize order ID
        this.userID = userID; // Initialize user ID
        this.restaurantId = restaurantId; // Initialize restaurant ID
        this.totalPrice = totalPrice; // Initialize total price
        this.orderAddress = orderAddress; // Initialize order address
        this.orderDate = orderDate; // Initialize order date
        this.deliveryTypeId = deliveryTypeId; // Initialize delivery type ID
        this.expectedDeliveryTime = expectedDeliveryTime; // Initialize expected delivery time
        this.actualDeliveryTime = actualDeliveryTime; // Initialize actual delivery time
        this.listNumber = listNumber; // Initialize list number
        this.branchId = branchId; // Initialize branch ID
        this.orderNum = orderNum; // Initialize order number
    }

    // Getters and setters for all fields

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

    public Double getTotalPrice() {
        return totalPrice; 
    }

    public void setTotalPrice(Double totalPrice) {
        if (totalPrice < 0) {
            throw new IllegalArgumentException("Total price cannot be negative");
        }
        this.totalPrice = totalPrice; 
    }

    public String getOrderAddress() {
        return orderAddress; 
    }

    public void setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress; 
    }

    public Timestamp getOrderDate() {
        return orderDate; 
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate; 
    }

    public int getDeliveryTypeId() {
        return deliveryTypeId; 
    }

    public void setDeliveryTypeId(int deliveryTypeId) {
        this.deliveryTypeId = deliveryTypeId; 
    }

    public String getExpectedDeliveryTime() {
        return expectedDeliveryTime; 
    }

    public void setExpectedDeliveryTime(String expectedDeliveryTime) {
        this.expectedDeliveryTime = expectedDeliveryTime; 
    }

    public String getActualDeliveryTime() {
        return actualDeliveryTime; 
    }

    public void setActualDeliveryTime(String actualDeliveryTime) {
        this.actualDeliveryTime = actualDeliveryTime; 
    }

    public String getListNumber() {
        return listNumber; 
    }

    public void setListNumber(String listNumber) {
        this.listNumber = listNumber; 
    }

    public int getBranchId() {
        return branchId; 
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId; 
    }

    public String getOrderNum() {
        return orderNum; 
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum; 
    }

    /**
     * Returns the order date as a formatted string.
     *
     * @return the formatted order date
     */
    public String getFormattedOrderDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(orderDate);
    }

    /**
     * Provides a string representation of the Order object.
     *
     * @return a string containing the details of the order
     */
    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", userID='" + userID + '\'' +
                ", restaurantId=" + restaurantId +
                ", totalPrice=" + totalPrice +
                ", orderAddress='" + orderAddress + '\'' +
                ", orderDate=" + getFormattedOrderDate() + // Use formatted order date
                ", deliveryTypeId=" + deliveryTypeId +
                ", expectedDeliveryTime='" + expectedDeliveryTime + '\'' +
                ", actualDeliveryTime='" + actualDeliveryTime + '\'' +
                ", listNumber='" + listNumber + '\'' +
                ", branchId=" + branchId +
                ", orderNum='" + orderNum + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;

        Order order = (Order) o;

        return orderId == order.orderId;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(orderId);
    }
}
