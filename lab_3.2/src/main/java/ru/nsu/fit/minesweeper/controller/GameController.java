package ru.nsu.fit.minesweeper.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import ru.nsu.fit.minesweeper.view.GameView;

public class GameController extends GameView {

    private int getCellIndex(MouseEvent event) {
        Node node = (Node) event.getTarget();
        int row = GridPane.getRowIndex(node);
        int column = GridPane.getColumnIndex(node);
        return row * widthField + column;
    }

    private void handleRightClick(MouseEvent event) {
        // правая кнопка мыши - сменить юзер ноут на следующий в списке
        int cellIndex = getCellIndex(event);
        minesweeperData.changeUserNote(cellIndex);
    }

    private void handleLeftClick(MouseEvent event) {
        // open cell
        int cellIndex = getCellIndex(event);
        minesweeperData.openCell(cellIndex);
    }


    @FXML
    public void restartGame(MouseEvent event) {
        timer.restartTimer();
        minesweeperData.restartGame();
    }

    @FXML
    public void clickInGameField(MouseEvent event) {
        if(event.getButton().equals(MouseButton.PRIMARY)) {
            handleLeftClick(event);
        }
        if(event.getButton().equals(MouseButton.SECONDARY)) {
            handleRightClick(event);
        }
    }
}