package com.axonactive.agiletools.agiledeck.gameboard.control;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.axonactive.agiletools.agiledeck.gameboard.entity.GameBoard;
import com.axonactive.agiletools.agiledeck.gameboard.entity.AnsweredQuestionDetail;

@Transactional
@RequestScoped
public class AnsweredQuestionDetailService {
    
    @PersistenceContext
    EntityManager em;

    @Inject
    GameBoardService gameBoardService;

    public List<AnsweredQuestionDetail> getAll(GameBoard gameBoard) {
        gameBoardService.validate(gameBoard);
        
        return null;
    }

}
