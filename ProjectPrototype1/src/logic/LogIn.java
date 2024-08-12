package logic;

import java.io.Serializable;

public class LogIn implements Serializable {
    private static final long serialVersionUID = 1L;

    private String userID;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String creditCard;
    private String username;
    private String password;
    private int homeBranchId; 
    private AccountType accountType; 

    private UserType userType;

    // Constructor
    /**
     * Constructs a new LogIn instance with the specified parameters.
     *
     * @param userID the unique identifier for the user
     * @param firstName the user's first name
     * @param lastName the user's last name
     * @param email the user's email address
     * @param phone the user's phone number
     * @param creditCard the user's credit card information
     * @param userType the type of user (e.g., admin, customer)
     * @param username the user's username
     * @param password the user's password
     * @param homeBranchId the ID of the user's home branch
     * @param accountType the type of account (e.g., savings, checking)
     */
    public LogIn(String userID, String firstName, String lastName, String email, String phone, String creditCard,
                 UserType userType, String username, String password, int homeBranchId, AccountType accountType) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.creditCard = creditCard;
        this.userType = userType;
        this.username = username;
        this.password = password;
        this.homeBranchId = homeBranchId; // Changed to int
        this.accountType = accountType; // Added to constructor
    }

    // Getters and Setters
    /**
     * Gets the unique identifier for the user.
     *
     * @return the userID
     */
    public String getUserID() {
        return userID;
    }

    /**
     * Sets the unique identifier for the user.
     *
     * @param userID the unique identifier to set
     */
    public void setUserID(String userID) {
        this.userID = userID;
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
     * Sets the user's first name.
     *
     * @param firstName the first name to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
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
     * Sets the user's last name.
     *
     * @param lastName the last name to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
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
     * Sets the user's email address.
     *
     * @param email the email address to set
     */
    public void setEmail(String email) {
        this.email = email;
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
     * Sets the user's phone number.
     *
     * @param phone the phone number to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
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
     * Sets the user's credit card information.
     *
     * @param creditCard the credit card information to set
     */
    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    /**
     * Gets the type of user.
     *
     * @return the userType
     */
    public UserType getUserType() {
        return userType;
    }

    /**
     * Sets the type of user.
     *
     * @param userType the userType to set
     */
    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    /**
     * Gets the user's username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the user's username.
     *
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the user's password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's password.
     *
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the ID of the user's home branch.
     *
     * @return the homeBranchId
     */
    public int getHomeBranchId() { // Changed return type to int
        return homeBranchId;
    }

    /**
     * Sets the ID of the user's home branch.
     *
     * @param homeBranchId the home branch ID to set
     */
    public void setHomeBranchId(int homeBranchId) { // Changed parameter type to int
        this.homeBranchId = homeBranchId;
    }

    /**
     * Gets the type of account for the user.
     *
     * @return the accountType
     */
    public AccountType getAccountType() { // Added getter for accountType
        return accountType;
    }

    /**
     * Sets the type of account for the user.
     *
     * @param accountType the accountType to set
     */
    public void setAccountType(AccountType accountType) { // Added setter for accountType
        this.accountType = accountType;
    }
}
