package logic;

import java.io.Serializable;
import java.sql.Date;

public class PerformanceReport implements Serializable {
    private static final long serialVersionUID = 1L;

    private int reportId; // Matches SQL reportId
    private int branchId; // Matches SQL branchId
    private Date reportDate; // Matches SQL reportDate
    private int totalOrders; // Matches SQL totalOrders
    private double totalRevenue; // Matches SQL totalRevenue
    private int averageExpectedDeliveryTime; // Matches SQL averageExpectedDeliveryTime
    private int averageActualDeliveryTime; // Matches SQL averageActualDeliveryTime
    private double onTimeDeliveryRate; // Matches SQL onTimeDeliveryRate

    // Constructor
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

    // Getters and Setters
    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public Date getReportDate() {
        return reportDate; // Return as java.sql.Date
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate; // Accept as java.sql.Date
    }

    public int getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(int totalOrders) {
        this.totalOrders = totalOrders;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public int getAverageExpectedDeliveryTime() {
        return averageExpectedDeliveryTime;
    }

    public void setAverageExpectedDeliveryTime(int averageExpectedDeliveryTime) {
        this.averageExpectedDeliveryTime = averageExpectedDeliveryTime;
    }

    public int getAverageActualDeliveryTime() {
        return averageActualDeliveryTime;
    }

    public void setAverageActualDeliveryTime(int averageActualDeliveryTime) {
        this.averageActualDeliveryTime = averageActualDeliveryTime;
    }

    public double getOnTimeDeliveryRate() {
        return onTimeDeliveryRate;
    }

    public void setOnTimeDeliveryRate(double onTimeDeliveryRate) {
        this.onTimeDeliveryRate = onTimeDeliveryRate;
    }

    // Optional: Override toString() for better debugging/printing
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
