module ru.nsu.fit.chat {
    requires javafx.controls;
    requires javafx.fxml;

    opens ru.nsu.fit.chat to javafx.fxml;
    exports ru.nsu.fit.chat;
    opens ru.nsu.fit.chat.controller to javafx.fxml;
    exports ru.nsu.fit.chat.controller to javafx.fxml;
    exports ru.nsu.fit.chat.command;
    opens ru.nsu.fit.chat.command to javafx.fxml;
    opens ru.nsu.fit.chat.view to javafx.fxml;
    exports ru.nsu.fit.chat.view;
}