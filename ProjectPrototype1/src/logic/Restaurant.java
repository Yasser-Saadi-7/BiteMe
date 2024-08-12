package logic;

import java.io.Serializable;

/**
 * Represents a restaurant entity.
 */
public class Restaurant implements Serializable {
    private static final long serialVersionUID = 1L;

    private int restaurantId;      // Unique identifier for the restaurant
    private int branchId;          // ID of the branch to which the restaurant belongs
    private String restaurantName;  // Name of the restaurant
    private String branch_location;  // Location of the restaurant branch
    private String phone_number;     // Phone number of the restaurant

    /**
     * Constructor to initialize all fields of the Restaurant class.
     *
     * @param restaurantId     the unique identifier for the restaurant
     * @param branchId         the ID of the branch to which the restaurant belongs
     * @param restaurantName    the name of the restaurant
     */
    public Restaurant(int restaurantId, int branchId, String restaurantName) {
        this.restaurantId = restaurantId;      // Initialize restaurantId
        this.branchId = branchId;              // Initialize branchId
        this.restaurantName = restaurantName;  // Initialize restaurantName
        this.branch_location = branch_location;  // Initialize branch location
        this.phone_number = phone_number;        // Initialize phone number
    }

    /**
     * Gets the unique identifier for the restaurant.
     *
     * @return the restaurant ID
     */
    public int getRestaurantId() {
        return restaurantId; // Return the restaurant ID
    }

    /**
     * Sets the unique identifier for the restaurant.
     *
     * @param restaurantId the restaurant ID
     */
    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId; // Set the restaurant ID
    }

    /**
     * Gets the ID of the branch to which the restaurant belongs.
     *
     * @return the branch ID
     */
    public int getBranchId() {
        return branchId; // Return the branch ID
    }

    /**
     * Sets the ID of the branch to which the restaurant belongs.
     *
     * @param branchId the branch ID
     */
    public void setBranchId(int branchId) {
        this.branchId = branchId; // Set the branch ID
    }

    /**
     * Gets the name of the restaurant.
     *
     * @return the restaurant name
     */
    public String getRestaurantName() {
        return restaurantName; // Return the restaurant name
    }

    /**
     * Sets the name of the restaurant.
     *
     * @param restaurantName the restaurant name
     */
    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName; // Set the restaurant name
    }

    /**
     * Gets the location of the restaurant branch.
     *
     * @return the branch location
     */
    public String getResLocation() {
        return branch_location; // Return the branch location
    }

    /**
     * Sets the location of the restaurant branch.
     *
     * @param resLocation the branch location
     */
    public void setResLocation(String resLocation) {
        this.branch_location = resLocation; // Set the branch location
    }

    /**
     * Gets the phone number of the restaurant.
     *
     * @return the phone number
     */
    public String getResPhoneNum() {
        return phone_number; // Return the phone number
    }

    /**
     * Sets the phone number of the restaurant.
     *
     * @param resPhoneNum the phone number
     */
    public void setResPhoneNum(String resPhoneNum) {
        this.phone_number = resPhoneNum; // Set the phone number
    }

    /**
     * Provides a string representation of the Restaurant object.
     *
     * @return a string containing the details of the restaurant
     */
    @Override
    public String toString() {
        return "Restaurant{" +
                "restaurantId=" + restaurantId +
                ", branchId=" + branchId +
                ", restaurantName='" + restaurantName + '\'' +
                ", branch_location='" + branch_location + '\'' +
                ", phone_number='" + phone_number + '\'' +
                '}';
    }
}
