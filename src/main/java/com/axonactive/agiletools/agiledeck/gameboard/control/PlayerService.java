package com.axonactive.agiletools.agiledeck.gameboard.control;

import java.util.Objects;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.axonactive.agiletools.agiledeck.AgileDeckException;
import com.axonactive.agiletools.agiledeck.gameboard.entity.GameBoard;
import com.axonactive.agiletools.agiledeck.gameboard.entity.GameBoardMsgCodes;
import com.axonactive.agiletools.agiledeck.gameboard.entity.Player;
import com.github.javafaker.Faker;

@RequestScoped
public class PlayerService {
    
    @PersistenceContext
    EntityManager em;

    public Player create(String code){
        GameBoard gameBoard = this.findGameBoardByCode(code);
        this.validate(gameBoard);
        Player player = this.init(gameBoard);
        em.persist(player);
        return player;
    }

    private GameBoard findGameBoardByCode(String code) {
        TypedQuery<GameBoard> query = em.createNamedQuery(GameBoard.GET_BY_CODE, GameBoard.class);
        query.setParameter("code", code);
        return query.getResultStream().findFirst().orElse(null);
    }

    private void validate(GameBoard gameBoard) {
        if(Objects.isNull(gameBoard)){
            throw new AgileDeckException(GameBoardMsgCodes.GAME_BOARD_NOT_FOUND);
        }
    }

    private Player init(GameBoard gameBoard) {
        String name = this.randomName();
        return new Player(gameBoard, name);
    }

    private String randomName() {
        Faker faker = new Faker();
        return faker.food().fruit();
    }
}
