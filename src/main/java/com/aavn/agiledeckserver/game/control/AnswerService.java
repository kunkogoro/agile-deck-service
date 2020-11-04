package com.aavn.agiledeckserver.game.control;

import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.aavn.agiledeckserver.game.entity.Answer;
import com.aavn.agiledeckserver.game.entity.AnswerGroup;

public class AnswerService {
    
    @PersistenceContext
    EntityManager em;

    public void add(Answer answer) {
        validate(answer);
        em.persist(answer);
    }

    public Answer getById(Integer id){
        validateId(id);
        TypedQuery<Answer> query = em.createQuery(Answer.GET_BY_ID, Answer.class);
        query.setParameter("id", id);
        return query.getResultStream().findFirst().orElse(null);
    }

    public List<Answer> getByAnswerGroup(AnswerGroup answerGroup) {
        validateAnswerGroup(answerGroup);
        TypedQuery<Answer> query = em.createQuery(Answer.GET_BY_ANSWER_GROUP, Answer.class);
        query.setParameter("answerGroupId", answerGroup.getId());
        return query.getResultList();
    }

    private void validateAnswerGroup(AnswerGroup answerGroup) {
        if (Objects.isNull(answerGroup)) {
            throw new IllegalArgumentException("Answer Group is missing");
        }

        if (!answerGroup.isValid()) {
            throw new IllegalArgumentException("Answer Group's data is missing or invalid");
        }
    }

    private void validateId(Integer id) {
        if (Objects.isNull(id)) {
            throw new IllegalArgumentException("id is missing");
        }
    }

    private void validate(Answer answer) {
        if (Objects.isNull(answer)) {
            throw new IllegalArgumentException("Answer is missing");
        }

        if (!answer.isValid()) {
            throw new IllegalArgumentException("Answer's data is missing or invalid");
        }
    }
}
