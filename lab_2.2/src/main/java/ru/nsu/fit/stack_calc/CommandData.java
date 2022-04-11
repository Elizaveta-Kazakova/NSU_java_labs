package ru.nsu.fit.stack_calc;

import java.util.ArrayList;

public class CommandData {
    private String commandName;
    private ArrayList<String> args = new ArrayList<>();

    public String getCommandName() {
        return commandName;
    }

    public ArrayList<String> getArgs() {
        return args;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public void setArgs(ArrayList<String> args) {
        this.args = args;
    }

    public void addInArgs(String arg) {
        args.add(arg);
    }
}
