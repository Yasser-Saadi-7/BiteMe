package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import logic.CreateAccount;
import logic.Dish;
import logic.MealsType;
import logic.Order;
import logic.Restaurant;
import logic.Selection;

public class mysqlConnection {
	private static Connection con = null;

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
		Order message;
		message = (Order) msg;
		orderNum = message.getOrederNumber();
		PreparedStatement preparedStatement;
		try {
			preparedStatement = con.prepareStatement(
					"UPDATE project.order SET Restaurant = ?, Total_price = ?,Order_list_number=? , Order_address=? WHERE Order_number = ?");
			preparedStatement.setString(1, message.getRestaurant());
			preparedStatement.setString(2, message.getTprice());
			preparedStatement.setString(3, message.getListNumber());
			preparedStatement.setString(4, message.getOrderAddress());
			preparedStatement.setString(5, message.getOrederNumber());
			// Execute the update
			int rowsAffected = preparedStatement.executeUpdate();

			// Check how many rows were affected
			System.out.println("Order updated.Rows affected: " + rowsAffected);
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

	public ArrayList<CreateAccount> getAccountsFromDB(String userID) {
		ArrayList<CreateAccount> accountList = new ArrayList<>();
		String query = "SELECT * FROM project.users WHERE userID = ?";

		try (PreparedStatement pstmt = con.prepareStatement(query)) {
			pstmt.setString(1, userID);
			ResultSet res = pstmt.executeQuery();

			while (res.next()) {
				String firstName = res.getString("firstName");
				String lastName = res.getString("lastName");
				String phone = res.getString("phone");
				String email = res.getString("email");
				String creditCard = res.getString("creditCard");
				String userType = res.getString("userType");
				String username = res.getString("username");
				String password = res.getString("password");

				CreateAccount account = new CreateAccount(userID, firstName, lastName, email, phone, creditCard,
						userType, username, password);
				accountList.add(account);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return accountList;
	}

	public boolean createAccount(String userID, String firstName, String lastName, String email, String phone,
	        String creditCard, String userType, String username, String password) {
	    // Prepare the SQL statement
	    String sql = "INSERT INTO project.users (userID, firstName, lastName, email, phone, creditCard, userType, username, password) "
	            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

	    // Check if user already exists
	    if (userExists(userID)) {
	        System.out.println("User already exists: " + userID);
	        return false; // Return false if user already exists
	    }

	    // Use try-with-resources for automatic resource management
	    try (PreparedStatement pstmt = con.prepareStatement(sql)) {
	        // Set parameters
	        pstmt.setString(1, userID);
	        pstmt.setString(2, firstName);
	        pstmt.setString(3, lastName);
	        pstmt.setString(4, email);
	        pstmt.setString(5, phone);
	        pstmt.setString(6, creditCard);
	        pstmt.setString(7, userType);
	        pstmt.setString(8, username);
	        pstmt.setString(9, password);

	        // Debug: print SQL command and parameters
	        System.out.println("Executing: " + pstmt.toString());

	        int rowsAffected = pstmt.executeUpdate();
	        System.out.println("Rows affected: " + rowsAffected);
	        return rowsAffected > 0; // Return true if at least one row was inserted

	    } catch (SQLException e) {
	        System.out.println("Error while creating account: " + e.getMessage());
	        e.printStackTrace(); // Print the stack trace for debugging
	        return false; // Return false if an error occurred
	    }
	}


	// Method to check if user already exists
	public boolean userExists(String userID) {
		String sql = "SELECT COUNT(*) FROM project.users WHERE userID = ?";
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, userID);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1) > 0; // Return true if user exists
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false; // Return false if any error occurs
	}
}