package com.axonactive.agiletools.agiledeck.gameboard.boundary;

import com.github.javafaker.Faker;
import com.google.gson.Gson;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
@ServerEndpoint("/ws/{code}")
public class GameBoardSocket {

    Map<String, Session> sessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("code") String code) {
        Faker faker = new Faker();
        String name = faker.food().fruit();
        sessions.put(name, session);

        Map<String, Object> data = new ConcurrentHashMap<>();
        data.put("key","players-list");
        data.put("data", sessions.keySet());
        broadcast(new Gson().toJson(data));
    }

    @OnClose
    public void onClose(Session session, @PathParam("code") String code) {
        sessions.forEach((key, val) -> {
            if (session.equals(val)) {
                sessions.remove(key);
            }
        });

        Map<String, Object> data = new ConcurrentHashMap<>();
        data.put("key","players-list");
        data.put("data", sessions.keySet());
        broadcast(new Gson().toJson(data));
    }

    @OnError
    public void onError(Session session, @PathParam("code") String code, Throwable throwable) {
        sessions.remove(code);
        broadcast("User " + code + " left on error: " + throwable);
    }

    @OnMessage
    public void onMessage(String message, @PathParam("code") String code) {
       Map<String, Object> data = new Gson().fromJson(message, Map.class);
       String key = data.get("key").toString();
       System.out.println(key);
    }

    private void broadcast(String message) {
        sessions.values().forEach(s -> s.getAsyncRemote().sendObject(message, result ->  {
            if (result.getException() != null) {
                System.out.println("Unable to send message: " + result.getException());
            }
        }));
    }


}
