package ru.nsu.fit.chat.network;

import ru.nsu.fit.chat.command.Command;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TCPHandler implements Runnable {
    private TCPListener listener;
    private Socket socket;
    private boolean isActive;
    ObjectOutputStream outputStream;
    ObjectInputStream inputStream;


    public TCPHandler(TCPListener listener, Socket socket) throws IOException {
        this.listener = listener;
        this.socket = socket;
        isActive = true;
        try {
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            disconnect();
            throw e;
        }
    }

    @Override
    public void run() {
        listener.onConnect(this);
        try {
            while (isActive) {
                Command command = (Command) inputStream.readObject();
                listener.onMessageReceive(this, command);
            }
        } catch (ClassNotFoundException | IOException e) {
            listener.onDisconnect(this);
            disconnect();
            return;
        }
        listener.onDisconnect(this);
        disconnect();
    }

    public synchronized void sendMessage(Command command) {
        try {
            outputStream.writeObject(command);
            outputStream.flush();
        } catch (IOException e) {
            disconnect();
            e.printStackTrace();
        }

    }

    public synchronized void disconnect() {
        isActive = false;
        try {
            inputStream.close();
            outputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
