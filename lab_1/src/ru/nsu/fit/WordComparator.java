package ru.nsu.fit;

import java.util.Comparator;
import java.util.Objects;

public class WordComparator implements Comparator<WordInfo> {
    @Override
    public int compare(WordInfo o1, WordInfo o2) {
        if (Objects.equals(o1.getNumInText(), o2.getNumInText())) {
            return o1.getWord().compareTo(o2.getWord());
        }
        return o2.getNumInText() - o1.getNumInText();
    }
}
