package com.aavn.agiledeckserver.game.entity;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@Entity
@Table(name = "tbl_games")
@ApplicationScoped
public class Game {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "{Game.Name.NotNull}")
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToOne(mappedBy = "game")
    @JoinColumn(nullable = false, name = "default_answer_group")
    private AnswerGroup defaultAnswerGroup;

	public Game(String name, String description, AnswerGroup defaultAnswerGroup) {
		this.name = name;
		this.description = description;
		this.defaultAnswerGroup = defaultAnswerGroup;
    }

    public Game(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Game(String name) {
        this.name = name;
    }

    
}
