package logic;

public class CreateAccount {
    private String firstName;
    private String lastName;
    private String phone;
    private String userId;
    private String email;
    private String creditCard;

    // Constructor
    public CreateAccount(String firstName, String lastName, String phone, String userId, String email, String creditCard) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.userId = userId;
        this.email = email;
        this.creditCard = creditCard;
    }

    // Getters
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getCreditCard() {
        return creditCard;
    }

    // Optionally, you can override toString() for easier debugging
    @Override
    public String toString() {
        return "CreateAccount{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", userId='" + userId + '\'' +
                ", email='" + email + '\'' +
                ", creditCard='" + creditCard + '\'' +
                '}';
    }
}
