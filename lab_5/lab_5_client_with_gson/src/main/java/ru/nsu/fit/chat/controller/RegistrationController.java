package ru.nsu.fit.chat.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import ru.nsu.fit.chat.App;
import ru.nsu.fit.chat.model.registration.RegistrationData;

import java.io.IOException;

public class RegistrationController {
    private static final String CHAT_VIEW = "/ru/nsu/fit/chat/chat.fxml";

    private Parent root;
    private RegistrationData data = new RegistrationData();

    @FXML
    private TextField nameField;

    @FXML
    void connect(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(CHAT_VIEW));
        try {
            root = loader.load();
            ChatController chatController = loader.getController();
            chatController.setData(data);

            App.setNewScene(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void enterName(ActionEvent event) {
        data.setUserName(nameField.getText());
    }
}
