package logic;

public enum UserType {
	CLIENT("Client"), CEO("CEO"), BRANCH_MANAGER("BranchManager"),
	QUALIFIED_WORKER("QualifiedWorker"), SPONSER("Sponser");

	private final String type;

	UserType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return type;
	}
}
