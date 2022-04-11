package ru.nsu.fit.stack_calc;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ru.nsu.fit.stack_calc.commands.Print;
import ru.nsu.fit.stack_calc.exceptions.CommandInvalidNumOfArgs;
import ru.nsu.fit.stack_calc.exceptions.EmptyStack;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

public class PrintTests {
    private CalcData data;
    private Stack<String> st;
    private List<String> args;
    private Print print;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    private static final double START_BOUND = -100000.0;
    private static final double END_BOUND = 100000.0;
    private static final String NEXT_LINE = "\r\n";

    @BeforeEach
    public void initData() {
        data = new CalcData();
        st = data.getElements();
        args = new ArrayList<>();
        print = new Print();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void setOriginalOut() {
        System.setOut(originalOut);
    }

    @Test
    @DisplayName("Should check simple operation of print")
    public void PrintTest1() {
        double randomNum1 = ThreadLocalRandom.current().nextDouble(START_BOUND, END_BOUND);
        st.push(String.valueOf(randomNum1));
        try {
            print.execute(data, args);
        } catch (CommandInvalidNumOfArgs | EmptyStack e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(randomNum1 + NEXT_LINE, outContent.toString());
    }

    @Test
    @DisplayName("Should check correct of num of args in print : too many args")
    public void argsPrintTest() {
        double randomNum1 = ThreadLocalRandom.current().nextDouble(START_BOUND, END_BOUND);
        st.push(String.valueOf(randomNum1));

        double randomNum2 = ThreadLocalRandom.current().nextDouble(START_BOUND, END_BOUND);
        args.add(String.valueOf(randomNum2));

        Assertions.assertThrows(CommandInvalidNumOfArgs.class,
                () -> print.execute(data, args), "An exception wasn't thrown!");
    }

    @Test
    @DisplayName("Should check correct of stack : empty stack")
    public void emptyStackPrintTest() {
        Assertions.assertThrows(EmptyStack.class,
                () -> print.execute(data, args), "An exception wasn't thrown!" );
    }
}
