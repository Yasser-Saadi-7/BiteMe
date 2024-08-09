package logic;

public class Branches {
    // Instance variables
    private int branchId;       // Unique identifier for the branch
    private String branchName;   // Name of the branch
    private String location;      // Location of the branch

    // Constructor
    public Branches(int branchId, String branchName, String location) {
        this.branchId = branchId;
        this.branchName = branchName;
        this.location = location;
    }

    // Getters and Setters
    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    // toString method for displaying branch information
    @Override
    public String toString() {
        return "Branch ID: " + branchId + ", Branch Name: " + branchName + ", Location: " + location;
    }
}
