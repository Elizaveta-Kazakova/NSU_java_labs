package ru.nsu.fit.chat.command;

public class UserLogIn extends Command {
    private static final String MESSAGE_DELIMITER = "\n";

    private String userName;
    private String text;

    public UserLogIn(String userName, String text) {
        super();
        this.userName = userName;
        this.text = text;
    }

    public UserLogIn(String userName) {
        super();
        this.userName = userName;
        this.text = "user " + userName + " log in" + MESSAGE_DELIMITER;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public String getText() {
        return text;
    }
}
