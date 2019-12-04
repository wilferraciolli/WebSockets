/*
 * (c) Midland Software Limited 2019
 * Name     : MessageDecoder.java
 * Author   : ferraciolliw
 * Date     : 04 Dec 2019
 */
package com.wiltech.websocket.decoders;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import com.wiltech.websocket.messages.BetMessage;

public class MessageDecoder implements Decoder.Text<BetMessage> {
    /* Stores the name-value pairs from a JSON message as a Map */
    private Map<String, String> messageMap;

    @Override
    public void init(EndpointConfig ec) {
    }

    @Override
    public void destroy() {
    }

    /* Create a new Message object if the message can be decoded */
    @Override
    public BetMessage decode(String string) throws DecodeException {
        BetMessage msg = null;
        if (willDecode(string)) {
            switch (messageMap.get("type")) {
            case "betMatchWinner":
                msg = new BetMessage(messageMap.get("name"));
                break;
            }
        } else {
            throw new DecodeException(string, "[Message] Can't decode.");
        }
        return msg;
    }

    /* Decode a JSON message into a Map and check if it contains
     * all the required fields according to its type. */
    @Override
    public boolean willDecode(String string) {
        boolean decodes = false;
        /* Convert the message into a map */
        messageMap = new HashMap<>();
        JsonParser parser = Json.createParser(new StringReader(string));
        while (parser.hasNext()) {
            if (parser.next() == JsonParser.Event.KEY_NAME) {
                String key = parser.getString();
                parser.next();
                String value = parser.getString();
                messageMap.put(key, value);
            }
        }
        /* Check the kind of message and if all fields are included */
        Set keys = messageMap.keySet();
        if (keys.contains("type")) {
            switch (messageMap.get("type")) {
            case "betMatchWinner":
                if (keys.contains("name")) {
                    decodes = true;
                }
                break;
            }
        }
        return decodes;
    }
}
