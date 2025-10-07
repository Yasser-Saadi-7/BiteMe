package entities;

import java.io.Serializable;

public class MealsType implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String resName,mealTypeId,mealTypeName;

	public MealsType(String resName, String mealTypeId, String mealTypeName) {
		super();
		this.resName = resName;
		this.mealTypeId = mealTypeId;
		this.mealTypeName = mealTypeName;
	}

	public String getResName() {
		return resName;
	}

	public void setResName(String resName) {
		this.resName = resName;
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
	

}
