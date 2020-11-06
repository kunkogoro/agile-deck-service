package com.aavn.agiletools.agiledeck.game.control;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.aavn.agiletools.agiledeck.game.entity.Answer;

@RequestScoped
public class AnswerService {
    
    @PersistenceContext
    EntityManager em;

    public List<Answer> getByGame(Long gameId) {
        TypedQuery<Answer> query = em.createNamedQuery(Answer.FIND_BY_GAME, Answer.class);
        query.setParameter("gameId", gameId);
        return query.getResultList();
    }

}
