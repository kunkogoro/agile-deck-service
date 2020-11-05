package com.aavn.agiletools.agiledeck.play.entity;

import java.util.Objects;

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

import com.aavn.agiletools.agiledeck.game.entity.Game;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
@Table (name = "tbl_game_boards")
@Entity
@NamedQueries({
    @NamedQuery(name = GameBoard.GET_BY_CODE, query = "SELECT gb FROM GameBoard gb WHERE gb.code = :code")
})
public class GameBoard {

    private static final String QUALIFIER = "com.aavn.agiletools.agiledeck.play.entity.GameBoard";

    public static final String GET_BY_CODE = QUALIFIER + "getByCode";    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", nullable = false, unique = true, length = 50)
    private String code;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;


    public boolean isValid() {
        return Objects.nonNull(this.code)
        && Objects.nonNull(this.game);
    }

    public GameBoard(String code, Game game) {
        this.code = code;
        this.game = game;
    }

}
