package ru.nsu.fit.chat.command;

import java.io.Serializable;

public abstract class Command implements Serializable {

    public abstract String getText();
}
