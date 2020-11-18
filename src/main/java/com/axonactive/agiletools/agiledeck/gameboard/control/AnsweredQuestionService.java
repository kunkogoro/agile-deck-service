package com.axonactive.agiletools.agiledeck.gameboard.control;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.axonactive.agiletools.agiledeck.gameboard.entity.AnsweredQuestion;

@RequestScoped
@Transactional
public class AnsweredQuestionService {
    
    @PersistenceContext 
    EntityManager em;

    public AnsweredQuestion findCurrenrPLaying(Long gameBoardId) {
        TypedQuery<AnsweredQuestion> query = em.createNamedQuery(AnsweredQuestion.GET_BY_GAME_BOARD_ID_AND_IS_PLAYING, AnsweredQuestion.class);
        query.setParameter("id", gameBoardId);        
        return query.getResultStream().findFirst().orElse(null);
    }


}
