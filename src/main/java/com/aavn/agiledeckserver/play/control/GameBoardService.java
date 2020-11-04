package com.aavn.agiledeckserver.play.control;

import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.aavn.agiledeckserver.play.entity.GameBoard;

public class GameBoardService {

    @PersistenceContext
    EntityManager em;

    public void add(GameBoard gameBoard) {
        validate(gameBoard);
        em.persist(gameBoard);
    }

    public GameBoard getByCode(String code) {
        validateCode(code);
        TypedQuery<GameBoard> query = em.createQuery(GameBoard.GET_BY_CODE, GameBoard.class);
        query.setParameter("code", code);
        return query.getResultStream().findFirst().orElse(null);
    }

    public GameBoard getById(Integer id) {
        validateId(id);
        TypedQuery<GameBoard> query = em.createQuery(GameBoard.GET_BY_ID, GameBoard.class);
        query.setParameter("id", id);
        return query.getResultStream().findFirst().orElse(null);
    }
    
    private void validateId(Integer id) {
        if (Objects.isNull(id)) {
            throw new IllegalArgumentException("ID is missing");
        }
    }

    private void validateCode(String code) {
        if (Objects.isNull(code)) {
            throw new IllegalArgumentException("Code is missing");
        }
    }

    private void validate(GameBoard gameBoard) {
        if (Objects.isNull(gameBoard)) {
            throw new IllegalArgumentException("GameBoard is missing");
        }

        if (!gameBoard.isValid()) {
            throw new IllegalArgumentException("GameBoard's data is missing or invalid");
        }
    }
}
