package com.aavn.agiletools.agiledeck.play.control;

import java.util.Objects;
import java.util.UUID;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.aavn.agiletools.agiledeck.AgileDeckException;
import com.aavn.agiletools.agiledeck.game.control.GameService;
import com.aavn.agiletools.agiledeck.game.entity.Game;
import com.aavn.agiletools.agiledeck.play.entity.GameBoard;

@RequestScoped
public class GameBoardService {

    @PersistenceContext
    EntityManager em;

    @Inject
    GameService gameService;

    @Transactional
    public GameBoard getByCode(String code) {
        validateCode(code);
        TypedQuery<GameBoard> query = em.createQuery(GameBoard.GET_BY_CODE, GameBoard.class);
        query.setParameter("code", code);
        return query.getResultStream().findFirst().orElse(null);
    }

    private void validateCode(String code) {
        if (Objects.isNull(code)) {
            throw new AgileDeckException();
        }
    }

    @Transactional
    public GameBoard create(Integer gameId) {
        Game game = findGameById(gameId);
        GameBoard gameBoard = init(game);
        em.persist(gameBoard);
        return gameBoard;
    }

    private Game findGameById(Integer gameId) {
        Game game = gameService.findById(gameId);
        gameService.validate(game);  
        return game;
    }

    private GameBoard init( Game game) {
        String generatedCode = generateGameBoardCode();
        return new GameBoard(generatedCode, game);
    }

    private String generateGameBoardCode() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();   
    }

    
}
