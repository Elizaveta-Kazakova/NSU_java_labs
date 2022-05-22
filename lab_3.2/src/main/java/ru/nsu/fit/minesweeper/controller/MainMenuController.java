package ru.nsu.fit.minesweeper.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ru.nsu.fit.minesweeper.App;
import ru.nsu.fit.minesweeper.model.scoreTable.ScoreTableData;
import ru.nsu.fit.minesweeper.model.settings.SettingsData;

import java.io.IOException;
import java.util.List;

public class MainMenuController {

    private Parent root;

    private static final String NEW_GAME_VIEW = "/ru/nsu/fit/minesweeper/game.fxml";
    private static final String SCORE_TABLE_VIEW = "/ru/nsu/fit/minesweeper/scoreTable.fxml";
    private static final String SETTINGS_VIEW = "/ru/nsu/fit/minesweeper/settingsForNewGame.fxml";
    private static final String EXIT_HEADER = "Are you sure you want to exit?";
    private static final String EXIT_LABEL_CONTENT  = ":c";

    private SettingsData settingsData = new SettingsData();

    @FXML
    private AnchorPane pane = new AnchorPane();

    @FXML
    private ImageView startImage = new ImageView();

    @FXML
    void createNewGame(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(NEW_GAME_VIEW));
        try {
            root = loader.load();
            GameController gameController = loader.getController();
            gameController.setData(settingsData);

            App.setNewScene(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void exitFromGame(MouseEvent event) {
        Alert exitAlert = new Alert(Alert.AlertType.CONFIRMATION);
        exitAlert.setTitle("Exit");
        ButtonType sureButton = new ButtonType("Sure");
        ButtonType noButton = new ButtonType("No!!!");
        exitAlert.getButtonTypes().clear();
        exitAlert.getButtonTypes().addAll(sureButton, noButton);
        exitAlert.setHeaderText(EXIT_HEADER);
        exitAlert.setContentText(EXIT_LABEL_CONTENT);
        if (exitAlert.showAndWait().get() == sureButton) {
            Stage stage = (Stage) pane.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    void showScoreTable(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(SCORE_TABLE_VIEW));
        try {
            root = loader.load();
            App.setNewScene(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void showSettings(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(SETTINGS_VIEW));
            root = loader.load();
            App.setNewScene(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initialize() {
            startImage.fitWidthProperty().bind(pane.widthProperty());
            startImage.fitHeightProperty().bind(pane.heightProperty());
    }

    public void setSettingsData(SettingsData settingsData) {
        this.settingsData = settingsData;

    }

    public void setScoreTableData(List<ScoreTableData> scoreTableDataList) {
        try {
            if (!scoreTableDataList.isEmpty()) {
                ScoreTableController.addDataInFile(scoreTableDataList);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}