package ru.nsu.fit.minesweeper.model.settings;

public class SettingsData {
    private static final int DEFAULT_LENGTH = 9;
    private static final int DEFAULT_WIDTH = 9;
    private static final int DEFAULT_NUM_OF_MINES = 10;
    private static final String DEFAULT_USERNAME = "UserName";

    private int length;
    private int width;
    private int numOfMines;
    private String userName;

    public SettingsData() {
        length = DEFAULT_LENGTH;
        width = DEFAULT_WIDTH;
        numOfMines = DEFAULT_NUM_OF_MINES;
        userName = DEFAULT_USERNAME;
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public int getNumOfMines() {
        return numOfMines;
    }

    public String getUserName() {
        return userName;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setNumOfMines(int numOfMines) {
        this.numOfMines = numOfMines;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
