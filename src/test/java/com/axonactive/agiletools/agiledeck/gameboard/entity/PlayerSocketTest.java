package com.axonactive.agiletools.agiledeck.gameboard.entity;

import com.axonactive.agiletools.agiledeck.game.entity.Game;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class PlayerSocketTest {

    private PlayerSocket player1;
    private PlayerSocket player2;
    private PlayerSocket player3;

    @BeforeEach
    public void init(){
        player1 = new PlayerSocket();
        player1.setId(1L);

        player2 = new PlayerSocket();
        player2.setId(1L);

        player3 = new PlayerSocket();
        player3.setId(3L);
    }




    

    @Test
    public void testPlayerSocketEqualTrue(){
        Assertions.assertTrue( player1.equals(player1));
    }

    @Test
    public void testPlayerSocketEqualFalse(){
        Game game = new Game();
        Assertions.assertFalse( player1.equals(game));
    }

    @Test
    public void testPlayerSocketEqual(){
       Assertions.assertTrue(player1.equals(player2));
    }

    @Test

    public void testPlayerSocketNotEqual(){
        Assertions.assertFalse(player1.equals(player3));
    }
}