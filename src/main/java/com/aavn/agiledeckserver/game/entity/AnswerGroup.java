package com.aavn.agiledeckserver.game.entity;


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
@Table(name = "tbl_answer_droups")
public class AnswerGroup {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
 
    @Column(name = "name")
    private String name;

    @OneToOne(mappedBy = "defaultAnswerGroup")
    @JoinColumn(name = "game_id")
    private Game game;

    public AnswerGroup(String name, Game game) {
        this.name = name;
        this.game = game;
    }
}
