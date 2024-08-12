package logic;

import java.io.Serializable;

public class Cart implements Serializable {
    private static final long serialVersionUID = 1L;

    private String resName; // Name of the restaurant
    private String mealType; // Type of the meal
    private String dishName; // Name of the dish
    private String sel; // Selection (could represent options or variations)
    private String quantity; // Quantity of the dish
    private Double totalPrice; // Total price for the items in the cart

    /**
     * Constructs a new Cart instance with the specified parameters.
     *
     * @param resName the name of the restaurant
     * @param mealType the type of the meal
     * @param dishName the name of the dish
     * @param sel the selection or variation of the dish
     * @param quantity the quantity of the dish
     * @param totalPrice the total price for the items in the cart
     */
    public Cart(String resName, String mealType, String dishName, String sel, String quantity, Double totalPrice) {
        super();
        this.resName = resName;
        this.mealType = mealType;
        this.dishName = dishName;
        this.sel = sel;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    /**
     * Gets the name of the restaurant.
     *
     * @return the resName
     */
    public String getResName() {
        return resName;
    }

    /**
     * Sets the name of the restaurant.
     *
     * @param resName the restaurant name to set
     */
    public void setResName(String resName) {
        this.resName = resName;
    }

    /**
     * Gets the type of meal.
     *
     * @return the mealType
     */
    public String getMealType() {
        return mealType;
    }

    /**
     * Sets the type of meal.
     *
     * @param mealType the meal type to set
     */
    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    /**
     * Gets the name of the dish.
     *
     * @return the dishName
     */
    public String getDishName() {
        return dishName;
    }

    /**
     * Sets the name of the dish.
     *
     * @param dishName the dish name to set
     */
    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    /**
     * Gets the selection or variation of the dish.
     *
     * @return the sel
     */
    public String getSel() {
        return sel;
    }

    /**
     * Sets the selection or variation of the dish.
     *
     * @param sel the selection to set
     */
    public void setSel(String sel) {
        this.sel = sel;
    }

    /**
     * Gets the quantity of the dish.
     *
     * @return the quantity
     */
    public String getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the dish.
     *
     * @param quantity the quantity to set
     */
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the total price for the items in the cart.
     *
     * @return the totalPrice
     */
    public Double getTotalPrice() {
        return totalPrice;
    }

    /**
     * Sets the total price for the items in the cart.
     *
     * @param totalPrice the total price to set
     */
    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * Compares this Cart instance to another object for equality.
     *
     * @param obj the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Cart cart = (Cart) obj;
        return resName.equals(cart.resName) &&
               mealType.equals(cart.mealType) &&
               dishName.equals(cart.dishName) &&
               sel.equals(cart.sel) &&
               quantity.equals(cart.quantity) &&
               totalPrice.equals(cart.totalPrice);
    }
}
