package com.axonactive.agiletools.agiledeck.gameboard.control;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.axonactive.agiletools.agiledeck.gameboard.entity.AnsweredQuestion;
import com.axonactive.agiletools.agiledeck.gameboard.entity.AnsweredQuestionDetail;
import com.axonactive.agiletools.agiledeck.gameboard.entity.GameBoard;
import com.axonactive.agiletools.agiledeck.gameboard.entity.Player;

@RequestScoped
public class AnsweredQuestionDetailService {
    
    @PersistenceContext 
    EntityManager em;

    @Inject
    GameBoardService gameBoardService;

    public AnsweredQuestionDetail create(AnsweredQuestion answeredQuestion, Player player){
        AnsweredQuestionDetail answeredQuestionDetail = new AnsweredQuestionDetail(answeredQuestion, player);
        em.persist(answeredQuestionDetail);
        return answeredQuestionDetail;
    }

    public List<AnsweredQuestionDetail> getAll(GameBoard gameBoard) {
        gameBoardService.validate(gameBoard);
        
        return null;
    }
    
}
