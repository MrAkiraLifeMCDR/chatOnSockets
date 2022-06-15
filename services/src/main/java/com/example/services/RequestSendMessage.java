package com.example.services;

import java.io.Serializable;

public record RequestSendMessage(TextMessage textMessage) implements MessageReqRes, Serializable {

    @Override
    public TypeMessage typeMessage() {
        return TypeMessage.REQUEST_SEND_MESSAGE;
    }
}
