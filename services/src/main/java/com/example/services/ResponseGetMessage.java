package com.example.services;

public record ResponseGetMessage(TextMessage textMessage) implements MessageReqRes {

    @Override
    public TypeMessage typeMessage() {
        return TypeMessage.RESPONSE_GET_MESSAGE;
    }
}
