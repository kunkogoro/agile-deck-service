package com.axonactive.agiletools.agiledeck.gameboard.boundary;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.axonactive.agiletools.agiledeck.gameboard.entity.PlayerSocket;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@ApplicationScoped
@ServerEndpoint("/ws/{code}")
public class GameBoardSocket {

    Map<String, List<Session>> sessions = new ConcurrentHashMap<>();
    Map<String, List<PlayerSocket>> players = new ConcurrentHashMap<>();
    Map<String, Boolean> flippedAnswers = new ConcurrentHashMap<>();

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
        } else if (action.equals("flip-card")) {
            flipCard(code);
        } else if (action.equals("reset-answer")) {
            resetAnswer(code);
        }

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
        broadcast(sessions.get(code), new Gson().toJson(data));
    }

    private void flipCard(String code) {
        flippedAnswers.put(code, true);
        Map<String, Object> data = new ConcurrentHashMap<>();
        data.put("action", "flip-card");
        broadcast(sessions.get(code), new Gson().toJson(data));
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

    private void sendFlipStatus(String code) {
        Map<String, Object> data = new ConcurrentHashMap<>();
        data.put("action", "flip-status");
        data.put("isFlip", flippedAnswers.get(code));

        broadcast(sessions.get(code), new Gson().toJson(data));
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

}
