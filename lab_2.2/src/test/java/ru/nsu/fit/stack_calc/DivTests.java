package ru.nsu.fit.stack_calc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ru.nsu.fit.stack_calc.commands.Div;
import ru.nsu.fit.stack_calc.exceptions.CommandInvalidNumOfArgs;
import ru.nsu.fit.stack_calc.exceptions.EmptyStack;
import ru.nsu.fit.stack_calc.exceptions.UndefinedWord;
import ru.nsu.fit.stack_calc.exceptions.div_exceptions.DivisionByZero;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

public class DivTests {
    private CalcData data;
    private Stack<String> st;
    private List<String> args;
    private Div div;

    private static final String UNDEFINED_WORD = "HelloWord";
    private static final int ERROR_DIV_VALUE = 0;
    private static final double START_BOUND = -100000.0;
    private static final double END_BOUND = 100000.0;

    @BeforeEach
    public void initData() {
        data = new CalcData();
        st = data.getElements();
        args = new ArrayList<>();
        div = new Div();
    }

    @Test
    @DisplayName("Should check simple arithmetic operations with div : one argument")
    public void arithmeticDivTest1() {
        double randomStNum = ThreadLocalRandom.current().nextDouble(START_BOUND, END_BOUND);
        st.push(String.valueOf(randomStNum));
        double randomArgsNum = ThreadLocalRandom.current().nextDouble(START_BOUND, END_BOUND);
        args.add(String.valueOf(randomArgsNum));
        try {
            div.execute(data, args);
        } catch (CommandInvalidNumOfArgs | EmptyStack | UndefinedWord | DivisionByZero e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(randomStNum / randomArgsNum, Double.valueOf(st.pop()));
    }

    @Test
    @DisplayName("Should check simple arithmetic operations with div : zero arguments")
    public void arithmeticDivTest2() {
        double randomStNum1 = ThreadLocalRandom.current().nextDouble(START_BOUND, END_BOUND);
        st.push(String.valueOf(randomStNum1));
        double randomStNum2 = ThreadLocalRandom.current().nextDouble(START_BOUND, END_BOUND);
        st.push(String.valueOf(randomStNum2));
        try {
            div.execute(data, args);
        } catch (CommandInvalidNumOfArgs | EmptyStack | UndefinedWord | DivisionByZero e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(randomStNum1 / randomStNum2, Double.valueOf(st.pop()));
    }

    @Test
    @DisplayName("Should check correct of num of args in div : too many args")
    public void argsDivTest2() {
        double randomFirst = ThreadLocalRandom.current().nextDouble(START_BOUND, END_BOUND);
        args.add(String.valueOf(randomFirst));
        double randomSecond = ThreadLocalRandom.current().nextDouble(START_BOUND, END_BOUND);
        args.add(String.valueOf(randomSecond));
        Assertions.assertThrows(CommandInvalidNumOfArgs.class,
                () -> div.execute(data, args), "An exception wasn't thrown!");
    }

    @Test
    @DisplayName("Should check correct of stack : empty stack")
    public void emptyStackDivTest1() {
        double randomNum = ThreadLocalRandom.current().nextDouble(START_BOUND, END_BOUND);
        args.add(String.valueOf(randomNum));
        Assertions.assertThrows(EmptyStack.class,
                () -> div.execute(data, args), "An exception wasn't thrown!" );
    }

    @Test
    @DisplayName("Should check correct of stack : empty stack")
    public void emptyStackDivTest2() {
        Assertions.assertThrows(EmptyStack.class,
                () -> div.execute(data, args), "An exception wasn't thrown!" );
    }

    @Test
    @DisplayName("Should check correct of div arguments : undefined word")
    public void undefinedNumDivTest() {
        double randomNum = ThreadLocalRandom.current().nextDouble(START_BOUND, END_BOUND);
        st.push(String.valueOf(randomNum));
        args.add(UNDEFINED_WORD);
        Assertions.assertThrows(UndefinedWord.class,
                () -> div.execute(data, args), "An exception wasn't thrown!");
    }

    @Test
    @DisplayName("Should check correct of div arguments : second is zero")
    public void DivisionByZeroTest() {
        double randomNum = ThreadLocalRandom.current().nextDouble(START_BOUND, END_BOUND);
        args.add(String.valueOf(ERROR_DIV_VALUE));
        st.push(String.valueOf(randomNum));
        Assertions.assertThrows(DivisionByZero.class,
                () -> div.execute(data, args), "An exception wasn't thrown!");
    }
}
