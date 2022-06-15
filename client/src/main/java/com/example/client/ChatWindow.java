package com.example.client;

import com.example.services.Client;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.Socket;
public class ChatWindow {
    public void startChatWindow(Stage primaryStage, String ip, String userName) throws IOException {
        Socket socket = new Socket(ip, 4000);
        Client client = new Client(socket);
        System.out.println("Socket для клиента создан");
        
        Label chatLabel = new Label("Окно чата");
        TextArea showTextOfChat = new TextArea(); // ВЫВОД СООБЩЕНИЙ НА ЭКРАН КЛИЕНТА

        Label yourNewMessagesLabel = new Label("Введите ваше новое сообщение");
        TextField enterNewMyMessage = new TextField(); // ПРИНИМАЕТ СООБЩЕНИЯ ОТ КЛИЕНТА

        Button send = new Button("Отправить сообщение");

        send.setOnAction(actionEvent -> {
            //todo send REQUEST_SEND_MESSAGE to server

            enterNewMyMessage.clear();
        });

        showTextOfChat.setEditable(false);

        FlowPane flowPane = new FlowPane(
                Orientation.VERTICAL, 10, 10, chatLabel, showTextOfChat, yourNewMessagesLabel, enterNewMyMessage, send);
        Scene scene = new Scene(flowPane);

        primaryStage.setScene(scene);
    }
}
