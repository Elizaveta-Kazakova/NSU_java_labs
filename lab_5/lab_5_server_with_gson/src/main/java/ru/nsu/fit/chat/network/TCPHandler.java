package ru.nsu.fit.chat.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.nsu.fit.chat.command.Command;
import ru.nsu.fit.chat.command.CommandAdapter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class TCPHandler implements Runnable {
    private TCPListener listener;
    private Socket socket;
    private boolean isActive;
    private BufferedWriter writer;
    private BufferedReader reader;
    private Gson gson;

    public TCPHandler(TCPListener listener, String ipAddress, int port) throws IOException {
        this(listener, new Socket(ipAddress, port));
    }

    public TCPHandler(TCPListener listener, Socket socket) throws IOException {
        this.listener = listener;
        this.socket = socket;
        isActive = true;
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        gson = new GsonBuilder().registerTypeAdapter(Command.class, new CommandAdapter()).create();
    }


    @Override
    public void run() {
        listener.onConnect(this);
        try {
            while (isActive) {
                String input = reader.readLine();
                Command command = gson.fromJson(input, Command.class);
                listener.onMessageReceive(this, command);
            }
        } catch (IOException e) {
            listener.onDisconnect(this);
        }
    }

    public synchronized void sendMessage(Command command) {
        try {
            writer.write(gson.toJson(command, Command.class) + "\n");
            writer.flush();
        } catch (IOException e) {
            disconnect();
        }
    }

    public synchronized void disconnect() {
        isActive = false;
        try {
            reader.close();
            writer.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
