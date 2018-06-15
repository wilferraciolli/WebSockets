package com.wiltech.JavaWebSockets.signaler;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetSocketAddress;
import java.util.*;

public class WebSocketServer extends org.java_websocket.server.WebSocketServer {

    private static Map<Integer,Set<WebSocket>> Rooms = new HashMap<>();
    private int myroom;

    public WebSocketServer() {
        super(new InetSocketAddress(30001));
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake clientHandshake) {
        System.out.println("New client connected: " + conn.getRemoteSocketAddress() + " hash " + conn.getRemoteSocketAddress().hashCode());

    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b) {
        System.out.println("Client disconnected: " + s);
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        Set<WebSocket> s;
        try {
            JSONObject obj = new JSONObject(message);
            String msgtype = obj.getString("type");
            switch (msgtype) {
                case "GETROOM":
                    myroom = generateRoomNumber();
                    s = new HashSet<>();
                    s.add(conn);
                    Rooms.put(myroom, s);
                    System.out.println("Generated new room: " + myroom);
                    conn.send("{\"type\":\"GETROOM\",\"value\":" + myroom + "}");
                    break;
                case "ENTERROOM":
                    myroom = obj.getInt("value");
                    System.out.println("New client entered room " + myroom);
                    s = Rooms.get(myroom);
                    s.add(conn);
                    Rooms.put(myroom, s);
                    break;
                default:
                    sendToAll(conn, message);
                    break;
            }
        } catch (JSONException e) {
            sendToAll(conn, message);
        }
        System.out.println();
    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {
        System.out.println("Error happened: " + e);
    }

    @Override
    public void onStart() {

    }

    private int generateRoomNumber() {
        return new Random(System.currentTimeMillis()).nextInt();
    }

    private void sendToAll(WebSocket conn, String message) {
        Iterator it = Rooms.get(myroom).iterator();
        while (it.hasNext()) {
            WebSocket c = (WebSocket)it.next();
            if (c != conn) c.send(message);
        }
    }

    public static void main(String[] args) {
        WebSocketServer server = new WebSocketServer();
        server.start();
    }

}
