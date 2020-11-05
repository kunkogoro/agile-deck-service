package com.aavn.agiletools.agiledeck.game.entity;


import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@Entity
@Table(name = "tbl_answer_groups")
public class AnswerGroup {
    
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
