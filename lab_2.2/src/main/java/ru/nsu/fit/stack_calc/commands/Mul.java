package ru.nsu.fit.stack_calc.commands;

import org.apache.commons.lang3.math.NumberUtils;
import ru.nsu.fit.stack_calc.CalcData;
import ru.nsu.fit.stack_calc.exceptions.CommandInvalidNumOfArgs;
import ru.nsu.fit.stack_calc.exceptions.EmptyStack;
import ru.nsu.fit.stack_calc.exceptions.UndefinedWord;

import java.util.List;

import static ru.nsu.fit.stack_calc.commands.CommandsConstants.FIRST_ARG;
import static ru.nsu.fit.stack_calc.commands.CommandsConstants.FIRST_OPTION_NUM_OF_ARGS;
import static ru.nsu.fit.stack_calc.commands.CommandsConstants.SECOND_OPTION_NUM_OF_ARGS;

public class Mul implements Command {

    @Override
    public CalcData execute(CalcData calcData, List<String> args) throws CommandInvalidNumOfArgs, EmptyStack, UndefinedWord {
        Double arg1 = switch (args.size()) {
            case FIRST_OPTION_NUM_OF_ARGS -> calcData.pop();
            case SECOND_OPTION_NUM_OF_ARGS -> {
                String strArg1 = args.get(FIRST_ARG);
                if (NumberUtils.isCreatable(strArg1)) { // if strArg1 is a number
                    yield Double.valueOf(strArg1);
                } else {
                    yield calcData.getDefinedNum(strArg1);
                }
            }
            default -> throw new CommandInvalidNumOfArgs("Too many arguments for command * : " + args.size() +
                    "! It is right to pass " + FIRST_OPTION_NUM_OF_ARGS + " or " + SECOND_OPTION_NUM_OF_ARGS + " arguments.");
        };

        Double arg2 = calcData.pop();
        Double res = arg1 * arg2;
        calcData.push(String.valueOf(res));
        return calcData;
    }
}
