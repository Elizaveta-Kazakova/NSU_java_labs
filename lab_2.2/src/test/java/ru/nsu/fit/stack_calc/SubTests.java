package ru.nsu.fit.stack_calc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ru.nsu.fit.stack_calc.commands.Sub;
import ru.nsu.fit.stack_calc.exceptions.CommandInvalidNumOfArgs;
import ru.nsu.fit.stack_calc.exceptions.EmptyStack;
import ru.nsu.fit.stack_calc.exceptions.UndefinedWord;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

public class SubTests {
    private CalcData data;
    private Stack<String> st;
    private List<String> args;
    private Sub sub;

    private static final String UNDEFINED_WORD = "HelloWord";
    private static final double START_BOUND = -100000.0;
    private static final double END_BOUND = 100000.0;

    @BeforeEach
    public void initData() {
        data = new CalcData();
        st = data.getElements();
        args = new ArrayList<>();
        sub = new Sub();
    }

    @Test
    @DisplayName("Should check simple arithmetic operations with sub : one argument")
    public void arithmeticSubTest1() {
        double randomStNum = ThreadLocalRandom.current().nextDouble(START_BOUND, END_BOUND);
        st.push(String.valueOf(randomStNum));
        double randomArgsNum = ThreadLocalRandom.current().nextDouble(START_BOUND, END_BOUND);
        args.add(String.valueOf(randomArgsNum));
        try {
            sub.execute(data, args);
        } catch (CommandInvalidNumOfArgs | EmptyStack | UndefinedWord e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(randomStNum - randomArgsNum, Double.valueOf(st.pop()));
    }

    @Test
    @DisplayName("Should check simple arithmetic operations with sub : zero arguments")
    public void arithmeticSubTest2() {
        double randomStNum1 = ThreadLocalRandom.current().nextDouble(START_BOUND, END_BOUND);
        st.push(String.valueOf(randomStNum1));
        double randomStNum2 = ThreadLocalRandom.current().nextDouble(START_BOUND, END_BOUND);
        st.push(String.valueOf(randomStNum2));
        try {
            sub.execute(data, args);
        } catch (CommandInvalidNumOfArgs | EmptyStack | UndefinedWord e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(randomStNum1 - randomStNum2, Double.valueOf(st.pop()));
    }

    @Test
    @DisplayName("Should check correct of num of args in sub : too many args")
    public void argsSubTest() {
        double randomArgsNum1 = ThreadLocalRandom.current().nextDouble(START_BOUND, END_BOUND);
        args.add(String.valueOf(randomArgsNum1));
        double randomArgsNum2 = ThreadLocalRandom.current().nextDouble(START_BOUND, END_BOUND);
        args.add(String.valueOf(randomArgsNum2));
        Assertions.assertThrows(CommandInvalidNumOfArgs.class,
                () -> sub.execute(data, args), "An exception wasn't thrown!");
    }

    @Test
    @DisplayName("Should check correct of stack : empty stack")
    public void emptyStackSubTest1() {
        double randomNum = ThreadLocalRandom.current().nextDouble(START_BOUND, END_BOUND);
        args.add(String.valueOf(randomNum));
        Assertions.assertThrows(EmptyStack.class,
                () -> sub.execute(data, args), "An exception wasn't thrown!" );
    }

    @Test
    @DisplayName("Should check correct of stack : empty stack")
    public void emptyStackSubTest2() {
        Assertions.assertThrows(EmptyStack.class,
                () -> sub.execute(data, args), "An exception wasn't thrown!" );
    }

    @Test
    @DisplayName("Should check correct of sub arguments : undefined word")
    public void undefinedNumSubTest() {
        double randomNum = ThreadLocalRandom.current().nextDouble(START_BOUND, END_BOUND);
        st.push(String.valueOf(randomNum));
        args.add(UNDEFINED_WORD);
        Assertions.assertThrows(UndefinedWord.class,
                () -> sub.execute(data, args), "An exception wasn't thrown!");
    }
}
