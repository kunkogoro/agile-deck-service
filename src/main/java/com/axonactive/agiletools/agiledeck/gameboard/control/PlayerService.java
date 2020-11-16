package com.axonactive.agiletools.agiledeck.gameboard.control;

import java.util.Objects;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.axonactive.agiletools.agiledeck.gameboard.entity.GameBoard;
import com.axonactive.agiletools.agiledeck.gameboard.entity.Player;
import com.github.javafaker.Faker;

@Transactional
@RequestScoped
public class PlayerService {

    @PersistenceContext
    EntityManager em;

    @Inject
    GameBoardService gameBoardService;

    public Player create(GameBoard gameBoard) {
        System.out.println(em);
        Player player = init(gameBoard);
        em.persist(player);
        return player;
    }

    private Player init(GameBoard gameBoard) {
        Faker faker = new Faker();
        String name = "";
        do {
            name =  faker.food().fruit();
        } while(isExisted(gameBoard.getCode(), name));
        return new Player(gameBoard, name);
    }

    private boolean isExisted(String code, String name) {
        System.out.println(em);
        TypedQuery<Player> query = em.createNamedQuery(Player.GET_BY_GAMEBOARD, Player.class);
        query.setParameter("gameBoardCode", code);
        query.setParameter("playerName", name);
        Player player = query.getResultStream().findFirst().orElse(null);
        return Objects.nonNull(player);
    }
}
