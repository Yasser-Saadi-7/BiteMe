package logic;

import java.io.Serializable;
import java.math.BigDecimal;

public class DeliveryType1 implements Serializable {
    private static final long serialVersionUID = 1L;

    private int deliveryTypeId; // Unique identifier for the delivery type
    private String deliveryType; // Name of the delivery type
    private Double price;    // Price for the delivery type

    // Constructor
    /**
     * Constructs a new DeliveryType instance with the specified parameters.
     *
     * @param deliveryTypeId the unique identifier for the delivery type
     * @param deliveryType the name of the delivery type
     * @param price the price for the delivery type
     */
    public DeliveryType1(int deliveryTypeId, String deliveryType, Double price) {
        this.deliveryTypeId = deliveryTypeId; // Initialize deliveryTypeId
        this.deliveryType = deliveryType;       // Initialize deliveryType
        this.price = price;                     // Initialize price
    }

    // Getters
    /**
     * Gets the unique identifier for the delivery type.
     *
     * @return the deliveryTypeId
     */
    public int getDeliveryTypeId() {
        return deliveryTypeId; // Return deliveryTypeId
    }

    /**
     * Gets the name of the delivery type.
     *
     * @return the deliveryType
     */
    public String getDeliveryType() {
        return deliveryType; // Return deliveryType
    }

    /**
     * Gets the price for the delivery type.
     *
     * @return the price as BigDecimal
     */
    public Double getPrice() {
        return price; // Return price
    }

    // Setters
    /**
     * Sets the unique identifier for the delivery type.
     *
     * @param deliveryTypeId the unique identifier to set
     */
    public void setDeliveryTypeId(int deliveryTypeId) {
        this.deliveryTypeId = deliveryTypeId; // Set deliveryTypeId
    }

    /**
     * Sets the name of the delivery type.
     *
     * @param deliveryType the name of the delivery type to set
     */
    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType; // Set deliveryType
    }

    /**
     * Sets the price for the delivery type.
     *
     * @param price the price to set as BigDecimal
     */
    public void setPrice(Double price) {
        this.price = price; // Set price
    }

    // Optional: Override toString() for better representation
    /**
     * Returns a string representation of the DeliveryType object.
     *
     * @return a string representation of the DeliveryType
     */
    @Override
    public String toString() {
        return "DeliveryType{" +
                "deliveryTypeId=" + deliveryTypeId +
                ", deliveryType='" + deliveryType + '\'' +
                ", price=" + price +
                '}';
    }
}
