package ru.nsu.fit.stack_calc.commands;

import ru.nsu.fit.stack_calc.CalcData;
import ru.nsu.fit.stack_calc.exceptions.CommandInvalidNumOfArgs;
import ru.nsu.fit.stack_calc.exceptions.EmptyStack;
import ru.nsu.fit.stack_calc.exceptions.UndefinedWord;
import ru.nsu.fit.stack_calc.exceptions.define_exceptions.DefineIsNumber;
import ru.nsu.fit.stack_calc.exceptions.define_exceptions.DefineNotNumber;
import ru.nsu.fit.stack_calc.exceptions.div_exceptions.DivisionByZero;
import ru.nsu.fit.stack_calc.exceptions.sqrt_exceptions.SqrtOfNegativeNum;

import java.util.List;

public interface Command {
    CalcData execute(CalcData calcData, List<String> args) throws CommandInvalidNumOfArgs, EmptyStack, UndefinedWord,
            SqrtOfNegativeNum, DivisionByZero, DefineIsNumber, DefineNotNumber;
}