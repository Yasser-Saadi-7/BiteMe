package logic;

import java.util.Date;

public class IncomeReport {
    private String selectedBranch;
    private double totalIncome;
    private Date reportDate; 

    // Constructor
    public IncomeReport(String selectedBranch, double totalIncome, Date reportDate) {
        this.selectedBranch = selectedBranch;
        this.totalIncome = totalIncome;
        this.reportDate = reportDate; // Initialize report date
    }

    // Getter for selectedBranch
    public String getSelectedBranch() {
        return selectedBranch;
    }

    // Getter for totalIncome
    public double getTotalIncome() {
        return totalIncome;
    }

    // Getter for reportDate
    public Date getReportDate() {
        return reportDate;
    }

    // Optional: Override toString() for better debugging/printing
    @Override
    public String toString() {
        return "IncomeReport{" +
                "selectedBranch='" + selectedBranch + '\'' +
                ", totalIncome=" + totalIncome +
                ", reportDate=" + reportDate + // Include report date in toString
                '}';
    }
}
