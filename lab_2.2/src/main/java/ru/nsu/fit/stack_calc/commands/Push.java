package ru.nsu.fit.stack_calc.commands;

import ru.nsu.fit.stack_calc.CalcData;
import ru.nsu.fit.stack_calc.exceptions.CommandInvalidNumOfArgs;
import ru.nsu.fit.stack_calc.exceptions.UndefinedWord;

import java.util.List;


import static ru.nsu.fit.stack_calc.commands.CommandsConstants.FIRST_ARG;

public class Push implements Command {

    private static final int NUM_OF_ARGS = 1;

    @Override
    public CalcData execute(CalcData calcData, List<String> args) throws CommandInvalidNumOfArgs, UndefinedWord {
        if (args.size() > NUM_OF_ARGS) {
            throw new CommandInvalidNumOfArgs("Too many arguments for command PUSH : " + args.size() +
                    "! It is right to pass " + NUM_OF_ARGS + " arguments.");
        }
        if (args.size() < NUM_OF_ARGS) {
            throw new CommandInvalidNumOfArgs("Too few arguments for command PUSH : " + args.size() +
                    "! It is right to pass " + NUM_OF_ARGS + " arguments.");
        }
        calcData.push(args.get(FIRST_ARG));
        return calcData;
    }
}
