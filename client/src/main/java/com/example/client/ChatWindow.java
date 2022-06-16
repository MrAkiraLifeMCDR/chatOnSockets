package com.example.client;

import com.example.services.*;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import java.io.*;
import java.net.Socket;

public class ChatWindow {

    public void startChatWindow(Stage primaryStage, String ip, String userName) throws IOException {

        Label chatLabel = new Label("Окно чата");
        TextArea showTextOfChat = new TextArea(); // ВЫВОД СООБЩЕНИЙ НА ЭКРАН КЛИЕНТА

        Label yourNewMessagesLabel = new Label("Введите ваше новое сообщение");
        TextField enterNewMyMessage = new TextField(); // ПРИНИМАЕТ СООБЩЕНИЯ ОТ КЛИЕНТА

        Button send = new Button("Отправить сообщение");

        final Client[] client = new Client[1];
        //todo слушать сервер
        new Thread(() -> {
            Socket socket = null;
            System.out.println("Socket для клиента создан");
            try {
                socket = new Socket(ip, 4000);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            client[0] = new Client(socket);
            System.out.println("Клиент подключен к серверу [" + client[0] + "]");

            StringBuilder stringBuilder = new StringBuilder();
            while (true) {
                var messageReqRes = client[0].getMessage();
                switch (messageReqRes) {
                    case ResponseGetMessage message -> {
                        // todo получить и занести в UI новое сообщение

                        System.out.println("Новые сообщения от клиентов -> " + message.textMessage().text());

                        stringBuilder.append("[").append(message.userName()).append("] ").append(message.textMessage().text()).append("\n");

                        showTextOfChat.setText(stringBuilder.toString());

                    }
                    case ResponseSendMessage message -> {
                        System.out.println(message.answer());
                    }
                    default -> throw new IllegalStateException();
                }
            }

        }).start();


        send.setOnAction(actionEvent -> {
            //todo send REQUEST_SEND_MESSAGE to server
            var requestSendMessage = new RequestSendMessage(new TextMessage(enterNewMyMessage.getText()), userName);
            try {
                client[0].sendMessageReqRes(requestSendMessage);
            } catch (IOException e) {
                //todo сообщить в UI сообщение о ошибке

            }
            enterNewMyMessage.clear();
        });

        showTextOfChat.setEditable(false);

        FlowPane flowPane = new FlowPane(
                Orientation.VERTICAL, 10, 10, chatLabel, showTextOfChat, yourNewMessagesLabel, enterNewMyMessage, send);
        Scene scene = new Scene(flowPane);

        primaryStage.setScene(scene);
    }
}
