package ru.nsu.fit.chat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ru.nsu.fit.chat.command.Answer;
import ru.nsu.fit.chat.command.Command;
import ru.nsu.fit.chat.command.LogIn;
import ru.nsu.fit.chat.command.LogOut;
import ru.nsu.fit.chat.command.UserList;
import ru.nsu.fit.chat.command.UserLogIn;
import ru.nsu.fit.chat.command.UserLogOut;
import ru.nsu.fit.chat.model.chat.MessageStore;
import ru.nsu.fit.chat.network.TCPHandler;
import ru.nsu.fit.chat.network.TCPListener;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Server implements TCPListener {

    private static final Logger logger = LogManager.getLogger(Server.class);
    private static final int SIZE_OF_POOL = 10;
    private static final int PORT = 8080;

    private ThreadPoolExecutor pool;
    private ServerSocket server;

    private List<TCPHandler> connections = new ArrayList<>();
    private MessageStore messageStore = new MessageStore();
    private UserList userList = new UserList();
    private Map<TCPHandler, String> connectionsForUsers = new HashMap<>();

    private void initServer() throws IOException {
        server = new ServerSocket(PORT);
        pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(SIZE_OF_POOL);
    }

    private void handleLogOutUser(String userName, TCPHandler connection) throws IOException {
        connections.remove(connection);
        userList.deleteParticipant(userName);
        sendToAll(userList);
        connectionsForUsers.remove(connection);
    }

    private void handleLogInUser(String userName, TCPHandler connection) throws IOException {
        userList.addParticipant(userName);
        sendToAll(userList);
        connectionsForUsers.put(connection, userName);
    }

    private void retainingSend(Command command) throws IOException {
        messageStore.addMessage(command);
        sendToAll(command);
    }

    private void sendToAll(Command command) {
        for (TCPHandler connection : connections) {
            connection.sendMessage(command);
        }
    }

    private void sendMessageStory(TCPHandler connection) {
        for (int index = 0; index < messageStore.getSize(); ++index) {
            connection.sendMessage(messageStore.getMessage(index));
        }
    }

    public Server() {
        try {
            initServer();
            while (true) {
                TCPHandler tcpHandler = new TCPHandler(this, server.accept());
                pool.execute(tcpHandler);
            }
        } catch (IOException e) {
            pool.shutdown();
        }
    }



    @Override
    public synchronized void onMessageReceive(TCPHandler connection, Command command) {
        logger.info("message {} receive", command.getClass());
        Answer answer = new Answer();
        connection.sendMessage(answer);
        Command sendingCommand = command;
        try {
            if (command instanceof LogIn) {
                String userName = ((LogIn) command).getUserName();
                handleLogInUser(userName, connection);
                sendingCommand = new UserLogIn(userName, command.getText());
            }
            if (command instanceof LogOut) {
                onDisconnect(connection);
                return;
            }
            logger.info("message text {}", sendingCommand.getText());
            retainingSend(sendingCommand);

        } catch (IOException e) {
            connection.disconnect();
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void onDisconnect(TCPHandler connection) {
        String userName = connectionsForUsers.get(connection);
        logger.info("disconnect {} user", userName);
        try {
            handleLogOutUser(userName, connection);
            retainingSend(new UserLogOut(userName));
            connection.disconnect();
            pool.remove(connection);
        } catch (IOException e) {
            connection.disconnect();
            e.printStackTrace();
        }

    }



    @Override
    public synchronized void onConnect(TCPHandler connection) {
        logger.info("connect new user");
        connections.add(connection);
        sendMessageStory(connection);
    }
}
