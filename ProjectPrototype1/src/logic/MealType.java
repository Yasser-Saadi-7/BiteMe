package logic;

import java.io.Serializable;

/**
 * Represents a meal type associated with a restaurant.
 */
public class MealType implements Serializable {

    private static final long serialVersionUID = 1L;
    private String restaurantName; // Name of the restaurant
    private String mealTypeId;      // Unique identifier for the meal type
    private String mealTypeName;    // Name of the meal type
    private int restaurantId;       // ID of the associated restaurant

    /**
     * Constructor to initialize all fields of the MealType class.
     *
     * @param restaurantName the name of the restaurant
     * @param mealTypeId     the unique identifier for the meal type
     * @param mealTypeName   the name of the meal type
     * @param restaurantId    the ID of the associated restaurant
     */
    public MealType(String restaurantName, String mealTypeId, String mealTypeName, int restaurantId) {
        this.restaurantName = restaurantName; // Initialize restaurant name
        this.mealTypeId = mealTypeId;         // Initialize meal type ID
        this.mealTypeName = mealTypeName;     // Initialize meal type name
        this.restaurantId = restaurantId;     // Initialize restaurant ID
    }

    /**
     * Gets the name of the restaurant.
     *
     * @return the name of the restaurant
     */
    public String getRestaurantName() {
        return restaurantName; // Return the restaurant name
    }

    /**
     * Sets the name of the restaurant.
     *
     * @param restaurantName the name of the restaurant
     */
    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName; // Set the restaurant name
    }

    /**
     * Gets the unique identifier for the meal type.
     *
     * @return the meal type ID
     */
    public String getMealTypeId() {
        return mealTypeId; // Return the meal type ID
    }

    /**
     * Sets the unique identifier for the meal type.
     *
     * @param mealTypeId the meal type ID
     */
    public void setMealTypeId(String mealTypeId) {
        this.mealTypeId = mealTypeId; // Set the meal type ID
    }

    /**
     * Gets the name of the meal type.
     *
     * @return the meal type name
     */
    public String getMealTypeName() {
        return mealTypeName; // Return the meal type name
    }

    /**
     * Sets the name of the meal type.
     *
     * @param mealTypeName the meal type name
     */
    public void setMealTypeName(String mealTypeName) {
        this.mealTypeName = mealTypeName; // Set the meal type name
    }

    /**
     * Gets the ID of the associated restaurant.
     *
     * @return the restaurant ID
     */
    public int getRestaurantId() {
        return restaurantId; // Return the restaurant ID
    }

    /**
     * Sets the ID of the associated restaurant.
     *
     * @param restaurantId the restaurant ID
     */
    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId; // Set the restaurant ID
    }

    /**
     * Provides a string representation of the MealType object.
     *
     * @return a string containing the details of the meal type
     */
    @Override
    public String toString() {
        return "MealType{" +
                "restaurantName='" + restaurantName + '\'' +
                ", mealTypeId='" + mealTypeId + '\'' +
                ", mealTypeName='" + mealTypeName + '\'' +
                ", restaurantId=" + restaurantId +
                '}';
    }
}
