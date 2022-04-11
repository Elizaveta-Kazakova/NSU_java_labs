package ru.nsu.fit.stack_calc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ru.nsu.fit.stack_calc.commands.Sqrt;
import ru.nsu.fit.stack_calc.exceptions.CommandInvalidNumOfArgs;
import ru.nsu.fit.stack_calc.exceptions.EmptyStack;
import ru.nsu.fit.stack_calc.exceptions.UndefinedWord;
import ru.nsu.fit.stack_calc.exceptions.sqrt_exceptions.SqrtOfNegativeNum;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

public class SqrtTests {
    private CalcData data;
    private Stack<String> st;
    private List<String> args;
    private Sqrt sqrt;

    private static final String UNDEFINED_WORD = "HelloWord";
    private static final int NEGATIVE_AND_POSITIVE_BOUND = 0;
    private static final double START_BOUND = -100000.0;
    private static final double END_BOUND = 100000.0;


    @BeforeEach
    public void initData() {
        data = new CalcData();
        st = data.getElements();
        args = new ArrayList<>();
        sqrt = new Sqrt();
    }

    @Test
    @DisplayName("Should check simple arithmetic operations with sqrt from stack")
    public void arithmeticSqrtTest1() {
        double randomNum = ThreadLocalRandom.current().nextDouble(NEGATIVE_AND_POSITIVE_BOUND, END_BOUND);
        st.push(String.valueOf(randomNum));
        try {
            sqrt.execute(data, args);
        } catch (CommandInvalidNumOfArgs | EmptyStack | UndefinedWord | SqrtOfNegativeNum e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(Math.sqrt(randomNum), Double.valueOf(st.pop()));
    }

    @Test
    @DisplayName("Should check simple arithmetic operations with sqrt from args")
    public void arithmeticSqrtTest2() {
        double randomNum = ThreadLocalRandom.current().nextDouble(NEGATIVE_AND_POSITIVE_BOUND, END_BOUND);
        args.add(String.valueOf(randomNum));
        try {
            sqrt.execute(data, args);
        } catch (CommandInvalidNumOfArgs | EmptyStack | UndefinedWord | SqrtOfNegativeNum e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(Math.sqrt(randomNum), Double.valueOf(st.pop()));
    }

    @Test
    @DisplayName("Should check correct of num of args in sqrt : too many args")
    public void argsSqrtTest() {
        double randomArgsNum1 = ThreadLocalRandom.current().nextDouble(START_BOUND, END_BOUND);
        args.add(String.valueOf(randomArgsNum1));
        double randomArgsNum2 = ThreadLocalRandom.current().nextDouble(START_BOUND, END_BOUND);
        args.add(String.valueOf(randomArgsNum2));
        Assertions.assertThrows(CommandInvalidNumOfArgs.class,
                () -> sqrt.execute(data, args), "An exception wasn't thrown!");
    }

    @Test
    @DisplayName("Should check correct of stack : empty stack")
    public void emptyStackSqrtTest() {
        Assertions.assertThrows(EmptyStack.class,
                () -> sqrt.execute(data, args), "An exception wasn't thrown!" );
    }

    @Test
    @DisplayName("Should check correct of sqrt arguments : undefined word")
    public void undefinedNumSqrtTest() {
        args.add(UNDEFINED_WORD);
        Assertions.assertThrows(UndefinedWord.class,
                () -> sqrt.execute(data, args), "An exception wasn't thrown!");
    }

    @Test
    @DisplayName("Should check correct of sqrt arguments : negative number")
    public void negativeNumSqrtTest() {
        double randomNum = ThreadLocalRandom.current().nextDouble(START_BOUND, NEGATIVE_AND_POSITIVE_BOUND);
        st.push(String.valueOf(randomNum));
        Assertions.assertThrows(SqrtOfNegativeNum.class,
                () -> sqrt.execute(data, args), "An exception wasn't thrown!");
    }
}
