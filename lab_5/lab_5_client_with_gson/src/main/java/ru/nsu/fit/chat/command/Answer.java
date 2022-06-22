package ru.nsu.fit.chat.command;

public class Answer extends Command {

    private String errorMessage = null;

    public Answer(String errorMessage) {
        this();
        this.errorMessage = errorMessage;
    }

    public Answer() {
        super();
    }

    @Override
    public String getText() {
        return errorMessage;
    }
}
