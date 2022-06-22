package ru.nsu.fit.chat.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import ru.nsu.fit.chat.view.ChatView;
import ru.nsu.fit.chat.model.chat.MessageData;

public class ChatController extends ChatView {
    private static final String EMPTY_STR = "";

    @FXML
    private TextArea messageField;

    @FXML
    public void sendMessage(MouseEvent event) {
        client.sendMessage(new MessageData(client.getUserName(), messageField.getText()));
        messageField.setText(EMPTY_STR);
    }
}