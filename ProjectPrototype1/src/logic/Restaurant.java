package logic;

import java.io.Serializable;

public class Restaurant implements Serializable {
    private static final long serialVersionUID = 1L;

    private int restaurantId; // Matches SQL restaurantId
    private int branchId; // Matches SQL branchId
    private String restaurantName; // Matches SQL restaurantName

    // Constructor to initialize all fields
    public Restaurant(int restaurantId, int branchId, String restaurantName) {
        this.restaurantId = restaurantId; // Initialize restaurantId
        this.branchId = branchId; // Initialize branchId
        this.restaurantName = restaurantName; // Initialize restaurantName
    }

    // Getters and setters
    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    // Optional: Override toString() for better debugging/printing
    @Override
    public String toString() {
        return "Restaurant{" +
                "restaurantId=" + restaurantId +
                ", branchId=" + branchId +
                ", restaurantName='" + restaurantName + '\'' +
                '}';
    }
}
