package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entities.AccountType;
import entities.Branches;
import entities.DeliveryType1;
import entities.Dish;
import entities.IncomeReport;
import entities.LogIn;
import entities.MealsType;
import entities.Order;
import entities.OrderList;
import entities.OrderListSponser;
import entities.OrderReport;
import entities.PerformanceReport;
import entities.Restaurant;
import entities.Selection;

/**
 * The `mysqlConnection` class provides various methods to interact with a MySQL
 * database. It allows for connecting to the database, retrieving, updating, and
 * inserting data, and managing various entities such as orders, users,
 * restaurants, meals, dishes, and delivery types.
 * 
 * @author Yasser Saadi
 * 
 */
public class mysqlConnection {
	/** The database connection instance */
	private static Connection con = null;

	/**
	 * Connects to the MySQL database using the provided username and password.
	 * 
	 * @param user     the username for the database
	 * @param password the password for the database
	 * @return true if the connection is successful, false otherwise
	 */
	public static boolean ConnectToDB(String user, String password) {

		if (con == null) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
				System.out.println("Driver definition succeed");
			} catch (Exception ex) {
				/* handle the error */
				System.out.println("Driver definition failed");
			}

			try {
				con = DriverManager.getConnection("jdbc:mysql://localhost/project?serverTimezone=IST", user, password);
				// Connection conn =
				// DriverManager.getConnection("jdbc:mysql://192.168.3.68/test","root","Root");
				System.out.println("SQL connection succeed");
				return true;
			} catch (SQLException ex) {/* handle any errors */
				System.out.println("SQLException: " + ex.getMessage());
				System.out.println("SQLState: " + ex.getSQLState());
				System.out.println("VendorError: " + ex.getErrorCode());
			}
		}
		return false;

	}

	/**
	 * Parses the provided message to retrieve order data from the database.
	 * 
	 * @param msg the message containing the order number
	 * @return an Order object containing the order details, or null if no matching
	 *         order is found
	 */
	public Order parseTheData(Object msg) {
		String message = (String) msg;
		String orderNum, res, price, listNum, address;
		try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM project.order;")) {
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// Method to save user data to the database
	public void updateOrderToDB(Object msg) {
		Order message = (Order) msg;
		String orderNum = message.getOrderNumber();
		try (PreparedStatement preparedStatement = con.prepareStatement(
				"UPDATE project.order_list SET Restaurant = ?, Total_price = ?, Order_list_number = ?, Order_address = ? WHERE Order_number = ?")) {
			preparedStatement.setString(1, message.getRestaurant());
			preparedStatement.setString(2, message.getTprice());
			preparedStatement.setString(3, message.getListNumber());
			preparedStatement.setString(4, message.getOrderAddress());
			preparedStatement.setString(5, orderNum);
			int rowsAffected = preparedStatement.executeUpdate();
			System.out.println("Order updated. Rows affected: " + rowsAffected);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean insertOrderReportToDB(Object msg) throws SQLException {
        // המרה בטוחה של האובייקט לרשימה
        @SuppressWarnings("unchecked") 
        ArrayList<OrderReport> reportList = (ArrayList<OrderReport>) msg;

        // שאילתת ההכנסה המוגדרת
        String query = "INSERT INTO project.orders_reports " +
                       "(reportId, branchId, month, year, restaurantName, " +
                       "itemId, itemName, itemCategory, quantity, itemPrice, totalRevenue) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"; 

        // שימוש ב-try-with-resources
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            
            for (OrderReport report : reportList) { 
                
                // 1. הגדרת הפרמטרים לשאילתה הנוכחית
                preparedStatement.setString(1, report.getReportId());
                preparedStatement.setString(2, report.getBranchId());
                preparedStatement.setString(3, report.getMonth());
                preparedStatement.setString(4, report.getYear());
                preparedStatement.setString(5, report.getRestaurantName());
                preparedStatement.setString(6, report.getItemId());
                preparedStatement.setString(7, report.getItemName());
                preparedStatement.setString(8, report.getItemCategory());
                preparedStatement.setString(9, report.getQuantity());
                preparedStatement.setString(10, report.getItemPrice());
                preparedStatement.setString(11, report.getTotalRevenue());
                
                // 2. הוספת השאילתה המוגדרת לתור האצווה
                preparedStatement.addBatch();
            }
           
            // 3. ביצוע כל השאילתות בתור האצווה בבת אחת
            int[] rowsAffected = preparedStatement.executeBatch();
            
            // סכימת הרשומות שהוכנסו בהצלחה
            int totalRows = 0;
            for (int count : rowsAffected) {
                totalRows += count;
            }
            
            System.out.println("OrderReport inserted. Total rows affected: " + totalRows);
            
            return true; // הוספה הצליחה

        } 
        // לא מטפל ב-SQLException כאן, אלא נותן ל-EchoServer לטפל
        // כך שגם במקרה של כישלון, ה-EchoServer יכול לשלוח תגובה ללקוח.
    }

	/**
	 * Retrieves all orders from the database.
	 * 
	 * @return an ArrayList of Order objects containing all orders in the database
	 */
	public static ArrayList<Order> getOrdersFromDB() {
		ArrayList<Order> list = new ArrayList<Order>();

		try {
			Statement stmt = con.createStatement();
			ResultSet res = stmt.executeQuery("SELECT * FROM project.order;");

			while (res.next()) {
				Order temp = new Order(res.getString(2), res.getString(1), res.getString(3), res.getString(5),
						res.getString(4));
				list.add(temp);
			}

			res.close();
		} catch (SQLException var5) {
			var5.printStackTrace();
		}

		return list;
	}

	/**
	 * Authenticates a user by checking the provided username and password against
	 * the database.
	 * 
	 * @param username the username to authenticate
	 * @param password the password to authenticate
	 * @return a LogIn object if authentication is successful, or null if
	 *         authentication fails
	 */
	public static LogIn userLogIn(String username, String password) {
		try {
			Statement stmt = con.createStatement();
			ResultSet res = stmt.executeQuery("SELECT * FROM project.users;");

			while (res.next()) {
				if (res.getString(8).equals(username) && res.getString(9).equals(password)) {
					return new LogIn(res.getString(1), res.getString(2), res.getString(3), res.getString(4),
							res.getString(5), res.getString(6), res.getString(7), res.getString(8), res.getString(9),
							res.getString(12), res.getString(10));
				}
			}

			res.close();
		} catch (SQLException var5) {
			var5.printStackTrace();
		}
		return null;

	}

	/**
	 * Retrieves all restaurants from the database.
	 * 
	 * @return an ArrayList of Restaurant objects containing all restaurants in the
	 *         database
	 */
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

	/**
	 * Retrieves all meal types associated with a specific restaurant from the
	 * database.
	 * 
	 * @param resName the name of the restaurant
	 * @return an ArrayList of MealsType objects associated with the specified
	 *         restaurant
	 */
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

	/**
	 * Retrieves all dishes associated with a specific meal type from the database.
	 * 
	 * @param typeMealId the ID of the meal type
	 * @return an ArrayList of Dish objects associated with the specified meal type
	 */
	public static ArrayList<Dish> getDishes(String typeMealId) {
		ArrayList<Dish> allDishes = new ArrayList<Dish>();
		try {
			Statement stmt = con.createStatement();
			ResultSet res = stmt.executeQuery("SELECT * FROM project.dishes;");

			while (res.next()) {
				if (typeMealId.equals(res.getString(4))) {
					allDishes.add(new Dish(res.getString(1), res.getString(2), res.getString(3), res.getString(4),
							res.getString(5), res.getString(6)));
				}
			}

			res.close();
		} catch (SQLException var5) {
			var5.printStackTrace();
		}
		return allDishes;
	}

	/**
	 * Retrieves all selections associated with a specific dish from the database.
	 * 
	 * @param dishId the ID of the dish
	 * @return an ArrayList of Selection objects associated with the specified dish
	 */
	public static ArrayList<Selection> getSelections(String dishId) {
		ArrayList<Selection> allSelections = new ArrayList<Selection>();
		try {
			Statement stmt = con.createStatement();
			ResultSet res = stmt.executeQuery("SELECT * FROM project.selections;");

			while (res.next()) {
				if (dishId.equals(res.getString(2))) {
					allSelections
							.add(new Selection(res.getString(1), res.getString(2), res.getString(3), res.getString(4)));
				}
			}

			res.close();
		} catch (SQLException var5) {
			var5.printStackTrace();
		}
		return allSelections;
	}

	/**
	 * Retrieves all delivery types from the database.
	 * 
	 * @return an ArrayList of DeliveryType1 objects containing all delivery types
	 *         in the database
	 */
	public static ArrayList<DeliveryType1> getAllDeliveryTypes() {
		ArrayList<DeliveryType1> allDeliveryTypes = new ArrayList<DeliveryType1>();
		try {
			Statement stmt = con.createStatement();
			ResultSet res = stmt.executeQuery("SELECT * FROM project.delivery_types;");

			while (res.next()) {
				allDeliveryTypes.add(new DeliveryType1(res.getString(1), res.getString(2)));
			}

			res.close();
		} catch (SQLException var5) {
			var5.printStackTrace();
		}
		return allDeliveryTypes;

	}

	/**
	 * Inserts a new order into the database.
	 * 
	 * @param userId          the ID of the user placing the order
	 * @param orderListNumber the order list number
	 * @param restaurant      the name of the restaurant
	 * @param orderDate       the date the order was placed
	 * @param requestedDate   the requested delivery date
	 * @param totalPrice      the total price of the order
	 * @param address         the delivery address
	 * @param deliveryService the delivery service used
	 * @param status          the status of the order
	 * @param approval        the approval status of the order
	 * @param arrivalTime     the arrival time of the order
	 * @param requestedTime   the requested delivery time
	 */
	public static void insertOrder(String userId, String orderListNumber, String restaurant, String orderDate,
			String requestedDate, String totalPrice, String address, String deliveryService, String status,
			String approval, String arrivalTime, String requestedTime) {
		String sql = "INSERT INTO order_list (user_id, order_list_number, restaurant, order_date, requested_date, total_price, address, delivery_service, status, approval, arrival_time, requested_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try (PreparedStatement pstmt = con.prepareStatement(sql)) {

			// Set the values for the placeholders in the SQL query
			pstmt.setString(1, userId);
			pstmt.setString(2, orderListNumber);
			pstmt.setString(3, restaurant);
			pstmt.setString(4, orderDate);
			pstmt.setString(5, requestedDate);
			pstmt.setString(6, totalPrice);
			pstmt.setString(7, address);
			pstmt.setString(8, deliveryService);
			pstmt.setString(9, status);
			pstmt.setString(10, approval);
			pstmt.setString(11, arrivalTime);
			pstmt.setString(12, requestedTime);

			// Execute the insert statement
			int affectedRows = pstmt.executeUpdate();

			if (affectedRows > 0) {
				System.out.println("A new order was inserted successfully!");
			}
		} catch (SQLException e) {
			System.out.println("An error occurred while inserting the data: " + e.getMessage());
		}
	}

	/**
	 * Retrieves the last list number from the database.
	 * 
	 * @return the list number as a String, or null if an error occurs
	 */
	public static String getListNumber() {

		String sql = "SELECT list_number FROM project.list_number WHERE id = 1"; // Assuming 'id' is always 1 for the
																					// single row

		try (PreparedStatement pstmt = con.prepareStatement(sql);

				ResultSet rs = pstmt.executeQuery()) {

			if (rs.next()) {
				return rs.getString("list_number");
			} else {
				throw new SQLException("No data found in list_table.");
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null; // Return an error value or handle accordingly
		}
	}
	public static String getReportNumber() {

		String sql = "SELECT report_number FROM project.reportnum WHERE id = 1"; // Assuming 'id' is always 1 for the
																					

		try (PreparedStatement pstmt = con.prepareStatement(sql);

				ResultSet rs = pstmt.executeQuery()) {

			if (rs.next()) {
				return rs.getString("report_number");
			} else {
				throw new SQLException("No data found in list_table.");
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null; // Return an error value or handle accordingly
		}
	}

	// Method to update the list number
	public static void updateListNumber(String newListNumber) {

		String sql = "UPDATE project.list_number SET list_number = ? WHERE id = ?"; // Assuming 'id' is always 1 for the
																					// single row

		try (PreparedStatement pstmt = con.prepareStatement(sql)) {

			pstmt.setString(1, newListNumber);
			pstmt.setString(2, "1");
			int affectedRows = pstmt.executeUpdate();

			if (affectedRows > 0) {
				System.out.println("List number updated successfully.");
			} else {
				System.out.println("No row was updated.");
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void updateReportNumber(String newListNumber) {

		String sql = "UPDATE project.reportnum SET report_number = ? WHERE id = ?"; // Assuming 'id' is always 1 for the
																					// single row

		try (PreparedStatement pstmt = con.prepareStatement(sql)) {

			pstmt.setString(1, newListNumber);
			pstmt.setString(2, "1");
			int affectedRows = pstmt.executeUpdate();

			if (affectedRows > 0) {
				System.out.println("Report number updated successfully.");
			} else {
				System.out.println("No row was updated.");
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Retrieves the order list for a specific user from the database.
	 * 
	 * @param userID the ID of the user
	 * @return an ArrayList of OrderList objects containing the user's order list
	 */
	public ArrayList<OrderList> getOrderList(String userID) {
		ArrayList<OrderList> allOrders = new ArrayList<OrderList>();
		try {
			Statement stmt = con.createStatement();
			ResultSet res = stmt.executeQuery("SELECT * FROM project.order_list;");

			while (res.next()) {
				if (userID.equals(res.getString(1))) {
					allOrders.add(new OrderList(res.getString(2), res.getString(3), res.getString(11),
							res.getString(11), res.getString(9), res.getString(10), res.getString(7), res.getString(8),
							res.getString(6)));
				}
			}

			res.close();
		} catch (SQLException var5) {
			var5.printStackTrace();
		}
		return allOrders;
	}

	/**
	 * Updates the status of an order in the database.
	 * 
	 * @param orderListNumber the order list number for which the status needs to be
	 *                        updated
	 */
	public void updateStatus(String orderListNumber) {
		String sql = "UPDATE project.order_list SET status = ? WHERE order_list_number = ?"; // Assuming 'id' is always
																								// 1 for the single row

		try (PreparedStatement pstmt = con.prepareStatement(sql)) {

			pstmt.setString(1, "Take It");
			pstmt.setString(2, orderListNumber);
			int affectedRows = pstmt.executeUpdate();

			if (affectedRows > 0) {
				System.out.println("Status updated successfully.");
			} else {
				System.out.println("No row was updated.");
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	// method to return the user type as a String
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

	// Method to create a user account
	public boolean createAccount(String userID, String firstName, String lastName, String email, String phone,
			String creditCard, String userType, String username, String password, String homeBranchId,
			AccountType accountType, String homeBranchName) {

		// Check if the user already exists
		if (userExists(userID)) {
			System.out.println("User already exists: " + userID);
			return false; // User already exists
		}

		// SQL statement to insert the user into the database
		String sql = "INSERT INTO project.users (userID, firstName, lastName, email, phone, creditCard, userType, username, password, homeBranchId, accountType, homeBranchName) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, userID);
			pstmt.setString(2, firstName);
			pstmt.setString(3, lastName);
			pstmt.setString(4, email);
			pstmt.setString(5, phone);
			pstmt.setString(6, creditCard);
			pstmt.setString(7, userType);
			pstmt.setString(8, username);
			pstmt.setString(9, password);
			pstmt.setString(10, homeBranchId); // Set homeBranchId
			pstmt.setString(11, accountType.toString()); // Set account type
			pstmt.setString(12, homeBranchName); // Set homeBranchName if required
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

	public boolean assignBranchToManager(String branchId, String managerId) {
		String sql = "INSERT INTO project.branch_managers (managerId, branchId) VALUES (?, ?)";
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, managerId);
			pstmt.setString(2, branchId);
			int rowsAffected = pstmt.executeUpdate();
			return rowsAffected > 0; // Return true if a row was inserted
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false; // Return false if there was an error or no rows were affected
	}

	/**
	 * Retrieves all branches managed by a specific manager.
	 *
	 * @param managerId the ID of the manager
	 * @return a list of Branch objects associated with the manager
	 * @throws SQLException if a database access error occurs
	 */
	public static ArrayList<Branches> getBranchesForManager(String managerId) throws SQLException {
		ArrayList<Branches> branchesList = new ArrayList<>();
		String query = "SELECT b.branchId, b.branchName, b.location " + "FROM project.branches b "
				+ "JOIN project.branch_managers bm ON b.branchId = bm.branchId " + "WHERE bm.managerId = ?";

		try (PreparedStatement pstmt = con.prepareStatement(query)) {
			pstmt.setString(1, managerId);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					branchesList.add(new Branches(rs.getString("branchId"), rs.getString("branchName"),
							rs.getString("location")));
				}
			}
		}
		return branchesList; // Return the list of branches managed by the specified manager
	}

	/**
	 * Retrieves performance reports for a specific branch from the database.
	 * 
	 * @param branchId the ID of the branch for which to retrieve performance
	 *                 reports
	 * @return an ArrayList of PerformanceReport objects associated with the
	 *         specified branch
	 */
	/**
	 * Retrieves performance reports for a specific branch from the database for a
	 * given month and year.
	 * 
	 * @param branchId the ID of the branch for which to retrieve performance
	 *                 reports
	 * @param month    the month of the report in 'YYYY-MM' format
	 * @param year     the year of the report
	 * @return an ArrayList of PerformanceReport objects associated with the
	 *         specified branch
	 */
	public static ArrayList<PerformanceReport> getPerformanceReports(String branchId, String month, String year) {
		ArrayList<PerformanceReport> performanceReportsList = new ArrayList<>();
		String query = "SELECT reportId, branchId, month, year, totalOrders, totalRevenue, "
				+ "averageExpectedDeliveryTime, averageActualDeliveryTime, onTimeDeliveryRate "
				+ "FROM project.performance_reports WHERE branchId = ? " + "AND month = ? AND year = ?"; // Update WHERE

		try (PreparedStatement pstmt = con.prepareStatement(query)) {
			pstmt.setString(1, branchId); // Set branchId parameter
			pstmt.setString(2, month); // Set month parameter
			pstmt.setString(3, year); // Set year parameter
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				performanceReportsList.add(new PerformanceReport(rs.getString("reportId"), rs.getString("branchId"),
						rs.getString("month"), rs.getString("year"), // Use month and year from the result set
						rs.getString("totalOrders"), rs.getString("totalRevenue"),
						rs.getString("averageExpectedDeliveryTime"), rs.getString("averageActualDeliveryTime"),
						rs.getString("onTimeDeliveryRate")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return performanceReportsList;
	}

	/**
	 * Retrieves income reports for a specific branch from the database.
	 *
	 * @param branchId the ID of the branch for which to retrieve reports
	 * @param month    the month of the report
	 * @param year     the year of the report
	 * @return an ArrayList of IncomeReport objects associated with the specified
	 *         branch
	 */
	public static ArrayList<IncomeReport> getIncomeReports(String branchId, String month, String year) {
		ArrayList<IncomeReport> incomeReportsList = new ArrayList<>();
		String query = "SELECT reportId, branchId, totalIncome FROM project.income_reports "
				+ "WHERE branchId = ? AND month = ? AND year = ?";

		try (PreparedStatement pstmt = con.prepareStatement(query)) {
			pstmt.setString(1, branchId); // Set branchId parameter
			pstmt.setString(2, month); // Set month parameter
			pstmt.setString(3, year); // Set year parameter

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					incomeReportsList.add(new IncomeReport(rs.getString("reportId"), // Use reportId from the result set
							rs.getString("branchId"), // Use branchId from the result set
							String.valueOf(rs.getDouble("totalIncome")), // Convert totalIncome to String
							month, // Use the month parameter
							year // Use the year parameter
					));
				}
			}
		} catch (SQLException e) {
			// Consider using a logging framework instead of printing stack trace
			System.err.println("Error while fetching income reports: " + e.getMessage());
			e.printStackTrace();
		}

		return incomeReportsList;
	}

	// Method to insert a new order report into the database
	public void insertOrderReport(String reportId, String month, String year, String restaurantName, String branchId,
			String itemId, String itemName, String itemCategory, String quantity, String itemPrice) {
		String sql = "INSERT INTO orders_reports (reportId, month, year, restaurantName, branchId, itemId, "
				+ "itemName, itemCategory, quantity, itemPrice, totalRevenue) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, reportId);
			pstmt.setString(2, branchId);
			pstmt.setString(3, month);
			pstmt.setString(4, year);
			pstmt.setString(5, restaurantName);
			pstmt.setString(6, itemId);
			pstmt.setString(7, itemName);
			pstmt.setString(8, itemCategory);
			pstmt.setString(9, quantity);
			pstmt.setString(10, itemPrice);
			pstmt.setString(11, calculateTotalRevenue(quantity, itemPrice)); // Calculate total revenue before inserting

			int affectedRows = pstmt.executeUpdate();
			if (affectedRows > 0) {
				System.out.println("Order report inserted successfully!");
			}
		} catch (SQLException e) {
			System.out.println("An error occurred while inserting the order report: " + e.getMessage());
		}
	}

	// Method to retrieve order reports filtered by branchId, month, and year
	public ArrayList<OrderReport> getAllOrderReports(String branchId, String month, String year) {
		ArrayList<OrderReport> reports = new ArrayList<>();
		String sql = "SELECT * FROM orders_reports WHERE branchId = ? AND month = ? AND year = ?";

		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, branchId); // Set branchId parameter
			pstmt.setString(2, month); // Set month parameter
			pstmt.setString(3, year); // Set year parameter

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					OrderReport report = new OrderReport(rs.getString("reportId"), rs.getString("branchId"),
							rs.getString("month"), rs.getString("year"), rs.getString("restaurantName"),
							rs.getString("itemId"), rs.getString("itemName"), rs.getString("itemCategory"),
							rs.getString("quantity"), rs.getString("itemPrice"), rs.getString("totalRevenue"));
					reports.add(report);
				}
			}
		} catch (SQLException e) {
			System.err.println("An error occurred while retrieving order reports: " + e.getMessage());
			e.printStackTrace(); // Optionally log stack trace for debugging
		}

		return reports;
	}

	// Helper method to calculate total revenue (could also be a method in
	// OrderReport class)
	private String calculateTotalRevenue(String quantity, String itemPrice) {
		try {
			int qty = Integer.parseInt(quantity);
			double price = Double.parseDouble(itemPrice);
			return String.format("%.2f", qty * price); // Format to 2 decimal places
		} catch (NumberFormatException e) {
			return "0.00"; // Return 0.00 if there's a number format issue
		}
	}

	// Method to save user data to the database
	public void updateDishNameToDB(String dishId, String newDishName) {
		PreparedStatement preparedStatement;
		try {
			preparedStatement = con.prepareStatement("UPDATE project.dishes SET dish_name = ? WHERE dishId = ?");
			preparedStatement.setString(1, newDishName);
			preparedStatement.setString(2, dishId);
			// Execute the update
			int rowsAffected = preparedStatement.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Dish name updated successfully.");
			} else {
				System.out.println("No row was updated.");
			}

			// Check how many rows were affected
			System.out.println("Dish Name updated. The affected row: " + rowsAffected);
			preparedStatement.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

// Method to save user data to the database
	public void updateDishPriceToDB(String dishId, String newDishPrice) {
		PreparedStatement preparedStatement;
		try {
			preparedStatement = con.prepareStatement("UPDATE project.dishes SET price = ? WHERE dishId = ?");
			preparedStatement.setString(1, newDishPrice);
			preparedStatement.setString(2, dishId);
			// Execute the update
			int rowsAffected = preparedStatement.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Dish price updated successfully.");
			} else {
				System.out.println("No row was updated.");
			}

			// Check how many rows were affected
			System.out.println("Dish price updated. The affected row: " + rowsAffected);
			preparedStatement.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void addDishToDB(String dishId, String newDishName, String newDishPrice, String mealTypeId,
			String mealTypeName, String restaurantName) {
		String query = "INSERT INTO dishes (dishId, restaurant_name, dish_name, meal_type_id, meal_type_name, price) VALUES (?, ?, ?, ?, ?, ?)";
		try {
			// Prepare the SQL query to insert a new dish into the database

			// Use a PreparedStatement to securely insert the dish
			PreparedStatement preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, dishId);
			preparedStatement.setString(2, restaurantName);
			preparedStatement.setString(3, newDishName);
			preparedStatement.setString(4, mealTypeId);
			preparedStatement.setString(5, mealTypeName);
			preparedStatement.setString(6, newDishPrice);

			// Execute the update to insert the new dish into the database
			preparedStatement.executeUpdate();

			System.out.println("Dish added successfully: " + newDishName);

		} catch (SQLException e) {
			// Handle any SQL exceptions that may occur during the operation
			e.printStackTrace();
			System.err.println("Error adding dish to the database.");
		}
	}

// Method to delete a dish from the database
	public void deleteDishFromDB(String dishId) {
		PreparedStatement preparedStatement;

		try {
			// Prepare the SQL DELETE statement to remove a dish based on the dishId
			preparedStatement = con.prepareStatement("DELETE FROM project.dishes WHERE dishId = ?");

			// Bind the dishId to the SQL statement
			preparedStatement.setString(1, dishId);

			// Execute the deletion
			int rowsAffected = preparedStatement.executeUpdate();

			// Check how many rows were affected
			System.out.println("Dish deleted. Rows affected: " + rowsAffected);

			// Close the PreparedStatement to free resources
			preparedStatement.close();

		} catch (SQLException e) {
			// Handle SQL exceptions
			e.printStackTrace();
		}
	}

	public static String getDishId() {
		String sql = "SELECT dishId FROM project.dish_id_counter WHERE id = 1"; // Assuming 'id' is always 1 for the
																				// single row

		try (PreparedStatement pstmt = con.prepareStatement(sql);

				ResultSet rs = pstmt.executeQuery()) {

			if (rs.next()) {
				return rs.getString("dishId");
			} else {
				throw new SQLException("No data found in list_table.");
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null; // Return an error value or handle accordingly
		}
	}

	// Method to update the list number
	public static void updateDishId(String newDishNumber) {

		String sql = "UPDATE project.dish_id_counter SET dishId = ? WHERE id = ?"; // Assuming 'id' is always 1 for the
																					// single row

		try (PreparedStatement pstmt = con.prepareStatement(sql)) {

			pstmt.setString(1, newDishNumber);
			pstmt.setString(2, "1");
			int affectedRows = pstmt.executeUpdate();

			if (affectedRows > 0) {
				System.out.println("Dish number updated successfully.");
			} else {
				System.out.println("No row was updated.");
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static ArrayList<OrderListSponser> getAllOrders(String restaurant) {
		ArrayList<OrderListSponser> allOrders = new ArrayList<OrderListSponser>();
		try {
			Statement stmt = con.createStatement();
			ResultSet res = stmt.executeQuery("SELECT * FROM project.order_list;");

			while (res.next()) {
				if (restaurant.equals(res.getString(3))) {
					allOrders.add(new OrderListSponser(res.getString(1), res.getString(2), res.getString(3),
							res.getString(11), res.getString(11), res.getString(9), res.getString(10), res.getString(7),
							res.getString(8), res.getString(6), res.getString(12)));
				}
			}

			res.close();
		} catch (SQLException var5) {
			var5.printStackTrace();
		}
		return allOrders;

	}

	public void updateStatusReady(String orderListNumber) {
		String sql = "UPDATE project.order_list SET status = ? WHERE order_list_number = ?"; // Assuming 'id' is always
																								// 1 for the single row

		try (PreparedStatement pstmt = con.prepareStatement(sql)) {

			pstmt.setString(1, "Ready");
			pstmt.setString(2, orderListNumber);
			int affectedRows = pstmt.executeUpdate();

			if (affectedRows > 0) {
				System.out.println("Status updated successfully.");
			} else {
				System.out.println("No row was updated.");
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void updateApproval(String orderListNumber, String exTime) {
		String sql = "UPDATE project.order_list SET approval = ?,arrival_time=? WHERE order_list_number = ?"; // Assuming
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {

			pstmt.setString(1, "Approval");
			pstmt.setString(2, exTime);
			pstmt.setString(3, orderListNumber);
			int affectedRows = pstmt.executeUpdate();

			if (affectedRows > 0) {
				System.out.println("Approval updated successfully.");
			} else {
				System.out.println("No row was updated.");
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

}
