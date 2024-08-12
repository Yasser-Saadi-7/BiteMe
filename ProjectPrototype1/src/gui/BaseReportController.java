package gui;

public class BaseReportController {

    protected int managerId; // Identifier for the manager
    protected String branch;  // Branch associated with the report
    protected String month;   // Month for the report
    protected String year;    // Year for the report

    /**
     * Sets the manager ID.
     *
     * @param managerId the ID of the manager to set
     */
    public void setManagerId(int managerId) {
        this.managerId = managerId; // Assign the manager ID
    }

    /**
     * Sets the branch associated with the report.
     *
     * @param branch the branch to set
     */
    public void setBranch(String branch) {
        this.branch = branch; // Assign the branch
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
