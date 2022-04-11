package ru.nsu.fit.stack_calc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ru.nsu.fit.stack_calc.commands.Define;
import ru.nsu.fit.stack_calc.commands.Sum;
import ru.nsu.fit.stack_calc.exceptions.CommandInvalidNumOfArgs;
import ru.nsu.fit.stack_calc.exceptions.EmptyStack;
import ru.nsu.fit.stack_calc.exceptions.UndefinedWord;
import ru.nsu.fit.stack_calc.exceptions.define_exceptions.DefineIsNumber;
import ru.nsu.fit.stack_calc.exceptions.define_exceptions.DefineNotNumber;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

public class DefineTests {
    private CalcData data;
    private Stack<String> st;
    private List<String> args;
    private Define define;

    private static final String DEFINE_STR1 = "ABC";
    private static final String DEFINE_STR2 = "CBA";
    private static final double START_BOUND = -100000.0;
    private static final double END_BOUND = 100000.0;

    @BeforeEach
    public void initData() {
        data = new CalcData();
        st = data.getElements();
        args = new ArrayList<>();
        define = new Define();
    }

    @Test
    @DisplayName("Should check that define is added")
    public void DefineTest1() {
        args.add(DEFINE_STR1);
        double randomNum = ThreadLocalRandom.current().nextDouble(START_BOUND, END_BOUND);
        args.add(String.valueOf(randomNum));
        try {
            define.execute(data, args);
            Assertions.assertEquals(randomNum, data.getDefinedNum(DEFINE_STR1));
        } catch (CommandInvalidNumOfArgs | DefineIsNumber | DefineNotNumber | UndefinedWord e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Should check simple operations with defined numbers")
    public void DefineTest2() {
        args.add(DEFINE_STR1);
        double randomNum1 = ThreadLocalRandom.current().nextDouble(START_BOUND, END_BOUND);
        args.add(String.valueOf(randomNum1));
        double randomNum2 = ThreadLocalRandom.current().nextDouble(START_BOUND, END_BOUND);
        try {
            define.execute(data, args);
            Sum sum = new Sum();
            args.clear();
            args.add(DEFINE_STR2);
            args.add(String.valueOf(randomNum2));
            define.execute(data, args);
            args.clear();
            st.push(DEFINE_STR1);
            st.push(DEFINE_STR2);
            sum.execute(data, args);
        } catch (CommandInvalidNumOfArgs | DefineIsNumber | DefineNotNumber | UndefinedWord | EmptyStack e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(randomNum1 + randomNum2, Double.valueOf(st.pop()));
    }

    @Test
    @DisplayName("Should check correct of num of args in define : too few args")
    public void argsDefineTest1() {
        Assertions.assertThrows(CommandInvalidNumOfArgs.class,
                () -> define.execute(data, args), "An exception wasn't thrown!");
    }

    @Test
    @DisplayName("Should check correct of num of args in define : too few args")
    public void argsDefineTest2() {
        args.add(DEFINE_STR1);
        Assertions.assertThrows(CommandInvalidNumOfArgs.class,
                () -> define.execute(data, args), "An exception wasn't thrown!");
    }

    @Test
    @DisplayName("Should check correct of num of args in define : too many args")
    public void argsDefineTest3() {
        args.add(DEFINE_STR1);
        double randomNum = ThreadLocalRandom.current().nextDouble(START_BOUND, END_BOUND);
        args.add(String.valueOf(randomNum));
        args.add(DEFINE_STR2);
        Assertions.assertThrows(CommandInvalidNumOfArgs.class,
                () -> define.execute(data, args), "An exception wasn't thrown!");
    }

    @Test
    @DisplayName("Should check correct of args in define : define number by number")
    public void defineErrorArgsTest1() {
        double randomNum1 = ThreadLocalRandom.current().nextDouble(START_BOUND, END_BOUND);
        args.add(String.valueOf(randomNum1));
        double randomNum2 = ThreadLocalRandom.current().nextDouble(START_BOUND, END_BOUND);
        args.add(String.valueOf(randomNum2));
        Assertions.assertThrows(DefineIsNumber.class,
                () -> define.execute(data, args), "An exception wasn't thrown!");
    }

    @Test
    @DisplayName("Should check correct of args in define : define not a number")
    public void defineErrorArgsTest2() {
        args.add(DEFINE_STR1);
        args.add(DEFINE_STR2);
        Assertions.assertThrows(DefineNotNumber.class,
                () -> define.execute(data, args), "An exception wasn't thrown!");
    }
}

