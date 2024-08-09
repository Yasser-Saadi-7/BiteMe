package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import logic.Branches;
import logic.CreateAccount;
import logic.Dish;
import logic.IncomeReport;
import logic.MealType;
import logic.Order;
import logic.PerformanceReport;
import logic.Restaurant;
import logic.Selection;
import logic.UserType;

public class mysqlConnection {
    private static Connection con = null;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public static boolean ConnectToDB(String user, String password) {
        if (con == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                System.out.println("Driver definition succeeded");
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

    public Order parseTheData(Object msg) {
        String message = (String) msg;
        String query = "SELECT * FROM project.orders WHERE orderId = ?";

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, Integer.parseInt(message));
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int orderNum = rs.getInt("orderId");
                String userID = rs.getString("userID");
                int restaurantId = rs.getInt("restaurantId");
                double totalPrice = rs.getDouble("totalPrice");
                String orderAddress = rs.getString("orderAddress");
                Timestamp orderDate = rs.getTimestamp("orderDate"); 
                String expectedDeliveryTime = rs.getString("expectedDeliveryTime");
                String actualDeliveryTime = rs.getString("actualDeliveryTime");
                return new Order(orderNum, userID, restaurantId, totalPrice, orderAddress, orderDate, 0, expectedDeliveryTime, actualDeliveryTime);
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
        String query = "SELECT * FROM project.orders";

        try (Statement stmt = con.createStatement();
             ResultSet res = stmt.executeQuery(query)) {
            while (res.next()) {
                Order temp = new Order(
                        res.getInt("orderId"),
                        res.getString("userID"),
                        res.getInt("restaurantId"),
                        res.getDouble("totalPrice"),
                        res.getString("orderAddress"),
                        res.getTimestamp("orderDate"), // Use Timestamp for date and time
                        0, // Default values for new fields
                        res.getString("expectedDeliveryTime"),
                        res.getString("actualDeliveryTime")
                );
                list.add(temp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static String userLogIn(String username, String password) {
        String query = "SELECT * FROM project.users WHERE username = ? AND password = ?";

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet res = pstmt.executeQuery();

            if (res.next()) {
                return res.getString("userType");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "error";
    }

    public static ArrayList<Restaurant> getAllRes() {
        ArrayList<Restaurant> allRes = new ArrayList<>();
        String query = "SELECT * FROM project.restaurants";

        try (Statement stmt = con.createStatement();
             ResultSet res = stmt.executeQuery(query)) {
            while (res.next()) {
                allRes.add(new Restaurant(
                        res.getInt("restaurantId"),
                        res.getInt("branchId"),
                        res.getString("restaurantName")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allRes;
    }

    public static ArrayList<MealType> getMealsType(String resName) {
        ArrayList<MealType> allMealsType = new ArrayList<>();
        String query = "SELECT * FROM project.mealtypes";

        try (Statement stmt = con.createStatement();
             ResultSet res = stmt.executeQuery(query)) {
            while (res.next()) {
                if (resName.equals(res.getString("restaurantId"))) {
                    allMealsType.add(new MealType(
                            res.getString("mealTypeId"),
                            res.getString("mealTypeName"),
                            res.getString("description"),
                            res.getInt("restaurantId")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allMealsType;
    }

    public static ArrayList<Dish> getDishes(String typeMealId) {
        ArrayList<Dish> allDishes = new ArrayList<>();
        String query = "SELECT * FROM project.dishes";

        try (Statement stmt = con.createStatement();
             ResultSet res = stmt.executeQuery(query)) {
            while (res.next()) {
                if (typeMealId.equals(res.getString("mealTypeId"))) {
                    allDishes.add(new Dish(
                            res.getString("dishId"),
                            res.getString("restaurantId"),
                            res.getString("dishName"),
                            res.getString("mealTypeId"),
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

        try (Statement stmt = con.createStatement();
             ResultSet res = stmt.executeQuery(query)) {
            while (res.next()) {
                if (dishId.equals(res.getString("dishId"))) {
                    allSelections.add(new Selection(
                            res.getString("selectionId"),
                            res.getString("dishId"),
                            res.getString("optionName"),
                            res.getDouble("price")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allSelections;
    }

    public ArrayList<CreateAccount> getAccountsFromDB(String userID) {
        ArrayList<CreateAccount> accountList = new ArrayList<>();
        String query = "SELECT * FROM project.users WHERE userID = ?";

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, userID);
            ResultSet res = pstmt.executeQuery();

            while (res.next()) {
                CreateAccount account = new CreateAccount(
                        userID,
                        res.getString("firstName"),
                        res.getString("lastName"),
                        res.getString("email"),
                        res.getString("phone"),
                        res.getString("creditCard"),
                        UserType.valueOf(res.getString("userType").toUpperCase()),
                        res.getString("username"),
                        res.getString("password")
                );
                accountList.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accountList;
    }

    public boolean createAccount(String userID, String firstName, String lastName, String email, String phone,
                                  String creditCard, UserType userType, String username, String password) {
        String sql = "INSERT INTO project.users (userID, firstName, lastName, email, phone, creditCard, userType, username, password) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

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

            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);
            return true; // Account created successfully
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Account creation failed
        }
    }

    public boolean userExists(String userID) {
        String sql = "SELECT * FROM project.users WHERE userID = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, userID);
            ResultSet res = pstmt.executeQuery();
            return res.next(); // Returns true if user exists
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // User does not exist
    }

    public void insertOrderIntoDB(Order order) {
        String sql = "INSERT INTO project.orders (userID, restaurantId, totalPrice, orderAddress, orderDate, expectedDeliveryTime, actualDeliveryTime) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, order.getUserID());
            pstmt.setInt(2, order.getRestaurantId());
            pstmt.setDouble(3, order.getTotalPrice());
            pstmt.setString(4, order.getOrderAddress());
            pstmt.setTimestamp(5, new java.sql.Timestamp(order.getOrderDate().getTime())); // Convert to Timestamp
            pstmt.setString(6, order.getExpectedDeliveryTime());
            pstmt.setString(7, order.getActualDeliveryTime());
            pstmt.executeUpdate();
            System.out.println("Order inserted successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<IncomeReport> getIncomeReports(String selectedBranch) {
        ArrayList<IncomeReport> incomeReports = new ArrayList<>();
        String query = "SELECT * FROM project.incomereports WHERE branchId = ?"; // Assuming branchId is used for filtering

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, selectedBranch); // Use selectedBranch instead of userID
            ResultSet res = pstmt.executeQuery();

            while (res.next()) {
                IncomeReport report = new IncomeReport(
                        selectedBranch, // Pass selectedBranch
                        res.getDouble("totalIncome"),
                        res.getTimestamp("reportDate") // Use Timestamp
                );
                incomeReports.add(report);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return incomeReports;
    }

    public ArrayList<PerformanceReport> getPerformanceReports(int branchId) {
        ArrayList<PerformanceReport> performanceReports = new ArrayList<>();
        String query = "SELECT * FROM project.performancereports WHERE branchId = ?";

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, branchId); // Pass branchId for filtering
            ResultSet res = pstmt.executeQuery();

            while (res.next()) {
                PerformanceReport report = new PerformanceReport(
                        res.getInt("reportId"),
                        res.getInt("branchId"),
                        res.getDate("reportDate"), // Use Timestamp
                        res.getInt("totalOrders"),
                        res.getDouble("totalRevenue"),
                        res.getInt("averageExpectedDeliveryTime"),
                        res.getInt("averageActualDeliveryTime"),
                        res.getDouble("onTimeDeliveryRate")
                );
                performanceReports.add(report);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return performanceReports;
    }

    public ArrayList<Branches> getBranchesForManager(String managerIdForBranches) {
        ArrayList<Branches> branchesList = new ArrayList<>();
        String query = "SELECT * FROM project.branches WHERE branchId IN (SELECT branchId FROM project.branch_managers WHERE managerId = ?)";

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, managerIdForBranches);
            ResultSet res = pstmt.executeQuery();

            while (res.next()) {
                Branches branch = new Branches(
                    res.getInt("branchId"),
                    res.getString("branchName"), // Assuming branchName is a column
                    res.getString("location") // Assuming location is a column
                );
                branchesList.add(branch);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return branchesList;
    }

    public ArrayList<Order> getOrdersForBranch(String managerIdForOrders, String month, String year) {
        ArrayList<Order> ordersList = new ArrayList<>();
        String query = "SELECT * FROM project.orders WHERE restaurantId IN (SELECT restaurantId FROM project.restaurants WHERE branchId IN (SELECT branchId FROM project.branch_managers WHERE managerId = ?)) " +
                       "AND MONTH(orderDate) = ? AND YEAR(orderDate) = ?";

        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, managerIdForOrders);
            pstmt.setInt(2, Integer.parseInt(month)); // Parsing month to Integer
            pstmt.setInt(3, Integer.parseInt(year));  // Parsing year to Integer
            ResultSet res = pstmt.executeQuery();

            while (res.next()) {
                Order order = new Order(
                    res.getInt("orderId"),
                    res.getString("userID"),
                    res.getInt("restaurantId"),
                    res.getDouble("totalPrice"),
                    res.getString("orderAddress"),
                    res.getTimestamp("orderDate"), // Use Timestamp for date and time
                    0,
                    res.getString("expectedDeliveryTime"),
                    res.getString("actualDeliveryTime")
                );
                ordersList.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ordersList;
    }

    public boolean assignBranchToManager(int branchId, String managerId) {
        String sql = "INSERT INTO project.branch_managers (branchId, managerId) VALUES (?, ?)";
        
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, branchId);
            pstmt.setString(2, managerId);
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Branch assigned to manager successfully.");
                return true; // Assignment was successful
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Assignment failed
        }
        return false; // Default return false if no rows were affected
    }

    public String formatDate(Date date) {
        return DATE_FORMAT.format(date);
    }

    public Date parseDate(String dateString) {
        try {
            return new java.sql.Date(DATE_FORMAT.parse(dateString).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
