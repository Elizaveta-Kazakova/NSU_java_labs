package ru.nsu.fit.chat.model.chat;

import ru.nsu.fit.chat.command.Command;

import java.util.ArrayList;
import java.util.List;

public class MessageStore {
    private static final int MAX_NUM_OF_MESSAGE = 10;
    private List<Command> messageList = new ArrayList<>();

    public void addMessage(Command message) {
        if (messageList.size() == MAX_NUM_OF_MESSAGE) {
            messageList.remove(0);
        }
        messageList.add(message);
    }

    public Command getMessage(int index) {
        return messageList.get(index);
    }

    public int getSize() {
        return messageList.size();
    }
}
