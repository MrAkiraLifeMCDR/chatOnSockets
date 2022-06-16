package com.example.services;

import java.io.Serializable;

public record ResponseGetMessage(TextMessage textMessage, String userName) implements MessageReqRes, Serializable {


}
