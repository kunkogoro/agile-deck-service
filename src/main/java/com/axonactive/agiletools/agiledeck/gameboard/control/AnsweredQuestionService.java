package com.axonactive.agiletools.agiledeck.gameboard.control;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.axonactive.agiletools.agiledeck.game.entity.Question;
import com.axonactive.agiletools.agiledeck.gameboard.entity.AnsweredQuestion;
import com.axonactive.agiletools.agiledeck.gameboard.entity.AnsweredQuestionDetail;
import com.axonactive.agiletools.agiledeck.gameboard.entity.GameBoard;

@RequestScoped
@Transactional
public class AnsweredQuestionService {
    
    @PersistenceContext 
    EntityManager em;

    @Inject
    AnsweredQuestionDetailService answeredQuestionDetailService;

    public AnsweredQuestion create(Question question, GameBoard gameBoard){
        AnsweredQuestion answeredQuestion = new AnsweredQuestion(gameBoard, question.getContent());
        answeredQuestion.setPlaying(true);
        em.persist(answeredQuestion);
        return answeredQuestion;
    }

    public AnsweredQuestion findCurrentPLaying(Long gameBoardId) {
        TypedQuery<AnsweredQuestion> query = em.createNamedQuery(AnsweredQuestion.GET_BY_GAME_BOARD_ID_AND_IS_PLAYING, AnsweredQuestion.class);
        query.setParameter("id", gameBoardId);        
        return query.getResultStream().findFirst().orElse(null);
    }

    public void updateStatusPlaying(AnsweredQuestion answeredQuestion){
        em.merge(answeredQuestion);
    }

    public List<AnsweredQuestion> findByGameBoardId(Long id) {
        TypedQuery<AnsweredQuestion> query = em.createNamedQuery(AnsweredQuestion.GET_BY_GAME_BOARD_ID, AnsweredQuestion.class);
        query.setParameter("id", id);
        return query.getResultList();
    }

    public AnsweredQuestion findById(Long id){
        return em.find(AnsweredQuestion.class, id);
    }
    
    public AnsweredQuestion update(GameBoard gameBoard, Question updateAnsweredQuestion){
        AnsweredQuestion answeredQuestion = findCurrentPLaying(gameBoard.getId());
        answeredQuestion.setPlaying(false);
        this.updateStatusPlaying(answeredQuestion);
        List<AnsweredQuestionDetail> previousAnsweredQuestionDetails = answeredQuestionDetailService.getAllByAnsweredQuestionId(answeredQuestion.getId());
        previousAnsweredQuestionDetails.forEach(previousAnsweredQuestionDetail -> {
            previousAnsweredQuestionDetail.setAnswer(null);
            em.merge(previousAnsweredQuestionDetail);
        });
        return this.create(updateAnsweredQuestion, gameBoard);
    }

    
    
}
