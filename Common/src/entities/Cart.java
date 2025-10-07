package entities;

import java.io.Serializable;

public class Cart implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String resName,mealType,dishName,sel,quantity,totalPrice;

	public Cart(String resName, String mealType, String dishName, String sel, String quantity, String totalPrice) {
		super();
		this.resName = resName;
		this.mealType = mealType;
		this.dishName = dishName;
		this.sel = sel;
		this.quantity = quantity;
		this.totalPrice = totalPrice;
	}

	public String getResName() {
		return resName;
	}

	public void setResName(String resName) {
		this.resName = resName;
	}

	public String getMealType() {
		return mealType;
	}

	public void setMealType(String mealType) {
		this.mealType = mealType;
	}

	public String getDishName() {
		return dishName;
	}

	public void setDishName(String dishName) {
		this.dishName = dishName;
	}

	public String getSel() {
		return sel;
	}

	public void setSel(String sel) {
		this.sel = sel;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}
	
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
