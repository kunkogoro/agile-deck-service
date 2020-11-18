package com.axonactive.agiletools.agiledeck.gameboard.control;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.axonactive.agiletools.agiledeck.gameboard.entity.AnsweredQuestion;
import com.axonactive.agiletools.agiledeck.gameboard.entity.AnsweredQuestionDetail;
import com.axonactive.agiletools.agiledeck.gameboard.entity.Player;

@RequestScoped
public class AnsweredQuestionDetailService {
    
    @PersistenceContext 
    EntityManager em;

    public AnsweredQuestionDetail create(AnsweredQuestion answeredQuestion, Player player){
        AnsweredQuestionDetail answeredQuestionDetail = new AnsweredQuestionDetail(answeredQuestion, player);
        em.persist(answeredQuestionDetail);
        return answeredQuestionDetail;
    }
    
}
