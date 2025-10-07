package entities;

import java.io.Serializable;
import java.util.Objects;

public class Selection implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String selectionId,dishId,selName,selPrice;

	public Selection(String selectionId, String dishId, String selName, String selPrice) {
		super();
		this.selectionId = selectionId;
		this.dishId = dishId;
		this.selName = selName;
		this.selPrice = selPrice;
	}

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

	public String getSelPrice() {
		return selPrice;
	}

	public void setSelPrice(String selPrice) {
		this.selPrice = selPrice;
	}

	
	
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;  // Check if the instances are exactly the same
        if (o == null || getClass() != o.getClass()) return false;  // Check for null and ensure objects are of the same class

        Selection selection = (Selection) o;  // Cast the Object o to Selection

        // Compare each field using .equals() and handle nulls
        return (selectionId != null ? selectionId.equals(selection.selectionId) : selection.selectionId == null) &&
               (dishId != null ? dishId.equals(selection.dishId) : selection.dishId == null) &&
               (selName != null ? selName.equals(selection.selName) : selection.selName == null) &&
               (selPrice != null ? selPrice.equals(selection.selPrice) : selection.selPrice == null);
    }

    @Override
    public int hashCode() {
        return Objects.hash(selectionId, dishId, selName, selPrice);
    }
	

}
