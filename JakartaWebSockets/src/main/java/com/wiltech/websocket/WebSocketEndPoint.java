package com.wiltech.websocket;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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

    private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session >());

    //to start mvn jetty:run
    //http://localhost:63342/web-sockets-hello-world/web-socket-client.html?
    public WebSocketEndPoint() {
        System.out.println("class loaded " + this.getClass());
    }

    @OnOpen
    public void onOpen(final Session session) {
        System.out.printf("Session opened, id: %s%n", session.getId());
        try {
            sessions.add(session);
            session.getBasicRemote().sendText("Hi there, we are successfully connected.");
        } catch (final IOException ex) {
            ex.printStackTrace();
        }
    }

    @OnMessage
    public void onMessage(final String message, final Session session) {
        System.out.printf("Message received. Session id: %s Message: %s%n",
                session.getId(), message);

            sessions.forEach(s -> {
                try {
                    s.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
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
