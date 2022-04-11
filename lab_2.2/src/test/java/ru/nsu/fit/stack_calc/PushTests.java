package ru.nsu.fit.stack_calc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ru.nsu.fit.stack_calc.commands.Push;
import ru.nsu.fit.stack_calc.exceptions.CommandInvalidNumOfArgs;
import ru.nsu.fit.stack_calc.exceptions.UndefinedWord;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

public class PushTests { // помешает ли то, что они в привате на то, если параллельно будем тестить...
    private CalcData data;
    private Stack<String> st;
    private List<String> args;
    private Push push;

    private static final String UNDEFINED_WORD = "HelloWord";
    private static final double START_BOUND = -100000.0;
    private static final double END_BOUND = 100000.0;

    @BeforeEach
    public void initData() {
        data = new CalcData();
        st = data.getElements();
        args = new ArrayList<>();
        push = new Push();
    }

    @Test
    @DisplayName("Should check simple arithmetic operations with push")
    public void arithmeticPushTest() {
        double randomNum = ThreadLocalRandom.current().nextDouble(START_BOUND, END_BOUND);
        args.add(String.valueOf(randomNum));
        try { // если я знаю, что всё точно ок, надо ли
            push.execute(data, args);
        } catch (CommandInvalidNumOfArgs | UndefinedWord e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(randomNum, Double.valueOf(st.pop()));
    }

    @Test
    @DisplayName("Should check correct of num of args in push : too few args")
    public void argsPushTest1() {
        Assertions.assertThrows(CommandInvalidNumOfArgs.class,
                () -> push.execute(data, args), "An exception wasn't thrown!");
    }

    @Test
    @DisplayName("Should check correct of num of args in push : too many args")
    public void argsPushTest2() {
        double randomFirst = ThreadLocalRandom.current().nextDouble(START_BOUND, END_BOUND);
        args.add(String.valueOf(randomFirst));
        double randomSecond = ThreadLocalRandom.current().nextDouble(START_BOUND, END_BOUND);
        args.add(String.valueOf(randomSecond));
        Assertions.assertThrows(CommandInvalidNumOfArgs.class,
                () -> push.execute(data, args), "An exception wasn't thrown!");
    }

    @Test
    @DisplayName("Should check correct of pushed argument : undefined word")
    public void undefinedNumPushTest() {
        args.add(UNDEFINED_WORD);
        Assertions.assertThrows(UndefinedWord.class,
                () -> push.execute(data, args), "An exception wasn't thrown!");
    }
}
