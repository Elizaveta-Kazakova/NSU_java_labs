package ru.nsu.fit.stack_calc;

public class Parser {

    private static final int INDEX_OF_FIRST_ARG = 1;

    public CommandData parseStr(String str) { // one name, some args; str not empty
        String[] strData = str.split("\\s");
        CommandData data = new CommandData();
        data.setCommandName(strData[0]);
        for (int i = INDEX_OF_FIRST_ARG; i < strData.length; ++i) {
            data.addInArgs(strData[i]);
        }
        return data;
    }

}