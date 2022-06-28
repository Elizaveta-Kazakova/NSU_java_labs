package ru.nsu.fit.chat.command;

public class Answer extends Command {
    private static final long serialVersionUID = 5929685098267757690L;

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
