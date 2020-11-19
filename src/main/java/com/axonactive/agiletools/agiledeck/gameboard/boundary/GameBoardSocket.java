package com.axonactive.agiletools.agiledeck.gameboard.boundary;

import com.axonactive.agiletools.agiledeck.gameboard.entity.PlayerSocket;
import com.github.javafaker.Faker;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.modelmapper.ModelMapper;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
@ServerEndpoint("/ws/{code}")
public class GameBoardSocket {

    Map<String, List<Session>> sessions = new ConcurrentHashMap<>();
    Map<String, List<PlayerSocket>> players = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("code") String code) {
        if(!sessions.containsKey(code)) {
            List<Session> playerSession = new ArrayList<>();
            playerSession.add(session);
            sessions.put(code, playerSession);
        } else {
            List<Session> playerSession = sessions.get(code);
            playerSession.add(session);
        }
    }

    @OnClose
    public void onClose(Session session, @PathParam("code") String code) {
        int i = sessions.get(code).indexOf(session);
        if (i!=-1) {
            sessions.get(code).remove(i);
            players.get(code).remove(i);
            sendListPlayer(code);
        }
    }

    @OnError
    public void onError(Session session, @PathParam("code") String code, Throwable throwable) {

    }

    @OnMessage
    public void onMessage(String message, @PathParam("code") String code) {
        JsonObject jsonObject = JsonParser.parseString(message).getAsJsonObject();

        String action = jsonObject.get("action").getAsString();

        if(action.equals("join-game")) {
            joinGame(jsonObject.get("info").getAsJsonObject(), code);
        } else if (action.equals("selected-card")) {
            playerSelectedCard(jsonObject, code);
        }

    }

    private void playerSelectedCard(JsonObject jsonObject, String code) {
        Long playerId = Long.parseLong(jsonObject.get("playerId").getAsString());
        Integer selectedCardId = Integer.parseInt(jsonObject.get("selectedCardId").getAsString());

        players.get(code).forEach(playerSocket -> {
            if (playerId.equals(playerSocket.getId())) {
                playerSocket.setImage(1);
                playerSocket.setSelectedCard(selectedCardId);

                Map<String, Object> data = new ConcurrentHashMap<>();
                data.put("action", "selected-card");
                data.put("data", playerSocket);

                broadcast(sessions.get(code), new Gson().toJson(data));
            }
        });
    }

    private void joinGame(JsonObject info, String code) {
        PlayerSocket player = new Gson().fromJson(info.toString(), PlayerSocket.class);
        if(!players.containsKey(code)) {
            List<PlayerSocket> list = new ArrayList<>();
            list.add(player);
            players.put(code, list);
        } else {
            List<PlayerSocket> list = players.get(code);
            list.add(player);
        }
        sendListPlayer(code);
    }

    private void sendListPlayer(String code) {
        Map<String, Object> data = new ConcurrentHashMap<>();
        data.put("action", "join-game");
        data.put("data", players.get(code));

        broadcast(sessions.get(code), new Gson().toJson(data));
    }

    private void broadcast(List<Session> sessions, String message) {
        sessions.forEach(s -> s.getAsyncRemote().sendObject(message, result ->  {
            if (result.getException() != null) {
                System.out.println("Unable to send message: " + result.getException());
            }
        }));
    }

    private void broadcast(Session session, String message) {
        session.getAsyncRemote().sendObject(message, sendResult -> {
            if (sendResult.getException() != null) {
                System.out.println("Unable to send message: " + sendResult.getException());
            }
        });
    }

}
