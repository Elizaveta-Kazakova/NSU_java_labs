package ru.nsu.fit.chat.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.nsu.fit.chat.command.Command;
import ru.nsu.fit.chat.command.CommandAdapter;
import ru.nsu.fit.chat.timer.Timer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class TCPHandler implements Runnable {
    private static final int TIMEOUT_IN_MINUTES = 1;

    private final TCPListener listener;
    private final Socket socket;
    private boolean isActive;
    private Timer timer;
    private BufferedWriter writer;
    private BufferedReader reader;
    private Gson gson;

    public TCPHandler(TCPListener listener, String ipAddress, int port, Timer timer) throws IOException {
        this(listener, new Socket(ipAddress, port), timer);
    }

    public TCPHandler(TCPListener listener, Socket socket, Timer timer) throws IOException {
        this.listener = listener;
        this.socket = socket;
        isActive = true;
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        gson = new GsonBuilder().registerTypeAdapter(Command.class, new CommandAdapter()).create();
        this.timer = timer;
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
                Command command = gson.fromJson(reader.readLine(), Command.class);
                listener.onMessageReceive(this, command);
            }
        } catch (IOException e) {
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
            writer.write(gson.toJson(command, Command.class) + "\n");
            writer.flush();
        } catch (IOException e) {
            disconnect();
            timer.setActive(false);
        }
    }

    public synchronized void disconnect() {
        isActive = false;
        try {
            reader.close();
            writer.close();
            socket.close();
        } catch (IOException e) {
            timer.setActive(false);
            e.printStackTrace();
        }
    }
}

