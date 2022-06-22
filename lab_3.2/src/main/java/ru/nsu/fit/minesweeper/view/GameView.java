package ru.nsu.fit.minesweeper.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import ru.nsu.fit.minesweeper.App;
import ru.nsu.fit.minesweeper.observerPattern.Observer;
import ru.nsu.fit.minesweeper.observerPattern.TimeObserver;
import ru.nsu.fit.minesweeper.controller.MainMenuController;
import ru.nsu.fit.minesweeper.model.game.CellFilling;
import ru.nsu.fit.minesweeper.model.game.CellUserNote;
import ru.nsu.fit.minesweeper.model.game.MinesweeperData;
import ru.nsu.fit.minesweeper.model.scoreTable.ScoreTableData;
import ru.nsu.fit.minesweeper.model.settings.SettingsData;
import ru.nsu.fit.minesweeper.timer.Timer;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.lang.Double.min;
import static java.util.Map.entry;

public class GameView implements Observer, TimeObserver {

    private static final String DEFAULT_CELL_IMAGE_PATH = "/ru/nsu/fit/minesweeper/images/cellImages/defaultCellImage.jpg";

    private static final String EMPTY_CELL_IMAGE = "/ru/nsu/fit/minesweeper/images/cellImages/emptyCellImage.jpg";
    private static final String FIRST_CELL_IMAGE = "/ru/nsu/fit/minesweeper/images/cellImages/firstCellImage.jpg";
    private static final String SECOND_CELL_IMAGE = "/ru/nsu/fit/minesweeper/images/cellImages/secondCellImage.jpg";
    private static final String THIRD_CELL_IMAGE = "/ru/nsu/fit/minesweeper/images/cellImages/thirdCellImage.jpg";
    private static final String FOURTH_CELL_IMAGE = "/ru/nsu/fit/minesweeper/images/cellImages/fourthCellImage.jpg";
    private static final String FIFTH_CELL_IMAGE = "/ru/nsu/fit/minesweeper/images/cellImages/fifthCellImage.jpg";
    private static final String SIXTH_CELL_IMAGE = "/ru/nsu/fit/minesweeper/images/cellImages/sixthCellImage.jpg";
    private static final String SEVENTH_CELL_IMAGE = "/ru/nsu/fit/minesweeper/images/cellImages/seventhCellImage.jpg";
    private static final String EIGHTH_CELL_IMAGE = "/ru/nsu/fit/minesweeper/images/cellImages/eighthCellImage.jpg";

    private static final String SUSPICIOUS_CELL_IMAGE = "/ru/nsu/fit/minesweeper/images/cellImages/suspiciousCellImage.jpg";
    private  static final String FLAG_CELL_IMAGE = "/ru/nsu/fit/minesweeper/images/cellImages/flagImage.jpg";

    private static final String MINE_CELL_IMAGE = "/ru/nsu/fit/minesweeper/images/cellImages/mineImage.jpg";


    private static final String MAIN_MENU_VIEW = "/ru/nsu/fit/minesweeper/primary.fxml";

    private static final String LOSING_HEADER = "You clicked on a mine";
    private static final String LOSING_CONTENT = "So you lost the game :c";
    private static final String WIN_HEADER = "You win !!!!";
    private static final String WIN_CONTENT ="My congratulations !! :)";


    private static final double PREF_GAME_FILED_LENGTH = 450;
    private static final double PREF_GAME_FIELD_WIDTH = 450;

    private static final Map<CellFilling, String> CELL_FILLING_IMAGE = Map.ofEntries(
            entry(CellFilling.EMPTY, EMPTY_CELL_IMAGE),
            entry(CellFilling.ONE, FIRST_CELL_IMAGE),
            entry(CellFilling.TWO, SECOND_CELL_IMAGE),
            entry(CellFilling.THREE, THIRD_CELL_IMAGE),
            entry(CellFilling.FOUR, FOURTH_CELL_IMAGE),
            entry(CellFilling.FIVE, FIFTH_CELL_IMAGE),
            entry(CellFilling.SIX, SIXTH_CELL_IMAGE),
            entry(CellFilling.SEVEN, SEVENTH_CELL_IMAGE),
            entry(CellFilling.EIGHT, EIGHTH_CELL_IMAGE),
            entry(CellFilling.BOMB, MINE_CELL_IMAGE)
    );

    private static final Map<CellUserNote, String> CELL_USER_NOTE_IMAGE = Map.ofEntries(
            entry(CellUserNote.UNKNOWN, DEFAULT_CELL_IMAGE_PATH),
            entry(CellUserNote.BOMB_FLAG, FLAG_CELL_IMAGE),
            entry(CellUserNote.SUSPICIOUS, SUSPICIOUS_CELL_IMAGE)
    );

