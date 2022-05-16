module ru.nsu.fit.minesweeper {
    requires javafx.controls;
    requires javafx.fxml;

    opens ru.nsu.fit.minesweeper to javafx.fxml;
    exports ru.nsu.fit.minesweeper;
    exports ru.nsu.fit.minesweeper.controller;
    opens ru.nsu.fit.minesweeper.controller to javafx.fxml;
    exports ru.nsu.fit.minesweeper.view;
    opens ru.nsu.fit.minesweeper.view to javafx.fxml;
    opens ru.nsu.fit.minesweeper.model.scoreTable to javafx.base;
}
