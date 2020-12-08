package com.axonactive.agiletools.agiledeck.gameboard.boundary;

import java.net.URI;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
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
        
        String selectCartData = "{\"action\":\"selected-card\",\"playerId\":\"11\",\"selectedCardId\":2}";

        try(Session session = ContainerProvider.getWebSocketContainer().connectToServer(Client.class, uri)){
            Assertions.assertEquals("CONNECT", MESSAGES.poll(10, TimeUnit.SECONDS));

            session.getAsyncRemote().sendText(joinGameData);
            Assertions.assertEquals("{\"isLastOne\":false,\"isFlip\":false,\"action\":\"init-data\"}", MESSAGES.poll(10, TimeUnit.SECONDS));

            session.getAsyncRemote().sendText(selectCartData);
            Assertions.assertEquals("{\"data\":[\"{\\\"player\\\":{\\\"gameBoard\\\":{\\\"code\\\":\\\"b4661d5e-f296-4cf6-887d-cfa0f97d1f36\\\",\\\"game\\\":{\\\"description\\\":\\\"A workshop game to encourage people to think about alternative approaches for tackling projects. - by Scum & Kanban\\\",\\\"id\\\":1,\\\"name\\\":\\\"Iterative - Incremental - Big Bang\\\"},\\\"id\\\":1},\\\"id\\\":11,\\\"name\\\":\\\"Soursop\\\"},\\\"selectedCardId\\\":null}\"],\"action\":\"join-game\"}", MESSAGES.poll(10, TimeUnit.SECONDS));
            
            session.getAsyncRemote().sendText("{\"action\":\"flip-card\"}");
            Assertions.assertEquals("{\"action\":\"flip-card\"}", MESSAGES.poll(10, TimeUnit.SECONDS));
        
            session.getAsyncRemote().sendText("{\"action\":\"reset-answer\"}");
            Assertions.assertEquals("{\"data\":[\"{\\\"player\\\":{\\\"gameBoard\\\":{\\\"code\\\":\\\"b4661d5e-f296-4cf6-887d-cfa0f97d1f36\\\",\\\"game\\\":{\\\"description\\\":\\\"A workshop game to encourage people to think about alternative approaches for tackling projects. - by Scum & Kanban\\\",\\\"id\\\":1,\\\"name\\\":\\\"Iterative - Incremental - Big Bang\\\"},\\\"id\\\":1},\\\"id\\\":11,\\\"name\\\":\\\"Soursop\\\"},\\\"selectedCardId\\\":null}\"],\"action\":\"join-game\"}", MESSAGES.poll(10, TimeUnit.SECONDS));
            
            session.getAsyncRemote().sendText("{\"action\":\"next-question\"}");
            Assertions.assertEquals("{\"isLastOne\":false,\"isFlip\":false,\"action\":\"init-data\"}", MESSAGES.poll(10, TimeUnit.SECONDS));
        
            session.getAsyncRemote().sendText("{\"action\":\"update-player\"}");
            Assertions.assertEquals("{\"action\":\"reset-answer\"}", MESSAGES.poll(10, TimeUnit.SECONDS));
        
            session.getAsyncRemote().sendText("{\"action\":\"update-question\"}");
            Assertions.assertEquals("{\"action\":\"update-question\"}", MESSAGES.poll(10, TimeUnit.SECONDS));
        }
    }

    @ClientEndpoint
    public static class Client{

        @OnOpen
        public void open(Session session){
            MESSAGES.add("CONNECT");
            session.getAsyncRemote().sendText("_ready_");
        }

        @OnMessage
        public void message(String message){
            MESSAGES.add(message);
        }
    }

}