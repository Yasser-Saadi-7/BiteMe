package entities;

import java.io.Serializable;

/**
 * Represents an income report for a specific branch.
 */
public class IncomeReport implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String reportId;      // ID of the report
    private String branchId;      // ID of the selected branch
    private String month;         // Month of the report as a String (e.g., "Jan")
    private String year;          // Year of the report as a String
    private String totalIncome;    // Total income for the report as a String

    /**
     * Constructor to initialize all fields of the IncomeReport class.
     *
     * @param reportId     the ID of the report
     * @param branchId     the ID of the selected branch
     * @param totalIncome   the total income for the report as a String
     * @param month        the month of the report as a String
     * @param year         the year of the report as a String
     */
    public IncomeReport(String reportId, String branchId, String totalIncome, String month, String year) {
        this.reportId = reportId;   // Initialize report ID
        this.branchId = branchId;   // Initialize selected branch ID
        this.totalIncome = totalIncome; // Initialize total income
        this.month = month;         // Initialize month
        this.year = year;           // Initialize year
    }

    /**
     * Gets the ID of the report.
     *
     * @return the report ID
     */
    public String getReportId() {
        return reportId; // Return the report ID
    }

    /**
     * Gets the ID of the selected branch.
     *
     * @return the ID of the selected branch
     */
    public String getBranchId() {
        return branchId; // Return the selected branch ID
    }

    /**
     * Gets the month of the report.
     *
     * @return the month as a String
     */
    public String getMonth() {
        return month; // Return the month
    }

    /**
     * Gets the year of the report.
     *
     * @return the year as a String
     */
    public String getYear() {
        return year; // Return the year
    }

    /**
     * Gets the total income for the report.
     *
     * @return the total income as a String
     */
    public String getTotalIncome() {
        return totalIncome; // Return the total income
    }

    @Override
    public String toString() {
        return "IncomeReport{" +
                "reportId='" + reportId + '\'' +
                ", branchId='" + branchId + '\'' +
                ", month='" + month + '\'' +
                ", year='" + year + '\'' +
                ", totalIncome='" + totalIncome + '\'' +
                '}';
    }
}
