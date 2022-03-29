package ru.nsu.fit;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

public class Parser {
    private final int DEFAULT_NUM_IN_TEXT = 0;
    private final int LENGTH_OF_EMPTY_STR = 0;
    private final int SIZE_OF_ONE_EL = 1;

    private int numOfWords = 0;

    private HashMap<String, Integer> parsedInfo;

    private void parseStr(String str) {
        StringBuilder wordBuilder = new StringBuilder();
        for (int i = 0; i < str.length(); ++i) {
            char sym = str.charAt(i);
            if (Character.isLetterOrDigit(sym)) { // build a word
                wordBuilder.append(sym);
            }
            if (!Character.isLetterOrDigit(sym) || i + SIZE_OF_ONE_EL == str.length()){ // add word in the container
                putWordInTreeMap(parsedInfo, wordBuilder);
                ++numOfWords;
                wordBuilder.setLength(LENGTH_OF_EMPTY_STR);
            }
        }
    }

    private void putWordInTreeMap(HashMap<String, Integer> info, StringBuilder wordBuilder) {
        if (0 == wordBuilder.length()) {
            return;
        }
        String word = String.valueOf(wordBuilder);
        Integer numInText = info.getOrDefault(word, DEFAULT_NUM_IN_TEXT);
        if (info.containsKey(word)) {
            info.replace(word, ++numInText);
        } else {
            info.put(word, ++numInText);
        }
    }

    public void parseData(BufferedReader reader) throws IOException {
        parsedInfo = new HashMap<>();
        String line;
        while ((line = reader.readLine()) != null) {
            parseStr(line);
        }
    }

    public Data getParsedData() {
        Data data = new Data(parsedInfo, numOfWords);
        return data;
    }
}

