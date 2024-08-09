package logic;

import java.io.Serializable;

public class OrderReport implements Serializable {
    private static final long serialVersionUID = 1L;

    private int reportId; // Matches SQL reportId
    private int branchId; // Matches SQL branchId
    private java.sql.Date reportDate; // Matches SQL reportDate
    private int totalOrders; // Matches SQL totalOrders
    private double totalRevenue; // Matches SQL totalRevenue

    // Constructor
    public OrderReport(int reportId, int branchId, java.sql.Date reportDate, int totalOrders, double totalRevenue) {
        this.reportId = reportId;
        this.branchId = branchId;
        this.reportDate = reportDate;
        this.totalOrders = totalOrders;
        this.totalRevenue = totalRevenue;
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

    public java.sql.Date getReportDate() {
        return reportDate; // Return as java.sql.Date
    }

    public void setReportDate(java.sql.Date reportDate) {
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

    // Optional: Override toString() for better debugging/printing
    @Override
    public String toString() {
        return "OrderReport{" +
                "reportId=" + reportId +
                ", branchId=" + branchId +
                ", reportDate=" + reportDate + // Include reportDate
                ", totalOrders=" + totalOrders +
                ", totalRevenue=" + totalRevenue +
                '}';
    }
}
