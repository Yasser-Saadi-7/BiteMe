package logic;

public enum UserType {
    CLIENT("Client"), CEO("CEO"), BRANCH_MANAGER("BranchManager"),
    QUALIFIED_WORKER("QualifiedWorker"), SPONSER("Sponser");

    private final String type;

    /**
     * Constructor for UserType enum.
     *
     * @param type the string representation of the user type
     */
    UserType(String type) {
        this.type = type;
    }

    /**
     * Returns the string representation of the user type.
     *
     * @return the string representation of the user type
     */
    @Override
    public String toString() {
        return type;
    }
}
