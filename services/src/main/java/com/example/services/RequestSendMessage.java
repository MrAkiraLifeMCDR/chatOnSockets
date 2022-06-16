package com.example.services;

import java.io.Serializable;

public record RequestSendMessage(TextMessage textMessage, String userName) implements MessageReqRes, Serializable {

}
