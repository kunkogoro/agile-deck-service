package com.aavn.agiledeckserver.play.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.aavn.agiledeckserver.game.entity.Game;
import com.aavn.agiledeckserver.user.entity.Player;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table (name = "tbl_game_boards")
@Entity
@NoArgsConstructor
@Getter @Setter
public class GameBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "code", nullable = false, unique = true, length = 50)
    private String code;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @ManyToMany
    @JoinTable(
        name = "tbl_game_board_players", 
        joinColumns = { @JoinColumn(name = "game_board_id") }, 
        inverseJoinColumns = { @JoinColumn(name = "player_id") }
    )
    Set<Player> player = new HashSet<>();

    public GameBoard(String code, Game game, Set<Player> player) {
        this.code = code;
        this.game = game;
        this.player = player;
    }
    
}
