package com.aavn.agiledeckserver.play.entity;

import java.util.HashSet;
import java.util.Objects;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@NamedQueries({
    @NamedQuery(name = GameBoard.GET_BY_CODE, query = "SELECT gb FROM tbl_game_boards gb WHERE gb.code = :code"),
    @NamedQuery(name = GameBoard.GET_BY_ID, query = "SELECT gb FROM tbl_game_boards gb WHERE gb.id = :id"),
})
public class GameBoard {

    private static final String QUALIFIER = "com.aavn.agiledeck.play.entity.GameBoard";

    public static final String GET_BY_CODE = QUALIFIER + "getByCode";
    public static final String GET_BY_ID = QUALIFIER + "getById";

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

    public boolean isValid() {
        return Objects.nonNull(this.code)
        && Objects.nonNull(this.game);
    }
    
}
