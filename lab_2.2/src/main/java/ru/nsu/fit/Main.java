package ru.nsu.fit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.nsu.fit.stack_calc.Calculator;
import ru.nsu.fit.stack_calc.exceptions.InvalidConfigFile;

import java.io.IOException;

public class Main {
    private static final int NUM_OF_FILENAME = 0;
    private static final int EMPTY_STR_LENGHT = 0;
    private static final Logger logger = LogManager.getLogger(Main.class);


    public static void  main(String[] args) {
         Calculator calc = new Calculator();
         if (args.length > EMPTY_STR_LENGHT) {
             calc.calculate(args[NUM_OF_FILENAME]);
         } else {
             calc.calculate();
         }
    }
}
