package com.axonactive.agiletools.agiledeck.game.control;

import java.util.List;
import java.util.Objects;
import java.util.Random;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.axonactive.agiletools.agiledeck.AgileDeckException;
import com.axonactive.agiletools.agiledeck.game.entity.Question;
import com.axonactive.agiletools.agiledeck.game.entity.QuestionMsgCodes;
import com.axonactive.agiletools.agiledeck.gameboard.entity.AnsweredQuestion;

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

        Question question = new Question();
        Integer checkNoQuestionsLeft = 0;

        do{
            if(checkNoQuestionsLeft == questions.size()){
                throw new AgileDeckException(QuestionMsgCodes.NO_QUESTIONS_LEFT);
            }
            int indexRandom = new Random().nextInt(questions.size());
            question = questions.get(indexRandom);
            checkNoQuestionsLeft += 1;
        }while(isExisted(gameBoardId, question));
        
        return question;
    }

    private boolean isExisted(Long gameBoardId, Question question) {
        TypedQuery<AnsweredQuestion> query = em.createNamedQuery(AnsweredQuestion.GET_BY_GAME_BOARD_ID_AND_QUESTION_CONTENT, AnsweredQuestion.class);
        query.setParameter("gameBoardId", gameBoardId);
        query.setParameter("content", question.getContent().getContent());
        AnsweredQuestion answeredQuestion = query.getResultStream().findFirst().orElse(null);
        return Objects.nonNull(answeredQuestion);
    }
}
