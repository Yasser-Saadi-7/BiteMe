package logic;

import java.io.Serializable;

/**
 * Represents a list of orders placed by a user in the system.
 */
public class OrderList implements Serializable {
    private static final long serialVersionUID = 1L;

    private String orderListNum; // Unique identifier for the order list
    private String restaurant; // Name of the restaurant
    private String orderDate; // Date when the order was placed
    private String requestedDate; // Date when the order was requested
    private String arrivalTime; // Estimated arrival time for the order
    private String status; // Current status of the order
    private String approval; // Approval status of the order
    private String address; // Delivery address for the order
    private String deliveryService; // Delivery service used for the order
    private double totalPrice; // Total price of the order

    /**
     * Constructor to initialize all fields of the OrderList class.
     *
     * @param orderListNum      the unique identifier for the order list
     * @param restaurant        the name of the restaurant
     * @param orderDate         the date when the order was placed
     * @param requestedDate     the date when the order was requested
     * @param arrivalTime       the estimated arrival time for the order
     * @param status            the current status of the order
     * @param approval          the approval status of the order
     * @param address           the delivery address for the order
     * @param deliveryService   the delivery service used for the order
     * @param totalPrice        the total price of the order
     */
    public OrderList(String orderListNum, String restaurant, String orderDate, String requestedDate, 
                     String arrivalTime, String status, String approval, String address, 
                     String deliveryService, double totalPrice) {
        this.orderListNum = orderListNum; // Initialize order list number
        this.restaurant = restaurant; // Initialize restaurant name
        this.orderDate = orderDate; // Initialize order date
        this.requestedDate = requestedDate; // Initialize requested date
        this.arrivalTime = arrivalTime; // Initialize arrival time
        this.status = status; // Initialize order status
        this.approval = approval; // Initialize approval status
        this.address = address; // Initialize delivery address
        this.deliveryService = deliveryService; // Initialize delivery service
        this.totalPrice = totalPrice; // Initialize total price
    }

    /**
     * Gets the unique identifier for the order list.
     *
     * @return the order list number
     */
    public String getOrderListNum() {
        return orderListNum; // Return the order list number
    }

    /**
     * Sets the unique identifier for the order list.
     *
     * @param orderListNum the order list number
     */
    public void setOrderListNum(String orderListNum) {
        this.orderListNum = orderListNum; // Set the order list number
    }

    /**
     * Gets the name of the restaurant.
     *
     * @return the restaurant name
     */
    public String getRestaurant() {
        return restaurant; // Return the restaurant name
    }

    /**
     * Sets the name of the restaurant.
     *
     * @param restaurant the restaurant name
     */
    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant; // Set the restaurant name
    }

    /**
     * Gets the date when the order was placed.
     *
     * @return the order date
     */
    public String getOrderDate() {
        return orderDate; // Return the order date
    }

    /**
     * Sets the date when the order was placed.
     *
     * @param orderDate the order date
     */
    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate; // Set the order date
    }

    /**
     * Gets the date when the order was requested.
     *
     * @return the requested date
     */
    public String getRequestedDate() {
        return requestedDate; // Return the requested date
    }

    /**
     * Sets the date when the order was requested.
     *
     * @param requestedDate the requested date
     */
    public void setRequestedDate(String requestedDate) {
        this.requestedDate = requestedDate; // Set the requested date
    }

    /**
     * Gets the estimated arrival time for the order.
     *
     * @return the arrival time
     */
    public String getArrivalTime() {
        return arrivalTime; // Return the arrival time
    }

    /**
     * Sets the estimated arrival time for the order.
     *
     * @param arrivalTime the arrival time
     */
    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime; // Set the arrival time
    }

    /**
     * Gets the current status of the order.
     *
     * @return the status of the order
     */
    public String getStatus() {
        return status; // Return the order status
    }

    /**
     * Sets the current status of the order.
     *
     * @param status the order status
     */
    public void setStatus(String status) {
        this.status = status; // Set the order status
    }

    /**
     * Gets the approval status of the order.
     *
     * @return the approval status
     */
    public String getApproval() {
        return approval; // Return the approval status
    }

    /**
     * Sets the approval status of the order.
     *
     * @param approval the approval status
     */
    public void setApproval(String approval) {
        this.approval = approval; // Set the approval status
    }

    /**
     * Gets the delivery address for the order.
     *
     * @return the delivery address
     */
    public String getAddress() {
        return address; // Return the delivery address
    }

    /**
     * Sets the delivery address for the order.
     *
     * @param address the delivery address
     */
    public void setAddress(String address) {
        this.address = address; // Set the delivery address
    }

    /**
     * Gets the delivery service used for the order.
     *
     * @return the delivery service
     */
    public String getDeliveryService() {
        return deliveryService; // Return the delivery service
    }

    /**
     * Sets the delivery service used for the order.
     *
     * @param deliveryService the delivery service
     */
    public void setDeliveryService(String deliveryService) {
        this.deliveryService = deliveryService; // Set the delivery service
    }

    /**
     * Gets the total price of the order.
     *
     * @return the total price
     */
    public double getTotalPrice() {
        return totalPrice; // Return the total price
    }

    /**
     * Sets the total price of the order.
     *
     * @param totalPrice the total price
     */
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice; // Set the total price
    }

    /**
     * Provides a string representation of the OrderList object.
     *
     * @return a string containing the details of the order list
     */
    @Override
    public String toString() {
        return "OrderList{" +
                "orderListNum='" + orderListNum + '\'' +
                ", restaurant='" + restaurant + '\'' +
                ", orderDate='" + orderDate + '\'' +
                ", requestedDate='" + requestedDate + '\'' +
                ", arrivalTime='" + arrivalTime + '\'' +
                ", status='" + status + '\'' +
                ", approval='" + approval + '\'' +
                ", address='" + address + '\'' +
                ", deliveryService='" + deliveryService + '\'' +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
