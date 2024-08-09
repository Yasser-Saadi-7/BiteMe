package logic;

import java.io.Serializable;

public class Dish implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String dishId;        // Unique identifier for the dish
    private String resName;       // Name of the restaurant
    private String dishName;      // Name of the dish
    private String mealTypeId;    // ID of the meal type (foreign key)
    private double price;         // Price of the dish (changed from String to double)

    // Constructor to initialize all fields
    public Dish(String dishId, String resName, String dishName, String mealTypeId, double price) {
        this.dishId = dishId;      // Initialize dishId
        this.resName = resName;    // Initialize resName
        this.dishName = dishName;  // Initialize dishName
        this.mealTypeId = mealTypeId; // Initialize mealTypeId
        this.price = price;         // Initialize price
    }

    // Getters and setters for all fields
    public String getDishId() {
        return dishId; // Return the dish ID
    }

    public void setDishId(String dishId) {
        this.dishId = dishId; // Set the dish ID
    }

    public String getResName() {
        return resName; // Return the restaurant name
    }

    public void setResName(String resName) {
        this.resName = resName; // Set the restaurant name
    }

    public String getDishName() {
        return dishName; // Return the dish name
    }

    public void setDishName(String dishName) {
        this.dishName = dishName; // Set the dish name
    }

    public String getMealTypeId() {
        return mealTypeId; // Return the meal type ID
    }

    public void setMealTypeId(String mealTypeId) {
        this.mealTypeId = mealTypeId; // Set the meal type ID
    }

    public double getPrice() {
        return price; // Return the price of the dish
    }

    public void setPrice(double price) {
        this.price = price; // Set the price of the dish
    }

    // Optional: Override toString() for better debugging/printing
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
