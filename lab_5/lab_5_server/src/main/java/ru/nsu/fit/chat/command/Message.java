package ru.nsu.fit.chat.command;

import ru.nsu.fit.chat.model.chat.MessageData;

public class Message extends Command {

    private static final long serialVersionUID = 6529685098267757690L;
    private static final String USER_AND_MESSAGE_DELIMITER = ": ";
    private static final String MESSAGE_DELIMITER = "\n";

    private MessageData message;

    public Message(MessageData message) {
        super();
        this.message = message;
    }


    @Override
    public String getText() {
        return message.getUserName() + USER_AND_MESSAGE_DELIMITER + message.getTextMessage() + MESSAGE_DELIMITER;
    }

    public void setMessage(MessageData messageData) {
        this.message = messageData;
    }

    public MessageData getMessage() {
        return message;
    }
}
