package ru.nsu.fit.stack_calc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ru.nsu.fit.stack_calc.commands.Command;
import ru.nsu.fit.stack_calc.exceptions.CommandInvalidNumOfArgs;
import ru.nsu.fit.stack_calc.exceptions.EmptyStack;
import ru.nsu.fit.stack_calc.exceptions.InvalidConfigFile;
import ru.nsu.fit.stack_calc.exceptions.UndefinedWord;
import ru.nsu.fit.stack_calc.exceptions.UnknownCommand;
import ru.nsu.fit.stack_calc.exceptions.define_exceptions.DefineIsNumber;
import ru.nsu.fit.stack_calc.exceptions.define_exceptions.DefineNotNumber;
import ru.nsu.fit.stack_calc.exceptions.div_exceptions.DivisionByZero;
import ru.nsu.fit.stack_calc.exceptions.sqrt_exceptions.SqrtOfNegativeNum;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;


public class Calculator {
    private final String CONFIG_FILENAME = "Properties.txt";
    private static final int EMPTY_STR_LENGTH = 0;
    private CalcData calcData = new CalcData();

    private static final Logger logger = LogManager.getLogger(Calculator.class);

    private void handleLine(String newLine, CommandFactory factory) throws IOException, InvalidConfigFile {
        Parser parser = new Parser();
        if (newLine.trim().length() == EMPTY_STR_LENGTH ) return;
        CommandData curData = parser.parseStr(newLine);
        try {
            Command curCommand = factory.createCommand(curData.getCommandName());
            logger.info("Starts to execute command : {}", curData.getCommandName());
            calcData = curCommand.execute(calcData, curData.getArgs());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException | UnknownCommand | CommandInvalidNumOfArgs | EmptyStack | DivisionByZero | SqrtOfNegativeNum | UndefinedWord | DefineIsNumber | DefineNotNumber ex) {
            logger.error("Program throws exception : {} Program continue working.", ex.getMessage());
        }
    }

    private void executeStream(InputStream inputStream) {
        CommandFactory factory = new CommandFactory(CONFIG_FILENAME);
        String newLine;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            while ((newLine = reader.readLine()) != null) {
                handleLine(newLine, factory);
            }
        } catch (IOException | InvalidConfigFile ex) {
            logger.error("Program throws exception {} and can`t work anymore ", ex.getMessage());
        }
    }

    public void calculate(String fileName) {
        logger.info("Calculator with commands from file starts working");
        try (InputStream fileStream = new FileInputStream(fileName)) {
            executeStream(fileStream);
        } catch (IOException ex) {
            logger.error("Program throws exception {} and can`t work anymore ", ex.getMessage());
        }
    }

    public void calculate() {
        logger.info("Calculator with commands from console starts working");
        executeStream(System.in);
    }
}