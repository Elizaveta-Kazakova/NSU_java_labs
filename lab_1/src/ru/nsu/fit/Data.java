package ru.nsu.fit;

import java.util.HashMap;

public class Data {
    private HashMap<String, Integer> info;
    private int numOfWords;

    public Data(HashMap<String, Integer> parsedInfo, int numOfWords) {
        this.info = parsedInfo;
        this.numOfWords = numOfWords;
    }

    public HashMap<String, Integer> getInfo() {
        return info;
    }
    public int getNumOfWords() {
        return numOfWords;
    }
}