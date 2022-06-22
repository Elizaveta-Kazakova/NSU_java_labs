package ru.nsu.fit.chat.command;

public class LogIn extends Command {
    private static final long serialVersionUID = 6129685098267757690L;

    private static final String MESSAGE_DELIMITER = "\n";

    private String userName;
    private String text;

    public LogIn(String userName) {
        super();
        this.userName = userName;
        this.text = "user " + userName + " log in" + MESSAGE_DELIMITER;
    }

    public LogIn(String userName, String text) {
        super();
        this.userName = userName;
        this.text = text;
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
