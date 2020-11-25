package com.axonactive.agiletools.agiledeck.gameboard.control;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.axonactive.agiletools.agiledeck.game.entity.Question;
import com.axonactive.agiletools.agiledeck.gameboard.entity.AnsweredQuestion;
import com.axonactive.agiletools.agiledeck.gameboard.entity.GameBoard;

@RequestScoped
@Transactional
public class AnsweredQuestionService {
    
    @PersistenceContext 
    EntityManager em;

    public AnsweredQuestion create(Question question, GameBoard gameBoard){
        AnsweredQuestion answeredQuestion = new AnsweredQuestion(gameBoard, question.getContent());
        answeredQuestion.setPlaying(true);
        em.persist(answeredQuestion);
        return answeredQuestion;
    }

    public AnsweredQuestion findCurrenrPLaying(Long gameBoardId) {
        TypedQuery<AnsweredQuestion> query = em.createNamedQuery(AnsweredQuestion.GET_BY_GAME_BOARD_ID_AND_IS_PLAYING, AnsweredQuestion.class);
        query.setParameter("id", gameBoardId);        
        return query.getResultStream().findFirst().orElse(null);
    }

    public void updateStatusPlaying(AnsweredQuestion answeredQuestion){
        em.merge(answeredQuestion);
    }

}
