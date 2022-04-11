package ru.nsu.fit.stack_calc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ru.nsu.fit.stack_calc.commands.Comment;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

public class CommentTests {
    private CalcData data;
    private Stack<String> st;
    private List<String> args;
    private Comment comment;

    private final String SIMPLE_COMMENT = "qwerty";
    private final String COMMENT_WITH_COMMAND = "PUSH 4";

    @BeforeEach
    public void initData() {
        data = new CalcData();
        st = data.getElements();
        args = new ArrayList<>();
        comment = new Comment();
    }

    @Test
    @DisplayName("Should check simple comment : empty comment")
    public void commentTest1() {
        CalcData initialData = data;
        comment.execute(data, args);
        Assertions.assertEquals(initialData, data);
    }

    @Test
    @DisplayName("Should check simple comment")
    public void commentTest2() {
        args.add(SIMPLE_COMMENT);
        CalcData initialData = data;
        comment.execute(data, args);
        Assertions.assertEquals(initialData, data);
    }

    @Test
    @DisplayName("Should check simple comment")
    public void commentTest3() {
        args.add(COMMENT_WITH_COMMAND);
        CalcData initialData = data;
        comment.execute(data, args);
        Assertions.assertEquals(initialData, data);
    }

    @Test
    @DisplayName("Should check simple comment : filed stack")
    public void commentTest4() {
        double randomNum1 = ThreadLocalRandom.current().nextDouble(Double.MIN_VALUE, Double.MAX_VALUE);
        st.push(String.valueOf(randomNum1));
        double randomNum2 = ThreadLocalRandom.current().nextDouble(Double.MIN_VALUE, Double.MAX_VALUE);
        st.push(String.valueOf(randomNum2));
        args.add(SIMPLE_COMMENT);
        CalcData initialData = data;
        comment.execute(data, args);
        Assertions.assertEquals(initialData, data);
    }

}
