package ru.nsu.fit.stack_calc.commands;

import ru.nsu.fit.stack_calc.CalcData;

import java.util.List;

public class Comment implements Command {
    @Override
    public CalcData execute(CalcData calcData, List<String> args) {
        return calcData;
    }

}
