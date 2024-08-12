package logic;

import java.util.Date;

/**
 * Represents an income report for a specific branch.
 */
public class IncomeReport {
    private String selectedBranch; // Name of the selected branch
    private double totalIncome;     // Total income for the report
    private Date reportDate;        // Date of the report

    /**
     * Constructor to initialize all fields of the IncomeReport class.
     *
     * @param selectedBranch the name of the selected branch
     * @param totalIncome    the total income for the report
     * @param reportDate     the date of the report
     */
    public IncomeReport(String selectedBranch, double totalIncome, Date reportDate) {
        this.selectedBranch = selectedBranch; // Initialize selected branch
        this.totalIncome = totalIncome;       // Initialize total income
        this.reportDate = reportDate;         // Initialize report date
    }

    /**
     * Gets the name of the selected branch.
     *
     * @return the name of the selected branch
     */
    public String getSelectedBranch() {
        return selectedBranch; // Return the selected branch name
    }

    /**
     * Gets the total income for the report.
     *
     * @return the total income
     */
    public double getTotalIncome() {
        return totalIncome; // Return the total income
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
     * Provides a string representation of the IncomeReport object.
     *
     * @return a string containing the details of the income report
     */
    @Override
    public String toString() {
        return "IncomeReport{" +
                "selectedBranch='" + selectedBranch + '\'' +
                ", totalIncome=" + totalIncome +
                ", reportDate=" + reportDate + // Include report date in toString
                '}';
    }
}
