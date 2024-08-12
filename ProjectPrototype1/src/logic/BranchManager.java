package logic;

import java.io.Serializable;

public class BranchManager implements Serializable {
    private static final long serialVersionUID = 1L;

    private String managerId; // Corresponds to managerId in the table
    private int branchId;     // Corresponds to branchId in the table

    // Constructor
    public BranchManager(String managerId, int branchId) {
        this.managerId = managerId;
        this.branchId = branchId;
    }

    // Getters and Setters

    /**
     * Gets the ID of the manager.
     *
     * @return the managerId
     */
    public String getManagerId() {
        return managerId;
    }

    /**
     * Sets the ID of the manager.
     *
     * @param managerId the managerId to set
     */
    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    /**
     * Gets the ID of the branch.
     *
     * @return the branchId
     */
    public int getBranchId() {
        return branchId;
    }

    /**
     * Sets the ID of the branch.
     *
     * @param branchId the branchId to set
     */
    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    @Override
    public String toString() {
        return "BranchManager{" +
                "managerId='" + managerId + '\'' +
                ", branchId=" + branchId +
                '}';
    }
}
