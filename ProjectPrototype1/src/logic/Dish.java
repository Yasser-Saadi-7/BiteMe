package logic;

import java.io.Serializable;

/**
 * Represents a dish in the system.
 */
public class Dish implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String dishId;        // Unique identifier for the dish
    private String resName;       // Name of the restaurant
    private String dishName;      // Name of the dish
    private String mealTypeId;    // ID of the meal type (foreign key)
    private double price;         // Price of the dish

    /**
     * Constructor to initialize all fields of the Dish class.
     *
     * @param dishId     the unique identifier for the dish
     * @param resName    the name of the restaurant
     * @param dishName   the name of the dish
     * @param mealTypeId the ID of the meal type (foreign key)
     * @param price      the price of the dish
     */
    public Dish(String dishId, String resName, String dishName, String mealTypeId, double price) {
        this.dishId = dishId;      // Initialize dishId
        this.resName = resName;    // Initialize resName
        this.dishName = dishName;  // Initialize dishName
        this.mealTypeId = mealTypeId; // Initialize mealTypeId
        this.price = price;         // Initialize price
    }

    /**
     * Gets the unique identifier of the dish.
     *
     * @return the dish ID
     */
    public String getDishId() {
        return dishId; // Return the dish ID
    }

    /**
     * Sets the unique identifier of the dish.
     *
     * @param dishId the new dish ID to set
     */
    public void setDishId(String dishId) {
        this.dishId = dishId; // Set the dish ID
    }

    /**
     * Gets the name of the restaurant.
     *
     * @return the restaurant name
     */
    public String getResName() {
        return resName; // Return the restaurant name
    }

    /**
     * Sets the name of the restaurant.
     *
     * @param resName the new restaurant name to set
     */
    public void setResName(String resName) {
        this.resName = resName; // Set the restaurant name
    }

    /**
     * Gets the name of the dish.
     *
     * @return the dish name
     */
    public String getDishName() {
        return dishName; // Return the dish name
    }

    /**
     * Sets the name of the dish.
     *
     * @param dishName the new dish name to set
     */
    public void setDishName(String dishName) {
        this.dishName = dishName; // Set the dish name
    }

    /**
     * Gets the ID of the meal type.
     *
     * @return the meal type ID
     */
    public String getMealTypeId() {
        return mealTypeId; // Return the meal type ID
    }

    /**
     * Sets the ID of the meal type.
     *
     * @param mealTypeId the new meal type ID to set
     */
    public void setMealTypeId(String mealTypeId) {
        this.mealTypeId = mealTypeId; // Set the meal type ID
    }

    /**
     * Gets the price of the dish.
     *
     * @return the price of the dish
     */
    public double getPrice() {
        return price; // Return the price of the dish
    }

    /**
     * Sets the price of the dish.
     *
     * @param price the new price to set for the dish
     */
    public void setPrice(double price) {
        this.price = price; // Set the price of the dish
    }

    /**
     * Provides a string representation of the Dish object.
     *
     * @return a string containing the details of the dish
     */
    @Override
    public String toString() {
        return "Dish{" +
                "dishId='" + dishId + '\'' +
                ", resName='" + resName + '\'' +
                ", dishName='" + dishName + '\'' +
                ", mealTypeId='" + mealTypeId + '\'' +
                ", price=" + price +
                '}';
    }
}
