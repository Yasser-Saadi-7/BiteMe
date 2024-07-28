package logic;

import java.io.Serializable;

public class Restaurant implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String resName,resLocation,branchId,resPhoneNum;

	public Restaurant(String resName, String resLocation, String branchId, String resPhoneNum) {
		super();
		this.resName = resName;
		this.resLocation = resLocation;
		this.branchId = branchId;
		this.resPhoneNum = resPhoneNum;
	}

	public String getResName() {
		return resName;
	}

	public void setResName(String resName) {
		this.resName = resName;
	}

	public String getResLocation() {
		return resLocation;
	}

	public void setResLocation(String resLocation) {
		this.resLocation = resLocation;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getResPhoneNum() {
		return resPhoneNum;
	}

	public void setResPhoneNum(String resPhoneNum) {
		this.resPhoneNum = resPhoneNum;
	}
	
	

}
