package ru.nsu.fit.stack_calc.commands;

import org.apache.commons.lang3.math.NumberUtils;
import ru.nsu.fit.stack_calc.CalcData;
import ru.nsu.fit.stack_calc.exceptions.CommandInvalidNumOfArgs;
import ru.nsu.fit.stack_calc.exceptions.define_exceptions.DefineIsNumber;
import ru.nsu.fit.stack_calc.exceptions.define_exceptions.DefineNotNumber;

import java.util.List;

public class Define implements Command {

    private static final int NUM_OF_ARGS = 2;
    private static final int ARG_FOR_NAME = 0;
    private static final int ARG_FOR_NUM = 1;

    @Override
    public CalcData execute(CalcData calcData, List<String> args) throws CommandInvalidNumOfArgs, DefineIsNumber,
            DefineNotNumber {
        if (args.size() > NUM_OF_ARGS) {
            throw new CommandInvalidNumOfArgs("Too many arguments for command DEFINE : " + args.size() +
                    "! It is right to pass " + NUM_OF_ARGS + " arguments.");
        }
        if (args.size() < NUM_OF_ARGS) {
            throw new CommandInvalidNumOfArgs("Too many arguments for command DEFINE : " + args.size() +
                    "! It is right to pass " + NUM_OF_ARGS + " arguments.");
        }
        if (NumberUtils.isCreatable(args.get(ARG_FOR_NAME))) { // if name is a number
             throw new DefineIsNumber("Impossible to define a number by a number : " + args.get(ARG_FOR_NAME) + " !");
        }
        if (!NumberUtils.isCreatable(args.get(ARG_FOR_NUM))) { // if second arg is not a number
            throw new DefineNotNumber("Impossible to define not a number : " + args.get(ARG_FOR_NUM) + " !");
        }
        calcData.defineNumber(args.get(ARG_FOR_NAME), Double.valueOf(args.get(ARG_FOR_NUM)));
        return calcData;
    }
}