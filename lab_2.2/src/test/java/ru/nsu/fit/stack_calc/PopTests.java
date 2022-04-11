package ru.nsu.fit.stack_calc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ru.nsu.fit.stack_calc.commands.Pop;
import ru.nsu.fit.stack_calc.exceptions.CommandInvalidNumOfArgs;
import ru.nsu.fit.stack_calc.exceptions.EmptyStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

public class PopTests {
    private CalcData data;
    private Stack<String> st;
    private List<String> args;
    private Pop pop;

    private static final int EMPTY_SIZE = 0;
    private static final double START_BOUND = -100000.0;
    private static final double END_BOUND = 100000.0;


    @BeforeEach
    public void initData() {
        data = new CalcData();
        st = data.getElements();
        args = new ArrayList<>();
        pop = new Pop();
    }

    @Test
    @DisplayName("Should check simple arithmetic operations with pop")
    public void arithmeticPopTest() {
        double randomNum = ThreadLocalRandom.current().nextDouble(START_BOUND, END_BOUND);
        st.push(String.valueOf(randomNum));
        try {
            pop.execute(data, args);
            Assertions.assertEquals(EMPTY_SIZE, st.size());
        } catch (CommandInvalidNumOfArgs | EmptyStack e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Should check correct of num of args in pop : too many args")
    public void argsPopTest1() {
        double randomNum = ThreadLocalRandom.current().nextDouble(START_BOUND, END_BOUND);
        st.push(String.valueOf(randomNum));
        double randomArg = ThreadLocalRandom.current().nextDouble(START_BOUND, END_BOUND);
        args.add(String.valueOf(randomArg));
        Assertions.assertThrows(CommandInvalidNumOfArgs.class,
                () -> pop.execute(data, args), "An exception wasn't thrown!" );
    }

    @Test
    @DisplayName("Should check correct of num of args in pop : too many args")
    public void argsPopTest2() {
        double randomNum = ThreadLocalRandom.current().nextDouble(START_BOUND, END_BOUND);
        st.push(String.valueOf(randomNum));
        double randomArg1 = ThreadLocalRandom.current().nextDouble(START_BOUND, END_BOUND);
        args.add(String.valueOf(randomArg1));
        double randomArg2 = ThreadLocalRandom.current().nextDouble(START_BOUND, END_BOUND);
        args.add(String.valueOf(randomArg2));
        Assertions.assertThrows(CommandInvalidNumOfArgs.class,
                () -> pop.execute(data, args), "An exception wasn't thrown!" );
    }

    @Test
    @DisplayName("Should check correct of stack : empty stack")
    public void argsPopTest3() {
        Assertions.assertThrows(EmptyStack.class,
                () -> pop.execute(data, args), "An exception wasn't thrown!" );
    }
}
