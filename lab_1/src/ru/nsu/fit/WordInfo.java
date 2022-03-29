package ru.nsu.fit;

public class WordInfo {
    private String word;
    private Integer numInText;

    public WordInfo(String word, Integer numInText) {
        this.word = word;
        this.numInText = numInText;
    }

    public Integer getNumInText() {
        return numInText;
    }

    public String getWord() {
        return word;
    }

}
