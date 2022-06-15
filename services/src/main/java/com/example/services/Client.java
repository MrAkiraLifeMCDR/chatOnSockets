package com.example.services;

import java.io.*;
import java.net.Socket;

public class Client {

    private final Socket socket;
    private final ObjectOutputStream outputStream;

    private final ObjectInputStream inputStream;

    public Client(Socket socket) {
        try {
            this.socket = socket;
            this.outputStream = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            this.inputStream = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void sendMessageReqRes(MessageReqRes messageReqRes) {
        try {
            outputStream.writeObject(messageReqRes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Socket socket() {
        return socket;
    }
}
