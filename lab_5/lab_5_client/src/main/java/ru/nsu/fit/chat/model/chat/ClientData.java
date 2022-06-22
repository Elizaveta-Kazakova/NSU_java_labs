package ru.nsu.fit.chat.model.chat;

import ru.nsu.fit.chat.observerPattern.Observable;

import java.util.ArrayList;
import java.util.List;

public class ClientData extends Observable {
    List<MessageData> messages = new ArrayList<>();

    public void addMessage(MessageData message) {
        messages.add(message);
        notifyObservers();
    }



}
