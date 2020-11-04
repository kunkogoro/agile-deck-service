package com.aavn.agiledeckserver.play.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.aavn.agiledeckserver.game.entity.Answer;
import com.aavn.agiledeckserver.game.entity.AnswerGroup;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "tbl_answered_questions")
@Entity
@NoArgsConstructor
@Getter @Setter
public class AnsweredQuestion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "content")
    private String content;

    @Column(name = "image")
    private String image;
    
    @ManyToOne
    @JoinColumn(name = "game_board_id", nullable = false)
    private GameBoard gameBoard;

    @OneToOne
    @JoinColumn(name = "prefered_answer", nullable = false)
    private Answer preferedAnswer;

    @OneToOne
    @JoinColumn(name = "answer_group_id", nullable = false)
    private AnswerGroup answerGroup;

    public AnsweredQuestion(String content, String image, GameBoard gameBoard, Answer preferedAnswer,
            AnswerGroup answerGroup) {
        this.content = content;
        this.image = image;
        this.gameBoard = gameBoard;
        this.preferedAnswer = preferedAnswer;
        this.answerGroup = answerGroup;
    }


}
