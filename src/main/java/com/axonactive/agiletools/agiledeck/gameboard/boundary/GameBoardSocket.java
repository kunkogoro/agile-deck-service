package com.axonactive.agiletools.agiledeck.gameboard.boundary;


import java.io.StringReader;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.context.ApplicationScoped;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.bind.JsonbBuilder;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.axonactive.agiletools.agiledeck.gameboard.entity.Player;
import com.axonactive.agiletools.agiledeck.gameboard.entity.PlayerSelectedCard;

@ApplicationScoped
@ServerEndpoint("/ws/{code}")
public class GameBoardSocket {

    private static final String ACTION = "action";
    
    private final Map<String, List<Session>> sessions = new ConcurrentHashMap<>();
    private final Map<String, List<PlayerSelectedCard>> players = new ConcurrentHashMap<>();
    private final Map<String, Boolean> flippedAnswers = new ConcurrentHashMap<>();

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
        JsonObject jsonObject = Json.createReader(new StringReader(message)).readObject();
        String action = jsonObject.getString(ACTION);

        switch (action) {
            case "join-game":
                joinGame(jsonObject.getJsonObject("info"), code);
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
            default:
                System.out.println("No action selected!");
        }
    }

    private List<PlayerSelectedCard> filterPlayers(String code) {
        List<PlayerSelectedCard> playerSelectedCards = new ArrayList<>();
        players.get(code).forEach(playerSelectedCard -> {
            if(!playerSelectedCards.contains(playerSelectedCard)) {
                playerSelectedCards.add(playerSelectedCard);
            }
        });

        return playerSelectedCards;
    }

    private void resetAnswer(String code) {
        players.get(code).forEach(playerSelectedCard -> playerSelectedCard.setSelectedCardId(null));
        sendListPlayer(code);

        flippedAnswers.put(code, false);
        sendFlipStatus(code);

        Map<String, Object> data = new ConcurrentHashMap<>();
        data.put(ACTION, "reset-answer");
        broadcast(sessions.get(code), toJson(data));
    }

    private void flipCard(String code) {
        flippedAnswers.put(code, true);
        Map<String, Object> data = new ConcurrentHashMap<>();
        data.put(ACTION, "flip-card");
        broadcast(sessions.get(code), toJson(data));
    }

    private void playerSelectedCard(JsonObject jsonObject, String code) {
        Long playerId = Long.parseLong(jsonObject.getString("playerId"));
        Long selectedCardId = (long) jsonObject.getInt("selectedCardId");

        players.get(code).forEach(playerSelectedCard -> {
            if (playerId.equals(playerSelectedCard.getPlayer().getId())) {
                playerSelectedCard.setSelectedCardId(selectedCardId);

                Map<String, Object> data = new ConcurrentHashMap<>();
                data.put(ACTION, "selected-card");
                data.put("data", playerSelectedCard);

                broadcast(sessions.get(code), toJson(data));
            }
        });
    }

    private void joinGame(JsonObject info, String code) {
        Player player = fromJson(info.toString());
        PlayerSelectedCard playerSelectedCard = new PlayerSelectedCard(player, null);


        System.out.println("1 = " + playerSelectedCard.toString());
        System.out.println("2 (json) = " + JsonbBuilder.create());
        System.out.println("3 (json) = " + JsonbBuilder.create().toJson(playerSelectedCard.getPlayer()));
        System.out.println("4 (json) = " + JsonbBuilder.create().toJson(playerSelectedCard));
        if(Objects.nonNull(playerSelectedCard.getPlayer())) {
            System.out.println("playerSelectedCard not null");
            System.out.println("player not null with   ID: " + playerSelectedCard.getPlayer().getId());
            System.out.println("player not null with Name: " + playerSelectedCard.getPlayer().getName());
        }

        if(!players.containsKey(code)) {
            List<PlayerSelectedCard> list = new ArrayList<>();
            list.add(playerSelectedCard);
            players.put(code, list);
        } else {
            List<PlayerSelectedCard> list = players.get(code);
            list.add(playerSelectedCard);
        }

        sendListPlayer(code);
    }

    private void sendFlipStatus(String code) {
        Map<String, Object> data = new ConcurrentHashMap<>();
        data.put(ACTION, "flip-status");
        data.put("isFlip", flippedAnswers.get(code));

        broadcast(sessions.get(code), toJson(data));
    }

    private void sendListPlayer(String code) {
        List<PlayerSelectedCard> playerSelectedCards = filterPlayers(code);

        Map<String, Object> data = new HashMap<>();
        data.put(ACTION, "join-game");
        data.put("data", playerSelectedCards);


        broadcast(sessions.get(code), toJson(data));
    }

    private void broadcast(List<Session> sessions, String message) {
        sessions.forEach(s -> s.getAsyncRemote().sendObject(message));
    }

    private String toJson(Map<String, Object> data) {
        return JsonbBuilder.create().toJson(data);
    }

    private Player fromJson(String stringifiedJson) {
        return JsonbBuilder.create().fromJson(stringifiedJson, Player.class);
    }
}
