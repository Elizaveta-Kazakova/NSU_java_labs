package ru.nsu.fit.chat.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import ru.nsu.fit.chat.command.Command;
import ru.nsu.fit.chat.model.chat.Client;
import ru.nsu.fit.chat.model.registration.RegistrationData;
import ru.nsu.fit.chat.observerPattern.Observer;

import java.util.List;

public class ChatView implements Observer {
    private static final int TEXT_FONT = 20;

    @FXML
    private TextFlow listOfParticipants;

    @FXML
    private TextFlow chatField;

    protected Client client; // model

    public void setData(RegistrationData registrationData) {
        client = new Client(registrationData.getUserName());
        client.addObserver(this);

        client.startClient();
    }

    private void addToTheChat(Command command) {
        Text text = new Text(command.getText());
        text.setFont(Font.font(TEXT_FONT));
        chatField.getChildren().add(text);
    }

    private void updateParticipants() {
        Text chatMembers = new Text(client.getUserList().getText());
        chatMembers.setFont(Font.font(TEXT_FONT));
        listOfParticipants.getChildren().clear();
        listOfParticipants.getChildren().add(chatMembers);
    }

    @Override
    public void update() {
        Platform.runLater(this::updateParticipants);
        List<Command> messages = client.getMessages();
        for (Command command : messages) {
            Platform.runLater(() -> addToTheChat(command));
        }
        messages.clear();
    }

}
