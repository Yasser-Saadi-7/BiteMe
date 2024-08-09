package logic;

import java.io.Serializable;

public class MealType implements Serializable {

    private static final long serialVersionUID = 1L;
    private String restaurantName;
    private String mealTypeId;
    private String mealTypeName;
    private int restaurantId; 

    public MealType(String restaurantName, String mealTypeId, String mealTypeName, int restaurantId) {
        super();
        this.restaurantName = restaurantName;
        this.mealTypeId = mealTypeId;
        this.mealTypeName = mealTypeName;
        this.restaurantId = restaurantId; // Initialize restaurantId
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getMealTypeId() {
        return mealTypeId;
    }

    public void setMealTypeId(String mealTypeId) {
        this.mealTypeId = mealTypeId;
    }

    public String getMealTypeName() {
        return mealTypeName;
    }

    public void setMealTypeName(String mealTypeName) {
        this.mealTypeName = mealTypeName;
    }

    public int getRestaurantId() {
        return restaurantId; // Getter for restaurantId
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId; // Setter for restaurantId
    }
}
