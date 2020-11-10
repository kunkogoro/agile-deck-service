package com.axonactive.agiletools.agiledeck.play.control;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.axonactive.agiletools.agiledeck.AgileDeckException;
import com.axonactive.agiletools.agiledeck.game.control.AnswerService;
import com.axonactive.agiletools.agiledeck.game.control.GameService;
import com.axonactive.agiletools.agiledeck.game.entity.Answer;
import com.axonactive.agiletools.agiledeck.game.entity.Game;
import com.axonactive.agiletools.agiledeck.play.entity.AnsweredQuestion;
import com.axonactive.agiletools.agiledeck.play.entity.GameBoard;
import com.axonactive.agiletools.agiledeck.play.entity.GameBoardMsgCodes;

@RequestScoped
public class GameBoardService {

    @PersistenceContext
    EntityManager em;

    @Inject
    GameService gameService;

    @Inject
    AnswerService answerService;

    @Transactional
    public AnsweredQuestion join(String code) {
        GameBoard gameBoard = this.getByCode(code);
        this.validate(gameBoard);
        List<Answer> defaultAnswerOptions = this.answerService.getByGame(gameBoard.getGame().getId());
        return AnsweredQuestion.createWithoutQuestion(gameBoard, defaultAnswerOptions);
    }

    private GameBoard getByCode(String code) {
        TypedQuery<GameBoard> query = em.createNamedQuery(GameBoard.GET_BY_CODE, GameBoard.class);
        query.setParameter("code", code);
        return query.getResultStream().findFirst().orElse(null);
    }

    private void validate(GameBoard gameBoard){
        if(Objects.isNull(gameBoard)) {
            throw new AgileDeckException(GameBoardMsgCodes.GAME_BOARD_NOT_FOUND);
        }
    }

    @Transactional
    public GameBoard create(Long gameId) {
        Game game = findGameById(gameId);
        GameBoard gameBoard = init(game);
        em.persist(gameBoard);
        return gameBoard;
    }

    private Game findGameById(Long gameId) {
        Game game = gameService.findById(gameId);
        gameService.validate(game);  
        return game;
    }

    private GameBoard init(Game game) {
        String generatedCode = generateGameBoardCode();
        return new GameBoard(generatedCode, game);
    }

    private String generateGameBoardCode() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();   
    }    
}