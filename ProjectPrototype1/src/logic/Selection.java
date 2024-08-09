package logic;

import java.io.Serializable;
import java.util.Objects;

public class Selection implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String selectionId; // Unique ID for the selection
    private String dishId; // ID of the dish (matches dishId from the dishes table)
    private String selName; // Name of the selected dish
    private double selPrice; // Price of the selected dish

    // Constructor to initialize all fields
    public Selection(String selectionId, String dishId, String selName, double selPrice) {
        this.selectionId = selectionId; // Initialize selectionId
        this.dishId = dishId; // Initialize dishId
        this.selName = selName; // Initialize selName
        this.selPrice = selPrice; // Initialize selPrice as double
    }

    // Getters and setters
    public String getSelectionId() {
        return selectionId;
    }

    public void setSelectionId(String selectionId) {
        this.selectionId = selectionId;
    }

    public String getDishId() {
        return dishId;
    }

    public void setDishId(String dishId) {
        this.dishId = dishId;
    }

    public String getSelName() {
        return selName;
    }

    public void setSelName(String selName) {
        this.selName = selName;
    }

    public double getSelPrice() {
        return selPrice; // Return as double
    }

    public void setSelPrice(double selPrice) {
        this.selPrice = selPrice; // Accept as double
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Check if both references point to the same object
        if (o == null || getClass() != o.getClass()) return false; // Check if the object is of the same class

        Selection selection = (Selection) o; // Cast the object to Selection

        // Compare all fields for equality
        return (selectionId != null ? selectionId.equals(selection.selectionId) : selection.selectionId == null) &&
               (dishId != null ? dishId.equals(selection.dishId) : selection.dishId == null) &&
               (selName != null ? selName.equals(selection.selName) : selection.selName == null) &&
               (Double.compare(selection.selPrice, selPrice) == 0); // Update equality check for double
    }

    @Override
    public int hashCode() {
        return Objects.hash(selectionId, dishId, selName, selPrice); // Generate hash code based on all fields
    }

    // Optional: Override toString() for better debugging/printing
    @Override
    public String toString() {
        return "Selection{" +
                "selectionId='" + selectionId + '\'' +
                ", dishId='" + dishId + '\'' +
                ", selName='" + selName + '\'' +
                ", selPrice=" + selPrice +
                '}';
    }
}
