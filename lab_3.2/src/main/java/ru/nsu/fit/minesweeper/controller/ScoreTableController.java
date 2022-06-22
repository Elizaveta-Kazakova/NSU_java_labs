package ru.nsu.fit.minesweeper.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import ru.nsu.fit.minesweeper.App;
import ru.nsu.fit.minesweeper.model.scoreTable.ScoreTableData;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScoreTableController {

    private static final String MAIN_MENU_VIEW = "/ru/nsu/fit/minesweeper/primary.fxml";
    private static final String SCORE_FILE = "src/main/java/ru/nsu/fit/minesweeper/model/scoreTable/scoreData";
    private static final String SCORE_FILE_DELIMITER = ";";
    private static final int FIRST_EL = 0;
    private static final int SECOND_EL = 1;
    private static final String NEXT_LINE_SYM = "\n";

    private final List<ScoreTableData> scoreTableDataList = new ArrayList<>();

    @FXML
    private TableView<ScoreTableData> scoreTable;

    @FXML
    private TableColumn<ScoreTableData, String> userName;

    @FXML
    private TableColumn<ScoreTableData,String> time;

    @FXML
    void goBack(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(MAIN_MENU_VIEW));
        try {
            Parent root = loader.load();
            App.setNewScene(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void downloadScoreFile() throws IOException {
        try (BufferedReader scoreFileReader = new BufferedReader(new FileReader(SCORE_FILE))) {
            String newLine;
            while ((newLine = scoreFileReader.readLine()) != null) {
                String[] scoreEls = newLine.split(SCORE_FILE_DELIMITER);
                scoreTableDataList.add(new ScoreTableData(scoreEls[FIRST_EL], scoreEls[SECOND_EL]));
            }
        }
    }

    private static String createRecordFromData(ScoreTableData scoreTableData) {
        return scoreTableData.getUserName() + SCORE_FILE_DELIMITER + scoreTableData.getTime() + NEXT_LINE_SYM;
    }

    public static void addDataInFile(List<ScoreTableData> scoreTableDataList) throws IOException  {
        try (BufferedWriter scoreFileWriter = new BufferedWriter(new FileWriter(SCORE_FILE, true))) {
            for (ScoreTableData curScoreData : scoreTableDataList) {
                String record = createRecordFromData(curScoreData);
                scoreFileWriter.write(record);
            }
        }
    }

    private void initTable() throws IOException {
        ObservableList<ScoreTableData> tableRowList = FXCollections.observableArrayList();
        downloadScoreFile();
        tableRowList.addAll(scoreTableDataList);
        userName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        time.setCellValueFactory(new PropertyValueFactory<>("time"));
        scoreTable.setItems(tableRowList);
    }

    public void initialize() {
        try {
            initTable();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
