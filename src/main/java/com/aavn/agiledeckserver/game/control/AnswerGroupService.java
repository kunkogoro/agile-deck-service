package com.aavn.agiledeckserver.game.control;

import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.aavn.agiledeckserver.game.entity.AnswerGroup;

import java.util.List;

public class AnswerGroupService {
    
    @PersistenceContext
    EntityManager em;

    public void add(AnswerGroup answerGroup) {
        validate(answerGroup);
        em.persist(answerGroup);
    }

    public AnswerGroup getById(Integer id) {
        validateId(id);
        TypedQuery<AnswerGroup> query = em.createQuery(AnswerGroup.GET_BY_ID, AnswerGroup.class);
        query.setParameter("id", id);
        return query.getResultStream().findFirst().orElse(null);
    }

    public List<AnswerGroup> getByName(String name) {
        validateName(name);
        TypedQuery<AnswerGroup> query = em.createQuery(AnswerGroup.GET_BY_NAME, AnswerGroup.class);
        query.setParameter("name", name);
        return query.getResultList();
    }

    private void validateName(String name) {
        if (Objects.isNull(name)) {
            throw new IllegalArgumentException("Name is missing");
        }
    }

    private void validateId(Integer id) {
        if (Objects.isNull(id)) {
            throw new IllegalArgumentException("ID is missing");
        }
    }

    private void validate(AnswerGroup answerGroup) {
        if (Objects.isNull(answerGroup)) {
            throw new IllegalArgumentException("Answer Group is missing");
        }

        if (!answerGroup.isValid()) {
            throw new IllegalArgumentException("Answer Group's data is missing or invalid");
        }
    }

}
