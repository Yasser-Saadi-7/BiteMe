package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import logic.MealsType;
import logic.Order;
import logic.Restaurant;

public class mysqlConnection {
    private static Connection con = null;

    public static boolean ConnectToDB(String user, String password) {
        if (con == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                System.out.println("Driver definition succeed");
            } catch (Exception ex) {
                System.out.println("Driver definition failed");
            }

            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost/project?serverTimezone=IST", user, password);
                System.out.println("SQL connection succeed");
                return true;
            } catch (SQLException ex) {
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            }
        }
        return false;
    }

    public Order parseTheData(Object msg) {
        String message, orderNum, res, price, listNum, address;
        message = (String) msg;
        Statement stmt;
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM project.order;");
            while (rs.next()) {
                orderNum = rs.getString(2);
                if (orderNum.equals(message)) {
                    res = rs.getString(1);
                    price = rs.getString(3);
                    listNum = rs.getString(4);
                    address = rs.getString(5);
                    return new Order(orderNum, res, price, address, listNum);
                }
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method to save user data to the database
    public void updateOrderToDB(Object msg) {
        String orderNum, res, price, listNum, address;
        Order message = (Order) msg;
        orderNum = message.getOrederNumber();
        PreparedStatement preparedStatement;
        try {
            preparedStatement = con.prepareStatement("UPDATE project.order SET Restaurant = ?, Total_price = ?, Order_list_number = ?, Order_address = ? WHERE Order_number = ?");
            preparedStatement.setString(1, message.getRestaurant());
            preparedStatement.setString(2, message.getTprice());
            preparedStatement.setString(3, message.getListNumber());
            preparedStatement.setString(4, message.getOrderAddress());
            preparedStatement.setString(5, message.getOrederNumber());
            // Execute the update
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Order updated. Rows affected: " + rowsAffected);
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Order> getOrdersFromDB() {
        ArrayList<Order> list = new ArrayList<Order>();
        try {
            Statement stmt = con.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM project.order;");
            while (res.next()) {
                Order temp = new Order(res.getString(2), res.getString(1), res.getString(3), res.getString(5), res.getString(4));
                list.add(temp);
            }
            res.close();
        } catch (SQLException var5) {
            var5.printStackTrace();
        }
        return list;
    }

    public static String userLogIn(String username, String password) {
        try {
            Statement stmt = con.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM project.users;");
            while (res.next()) {
                if (res.getString(8).equals(username) && res.getString(9).equals(password)) {
                    return res.getString(7);
                }
            }
            res.close();
        } catch (SQLException var5) {
            var5.printStackTrace();
        }
        return "error";
    }

    // Method to check if the user ID is unique
    public boolean isUserIdUnique(String userId) {
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM project.users WHERE UserID = ?");
            stmt.setString(1, userId);
            ResultSet res = stmt.executeQuery();
            boolean isUnique = !res.next(); // If no records are found, the user ID is unique
            res.close();
            return isUnique;
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // In case of an error, assume it's not unique
        }
    }

    // Method to create a new user account
    public boolean createAccount(String firstName, String lastName, String phone, String userId, String email, String creditCard) {
        try {
            // Prepare the SQL statement with the CreditCard column that can accept NULL
            PreparedStatement stmt = con.prepareStatement("INSERT INTO project.users (FirstName, LastName, Phone, UserID, Email, CreditCard) VALUES (?, ?, ?, ?, ?, ?)");
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, phone);
            stmt.setString(4, userId);
            stmt.setString(5, email);

            // Check if creditCard is empty and set the prepared statement accordingly
            if (creditCard.isEmpty()) {
                stmt.setNull(6, java.sql.Types.VARCHAR); // Set CreditCard to NULL if empty
            } else {
                stmt.setString(6, creditCard); // Otherwise, set it to the provided value
            }

            int rowsInserted = stmt.executeUpdate();
            stmt.close();
            return rowsInserted > 0; // Return true if a row was inserted
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false in case of an error
        }
    }

    // To help assign a branch to each manager
    public static Connection getConnection() {
        return con;
    }

    public static class BranchService {
        public List<String> getBranchesForManager(int managerId) {
            List<String> branches = new ArrayList<>();
            String query = "SELECT b.name FROM branches b " +
                           "JOIN users u ON b.id = u.branch_id " +
                           "WHERE u.id = ? AND u.user_type = 'manager'";

            try (PreparedStatement stmt = mysqlConnection.getConnection().prepareStatement(query)) {
                stmt.setInt(1, managerId);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    branches.add(rs.getString("name"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return branches;
        }
    }

    public static ArrayList<Restaurant> getAllRes() {
        ArrayList<Restaurant> allRes = new ArrayList<Restaurant>();
        try {
            Statement stmt = con.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM project.restaurant;");

            while (res.next()) {
                allRes.add(new Restaurant(res.getString(1), res.getString(2), res.getString(3), res.getString(4)));
            }

            res.close();
        } catch (SQLException var5) {
            var5.printStackTrace();
        }
        return allRes;
    }

    public static ArrayList<MealsType> getMealsType(String resName) {
        ArrayList<MealsType> allMealsType = new ArrayList<MealsType>();
        try {
            Statement stmt = con.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM project.mealtype;");

            while (res.next()) {
                if (resName.equals(res.getString(1))) {
                    allMealsType.add(new MealsType(res.getString(1), res.getString(2), res.getString(3)));
                }
            }

            res.close();
        } catch (SQLException var5) {
            var5.printStackTrace();
        }
        return allMealsType;
    }
}
