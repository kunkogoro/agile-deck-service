package com.axonactive.agiletools.agiledeck.gameboard.boundary;


import java.io.StringReader;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.context.ApplicationScoped;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.axonactive.agiletools.agiledeck.gameboard.entity.Player;
import com.axonactive.agiletools.agiledeck.gameboard.entity.PlayerSelectedCard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
@ServerEndpoint("/ws/{code}")
public class GameBoardSocket {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameBoardSocket.class);

    private final Map<String, List<Session>> sessions = new ConcurrentHashMap<>();
    private final Map<String, List<PlayerSelectedCard>> players = new ConcurrentHashMap<>();
    private final Map<String, Boolean> flippedAnswers = new ConcurrentHashMap<>();

    private final Jsonb jsonb = JsonbBuilder.create();
    
    private final String action = "action";

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
        String action = jsonObject.getString(this.action);

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
                LOGGER.debug("No action selected!");
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
        data.put(this.action, "reset-answer");
        broadcast(sessions.get(code), jsonb.toJson(data));
    }

    private void flipCard(String code) {
        flippedAnswers.put(code, true);
        Map<String, Object> data = new ConcurrentHashMap<>();
        data.put(this.action, "flip-card");
        broadcast(sessions.get(code), jsonb.toJson(data));
    }

    private void playerSelectedCard(JsonObject jsonObject, String code) {
        Long playerId = Long.parseLong(jsonObject.getString("playerId"));
        Long selectedCardId = (long) jsonObject.getInt("selectedCardId");

        players.get(code).forEach(playerSelectedCard -> {
            if (playerId.equals(playerSelectedCard.getPlayer().getId())) {
                playerSelectedCard.setSelectedCardId(selectedCardId);

                Map<String, Object> data = new ConcurrentHashMap<>();
                data.put(this.action, "selected-card");
                data.put("data", playerSelectedCard);

                broadcast(sessions.get(code), jsonb.toJson(data));
            }
        });
    }

    private void joinGame(JsonObject info, String code) {
        Player player = jsonb.fromJson(info.toString(), Player.class);
        PlayerSelectedCard playerSelectedCard = new PlayerSelectedCard(player, null);

        
        LOGGER.debug("Object to json with Jsonb: " + jsonb.toJson(playerSelectedCard));
        LOGGER.debug("toString of PlayerSocket: " + playerSelectedCard.toString());
        if(Objects.nonNull(playerSelectedCard.getPlayer())) {
            LOGGER.debug("playerSelectedCard not null");
            LOGGER.debug("player not null with ID: " + playerSelectedCard.getPlayer().getId());
            LOGGER.debug("player not null with Name: " + playerSelectedCard.getPlayer().getName());
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
        data.put(this.action, "flip-status");
        data.put("isFlip", flippedAnswers.get(code));

        broadcast(sessions.get(code), jsonb.toJson(data));
    }

    private void sendListPlayer(String code) {
        List<PlayerSelectedCard> playerSelectedCards = filterPlayers(code);

        Map<String, Object> data = new HashMap<>();
        data.put(this.action, "join-game");
        data.put("data", playerSelectedCards);


        broadcast(sessions.get(code), jsonb.toJson(data));
    }

    private void broadcast(List<Session> sessions, String message) {
        sessions.forEach(s -> s.getAsyncRemote().sendObject(message));
    }

}
