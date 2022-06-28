package ru.nsu.fit.chat.command;

public class LogOut extends Command {
    private static final long serialVersionUID = 5729685098267757690L;

    private static final String MESSAGE_DELIMITER = "\n";

    private String userName;
    private String text;

    public LogOut(String userName, String text) {
        super();
        this.userName = userName;
        this.text = text;
    }

    public LogOut(String userName) {
        super();
        this.userName = userName;
        this.text = "user " + userName + " log out" + MESSAGE_DELIMITER;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String getText() {
        return text;
    }
}
