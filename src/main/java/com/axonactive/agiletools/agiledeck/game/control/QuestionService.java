package com.axonactive.agiletools.agiledeck.game.control;

import com.axonactive.agiletools.agiledeck.AgileDeckException;
import com.axonactive.agiletools.agiledeck.game.entity.Question;
import com.axonactive.agiletools.agiledeck.game.entity.QuestionMsgCodes;
import com.axonactive.agiletools.agiledeck.gameboard.entity.AnsweredQuestion;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.security.SecureRandom;
import java.util.List;
import java.util.Objects;

@RequestScoped
public class QuestionService {
    
    @PersistenceContext
    EntityManager em;

    public List<Question> getAllByGameID(Long gameID){
        TypedQuery<Question> query = em.createNamedQuery(Question.GET_ALL_BY_GAME_ID, Question.class);
        query.setParameter("gameId", gameID);
        return query.getResultList();
    }

    public Question random(List<Question> questions, Long gameBoardId){
        Question question;
        do{
            if(questions.isEmpty()){
                throw new AgileDeckException(QuestionMsgCodes.NO_QUESTIONS_LEFT);
            }

            SecureRandom random = new SecureRandom();
            int indexRandom = random.nextInt(questions.size());
            question = questions.get(indexRandom);

            if(isExisted(gameBoardId, question)) {
                questions.remove(indexRandom);
            } else {
                return question;
            }
        } while(true);
    }

    private boolean isExisted(Long gameBoardId, Question question) {
        TypedQuery<AnsweredQuestion> query = em.createNamedQuery(AnsweredQuestion.GET_BY_GAME_BOARD_ID_AND_QUESTION_CONTENT, AnsweredQuestion.class);
        query.setParameter("gameBoardId", gameBoardId);
        query.setParameter("content", question.getContent().getContent());
        AnsweredQuestion answeredQuestion = query.getResultStream().findFirst().orElse(null);
        return Objects.nonNull(answeredQuestion);
    }
}
