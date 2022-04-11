package ru.nsu.fit.stack_calc.commands;

import org.apache.commons.lang3.math.NumberUtils;
import ru.nsu.fit.stack_calc.CalcData;
import ru.nsu.fit.stack_calc.exceptions.CommandInvalidNumOfArgs;
import ru.nsu.fit.stack_calc.exceptions.EmptyStack;
import ru.nsu.fit.stack_calc.exceptions.UndefinedWord;
import ru.nsu.fit.stack_calc.exceptions.sqrt_exceptions.SqrtOfNegativeNum;

import java.util.List;

import static ru.nsu.fit.stack_calc.commands.CommandsConstants.FIRST_ARG;
import static ru.nsu.fit.stack_calc.commands.CommandsConstants.FIRST_OPTION_NUM_OF_ARGS;
import static ru.nsu.fit.stack_calc.commands.CommandsConstants.SECOND_OPTION_NUM_OF_ARGS;

public class Sqrt implements Command {

    private static final int SQRT_BOUNDARY = 0;

    @Override
    public CalcData execute(CalcData calcData, List<String> args) throws CommandInvalidNumOfArgs, EmptyStack,
            UndefinedWord, SqrtOfNegativeNum {
        Double arg = switch (args.size()) {
            case FIRST_OPTION_NUM_OF_ARGS -> calcData.pop();
            case SECOND_OPTION_NUM_OF_ARGS -> {
                String strArg1 = args.get(FIRST_ARG);
                if (NumberUtils.isCreatable(strArg1)) { // if strArg1 is a number
                    yield Double.valueOf(strArg1);
                } else {
                    yield calcData.getDefinedNum(strArg1);
                }
            }
            default -> throw new CommandInvalidNumOfArgs("Too many arguments for command SQRT : " + args.size()
                    + "! It is right to pass " + FIRST_OPTION_NUM_OF_ARGS + " or " + SECOND_OPTION_NUM_OF_ARGS + " arguments.");
        };
        if (arg < SQRT_BOUNDARY) {
            throw new SqrtOfNegativeNum("Impossible to calculate sqrt of negative number : " + arg +"!");
        }
        calcData.push(String.valueOf(Math.sqrt(arg)));
        return calcData;
    }

}