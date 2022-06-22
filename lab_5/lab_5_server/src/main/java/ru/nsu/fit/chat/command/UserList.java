package ru.nsu.fit.chat.command;

import java.util.ArrayList;

public class UserList extends Command {
    private static final long serialVersionUID = 5529685098267757690L;

    private static final String DELIMITER = "\n";

    private java.util.List<String> listOfParticipants = new ArrayList<>();

    public UserList(String participant) {
        this();
        listOfParticipants.add(participant);
    }

    public UserList() {
        super();
    }

    public void addParticipant(String participant) {
        listOfParticipants.add(participant);
    }

    public void deleteParticipant(String participant) {
        listOfParticipants.remove(participant);
    }

    @Override
    public String getText() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String member : listOfParticipants) {
            stringBuilder.append(member).append(DELIMITER);
        }
        return stringBuilder.toString();
    }

    public void clear() {
        listOfParticipants.clear();
    }



}