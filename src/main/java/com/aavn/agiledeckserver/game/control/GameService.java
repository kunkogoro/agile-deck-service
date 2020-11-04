package com.aavn.agiledeckserver.game.control;

import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.aavn.agiledeckserver.game.entity.Game;

public class GameService {
    
    @PersistenceContext
    EntityManager em;

    @Transactional
    public void add(Game game){
        validate(game);
        em.persist(game);
    }

    private void validate(Game game){
        if(Objects.isNull(game)){
            throw new IllegalArgumentException("Game is missing");
        }
    }
}
