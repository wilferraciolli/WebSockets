package com.wiltech.websocket;

import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * Web socket server.
 */
@ServerEndpoint("/message")
public class WebSocketEndPoint {

    //to start mvn jetty:run
    //http://localhost:63342/web-sockets-hello-world/web-socket-client.html?
    public WebSocketEndPoint() {
        System.out.println("class loaded " + this.getClass());
    }

    @OnOpen
    public void onOpen(final Session session) {
        System.out.printf("Session opened, id: %s%n", session.getId());
        try {
            session.getBasicRemote().sendText("Hi there, we are successfully connected.");
        } catch (final IOException ex) {
            ex.printStackTrace();
        }
    }

    @OnMessage
    public void onMessage(final String message, final Session session) {
        System.out.printf("Message received. Session id: %s Message: %s%n",
                session.getId(), message);
        try {
            session.getBasicRemote().sendText(String.format("We received your message: %s%n", message));
        } catch (final IOException ex) {
            ex.printStackTrace();
        }
    }

    @OnError
    public void onError(final Throwable e) {
        e.printStackTrace();
    }

    @OnClose
    public void onClose(final Session session) {
        System.out.printf("Session closed with id: %s%n", session.getId());
    }
}
