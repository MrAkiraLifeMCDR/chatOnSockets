package com.example.client;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AuthorizationWindow extends Application {

    @Override
    public void start(Stage primaryStage) {
        Label userNameLabel = new Label("Введите имя пользователя");
        TextField enterUserName = new TextField();
        enterUserName.setText("Akira");

        Label ipLabel = new Label("Введите IP к которому хотите подключиться");
        TextField enterIp = new TextField();
        enterIp.setText("localhost");

        Button button = new Button("Подключиться");

        button.setOnAction(actionEvent ->
        {
            try {
                new ChatWindow().startChatWindow(primaryStage, enterIp.getText(), enterUserName.getText());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        FlowPane root = new FlowPane(Orientation.VERTICAL, 10, 10, userNameLabel, enterUserName, ipLabel, enterIp, button);
        Scene scene = new Scene(root, 300, 300);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Афторизация нового пользователя");
        primaryStage.show();
    }
}
