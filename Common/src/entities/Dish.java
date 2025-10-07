package entities;

import java.io.Serializable;

public class Dish implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String dishId,resName,dishName,mealTypeId,mealTypeName,price;

	public Dish(String dishId, String resName, String dishName, String mealTypeId, String mealTypeName, String price) {
		super();
		this.dishId = dishId;
		this.resName = resName;
		this.dishName = dishName;
		this.mealTypeId = mealTypeId;
		this.mealTypeName = mealTypeName;
		this.price = price;
	}

	public String getDishId() {
		return dishId;
	}

	public void setDishId(String dishId) {
		this.dishId = dishId;
	}

	public String getResName() {
		return resName;
	}

	public void setResName(String resName) {
		this.resName = resName;
	}

	public String getDishName() {
		return dishName;
	}

	public void setDishName(String dishName) {
		this.dishName = dishName;
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

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	
	

}
