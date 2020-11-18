package com.axonactive.agiletools.agiledeck.gameboard.control;

import java.util.List;
import java.util.Objects;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.axonactive.agiletools.agiledeck.AgileDeckException;
import com.axonactive.agiletools.agiledeck.game.entity.AnswerContent;
import com.axonactive.agiletools.agiledeck.gameboard.entity.AnsweredQuestion;
import com.axonactive.agiletools.agiledeck.gameboard.entity.AnsweredQuestionDetail;
import com.axonactive.agiletools.agiledeck.gameboard.entity.AnsweredQuestionDetailMsgCodes;
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

    public AnsweredQuestionDetail update(Long answerQuestionDetailId, AnswerContent answerContent){
        AnsweredQuestionDetail answeredQuestionDetail = this.getById(answerQuestionDetailId);
        this.validate(answeredQuestionDetail);
        answeredQuestionDetail.setAnswer(answerContent);
        return em.merge(answeredQuestionDetail);
    }

    private AnsweredQuestionDetail getById(Long answerQuestionDetailId) {
        TypedQuery<AnsweredQuestionDetail> query = em.createNamedQuery(AnsweredQuestionDetail.GET_BY_ID, AnsweredQuestionDetail.class);
        query.setParameter("id", answerQuestionDetailId);
        return query.getResultStream().findFirst().orElse(null);
    }

    private void validate(AnsweredQuestionDetail answeredQuestionDetail) {
        if(Objects.isNull(answeredQuestionDetail)){
            throw new AgileDeckException(AnsweredQuestionDetailMsgCodes.ANSWER_QUESTION_DETAIL_NOT_FOUND);
        }
    }

    public List<AnsweredQuestionDetail> getAll(GameBoard gameBoard) {
        gameBoardService.validate(gameBoard);
        return null;
    }
    
}
