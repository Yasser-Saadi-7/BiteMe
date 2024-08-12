package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import logic.AccountType;
import logic.Branches;
import logic.CreateAccount;
import logic.Dish;
import logic.DeliveryType1;
import logic.IncomeReport;
import logic.LogIn;
import logic.MealType;
import logic.Order;
import logic.OrderList;
import logic.PerformanceReport;
import logic.Restaurant;
import logic.Selection;
import logic.UserType;

public class mysqlConnection {
    private static Connection con = null;

    public static boolean connectToDB(String user, String password) {
        if (con == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                con = DriverManager.getConnection("jdbc:mysql://localhost/project?serverTimezone=IST", user, password);
                System.out.println("SQL connection succeeded");
                return true;
            } catch (SQLException ex) {
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    // Order Methods
    public Order parseTheData(Object msg) {
        String message = (String) msg;
        String query = "SELECT * FROM project.orders WHERE orderId = ?";

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, Integer.parseInt(message));
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Order(
                    rs.getInt("orderId"),
                    rs.getString("userID"),
                    rs.getInt("restaurantId"),
                    rs.getDouble("totalPrice"),
                    rs.getString("orderAddress"),
                    rs.getTimestamp("orderDate"),
                    rs.getInt("deliveryTypeId"),
                    rs.getString("expectedDeliveryTime"),
                    rs.getString("actualDeliveryTime"),
                    rs.getString("listNumber"),
                    rs.getInt("branchId"),
                    rs.getString("orderNum")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateOrderToDB(Object msg) {
        Order message = (Order) msg;
        String sql = "UPDATE project.orders SET restaurantId = ?, totalPrice = ?, orderAddress = ? WHERE orderId = ?";

        try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            preparedStatement.setInt(1, message.getRestaurantId());
            preparedStatement.setDouble(2, message.getTotalPrice());
            preparedStatement.setString(3, message.getOrderAddress());
            preparedStatement.setInt(4, message.getOrderId());
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Order updated. Rows affected: " + rowsAffected);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Order> getOrdersFromDB() {
        ArrayList<Order> list = new ArrayList<>();
        String query = "SELECT orderId, userID, restaurantId, totalPrice, orderAddress, orderDate, " +
                       "deliveryTypeId, expectedDeliveryTime, actualDeliveryTime, listNumber, branchId, orderNum FROM project.orders";

        try (Statement stmt = con.createStatement(); ResultSet res = stmt.executeQuery(query)) {
            while (res.next()) {
                list.add(new Order(
                    res.getInt("orderId"),
                    res.getString("userID"),
                    res.getInt("restaurantId"),
                    res.getDouble("totalPrice"),
                    res.getString("orderAddress"),
                    res.getTimestamp("orderDate"),
                    res.getInt("deliveryTypeId"),
                    res.getString("expectedDeliveryTime"),
                    res.getString("actualDeliveryTime"),
                    res.getString("listNumber"),
                    res.getInt("branchId"),
                    res.getString("orderNum")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static LogIn userLogIn(String username, String password) {
        String query = "SELECT userID, firstName, lastName, email, phone, creditCard, userType, username, password, homeBranchId, accountType FROM project.users WHERE username = ? AND password = ?";
        
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet res = pstmt.executeQuery();

            if (res.next()) {
                return new LogIn(
                    res.getString("userID"),
                    res.getString("firstName"),
                    res.getString("lastName"),
                    res.getString("email"),
                    res.getString("phone"),
                    res.getString("creditCard"),
                    UserType.valueOf(res.getString("userType")),
                    res.getString("username"),
                    res.getString("password"),
                    res.getInt("homeBranchId"),
                    AccountType.valueOf(res.getString("accountType"))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // New method to return the user type as a String
    public static String getUserType(String username, String password) {
        String query = "SELECT userType FROM project.users WHERE username = ? AND password = ?";
        
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet res = pstmt.executeQuery();

            if (res.next()) {
                return res.getString("userType"); // Return the userType directly as a String
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Restaurant> getAllRes() {
        ArrayList<Restaurant> allRes = new ArrayList<>();
        String query = "SELECT * FROM project.restaurants";

        try (Statement stmt = con.createStatement(); ResultSet res = stmt.executeQuery(query)) {
            while (res.next()) {
                allRes.add(new Restaurant(
                    res.getInt("restaurantId"), res.getInt("branchId"),
                    res.getString("restaurantName")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allRes;
    }

    public static ArrayList<MealType> getMealType(int restaurantId) {
        ArrayList<MealType> allMealType = new ArrayList<>();
        String query = "SELECT * FROM project.mealtypes";

        try (Statement stmt = con.createStatement(); ResultSet res = stmt.executeQuery(query)) {
            while (res.next()) {
                if (restaurantId == res.getInt("restaurantId")) {
                    allMealType.add(new MealType(
                        res.getString("restaurantName"),
                        res.getString("mealTypeId"),
                        res.getString("mealTypeName"),
                        restaurantId
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allMealType;
    }

    public static ArrayList<Dish> getDishes(String typeMealId) {
        ArrayList<Dish> allDishes = new ArrayList<>();
        String query = "SELECT * FROM project.dishes";

        try (Statement stmt = con.createStatement(); ResultSet res = stmt.executeQuery(query)) {
            while (res.next()) {
                if (typeMealId.equals(res.getString("mealTypeId"))) {
                    allDishes.add(new Dish(
                        res.getString("dishId"), res.getString("restaurantId"),
                        res.getString("dishName"), res.getString("mealTypeId"),
                        res.getDouble("price")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allDishes;
    }

    public static ArrayList<Selection> getSelections(String dishId) {
        ArrayList<Selection> allSelections = new ArrayList<>();
        String query = "SELECT * FROM project.selections";

        try (Statement stmt = con.createStatement(); ResultSet res = stmt.executeQuery(query)) {
            while (res.next()) {
                if (dishId.equals(res.getString("dishId"))) {
                    allSelections.add(new Selection(
                        res.getString("selectionId"), res.getString("dishId"),
                        res.getString("optionName"), res.getDouble("price")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allSelections;
    }

    public static ArrayList<DeliveryType1> getAllDeliveryTypes() {
        ArrayList<DeliveryType1> allDeliveryTypes = new ArrayList<>();
        String query = "SELECT * FROM project.delivery_types";

        try (Statement stmt = con.createStatement(); ResultSet res = stmt.executeQuery(query)) {
            while (res.next()) {
                allDeliveryTypes.add(new DeliveryType1(
                    res.getInt("id"),
                    res.getString("typeName"),
                    res.getDouble("price")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allDeliveryTypes;
    }

    public boolean createAccount(String userID, String firstName, String lastName, String email, String phone,
                                  String creditCard, UserType userType, String username, String password, int homeBranchId,
                                  AccountType accountType) {
        String sql = "INSERT INTO project.users (userID, firstName, lastName, email, phone, creditCard, userType, username, password, homeBranchId, accountType) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        if (userExists(userID)) {
            System.out.println("User already exists: " + userID);
            return false; // User already exists
        }

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, userID);
            pstmt.setString(2, firstName);
            pstmt.setString(3, lastName);
            pstmt.setString(4, email);
            pstmt.setString(5, phone);
            pstmt.setString(6, creditCard);
            pstmt.setString(7, userType.toString());
            pstmt.setString(8, username);
            pstmt.setString(9, password);
            pstmt.setInt(10, homeBranchId);
            pstmt.setString(11, accountType.toString());
            pstmt.executeUpdate();
            return true; // User created successfully
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // User creation failed
    }

    public static boolean userExists(String userID) {
        String sql = "SELECT COUNT(*) FROM project.users WHERE userID = ?";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, userID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Returns true if user exists
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // User does not exist
    }

    public static void main(String[] args) {
        connectToDB("root", "password"); // replace with actual credentials
    }

    public ArrayList<PerformanceReport> getPerformanceReports(int branchId) {
        // TODO Auto-generated method stub
        return null;
    }

    public ArrayList<IncomeReport> getIncomeReports(String selectedBranch) {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean assignBranchToManager(int branchId, String managerId) {
        // TODO Auto-generated method stub
        return false;
    }

    public ArrayList<Branches> getBranchesForManager(String managerIdForBranches) {
        // TODO Auto-generated method stub
        return null;
    }

    public ArrayList<Order> getOrdersForBranch(String managerIdForOrders, String month, String year) {
        // TODO Auto-generated method stub
        return null;
    }
}
