package Server;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import logic.Order;



public class mysqlConnection {
	private static Connection con = null;

	public static boolean ConnectToDB(String user,String password) {
		
		if(con==null) {
			try 
			{
				 Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
	            System.out.println("Driver definition succeed");
	        } catch (Exception ex) {
	        	/* handle the error*/
	        	 System.out.println("Driver definition failed");
	        	 }
			
			 try 
		        {
		            con = DriverManager.getConnection("jdbc:mysql://localhost/project?serverTimezone=IST",user,password);
		            //Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.3.68/test","root","Root");
		            System.out.println("SQL connection succeed");
		            return true;
		     	} 
			 catch (SQLException ex) 
		     	    {/* handle any errors*/
		            System.out.println("SQLException: " + ex.getMessage());
		            System.out.println("SQLState: " + ex.getSQLState());
		            System.out.println("VendorError: " + ex.getErrorCode());
		            }}
			 return false;
		
	}	

    
    public Order parseTheData(Object msg) {
    	String message,orderNum,res,price,listNum,address;
        message=(String)msg;
        Statement stmt;
        try {
        	stmt = con.createStatement();
        	ResultSet rs= stmt.executeQuery("SELECT * FROM project.order;");
        	while(rs.next()) {
        		orderNum=rs.getString(2);
        		if(orderNum.equals(message)) {
        			res=rs.getString(1);
        			price=rs.getString(3);
        			listNum=rs.getString(4);
        			address=rs.getString(5);
        			return new Order(orderNum,res,price,address,listNum);
        		}
        	}
        	rs.close();
			
		}catch (SQLException e) {e.printStackTrace();}
        return null;
        
    }
    

    // Method to save user data to the database
    public void updateOrderToDB(Object msg) {
    	String orderNum,res,price,listNum,address;
        Order message;
        message=(Order)msg;
        orderNum=message.getOrederNumber();
        PreparedStatement preparedStatement;
        try {
        	preparedStatement = con.prepareStatement("UPDATE project.order SET Restaurant = ?, Total_price = ?,Order_list_number=? , Order_address=? WHERE Order_number = ?");
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
			
		}catch (SQLException e) {e.printStackTrace();}
    }
    
    public static ArrayList<Order> getOrdersFromDB() {
    	ArrayList<Order> list = new ArrayList<Order>();

        try {
           Statement stmt = con.createStatement();
           ResultSet res = stmt.executeQuery("SELECT * FROM project.order;");

           while(res.next()) {
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

	           while(res.next()) {
	              if(res.getString(8).equals(username) && res.getString(9).equals(password)) {
	            	  return res.getString(7);
	              }
	           }

	           res.close();
	        } catch (SQLException var5) {
	           var5.printStackTrace();
	        }
		return "error";

	}
	
//	//Create Account 
//	public static String CreateAccount(String ID, String Lname) {
//		try {
//	           Statement stmt = con.createStatement();
//	           ResultSet res = stmt.executeQuery("SELECT * FROM project.users;");
////check if user already exist
//	           while(res.next()) {
//	              if(res.getString(1).equals(ID) && res.getString(2).equals(Lname)) {
//	            	  return res.getString(7);
//	              }
//	           }
//
//	           res.close();
//	        } catch (SQLException var5) {
//	           var5.printStackTrace();
//	        }
//		return "error";
//
//	}
//	
	
}



