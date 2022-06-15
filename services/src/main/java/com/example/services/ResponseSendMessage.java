package com.example.services;

public record ResponseSendMessage(String answer) implements MessageReqRes {

    @Override
    public TypeMessage typeMessage() {
        return TypeMessage.RESPONSE_SEND_MESSAGE;
    }
}
