package com.axonactive.agiletools.agiledeck.gameboard.boundary;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.axonactive.agiletools.agiledeck.gameboard.entity.PlayerSocket;
import com.axonactive.agiletools.agiledeck.gameboard.entity.PlayerSocketInstanceCreator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@ApplicationScoped
@ServerEndpoint("/ws/{code}")
public class GameBoardSocket {

    private final Map<String, List<Session>> sessions = new ConcurrentHashMap<>();
    private final Map<String, List<PlayerSocket>> players = new ConcurrentHashMap<>();
    private final Map<String, Boolean> flippedAnswers = new ConcurrentHashMap<>();

    private final Gson gson = new GsonBuilder().registerTypeAdapter(
            PlayerSocket.class,
            new PlayerSocketInstanceCreator()
    ).create();

    @OnOpen
    public void onOpen(Session session, @PathParam("code") String code) {
        if(!sessions.containsKey(code)) {
            List<Session> playerSession = new ArrayList<>();
            playerSession.add(session);
            sessions.put(code, playerSession);

            flippedAnswers.put(code, false);
        } else {
            List<Session> playerSession = sessions.get(code);
            playerSession.add(session);
        }

        sendFlipStatus(code);
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

    @OnMessage
    public void onMessage(String message, @PathParam("code") String code) {
        JsonObject jsonObject = JsonParser.parseString(message).getAsJsonObject();

        String action = jsonObject.get("action").getAsString();

        switch (action) {
            case "join-game":
                joinGame(jsonObject.get("info").getAsJsonObject(), code);
                break;
            case "selected-card":
                playerSelectedCard(jsonObject, code);
                break;
            case "flip-card":
                flipCard(code);
                break;
            case "reset-answer":
                resetAnswer(code);
                break;
        }

    }

    private List<PlayerSocket> filterPlayers(String code) {
        List<PlayerSocket> playerSockets = new ArrayList<>();
        players.get(code).forEach(playerSocket -> {
            if(!playerSockets.contains(playerSocket)) {
                playerSockets.add(playerSocket);
            }
        });

        return playerSockets;
    }

    private void resetAnswer(String code) {
        players.get(code).forEach(playerSocket -> {
            playerSocket.setSelected(false);
            playerSocket.setSelectedCardId(-1);
        });
        sendListPlayer(code);

        flippedAnswers.put(code, false);
        sendFlipStatus(code);

        Map<String, Object> data = new ConcurrentHashMap<>();
        data.put("action", "reset-answer");
        broadcast(sessions.get(code), gson.toJson(data));
    }

    private void flipCard(String code) {
        flippedAnswers.put(code, true);
        Map<String, Object> data = new ConcurrentHashMap<>();
        data.put("action", "flip-card");
        broadcast(sessions.get(code), gson.toJson(data));
    }

    private void playerSelectedCard(JsonObject jsonObject, String code) {
        Long playerId = Long.parseLong(jsonObject.get("playerId").getAsString());
        Integer selectedCardId = Integer.parseInt(jsonObject.get("selectedCardId").getAsString());

        players.get(code).forEach(playerSocket -> {
            if (playerId.equals(playerSocket.getId())) {
                playerSocket.setSelected(true);
                playerSocket.setSelectedCardId(selectedCardId);

                Map<String, Object> data = new ConcurrentHashMap<>();
                data.put("action", "selected-card");
                data.put("data", playerSocket);

                broadcast(sessions.get(code), gson.toJson(data));
            }
        });



    }

    private void joinGame(JsonObject info, String code) {
        System.out.println(info.toString());
        PlayerSocket player = gson.fromJson(info.toString(), PlayerSocket.class);
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

    private void sendFlipStatus(String code) {
        Map<String, Object> data = new ConcurrentHashMap<>();
        data.put("action", "flip-status");
        data.put("isFlip", flippedAnswers.get(code));

        broadcast(sessions.get(code), gson.toJson(data));
    }

    private void sendListPlayer(String code) {
        Map<String, Object> data = new ConcurrentHashMap<>();
        data.put("action", "join-game");
        data.put("data", filterPlayers(code));

        broadcast(sessions.get(code), gson.toJson(data));
    }

    private void broadcast(List<Session> sessions, String message) {
        sessions.forEach(s -> s.getAsyncRemote().sendObject(message));
    }

}
