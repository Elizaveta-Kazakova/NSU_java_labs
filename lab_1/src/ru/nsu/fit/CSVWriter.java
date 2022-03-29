package ru.nsu.fit;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class CSVWriter {
    static final int TRUE_PROBABILITY_OF_EVENT = 100;
    static final String DELIMETR = ",";
    static final String NEXT_LINE = "\n";

    private Vector<WordInfo> getSortedInfo(HashMap<String, Integer> info) {
        Vector<WordInfo> sortedInfo = new Vector<>();
        for (Map.Entry<String, Integer> wordInfo : info.entrySet()) {
            WordInfo curWordInfo = new WordInfo(wordInfo.getKey(), wordInfo.getValue());
            sortedInfo.add(curWordInfo);
            sortedInfo.sort(new WordComparator());
        }
        return sortedInfo;
    }

    private void writeData(Vector<WordInfo> info, int numOfWords, BufferedWriter writer) throws IOException {
        for (WordInfo wordInfo : info) {
            writer.write(wordInfo.getWord() + DELIMETR + wordInfo.getNumInText() + DELIMETR
                    + (double)wordInfo.getNumInText() / numOfWords * TRUE_PROBABILITY_OF_EVENT + NEXT_LINE);
        }

    }

    public void writeSortedData(Data data, BufferedWriter writer) throws IOException {
        Vector<WordInfo> sortedInfo = getSortedInfo(data.getInfo());
        writeData(sortedInfo, data.getNumOfWords(), writer);
    }
}

