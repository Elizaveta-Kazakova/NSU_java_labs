package ru.nsu.fit.stack_calc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ru.nsu.fit.stack_calc.commands.Mul;
import ru.nsu.fit.stack_calc.exceptions.CommandInvalidNumOfArgs;
import ru.nsu.fit.stack_calc.exceptions.EmptyStack;
import ru.nsu.fit.stack_calc.exceptions.UndefinedWord;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

public class MulTests {
    private CalcData data;
    private Stack<String> st;
    private List<String> args;
    private Mul mul;

    private static final String UNDEFINED_WORD = "HelloWord";
    private static final double START_BOUND = -100000.0;
    private static final double END_BOUND = 100000.0;

    @BeforeEach
    public void initData() {
        data = new CalcData();
        st = data.getElements();
        args = new ArrayList<>();
        mul = new Mul();
    }

    @Test
    @DisplayName("Should check simple arithmetic operations with mul : one argument")
    public void arithmeticMulTest1() {
        double randomStNum = ThreadLocalRandom.current().nextDouble(START_BOUND, END_BOUND);
        st.push(String.valueOf(randomStNum));
        double randomArgsNum = ThreadLocalRandom.current().nextDouble(START_BOUND, END_BOUND);
        args.add(String.valueOf(randomArgsNum));
        try {
            mul.execute(data, args);
        } catch (CommandInvalidNumOfArgs | EmptyStack | UndefinedWord e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(randomStNum * randomArgsNum, Double.valueOf(st.pop()));
    }

    @Test
    @DisplayName("Should check simple arithmetic operations with mul : zero arguments")
    public void arithmeticMulTest2() {
        double randomStNum1 = ThreadLocalRandom.current().nextDouble(START_BOUND, END_BOUND);
        st.push(String.valueOf(randomStNum1));
        double randomStNum2 = ThreadLocalRandom.current().nextDouble(START_BOUND, END_BOUND);
        st.push(String.valueOf(randomStNum2));
        try {
            mul.execute(data, args);
        } catch (CommandInvalidNumOfArgs | EmptyStack | UndefinedWord e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(randomStNum2 * randomStNum1, Double.valueOf(st.pop()));
    }

    @Test
    @DisplayName("Should check correct of num of args in mul : too many args")
    public void argsMulTest2() {
        double randomFirst = ThreadLocalRandom.current().nextDouble(START_BOUND, END_BOUND);
        args.add(String.valueOf(randomFirst));
        double randomSecond = ThreadLocalRandom.current().nextDouble(START_BOUND, END_BOUND);
        args.add(String.valueOf(randomSecond));
        Assertions.assertThrows(CommandInvalidNumOfArgs.class,
                () -> mul.execute(data, args), "An exception wasn't thrown!");
    }

    @Test
    @DisplayName("Should check correct of stack : empty stack")
    public void emptyStackMulTest1() {
        double randomNum = ThreadLocalRandom.current().nextDouble(START_BOUND, END_BOUND);
        args.add(String.valueOf(randomNum));
        Assertions.assertThrows(EmptyStack.class,
                () -> mul.execute(data, args), "An exception wasn't thrown!" );
    }

    @Test
    @DisplayName("Should check correct of stack : empty stack")
    public void emptyStackMulTest2() {
        Assertions.assertThrows(EmptyStack.class,
                () -> mul.execute(data, args), "An exception wasn't thrown!" );
    }

    @Test
    @DisplayName("Should check correct of mul arguments : undefined word")
    public void undefinedNumMulTest() {
        double randomNum = ThreadLocalRandom.current().nextDouble(START_BOUND, END_BOUND);
        st.push(String.valueOf(randomNum));
        args.add(UNDEFINED_WORD);
        Assertions.assertThrows(UndefinedWord.class,
                () -> mul.execute(data, args), "An exception wasn't thrown!");
    }
}
