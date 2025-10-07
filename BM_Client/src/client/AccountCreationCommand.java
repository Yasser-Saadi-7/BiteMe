package client;

public enum AccountCreationCommand {
    CREATE_ACCOUNT("CreateAccount"),
    CHECK_USER_ID("CheckUserId");

    private final String command;

    AccountCreationCommand(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
