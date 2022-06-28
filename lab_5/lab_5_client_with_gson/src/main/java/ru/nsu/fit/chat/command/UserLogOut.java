package ru.nsu.fit.chat.command;

public class UserLogOut extends Command {
    private static final String MESSAGE_DELIMITER = "\n";


    private String userName;
    private String text;

    public UserLogOut(String userName, String text) {
        super();
        this.userName = userName;
        this.text = text;
    }

    public UserLogOut(String userName) {
        super();
        this.userName = userName;
        this.text = "user " + userName + " log out" + MESSAGE_DELIMITER;
    }

    @Override
    public String getText() {
        return text;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
}
