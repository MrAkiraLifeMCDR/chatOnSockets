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
            System.out.println("-");
            this.outputStream = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("+");
            this.inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public MessageReqRes getMessage() {
        try {
            return (MessageReqRes) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMessageReqRes(MessageReqRes messageReqRes) throws IOException {
        this.outputStream.writeObject(messageReqRes);
        this.outputStream.flush();

    }
}
