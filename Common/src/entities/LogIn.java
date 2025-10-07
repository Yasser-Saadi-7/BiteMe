package entities;

import java.io.Serializable;

public class LogIn implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String userID,firstName,lastName,email,phone,creditCard,userType,username,password,homeBranch,homeBranchId;

	public LogIn(String userID, String firstName, String lastName, String email, String phone, String creditCard,
			String userType, String username, String password, String homeBranch,String homeBranchId) {
		super();
		this.userID = userID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.creditCard = creditCard;
		this.userType = userType;
		this.username = username;
		this.password = password;
		this.homeBranch = homeBranch;
		this.homeBranchId = homeBranchId;

	}

	public String getUserID() {
		return userID;
	}

	public String getHomeBranchId() {
		return homeBranchId;
	}

	public void setHomeBranchId(String homeBranchId) {
		this.homeBranchId = homeBranchId;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(String creditCard) {
		this.creditCard = creditCard;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHomeBranch() {
		return homeBranch;
	}

	public void setHomeBranch(String homeBranch) {
		this.homeBranch = homeBranch;
	}
	
	

}
