package logic;

import java.io.Serializable;
import java.sql.Date;

/**
 * Represents a performance report for a specific branch.
 */
public class PerformanceReport implements Serializable {
    private static final long serialVersionUID = 1L;

    private int reportId;                  // Unique identifier for the report
    private int branchId;                  // ID of the branch associated with the report
    private Date reportDate;               // Date of the report
    private int totalOrders;               // Total number of orders in the report
    private double totalRevenue;           // Total revenue generated from the orders
    private int averageExpectedDeliveryTime; // Average expected delivery time
    private int averageActualDeliveryTime; // Average actual delivery time
    private double onTimeDeliveryRate;     // Rate of on-time deliveries

    /**
     * Constructor to initialize all fields of the PerformanceReport class.
     *
     * @param reportId                        the unique identifier for the report
     * @param branchId                        the ID of the branch associated with the report
     * @param reportDate                      the date of the report
     * @param totalOrders                     the total number of orders in the report
     * @param totalRevenue                    the total revenue generated from the orders
     * @param averageExpectedDeliveryTime     the average expected delivery time
     * @param averageActualDeliveryTime       the average actual delivery time
     * @param onTimeDeliveryRate              the rate of on-time deliveries
     */
    public PerformanceReport(int reportId, int branchId, Date reportDate, int totalOrders, double totalRevenue,
                             int averageExpectedDeliveryTime, int averageActualDeliveryTime, double onTimeDeliveryRate) {
        this.reportId = reportId;
        this.branchId = branchId;
        this.reportDate = reportDate;
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
    public int getReportId() {
        return reportId; // Return the report ID
    }

    /**
     * Sets the unique identifier for the report.
     *
     * @param reportId the report ID
     */
    public void setReportId(int reportId) {
        this.reportId = reportId; // Set the report ID
    }

    /**
     * Gets the ID of the branch associated with the report.
     *
     * @return the branch ID
     */
    public int getBranchId() {
        return branchId; // Return the branch ID
    }

    /**
     * Sets the ID of the branch associated with the report.
     *
     * @param branchId the branch ID
     */
    public void setBranchId(int branchId) {
        this.branchId = branchId; // Set the branch ID
    }

    /**
     * Gets the date of the report.
     *
     * @return the report date
     */
    public Date getReportDate() {
        return reportDate; // Return the report date
    }

    /**
     * Sets the date of the report.
     *
     * @param reportDate the report date
     */
    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate; // Set the report date
    }

    /**
     * Gets the total number of orders in the report.
     *
     * @return the total number of orders
     */
    public int getTotalOrders() {
        return totalOrders; // Return the total number of orders
    }

    /**
     * Sets the total number of orders in the report.
     *
     * @param totalOrders the total number of orders
     */
    public void setTotalOrders(int totalOrders) {
        this.totalOrders = totalOrders; // Set the total number of orders
    }

    /**
     * Gets the total revenue generated from the orders.
     *
     * @return the total revenue
     */
    public double getTotalRevenue() {
        return totalRevenue; // Return the total revenue
    }

    /**
     * Sets the total revenue generated from the orders.
     *
     * @param totalRevenue the total revenue
     */
    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue; // Set the total revenue
    }

    /**
     * Gets the average expected delivery time.
     *
     * @return the average expected delivery time
     */
    public int getAverageExpectedDeliveryTime() {
        return averageExpectedDeliveryTime; // Return the average expected delivery time
    }

    /**
     * Sets the average expected delivery time.
     *
     * @param averageExpectedDeliveryTime the average expected delivery time
     */
    public void setAverageExpectedDeliveryTime(int averageExpectedDeliveryTime) {
        this.averageExpectedDeliveryTime = averageExpectedDeliveryTime; // Set the average expected delivery time
    }

    /**
     * Gets the average actual delivery time.
     *
     * @return the average actual delivery time
     */
    public int getAverageActualDeliveryTime() {
        return averageActualDeliveryTime; // Return the average actual delivery time
    }

    /**
     * Sets the average actual delivery time.
     *
     * @param averageActualDeliveryTime the average actual delivery time
     */
    public void setAverageActualDeliveryTime(int averageActualDeliveryTime) {
        this.averageActualDeliveryTime = averageActualDeliveryTime; // Set the average actual delivery time
    }

    /**
     * Gets the rate of on-time deliveries.
     *
     * @return the on-time delivery rate
     */
    public double getOnTimeDeliveryRate() {
        return onTimeDeliveryRate; // Return the on-time delivery rate
    }

    /**
     * Sets the rate of on-time deliveries.
     *
     * @param onTimeDeliveryRate the on-time delivery rate
     */
    public void setOnTimeDeliveryRate(double onTimeDeliveryRate) {
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
                "reportId=" + reportId +
                ", branchId=" + branchId +
                ", reportDate=" + reportDate +
                ", totalOrders=" + totalOrders +
                ", totalRevenue=" + totalRevenue +
                ", averageExpectedDeliveryTime=" + averageExpectedDeliveryTime +
                ", averageActualDeliveryTime=" + averageActualDeliveryTime +
                ", onTimeDeliveryRate=" + onTimeDeliveryRate +
                '}';
    }
}
