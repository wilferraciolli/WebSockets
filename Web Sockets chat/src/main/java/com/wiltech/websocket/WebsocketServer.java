package com.wiltech.websocket;

import java.io.IOException;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.naming.NamingException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import javax.xml.registry.infomodel.User;

@ServerEndpoint(value = "/session")
public class WebsocketServer {

    @Inject
    private WebSocketsSessionRegistry webSocketsSessionRegistry;

    @OnOpen
    public void onOpen(Session session) throws NamingException, IOException {
        // Register the newly created session
        webSocketsSessionRegistry.add(session);

        // send message on connection
        session.getBasicRemote().sendText("Hi!");
    }

    @OnMessage
    public String onMessage(String message, Session session) {
        return "Server received [" + message + "]";
    }

    @OnClose
    public void onClose(Session session) {
        webSocketsSessionRegistry.remove(session);
    }

    @OnError
    public void onError(Throwable exception, Session session) {
    }

    // send a message to every session
    public void send(@Observes Template template) {
        webSocketsSessionRegistry.getAll()
                .forEach(session  ->  session.getAsyncRemote()
                        .sendText(toJson(template)));
    }

    private String toJson(Template template) {
        final JsonObject jsonObject = Json.createObjectBuilder()
                .add("id", template.getId())
                .add("name", template.getName())
                .build();
        return jsonObject.toString();
    }

}
class Template{
    private String id;
    private String name;

    public Template(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
