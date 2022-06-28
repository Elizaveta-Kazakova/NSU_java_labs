package ru.nsu.fit.chat.model.chat;

import java.io.Serializable;

public class MessageData implements Serializable {
    private static final long serialVersionUID = 6329685098267757690L;

    private static final String DEFAULT_USER_NAME = "userName";
    private static final String DEFAULT_TEXT_MESSAGE = "";

    private String userName;
    private String textMessage;

    public MessageData(String userName, String textMessage) {
        this.userName = userName;
        this.textMessage = textMessage;
    }

    public  MessageData() {
        userName = DEFAULT_USER_NAME;
        textMessage = DEFAULT_TEXT_MESSAGE;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public String getUserName() {
        return userName;
    }

    public String getTextMessage() {
        return textMessage;
    }
}