    @FXML
    private Label timeLabel;

    @FXML
    private GridPane gameField;

    private List<ScoreTableData> scoreTableDataList = new ArrayList<>();

    private final ArrayList<ImageView> gameCells = new ArrayList<>();

    protected Timer timer = new Timer();

    protected MinesweeperData minesweeperData;

    protected String userName;
    protected int lengthField;
    protected int widthField;
    protected int numOfMines;


    private void initField() {
        double imageSize = min(PREF_GAME_FIELD_WIDTH / widthField, PREF_GAME_FILED_LENGTH / lengthField);
        gameField.setMaxSize(widthField * imageSize, lengthField * imageSize);
        for (int curLength  = 0; curLength < lengthField; ++curLength) {
            for (int curWidth = 0; curWidth < widthField; ++curWidth) {
                Image cellImage =
                        new Image(Objects.requireNonNull(getClass().getResourceAsStream(DEFAULT_CELL_IMAGE_PATH)));
                ImageView cell = new ImageView(cellImage);
                cell.setFitWidth(imageSize);
                cell.setFitHeight(imageSize);
                gameCells.add(cell);
                gameField.add(cell, curWidth, curLength);

            }
        }
    }

    private void showEndGameAlert() throws IOException {
        Alert endGameAlert = new Alert(Alert.AlertType.CONFIRMATION);
        endGameAlert.setTitle("end of game");
        ButtonType restartGame = new ButtonType("Restart");
        ButtonType goToMainMenu = new ButtonType("Main menu");
        endGameAlert.getButtonTypes().clear();
        endGameAlert.getButtonTypes().addAll(restartGame, goToMainMenu);
        if (!minesweeperData.isWin()) {
            endGameAlert.setHeaderText(LOSING_HEADER);
            endGameAlert.setContentText(LOSING_CONTENT);
        } else {
            endGameAlert.setHeaderText(WIN_HEADER);
            endGameAlert.setContentText(WIN_CONTENT);
        }

        ButtonType clickedButtonType = endGameAlert.showAndWait().get();

        if (clickedButtonType == restartGame) {
            endGameAlert.close();
            timer.restartTimer();
            minesweeperData.restartGame();
            startTimerThread();
        }
        if (clickedButtonType == goToMainMenu) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(MAIN_MENU_VIEW));
            Parent root = loader.load();

            MainMenuController mainMenuController = loader.getController();
            mainMenuController.setScoreTableData(scoreTableDataList);

            App.setNewScene(root);
        }
    }

    private void endGame() throws IOException {
        timer.setActive(false);
        if (minesweeperData.isWin()) {
            scoreTableDataList.add(new ScoreTableData(userName, timer.getFormattedTime()));
        }
        showEndGameAlert();
    }

    private void setModel(SettingsData settingsData) {
        userName = settingsData.getUserName();
        lengthField = settingsData.getLength();
        widthField = settingsData.getWidth();
        numOfMines = settingsData.getNumOfMines();
        minesweeperData = new MinesweeperData(userName, numOfMines, widthField, lengthField);
    }

    private void addObservers() {
        minesweeperData.addObserver(this);
        timer.addTimeObserver(this);
    }

    private void startTimerThread() {
        Thread timer = new Thread(this.timer);
        timer.start();
    }

    public void setData(SettingsData settingsData) {
        setModel(settingsData);
        addObservers();
        startTimerThread();
        initField();
    }

    private void updateTimeLabel() {
        timeLabel.setText(timer.getFormattedTime());
    }

    @Override
    public void update() {
        for (int cellIndex = 0; cellIndex < minesweeperData.getSizeOfField(); ++cellIndex) {
            // show user notes
            CellUserNote userNote = minesweeperData.getCell(cellIndex).getCellUserNote();
            String nameOfImage = CELL_USER_NOTE_IMAGE.get(userNote);
            // show opened cells
            if (minesweeperData.getCell(cellIndex).isOpened()) {
                CellFilling cellFilling = minesweeperData.getCell(cellIndex).getCellFilling();
                nameOfImage = CELL_FILLING_IMAGE.get(cellFilling);
            }

            double imageSize = min(PREF_GAME_FIELD_WIDTH / widthField , PREF_GAME_FILED_LENGTH / lengthField);
            InputStream streamForImage = Objects.requireNonNull(getClass().getResourceAsStream(nameOfImage));
            Image newCellImage = new Image(streamForImage,
                    imageSize , imageSize, true, true );

            gameCells.get(cellIndex).setImage(newCellImage);
        }
        if (minesweeperData.isEndGame()) {
            try {
                endGame();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void updateTime() {
        Platform.runLater(this::updateTimeLabel);
    }
}
