package com.wiltech.websocket;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

import javax.websocket.CloseReason;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.Session;

public class WebsocketClient extends Endpoint {

    private static final BlockingDeque<String> queue = new LinkedBlockingDeque<>();

    public static void awake() throws InterruptedException {
        queue.poll(5, SECONDS);
    }

    @Override
    public void onOpen(Session session, EndpointConfig config) {
        session.getAsyncRemote().sendText("hi");
    }

    @Override
    public void onClose(Session session, CloseReason closeReason) {
//        super.onClose(session, closeReason);
    }



}
