package com.axonactive.agiletools.agiledeck.gameboard.boundary;

import java.net.URI;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class GameBoardSocketTest {

    private static final LinkedBlockingDeque<String> MESSAGES = new LinkedBlockingDeque<>();

    @TestHTTPResource("/ws/b4661d5e-f296-4cf6-887d-cfa0f97d1f36")
    URI uri;

    @Test
    public void testGameBoardSocket() throws Exception {

        String joinGameData = "{\"action\":\"join-game\",\"info\":{\"gameBoard\":{\"code\":\"b4661d5e-f296-4cf6-887d-cfa0f97d1f36\",\"game\":{\"description\":\"A workshop game to encourage people to think about alternative approaches for tackling projects. - by Scum & Kanban\",\"id\":1,\"name\":\"Iterative - Incremental - Big Bang\"},\"id\":1},\"id\":11,\"name\":\"Soursop\"}}";
        
        String selectCartData = "";
        
        try(Session session = ContainerProvider.getWebSocketContainer().connectToServer(Client.class, uri)){
            Assertions.assertEquals("CONNECT", MESSAGES.poll(10, TimeUnit.SECONDS));
            session.getAsyncRemote().sendText(joinGameData);
            Assertions.assertEquals("{\"isFlip\":false,\"action\":\"flip-status\"}", MESSAGES.poll(10, TimeUnit.SECONDS));
            session.getAsyncRemote().sendText("selected-card");
            Assertions.assertEquals(null, MESSAGES.poll(10, TimeUnit.SECONDS));
        }
    }

    @ClientEndpoint
    public static class Client{

        @OnOpen
        public void open(Session session){
            MESSAGES.add("CONNECT");
            session.getAsyncRemote().sendText("_ready_");
        }

        @OnClose
        public void close(Session session){
            MESSAGES.add("");
        }

        @OnMessage
        public void message(String message){
            MESSAGES.add(message);
        }
    }

}