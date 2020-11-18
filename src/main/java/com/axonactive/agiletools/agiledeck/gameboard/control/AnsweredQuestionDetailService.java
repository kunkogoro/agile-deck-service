package com.axonactive.agiletools.agiledeck.gameboard.control;

import java.util.List;
import java.util.Objects;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.axonactive.agiletools.agiledeck.AgileDeckException;
import com.axonactive.agiletools.agiledeck.gameboard.entity.AnsweredQuestion;
import com.axonactive.agiletools.agiledeck.gameboard.entity.AnsweredQuestionDetail;
import com.axonactive.agiletools.agiledeck.gameboard.entity.Player;

@RequestScoped
@Transactional
public class AnsweredQuestionDetailService {
    
    @PersistenceContext 
    EntityManager em;

    @Inject
    GameBoardService gameBoardService;

    @Inject
    AnsweredQuestionService answeredQuestionService;

    public AnsweredQuestionDetail create(AnsweredQuestion answeredQuestion, Player player){
        AnsweredQuestionDetail answeredQuestionDetail = new AnsweredQuestionDetail(answeredQuestion, player);
        em.persist(answeredQuestionDetail);
        return answeredQuestionDetail;
    }

    public List<AnsweredQuestionDetail> getAll(Long id) {
        TypedQuery<AnsweredQuestionDetail> query = em.createNamedQuery(AnsweredQuestionDetail.GET_ALL_OF_PLAYING_ANSWERED_QUESTION, AnsweredQuestionDetail.class);
        query.setParameter("id", id);
        return query.getResultList();
    }
}
