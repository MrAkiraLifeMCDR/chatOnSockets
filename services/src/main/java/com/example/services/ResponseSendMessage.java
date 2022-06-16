package com.example.services;

import java.io.Serializable;

public record ResponseSendMessage(String answer) implements MessageReqRes, Serializable {

}
