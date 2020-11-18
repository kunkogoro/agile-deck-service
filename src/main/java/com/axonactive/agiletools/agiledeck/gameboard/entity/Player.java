package com.axonactive.agiletools.agiledeck.gameboard.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tbl_players")
@Getter @Setter
@NoArgsConstructor
public class Player {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
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
