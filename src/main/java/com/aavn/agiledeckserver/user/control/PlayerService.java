package com.aavn.agiledeckserver.user.control;

import java.util.List;
import java.util.Objects;

import javax.management.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.aavn.agiledeckserver.play.entity.GameBoard;
import com.aavn.agiledeckserver.user.entity.Player;

import io.quarkus.arc.Lock.Type;


public class PlayerService {
    
    @PersistenceContext
    EntityManager em;

    public Player getById(Integer id) {
        validateId(id);
        TypedQuery<Player> query = em.createQuery(Player.GET_BY_ID, Player.class);
        query.setParameter("id", id);
        return query.getResultStream().findFirst().orElse(null);
    }

    private void validateId(Integer id) {
        if (Objects.isNull(id)) {
            throw new IllegalArgumentException("ID is missing");
        }
    }

    public void addtoGameBoard(Player player, GameBoard gameBoard) {
        validateGameBoard(gameBoard);
        validatePlayer(player);
        TypedQuery<Player> query =  em.createQuery(Player.ADD_PLAYER_TO_GAME_BOARD, Player.class);
        query.setParameter("gameBoardId", gameBoard.getId());
        query.setParameter("playerId", player.getId());
    }

    private void validatePlayer(Player player) {
        if (Objects.isNull(player)) {
            throw new IllegalArgumentException("Game Board is missing");
        }

        if (!player.isValid()) {
            throw new IllegalArgumentException("Game Board's data is missing or invalid");
        }
    }

    public List<Player> getByGameBoard(GameBoard gameBoard) {
        validateGameBoard(gameBoard);
        TypedQuery<Player> query = em.createQuery(Player.GET_BY_GAME_BOARD, Player.class);
        query.setParameter("gameBoardId", gameBoard.getId());
        return query.getResultList();
    }

    private void validateGameBoard(GameBoard gameBoard) {
        if (Objects.isNull(gameBoard)) {
            throw new IllegalArgumentException("Game Board is missing");
        }

        if (!gameBoard.isValid()) {
            throw new IllegalArgumentException("Game Board's data is missing or invalid");
        }
    }
}
