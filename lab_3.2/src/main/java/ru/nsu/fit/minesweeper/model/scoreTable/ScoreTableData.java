package ru.nsu.fit.minesweeper.model.scoreTable;

public class ScoreTableData {
    private String userName;
    private String time;

    public ScoreTableData(String userName, String time) {
        this.userName = userName;
        this.time = time;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUserName() {
        return userName;
    }

    public String getTime() {
        return time;
    }
}
