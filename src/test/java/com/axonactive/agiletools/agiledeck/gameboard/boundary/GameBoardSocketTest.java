package com.axonactive.agiletools.agiledeck.gameboard.boundary;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import javax.websocket.Session;

import com.axonactive.agiletools.agiledeck.game.entity.Game;
import com.axonactive.agiletools.agiledeck.gameboard.entity.GameBoard;
import com.axonactive.agiletools.agiledeck.gameboard.entity.PlayerSocket;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
// import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
// import org.powermock.modules.junit4.PowerMockRunner;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
//@RunWith(PowerMockRunner.class)
public class GameBoardSocketTest {
    
    @Mock
    GameBoardSocket gameBoardSocket;

    Game game;

    GameBoard gameBoard;

    @BeforeEach
    public void init(){
        game.setName("Agile Deck");
        gameBoard = new GameBoard("b4661d5e-f296-4cf6-887d-cfa0f97d1f36", game);
    }

    @Test
    public void whenOnOpenSocket(){
        Session session = Mockito.mock(Session.class);
        gameBoardSocket = mock(GameBoardSocket.class);
        gameBoardSocket.onOpen(session, this.gameBoard.getCode());

        // when(this.gameBoardSocket).thenReturn(null);

        verify(gameBoardSocket).onOpen(Mockito.any(), Mockito.anyString());
        verify(gameBoardSocket, times(1));

        // when(gameBoardSocket.onOpen(Mockito.any(), Mockito.anyString()));
        // verify(this.gameBoardSocket, times(1)).save(Mockito.any());

        //when(this.gameBoardSocket.onOpen(session, gameBoard.getCode())).thenReturn(null);

        // doReturn(null).when(gameBoardSocket).onOpen(Mockito.any(), Mockito.anyString());
        //gameBoardSocket.onOpen(Mockito.any(), Mockito.anyString());
        // Mockito.verify(this.gameBoardSocket, times(1));
    }

    @Test
    public void whenFilterPlayer(){
        
        List<PlayerSocket> player = new ArrayList<>();
        
    }
}