package ru.nsu.fit.stack_calc.exceptions;

public class UnknownCommand extends Exception {
    public UnknownCommand(String errMessage) {
        super(errMessage);
    }
}
