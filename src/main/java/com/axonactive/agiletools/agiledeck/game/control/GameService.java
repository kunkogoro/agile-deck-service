package com.axonactive.agiletools.agiledeck.game.control;

import java.util.Objects;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.axonactive.agiletools.agiledeck.AgileDeckException;
import com.axonactive.agiletools.agiledeck.game.entity.Game;
import com.axonactive.agiletools.agiledeck.game.entity.GameMsgCodes;

@RequestScoped
public class GameService {

    @PersistenceContext
    EntityManager em;

    public Game findById(Long id){
        return em.find(Game.class, id);
    }

    public void validate(Game game){
        if(Objects.isNull(game)){
            throw new AgileDeckException(GameMsgCodes.GAME_NOT_FOUND); 
        }
    }
    
}