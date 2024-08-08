package logic;

import java.io.Serializable;

public class CreateAccount implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private String userID;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String creditCard;
    private UserType userType;
    private String username;
    private String password;

    // Constructor
    public CreateAccount(String userID, String firstName, String lastName, String email, String phone,
                         String creditCard, UserType userType, String username, String password) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone; 
        this.creditCard = creditCard;
        this.userType = userType; 
        this.username = username;
        this.password = password;
        
    }

    // Getters
    public String getUserID() {
        return userID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public UserType getUserType() {
        return userType;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    // Setters
    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
