package ru.nsu.fit.chat.model.chat;

import ru.nsu.fit.chat.command.Command;
import ru.nsu.fit.chat.command.LogIn;
import ru.nsu.fit.chat.command.LogOut;
import ru.nsu.fit.chat.command.Message;
import ru.nsu.fit.chat.command.UserList;
import ru.nsu.fit.chat.network.TCPHandler;
import ru.nsu.fit.chat.network.TCPListener;
import ru.nsu.fit.chat.observerPattern.Observable;
import ru.nsu.fit.chat.timer.Timer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Client extends Observable implements TCPListener {
    private static final String IP_ADDR = "localhost";
    private static final int PORT = 8080;

    private Timer timer = new Timer();

    private String userName;

    private TCPHandler connection;

    private List<Command> messages = new ArrayList<>();
    private UserList userList = new UserList();

    public Client(String userName) {
        this.userName = userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void startClient() {
        try {
            connection = new TCPHandler(this, IP_ADDR, PORT, timer);
            Thread connectionThread = new Thread(connection);
            connectionThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessageReceive(TCPHandler connection, Command command) {
        if (command instanceof UserList) {
            userList = (UserList) command;
        } else {
            messages.add(command);
        }
        notifyObservers();
    }

    @Override
    public void onDisconnect(TCPHandler connection) {
        LogOut logOut = new LogOut(userName);
        connection.sendMessage(logOut);
    }

    @Override
    public void onConnect(TCPHandler connection) {
        LogIn logIn = new LogIn(userName);
        connection.sendMessage(logIn);
        timer.restartTimer();
    }

    public void sendMessage(MessageData messageData) {
        Message message = new Message(messageData);
        connection.sendMessage(message);
        timer.restartTimer();
    }

    public List<Command> getMessages() {
            return messages;
    }

    public UserList getUserList() {
        return userList;
    }

}

