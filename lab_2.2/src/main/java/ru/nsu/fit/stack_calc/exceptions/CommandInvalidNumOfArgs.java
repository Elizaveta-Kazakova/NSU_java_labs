package ru.nsu.fit.stack_calc.exceptions;

public class CommandInvalidNumOfArgs extends Exception {
    public CommandInvalidNumOfArgs(String errMessage) { // а нужен ли err если у нас просто наследование от exception
        super(errMessage);
    }
}
