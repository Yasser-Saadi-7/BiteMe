package entities;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents a Branch Manager in the system.
 * The Branch Manager is associated with a specific branch.
 */
public class BranchManager implements Serializable {
    private static final long serialVersionUID = 1L;

    private String managerId; // Corresponds to managerId in the database table
    private String branchId;  // Corresponds to branchId in the database table

    /**
     * Constructor to create a new BranchManager instance.
     *
     * @param managerId The ID of the manager.
     * @param branchId  The ID of the branch managed by the manager.
     */
    public BranchManager(String managerId, String branchId) {
        this.managerId = managerId; // Initialize managerId
        this.branchId = branchId;   // Initialize branchId
    }

    // Getters and Setters

    public String getManagerId() {
        return managerId; 
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId; 
    }

    public String getBranchId() {
        return branchId; 
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId; 
    }

    @Override
    public String toString() {
        return "BranchManager{" +
                "managerId='" + managerId + '\'' +
                ", branchId='" + branchId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BranchManager)) return false;
        BranchManager that = (BranchManager) o;
        return Objects.equals(managerId, that.managerId) && 
               Objects.equals(branchId, that.branchId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(managerId, branchId);
    }
}
