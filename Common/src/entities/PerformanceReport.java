package entities;

import java.io.Serializable;

/**
 * Represents a performance report for a specific branch.
 */
public class PerformanceReport implements Serializable {
    private static final long serialVersionUID = 1L;

    private String reportId;                  // Unique identifier for the report
    private String branchId;                  // ID of the branch associated with the report
    private String month;                      // Month of the report as a String
    private String year;                       // Year of the report as a String
    private String totalOrders;               // Total number of orders in the report as a String
    private String totalRevenue;              // Total revenue generated from the orders as a String
    private String averageExpectedDeliveryTime; // Average expected delivery time as a String
    private String averageActualDeliveryTime; // Average actual delivery time as a String
    private String onTimeDeliveryRate;        // Rate of on-time deliveries as a String

    /**
     * Constructor to initialize all fields of the PerformanceReport class.
     *
     * @param reportId                        the unique identifier for the report
     * @param branchId                        the ID of the branch associated with the report
     * @param month                           the month of the report as a String
     * @param year                            the year of the report as a String
     * @param totalOrders                     the total number of orders in the report as a String
     * @param totalRevenue                    the total revenue generated from the orders as a String
     * @param averageExpectedDeliveryTime     the average expected delivery time as a String
     * @param averageActualDeliveryTime       the average actual delivery time as a String
     * @param onTimeDeliveryRate              the rate of on-time deliveries as a String
     */
    public PerformanceReport(String reportId, String branchId, String month, String year, 
                             String totalOrders, String totalRevenue,
                             String averageExpectedDeliveryTime, String averageActualDeliveryTime, 
                             String onTimeDeliveryRate) {
        this.reportId = reportId;
        this.branchId = branchId;
        this.month = month;
        this.year = year;
        this.totalOrders = totalOrders;
        this.totalRevenue = totalRevenue;
        this.averageExpectedDeliveryTime = averageExpectedDeliveryTime;
        this.averageActualDeliveryTime = averageActualDeliveryTime;
        this.onTimeDeliveryRate = onTimeDeliveryRate;
    }

    /**
     * Gets the unique identifier for the report.
     *
     * @return the report ID
     */
    public String getReportId() {
        return reportId; // Return the report ID
    }

    /**
     * Sets the unique identifier for the report.
     *
     * @param reportId the report ID
     */
    public void setReportId(String reportId) {
        this.reportId = reportId; // Set the report ID
    }

    /**
     * Gets the ID of the branch associated with the report.
     *
     * @return the branch ID
     */
    public String getBranchId() {
        return branchId; // Return the branch ID
    }

    /**
     * Sets the ID of the branch associated with the report.
     *
     * @param branchId the branch ID
     */
    public void setBranchId(String branchId) {
        this.branchId = branchId; // Set the branch ID
    }

    /**
     * Gets the month of the report.
     *
     * @return the month
     */
    public String getMonth() {
        return month; // Return the month
    }

    /**
     * Sets the month of the report.
     *
     * @param month the month
     */
    public void setMonth(String month) {
        this.month = month; // Set the month
    }

    /**
     * Gets the year of the report.
     *
     * @return the year
     */
    public String getYear() {
        return year; // Return the year
    }

    /**
     * Sets the year of the report.
     *
     * @param year the year
     */
    public void setYear(String year) {
        this.year = year; // Set the year
    }

    /**
     * Gets the total number of orders in the report.
     *
     * @return the total number of orders
     */
    public String getTotalOrders() {
        return totalOrders; // Return the total number of orders
    }

    /**
     * Sets the total number of orders in the report.
     *
     * @param totalOrders the total number of orders
     */
    public void setTotalOrders(String totalOrders) {
        this.totalOrders = totalOrders; // Set the total number of orders
    }

    /**
     * Gets the total revenue generated from the orders.
     *
     * @return the total revenue
     */
    public String getTotalRevenue() {
        return totalRevenue; // Return the total revenue
    }

    /**
     * Sets the total revenue generated from the orders.
     *
     * @param totalRevenue the total revenue
     */
    public void setTotalRevenue(String totalRevenue) {
        this.totalRevenue = totalRevenue; // Set the total revenue
    }

    /**
     * Gets the average expected delivery time.
     *
     * @return the average expected delivery time
     */
    public String getAverageExpectedDeliveryTime() {
        return averageExpectedDeliveryTime; // Return the average expected delivery time
    }

    /**
     * Sets the average expected delivery time.
     *
     * @param averageExpectedDeliveryTime the average expected delivery time
     */
    public void setAverageExpectedDeliveryTime(String averageExpectedDeliveryTime) {
        this.averageExpectedDeliveryTime = averageExpectedDeliveryTime; // Set the average expected delivery time
    }

    /**
     * Gets the average actual delivery time.
     *
     * @return the average actual delivery time
     */
    public String getAverageActualDeliveryTime() {
        return averageActualDeliveryTime; // Return the average actual delivery time
    }

    /**
     * Sets the average actual delivery time.
     *
     * @param averageActualDeliveryTime the average actual delivery time
     */
    public void setAverageActualDeliveryTime(String averageActualDeliveryTime) {
        this.averageActualDeliveryTime = averageActualDeliveryTime; // Set the average actual delivery time
    }

    /**
     * Gets the rate of on-time deliveries.
     *
     * @return the on-time delivery rate
     */
    public String getOnTimeDeliveryRate() {
        return onTimeDeliveryRate; // Return the on-time delivery rate
    }

    /**
     * Sets the rate of on-time deliveries.
     *
     * @param onTimeDeliveryRate the on-time delivery rate
     */
    public void setOnTimeDeliveryRate(String onTimeDeliveryRate) {
        this.onTimeDeliveryRate = onTimeDeliveryRate; // Set the on-time delivery rate
    }

    /**
     * Provides a string representation of the PerformanceReport object.
     *
     * @return a string containing the details of the performance report
     */
    @Override
    public String toString() {
        return "PerformanceReport{" +
                "reportId='" + reportId + '\'' +
                ", branchId='" + branchId + '\'' +
                ", month='" + month + '\'' +
                ", year='" + year + '\'' +
                ", totalOrders='" + totalOrders + '\'' +
                ", totalRevenue='" + totalRevenue + '\'' +
                ", averageExpectedDeliveryTime='" + averageExpectedDeliveryTime + '\'' +
                ", averageActualDeliveryTime='" + averageActualDeliveryTime + '\'' +
                ", onTimeDeliveryRate='" + onTimeDeliveryRate + '\'' +
                '}';
    }
}
