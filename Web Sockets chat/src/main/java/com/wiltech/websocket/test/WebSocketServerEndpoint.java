package com.wiltech.websocket.test;

import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/endpoint/session")
public class WebSocketServerEndpoint {

    private Session session;

    @OnOpen
    public void onOpen(Session session) throws IOException {
        this.session = session;

        // send message on connection
        session.getBasicRemote().sendText("Hi!");
        System.out.println("Opened");
    }

    @OnMessage
    public String onMessage(String message, Session session) {
        this.session.getAsyncRemote()
                .sendText(message);

        return "Server received [" + message + "]";
    }

    @OnClose
    public void onClose(Session session) {
        this.session = null;
        System.out.println("Closed");
    }

    @OnError
    public void onError(Throwable exception, Session session) {
    }

}
