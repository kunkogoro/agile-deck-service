package com.aavn.agiledeckserver.user.entity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.aavn.agiledeckserver.play.entity.GameBoard;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tbl_players")
@Getter @Setter
@NoArgsConstructor
@NamedQueries({
    @NamedQuery(name = Player.GET_BY_ID, query = "SELECT p FROM tbl_players p WHERE p.id = :id"),
    @NamedQuery(name = Player.GET_BY_GAME_BOARD, query = "SELECT p FROM tbl_game_board_players p WHERE p.game_board_id = :gameBoardId"),
    @NamedQuery(name = Player.ADD_PLAYER_TO_GAME_BOARD, query = "INSERT INTO tbl_game_board_players (game_board_id, player_id) VALUES (:gameBoardId, :playerId)"),
})
public class Player {

    private static final String QUALIFIER = "com.aavn.agiledeck.user.entity.Player";

    public static final String GET_BY_ID = QUALIFIER + "getById";
    public static final String GET_BY_GAME_BOARD = QUALIFIER + "getByGameBoard";
    public static final String ADD_PLAYER_TO_GAME_BOARD = QUALIFIER + "addPlayerToGameBoard";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "image")
    private String image;

    @ManyToMany(mappedBy = "player")
    private Set<GameBoard> gameBoard = new HashSet<>();

    public Player(String name, String image, Set<GameBoard> gameBoard) {
        this.name = name;
        this.image = image;
        this.gameBoard = gameBoard;
    }

	public boolean isValid() {
		return Objects.nonNull(this.name);
	}
}
