package entities;

import java.io.Serializable;

/**
 * Represents a user account with various personal and account details.
 */
public class CreateAccount implements Serializable {

	private static final long serialVersionUID = 1L;

	private String userID; // Unique identifier for the user
	private String firstName; // User's first name
	private String lastName; // User's last name
	private String email; // User's email address
	private String phone; // User's phone number
	private String creditCard; // User's credit card information
	private String userType; // Type of user (e.g., Client, CEO, etc.)
	private String username; // Username for account login
	private String password; // Password for account login
	private String homeBranchId; // ID of the user's home branch
	private AccountType accountType; // Type of account (PRIVATE or BUSINESS)
	private String homeBranchName;

	/**
	 * Constructor to create a new user account.
	 *
	 * @param userID       the unique identifier for the user
	 * @param firstName    the first name of the user
	 * @param lastName     the last name of the user
	 * @param email        the email address of the user
	 * @param phone        the phone number of the user
	 * @param creditCard   the credit card information of the user
	 * @param userType     the type of user (Client, CEO, etc.)
	 * @param username     the username for account login
	 * @param password     the password for account login
	 * @param homeBranchId the ID of the user's home branch
	 * @param accountType  the type of account (PRIVATE or BUSINESS)
	 */
	public CreateAccount(String userID, String firstName, String lastName, String email, String phone,
			String creditCard, String userType, String username, String password, String homeBranchId,
			AccountType accountType, String homeBranchName) {
		this.userID = userID; // Initialize userID
		this.firstName = firstName; // Initialize firstName
		this.lastName = lastName; // Initialize lastName
		this.email = email; // Initialize email
		this.phone = phone; // Initialize phone
		this.creditCard = creditCard; // Initialize creditCard
		this.userType = userType; // Initialize userType
		this.username = username; // Initialize username
		this.password = password; // Initialize password
		this.homeBranchId = homeBranchId; // Initialize homeBranchId
		this.accountType = accountType; // Initialize accountType
		this.homeBranchName = homeBranchName;
	}

	// Getters
	public String getHomeBranchName() {
		return homeBranchName;
	}

	/**
	 * Gets the unique identifier for the user.
	 *
	 * @return the userID
	 */
	public String getUserID() {
		return userID;
	}

	/**
	 * Gets the user's first name.
	 *
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Gets the user's last name.
	 *
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Gets the user's email address.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Gets the user's phone number.
	 *
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Gets the user's credit card information.
	 *
	 * @return the creditCard
	 */
	public String getCreditCard() {
		return creditCard;
	}

	/**
	 * Gets the user's type.
	 *
	 * @return the userType
	 */
	public String getUserType() {
		return userType;
	}

	/**
	 * Gets the username for account login.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Gets the password for account login.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Gets the ID of the user's home branch.
	 *
	 * @return the homeBranchId
	 */
	public String getHomeBranchId() {
		return homeBranchId; // Return homeBranchId as a String
	}

	/**
	 * Gets the account type (PRIVATE or BUSINESS).
	 *
	 * @return the accountType
	 */
	public AccountType getAccountType() {
		return accountType;
	}

	// Setters

	public void setHomeBranchName(String homeBranchName) {
		this.homeBranchName = homeBranchName;
	}

	/**
	 * Sets the unique identifier for the user.
	 *
	 * @param userID the new userID
	 */
	public void setUserID(String userID) {
		this.userID = userID;
	}

	/**
	 * Sets the user's first name.
	 *
	 * @param firstName the new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Sets the user's last name.
	 *
	 * @param lastName the new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Sets the user's email address.
	 *
	 * @param email the new email address
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Sets the user's phone number.
	 *
	 * @param phone the new phone number
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * Sets the user's credit card information.
	 *
	 * @param creditCard the new credit card information
	 */
	public void setCreditCard(String creditCard) {
		this.creditCard = creditCard;
	}

	/**
	 * Sets the user's type.
	 *
	 * @param userType the new user type
	 */
	public void setUserType(String userType) {
		this.userType = userType; // Set userType as a String
	}

	/**
	 * Sets the username for account login.
	 *
	 * @param username the new username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Sets the password for account login.
	 *
	 * @param password the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Sets the ID of the user's home branch.
	 *
	 * @param homeBranchId the new home branch ID
	 */
	public void setHomeBranchId(String homeBranchId) {
		this.homeBranchId = homeBranchId; // Set homeBranchId as a String
	}

	/**
	 * Sets the account type (PRIVATE or BUSINESS).
	 *
	 * @param accountType the new account type
	 */
	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}
}
