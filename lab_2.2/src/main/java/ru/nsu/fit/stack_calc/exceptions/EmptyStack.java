package ru.nsu.fit.stack_calc.exceptions;

public class EmptyStack extends Exception {
    public EmptyStack(String errMessage) {
        super(errMessage);
    }
}
