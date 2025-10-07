package entities;

/**
 * Represents a branch with a unique identifier, name, and location.
 */
public class Branches {
	// Instance variables
	private String branchId; // Unique identifier for the branch
	private String branchName; // Name of the branch
	private String location; // Location of the branch

	/**
	 * Constructor to create a new Branch instance.
	 *
	 * @param branchId   the unique identifier for the branch
	 * @param branchName the name of the branch
	 * @param location   the location of the branch
	 */
	public Branches(String branchId, String branchName, String location) {
		this.branchId = branchId; // Initialize branchId
		this.branchName = branchName; // Initialize branchName
		this.location = location; // Initialize location
	}

	/**
	 * Gets the unique identifier for the branch.
	 *
	 * @return the branchId
	 */
	public String getBranchId() {
		return branchId; // Return branchId
	}

	/**
	 * Sets the unique identifier for the branch.
	 *
	 * @param branchId the new unique identifier for the branch
	 */
	public void setBranchId(String branchId) {
		this.branchId = branchId; // Set branchId
	}

	/**
	 * Gets the name of the branch.
	 *
	 * @return the branchName
	 */
	public String getBranchName() {
		return branchName; // Return branchName
	}

	/**
	 * Sets the name of the branch.
	 *
	 * @param branchName the new name for the branch
	 */
	public void setBranchName(String branchName) {
		this.branchName = branchName; // Set branchName
	}

	/**
	 * Gets the location of the branch.
	 *
	 * @return the location
	 */
	public String getLocation() {
		return location; // Return location
	}

	/**
	 * Sets the location of the branch.
	 *
	 * @param location the new location for the branch
	 */
	public void setLocation(String location) {
		this.location = location; // Set location
	}

	/**
	 * Returns a string representation of the branch.
	 *
	 * @return a string displaying the branch name
	 */
	@Override
	public String toString() {
		return "Branch ID: " + branchId + ", Name: " + branchName + ", Location: " + location;

	}
}
