package com.aavn.agiledeckserver.game.entity;


import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@Entity
@Table(name = "tbl_answer_groups")
@NamedQueries({
    @NamedQuery(name = AnswerGroup.GET_BY_ID, query = "SELECT ag FROM tbl_answer_groups ag WHERE ag.id = :id"),
    @NamedQuery(name = AnswerGroup.GET_BY_NAME, query = "SELECT * FROM tbl_answer_groups ag WHERE ag.name = :name"),
})
public class AnswerGroup {

    private static final String QUALIFIER = "com.aavn.agiledeck.game.entity.AnswerGroup";

    public static final String GET_BY_ID = QUALIFIER + "getById";
    public static final String GET_BY_NAME = QUALIFIER + "getByName";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
 
    @Column(name = "name")
    private String name;

    @OneToOne
    @JoinColumn(name = "game_id")
    private Game game;

    public AnswerGroup(String name, Game game) {
        this.name = name;
        this.game = game;
    }

    public AnswerGroup(String name) {
        this.name = name;
    }

    public boolean isValid() {
        return Objects.nonNull(this.game);
    }

}
