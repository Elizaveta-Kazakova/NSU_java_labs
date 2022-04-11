package ru.nsu.fit.stack_calc;

import org.apache.commons.lang3.math.NumberUtils;
import ru.nsu.fit.stack_calc.exceptions.EmptyStack;
import ru.nsu.fit.stack_calc.exceptions.UndefinedWord;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class CalcData {
    private static final int EMPTY_SIZE = 0;

    private Stack<String> elements = new Stack<>();
    private Map<String, Double> parameters = new HashMap<>();

    public void push(String number) throws UndefinedWord {
        if (!NumberUtils.isCreatable(number) && !parameters.containsKey(number)) {
            throw new UndefinedWord("Tried to push word " + number + " that is not defined later!");
        }
        elements.push(number);
    }

    public Double pop() throws EmptyStack {
        Double res;
        if (elements.size() == EMPTY_SIZE) {
            throw new EmptyStack("Impossible to pop from empty stack!");
        }
        String el = elements.pop();
        if (parameters.containsKey(el)) {
            res = parameters.get(el);
        } else {
            res = Double.valueOf(el);
        }
        return res;
    }

    public Double peek() throws EmptyStack {
        Double res;
        if (elements.size() == EMPTY_SIZE) {
            throw new EmptyStack("Impossible to peek empty stack!");
        }
        String el = elements.peek();
        if (parameters.containsKey(el)) {
            res = parameters.get(el);
        } else {
            res = Double.valueOf(el);
        }
        return res;
    }

    public Double getDefinedNum(String name) throws UndefinedWord {
        if (!parameters.containsKey(name)) {
            throw new UndefinedWord("This word : " + name + " is not defined later!");
        }
        return parameters.get(name);
    }

    public void defineNumber(String name, Double num) {
        parameters.put(name, num);
    }

    public Stack<String> getElements() {
        return elements;
    }


}
