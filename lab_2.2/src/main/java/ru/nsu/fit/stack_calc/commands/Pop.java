package ru.nsu.fit.stack_calc.commands;

import ru.nsu.fit.stack_calc.CalcData;
import ru.nsu.fit.stack_calc.exceptions.CommandInvalidNumOfArgs;
import ru.nsu.fit.stack_calc.exceptions.EmptyStack;

import java.util.List;

public class Pop implements Command {

    private static final int NUM_OF_ARGS = 0;

    @Override
    public CalcData execute(CalcData calcData, List<String> args) throws CommandInvalidNumOfArgs, EmptyStack {
        if (args.size() > NUM_OF_ARGS) {
            throw new CommandInvalidNumOfArgs("Too many arguments for command POP : " + args.size() +
                    ". It is right to pass " + NUM_OF_ARGS + " arguments.");
        }
        calcData.pop();
        return calcData;
    }
}
