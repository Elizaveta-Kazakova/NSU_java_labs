package ru.nsu.fit.chat.network;

import ru.nsu.fit.chat.command.Command;
import ru.nsu.fit.chat.timer.Timer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TCPHandler implements Runnable {
    private static final int TIMEOUT_IN_MINUTES = 1;


    private TCPListener listener;
    private Socket socket;
    private boolean isActive;
    private Timer timer;
    ObjectOutputStream outputStream;
    ObjectInputStream inputStream;

    public TCPHandler(TCPListener listener, String ipAddress, int port, Timer timer) throws IOException {
        this(listener, new Socket(ipAddress, port), timer);
    }

    public TCPHandler(TCPListener listener, Socket socket, Timer timer) throws IOException {
        this.listener = listener;
        this.socket = socket;
        isActive = true;
        this.timer = timer;
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
        Thread timeThread = new Thread(timer);
        timeThread.start();
        listener.onConnect(this);
        try {
            while (isActive) {
                synchronized (timer) {
                    if (timer.getAmountOfElapsedMinutes() >= TIMEOUT_IN_MINUTES) {
                        break;
                    }
                }
                Command command = (Command) inputStream.readObject();
                listener.onMessageReceive(this, command);

            }
        } catch (ClassNotFoundException | IOException e) {
            listener.onDisconnect(this);
            disconnect();
            timer.setActive(false);
            return;
        }
        listener.onDisconnect(this);
        disconnect();
        timer.setActive(false);
    }

    public synchronized void sendMessage(Command command) {
        try {
            outputStream.writeObject(command);
            outputStream.flush();
        } catch (IOException e) {
            disconnect();
            timer.setActive(false);
        }
    }

    public synchronized void disconnect() {
        isActive = false;
        try {
            inputStream.close();
            outputStream.close();
            socket.close();
        } catch (IOException e) {
            timer.setActive(false);
            e.printStackTrace();
        }
    }
}