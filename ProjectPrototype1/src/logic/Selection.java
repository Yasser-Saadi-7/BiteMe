package logic;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents a selection entity for a dish.
 */
public class Selection implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String selectionId;    // Unique ID for the selection
    private String dishId;         // ID of the dish
    private String selectionName;   // Name of the selected dish
    private double selectionPrice;  // Price of the selected dish

    /**
     * Constructor to initialize all fields of the Selection class.
     *
     * @param selectionId    the unique ID for the selection
     * @param dishId         the ID of the dish
     * @param selectionName   the name of the selected dish
     * @param selectionPrice  the price of the selected dish
     */
    public Selection(String selectionId, String dishId, String selectionName, double selectionPrice) {
        this.selectionId = selectionId;       // Initialize selectionId
        this.dishId = dishId;                 // Initialize dishId
        this.selectionName = selectionName;   // Initialize selectionName
        this.selectionPrice = selectionPrice; // Initialize selectionPrice
    }

    /**
     * Gets the unique ID for the selection.
     *
     * @return the selection ID
     */
    public String getSelectionId() {
        return selectionId; // Return the selection ID
    }

    /**
     * Sets the unique ID for the selection.
     *
     * @param selectionId the selection ID
     */
    public void setSelectionId(String selectionId) {
        this.selectionId = selectionId; // Set the selection ID
    }

    /**
     * Gets the ID of the dish associated with the selection.
     *
     * @return the dish ID
     */
    public String getDishId() {
        return dishId; // Return the dish ID
    }

    /**
     * Sets the ID of the dish associated with the selection.
     *
     * @param dishId the dish ID
     */
    public void setDishId(String dishId) {
        this.dishId = dishId; // Set the dish ID
    }

    /**
     * Gets the name of the selected dish.
     *
     * @return the selection name
     */
    public String getSelectionName() {
        return selectionName; // Return the selection name
    }

    /**
     * Sets the name of the selected dish.
     *
     * @param selectionName the selection name
     */
    public void setSelectionName(String selectionName) {
        this.selectionName = selectionName; // Set the selection name
    }

    /**
     * Gets the price of the selected dish.
     *
     * @return the selection price
     */
    public double getSelectionPrice() {
        return selectionPrice; // Return the selection price
    }

    /**
     * Sets the price of the selected dish.
     *
     * @param selectionPrice the selection price
     */
    public void setSelectionPrice(double selectionPrice) {
        this.selectionPrice = selectionPrice; // Set the selection price
    }

    /**
     * Compares this selection to another object for equality.
     *
     * @param o the object to compare to
     * @return true if the selections are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Check if both references point to the same object
        if (o == null || getClass() != o.getClass()) return false; // Check if the object is of the same class

        Selection selection = (Selection) o; // Cast the object to Selection

        // Compare all fields for equality
        return (selectionId != null ? selectionId.equals(selection.selectionId) : selection.selectionId == null) &&
               (dishId != null ? dishId.equals(selection.dishId) : selection.dishId == null) &&
               (selectionName != null ? selectionName.equals(selection.selectionName) : selection.selectionName == null) &&
               (Double.compare(selection.selectionPrice, selectionPrice) == 0); // Update equality check for double
    }

    /**
     * Generates a hash code for the selection based on its fields.
     *
     * @return the hash code for the selection
     */
    @Override
    public int hashCode() {
        return Objects.hash(selectionId, dishId, selectionName, selectionPrice); // Generate hash code based on all fields
    }

    /**
     * Provides a string representation of the Selection object.
     *
     * @return a string containing the details of the selection
     */
    @Override
    public String toString() {
        return "Selection{" +
                "selectionId='" + selectionId + '\'' +
                ", dishId='" + dishId + '\'' +
                ", selectionName='" + selectionName + '\'' +
                ", selectionPrice=" + selectionPrice +
                '}';
    }
}
