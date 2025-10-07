package controllers;

import entities.Branches;

public class BaseReportController {

    protected String managerId; // Identifier for the manager as a String
    protected String branchName;   // Branch associated with the report
    protected String month;      // Month for the report
    protected String year;       // Year for the report

    /**
     * Sets the manager ID.
     *
     * @param managerId the ID of the manager to set
     */
    public void setManagerId(String managerId) {
        this.managerId = managerId; // Assign the manager ID
    }

    /**
     * Sets the branch associated with the report.
     *
     * @param branches the branch to set
     */
    public void setBranchName(String branchName) {
        this.branchName = branchName; // Assign the branch
    }

    /**
     * Sets the month for the report.
     *
     * @param month the month to set
     */
    public void setMonth(String month) {
        this.month = month; // Assign the month
    }

    /**
     * Sets the year for the report.
     *
     * @param year the year to set
     */
    public void setYear(String year) {
        this.year = year; // Assign the year
    }
}
