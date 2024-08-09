package gui;

public class BaseReportController {

    protected int managerId;
    protected String branch;
    protected String month;
    protected String year;

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setYear(String year) {
        this.year = year;
    }

}
