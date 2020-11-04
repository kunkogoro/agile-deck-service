package com.aavn.agiledeckserver.game.entity;

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

@Getter @Setter
@NoArgsConstructor
@Entity
@Table(name = "tbl_questions")
public class Question {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "content")
    private String content;

    @Column(name = "image")
    private String image;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;
    
    @ManyToOne
    @JoinColumn(nullable = false, name = "answer_group_id")
    private AnswerGroup answerGroup;

    public Question(String content, String image, Game game, AnswerGroup answerGroup) {
        this.content = content;
        this.image = image;
        this.game = game;
        this.answerGroup = answerGroup;
    }

    public Question(String content, String image, Game game) {
        this.content = content;
        this.image = image;
        this.game = game;
    }

}
