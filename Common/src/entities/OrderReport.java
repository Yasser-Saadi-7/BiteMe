package entities;

import java.io.Serializable;

/**
 * Represents an order report in the system.
 */
public class OrderReport implements Serializable {
	private static final long serialVersionUID = 1L;

	private String reportId; // Unique identifier for the order report
	private String branchId; // ID of the branch associated with the report
	private String month; // Month of the report
	private String year; // Year of the report
	private String restaurantName; // Name of the restaurant
	private String itemId; // Unique identifier for the item
	private String itemName; // Name of the item
	private String itemCategory; // Category of the item
	private String quantity; // Quantity of the item
	private String itemPrice; // Unit price of the item
	private String totalRevenue; // Total revenue for this order report

	/**
	 * Constructor to initialize an OrderReport object with the specified details.
	 */
	public OrderReport(String reportId, String branchId, String month, String year, String restaurantName,
			String itemId, String itemName, String itemCategory, String quantity, String itemPrice, String totalRevenue) {
		this.reportId = reportId;
		this.branchId = branchId;
		this.month = month;
		this.year = year;
		this.restaurantName = restaurantName;
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemCategory = itemCategory;
		this.quantity = quantity;
		this.itemPrice = itemPrice;
		// Calculate total revenue as a String
		this.totalRevenue = calculateTotalRevenue(quantity, itemPrice);
	}
	public OrderReport() {
		
	}

	// Getters and Setters for each attribute

	public void setTotalRevenue(String totalRevenue) {
		this.totalRevenue = totalRevenue;
	}

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemCategory() {
		return itemCategory;
	}

	public void setItemCategory(String itemCategory) {
		this.itemCategory = itemCategory;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;

	}

	public String getTotalRevenue() {
		return totalRevenue;
	}

	/**
	 * Calculates the total revenue based on quantity and item price.
	 *
	 * @param quantity  The quantity of the item.
	 * @param itemPrice The price of the item.
	 * @return The calculated total revenue as a String.
	 */
	private String calculateTotalRevenue(String quantity, String itemPrice) {
		try {
			int qty = Integer.parseInt(quantity);
			double price = Double.parseDouble(itemPrice);
			return String.format("%.2f", qty * price); // Format to 2 decimal places
		} catch (NumberFormatException e) {
			return "0.00"; // Return 0.00 if there's a number format issue
		}
	}

	public String getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(String itemPrice) {
		this.itemPrice = itemPrice;
		this.totalRevenue = calculateTotalRevenue(this.quantity, itemPrice); // Update total revenue when item price changes
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * Returns a string representation of the OrderReport object for better
	 * debugging and printing.
	 *
	 * @return A string representation of the OrderReport.
	 */
	@Override
	public String toString() {
		return "OrderReport{" +
				"reportId='" + reportId + '\'' +
				", branchId='" + branchId + '\'' +
				", month='" + month + '\'' +
				", year='" + year + '\'' +
				", restaurantName='" + restaurantName + '\'' +
				", itemId='" + itemId + '\'' +
				", itemName='" + itemName + '\'' +
				", itemCategory='" + itemCategory + '\'' +
				", quantity='" + quantity + '\'' +
				", itemPrice='" + itemPrice + '\'' +
				", totalRevenue='" + totalRevenue + '\'' +
				'}';
	}
}
