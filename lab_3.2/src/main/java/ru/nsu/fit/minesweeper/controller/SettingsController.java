package ru.nsu.fit.minesweeper.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import ru.nsu.fit.minesweeper.App;
import ru.nsu.fit.minesweeper.model.settings.SettingsData;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SettingsController {

    private static final int FIRST_EL = 0;
    private static final int SECOND_EL = 1;
    private static final int THIRD_EL = 2;
    private static final int FOURTH_EL = 3;
    private static final String SETTINGS_DATA_FILE = "src/main/java/ru/nsu/fit/minesweeper/model/settings/settingsData";
    private static final String MAIN_MENU_VIEW = "/ru/nsu/fit/minesweeper/primary.fxml";
    private static final String EMPTY_STR = "";
    private static final String WRONG_NUMBER = "Write only positive integer numbers !";
    private static final String POSITIVE_INTEGER_NUMBER = "\\d+";
    private static final String WRONG_SIZE_OF_FIELD = "You chose num of mines > size of field !";
    private static final String WRONG_USER_NAME = "Don`t use ';' please...";
    private static final String FILE_DELIMITER = ";";

    private int parsedLength;
    private int parsedWidth;
    private int parsedNumOfMines;

    private SettingsData settingsData = new SettingsData();

    @FXML
    private AnchorPane SettingPane = new AnchorPane();

    @FXML
    private ImageView settingImage = new ImageView();

    @FXML
    private TextField lengthField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField numOfMinesField;

    @FXML
    private TextField widthField;

    @FXML
    private Label labelUnderLength;

    @FXML
    private Label labelUnderNumOfMines;

    @FXML
    private Label labelUnderWidth;

    @FXML
    private Label labelUnderUserName;

    private void setTextFields(SettingsData settingsData) {
        lengthField.setText(String.valueOf(settingsData.getLength()));
        widthField.setText(String.valueOf(settingsData.getWidth()));
        numOfMinesField.setText(String.valueOf(settingsData.getNumOfMines()));
        nameField.setText(settingsData.getUserName());
    }

    private void setParsedData(SettingsData settingsData) {
        parsedLength = settingsData.getLength();
        parsedWidth = settingsData.getWidth();
        parsedNumOfMines = settingsData.getNumOfMines();
    }

    private static String createSettingsRecord(SettingsData settingsData) {
        return settingsData.getUserName() + FILE_DELIMITER + settingsData.getLength() + FILE_DELIMITER +
                settingsData.getWidth() + FILE_DELIMITER + settingsData.getNumOfMines();
    }

    public static SettingsData downloadSettingFile() throws IOException {
        SettingsData settingsData = new SettingsData();
        try (BufferedReader settingsFileReader = new BufferedReader(new FileReader(SETTINGS_DATA_FILE))) {
            String newLine;
            while ((newLine = settingsFileReader.readLine()) != null) {
                String[] scoreEls = newLine.split(FILE_DELIMITER);
                settingsData.setData(scoreEls[FIRST_EL], Integer.parseInt(scoreEls[SECOND_EL]),
                        Integer.parseInt(scoreEls[THIRD_EL]), Integer.parseInt(scoreEls[FOURTH_EL]));
            }
        }
        return settingsData;
    }

    public static void addSettingsInFile(SettingsData settingsData) throws IOException {
        try (BufferedWriter settingsFileWriter = new BufferedWriter(new FileWriter(SETTINGS_DATA_FILE))) {
            settingsFileWriter.write(createSettingsRecord(settingsData));
        }
    }


    public String getUserName() {
        return nameField.getText();
    }

    public int getLength() {
        return Integer.parseInt(lengthField.getText());
    }

    public int getWidth() {
        return Integer.parseInt(widthField.getText());
    }

    public int getNumOfMines() {
        return Integer.parseInt(numOfMinesField.getText());
    }

    public void initialize() {
        settingImage.fitWidthProperty().bind(SettingPane.widthProperty());
        settingImage.fitHeightProperty().bind(SettingPane.heightProperty());
        try {
            settingsData = downloadSettingFile();
            setTextFields(settingsData);
            setParsedData(settingsData);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void goBack(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(MAIN_MENU_VIEW));
        try {
            SettingsController.addSettingsInFile(settingsData);

            Parent root = loader.load();
            App.setNewScene(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void setLength(ActionEvent event) {
        String length = lengthField.getText();
        if (!length.matches(POSITIVE_INTEGER_NUMBER)) {
            lengthField.setText(String.valueOf(settingsData.getLength()));
            labelUnderLength.setText(WRONG_NUMBER);
            return;
        }
        if (Integer.parseInt(length) * parsedWidth < parsedNumOfMines) {
            lengthField.setText(String.valueOf(settingsData.getLength()));
            labelUnderLength.setText(WRONG_SIZE_OF_FIELD);
            return;
        }
        labelUnderLength.setText(EMPTY_STR);
        parsedLength = Integer.parseInt(length);
        settingsData.setLength(this.getLength());
    }

    @FXML
    public void setNumOfMines(ActionEvent event) {
        String numOfMines = numOfMinesField.getText();
        if (!numOfMines.matches(POSITIVE_INTEGER_NUMBER)) {
            numOfMinesField.setText(String.valueOf(settingsData.getNumOfMines()));
            labelUnderNumOfMines.setText(WRONG_NUMBER);
            return;
        }
        if (parsedLength * parsedWidth < Integer.parseInt(numOfMines) ) {
            numOfMinesField.setText(String.valueOf(settingsData.getNumOfMines()));
            labelUnderNumOfMines.setText(WRONG_SIZE_OF_FIELD);
            return;
        }
        labelUnderNumOfMines.setText(EMPTY_STR);
        parsedNumOfMines = Integer.parseInt(numOfMines);
        settingsData.setNumOfMines(this.getNumOfMines());
    }

    @FXML
    public void setUserName(ActionEvent event) {
        if (nameField.getText().contains(FILE_DELIMITER)) {
            nameField.setText(settingsData.getUserName());
            labelUnderUserName.setText(WRONG_USER_NAME);
            return;
        }
        labelUnderUserName.setText(EMPTY_STR);
        settingsData.setUserName(this.getUserName());
    }

    @FXML
    public void setWidth(ActionEvent event) {
        String width = widthField.getText();
        if (!width.matches(POSITIVE_INTEGER_NUMBER)) {
            widthField.setText(String.valueOf(settingsData.getWidth()));
            labelUnderWidth.setText(WRONG_NUMBER);
            return;
        }
        if (parsedLength * Integer.parseInt(width) < parsedNumOfMines) {
            widthField.setText(String.valueOf(settingsData.getWidth()));
            labelUnderWidth.setText(WRONG_SIZE_OF_FIELD);
            return;
        }
        labelUnderWidth.setText(EMPTY_STR);
        parsedWidth = Integer.parseInt(width);
        settingsData.setWidth(this.getWidth());
    }

}
