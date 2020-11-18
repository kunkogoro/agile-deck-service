package com.axonactive.agiletools.agiledeck.gameboard.entity;

import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tbl_players")
@NamedQueries({
    @NamedQuery(name = Player.GET_BY_GAMEBOARD, query = "SELECT pl FROM Player pl WHERE pl.gameBoard.code = :gameBoardCode AND pl.name = :playerName")
})
@Getter @Setter
@NoArgsConstructor
public class Player {

    private static final String QUALIFIER = "com.axonactive.agiletools.agiledeck.play.entity.Player";

    public static final String GET_BY_GAMEBOARD = QUALIFIER + "getByGameBoard";    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "game_board_id", nullable = false)
    private GameBoard gameBoard;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "avatar")
    private String avatar;

	public boolean isValid() {
		return Objects.nonNull(this.name);
	}

    public Player(GameBoard gameBoard, String name) {
        this.gameBoard = gameBoard;
        this.name = name;
    }
}
