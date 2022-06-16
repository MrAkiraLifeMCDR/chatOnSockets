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
                System.out.println("Пошла работа с клиентом (поток)");
                serveClient(client);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void serveClient(Client client) {
        //todo обслужить клиента
        System.out.println("Обоюдная связсь Сервер/Клиент налажена");
        System.out.println("Ждем сообщение от клиента");

        new Thread(() -> {
            try {

                while (true) {
                    var messageReqRes = client.getMessage();

                    if (messageReqRes instanceof RequestSendMessage message) {
                        var responseGetMessage = new ResponseGetMessage(message.textMessage(), message.userName());
                        sendResponseGetMessageAllClients(responseGetMessage);
                        var responseSendMessage = new ResponseSendMessage("Сообщение успешно доставленно");
                        client.sendMessageReqRes(responseSendMessage);
                        System.out.println("Сообщение от клиента полученно: " + messageReqRes.toString());
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }).start();

    }

    private void sendResponseGetMessageAllClients(MessageReqRes messageReqRes) throws IOException {
        for (Client client : clientList) {
            client.sendMessageReqRes(messageReqRes);
            System.out.println("Отправили (" + messageReqRes.toString() + ") к (" + client + ")");
        }
    }
}
