package ru.nsu.fit.chat.network;

import ru.nsu.fit.chat.command.Command;

public interface TCPListener {

    public void onMessageReceive(TCPHandler connection, Command command);
    public void onDisconnect(TCPHandler connection);
    public void onConnect(TCPHandler connection);

}
