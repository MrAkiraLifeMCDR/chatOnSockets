package com.example.server;

import com.example.services.*;

import java.io.*;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class ServerLogic {

    private final ServerSocket serverSocket;

    private final List<Client> clientList;


    public ServerLogic(int port) {
        try {
            this.serverSocket = new ServerSocket(port);
            this.clientList = new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public void startServer() {
        System.out.println("Сервер запущен!");

        while (true) {
            System.out.println("Ожидание подключения от клиента");
            try {
                var socket = serverSocket.accept();
                System.out.println("Есть подключение от: " + socket.toString());
                var client = new Client(socket);
                System.out.println("Клиент добавлен в список");
                clientList.add(client);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("Пошла работа с клиентом (поток)");
                        serveClient(client);
                    }
                }).start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void serveClient(Client client) {
        //todo обслужить клиента
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new BufferedInputStream(client.socket().getInputStream()));
            System.out.println("Обоюдная связсь Сервер/Клиент налажена");
            System.out.println("Ждем сообщение от клиента");
            var messageReqRes = (MessageReqRes) inputStream.readObject();
            System.out.println("Сообщение от клиента полученно: " + messageReqRes.toString());

            if (messageReqRes instanceof RequestSendMessage message) {
                var responseGetMessage = new ResponseGetMessage(message.textMessage());
                sendResponseGetMessageAllClients(responseGetMessage);
                var responseSendMessage = new ResponseSendMessage("Сообщение успешно доставленно");
                client.sendMessageReqRes(responseSendMessage);
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendResponseGetMessageAllClients(MessageReqRes messageReqRes) {
        for (Client client : clientList) {
            client.sendMessageReqRes(messageReqRes);
            System.out.println("Отправили (" + messageReqRes.toString() + ") к (" + client + ")");
        }
    }
}
