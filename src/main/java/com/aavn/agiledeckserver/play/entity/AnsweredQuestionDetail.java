package com.aavn.agiledeckserver.play.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.aavn.agiledeckserver.game.entity.Answer;
import com.aavn.agiledeckserver.user.entity.Player;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "tbl_answered_question_details")
@Entity
@NoArgsConstructor
@Getter @Setter
public class AnsweredQuestionDetail {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @OneToOne
    @JoinColumn(name = "player_id")
    private Player player;


    @ManyToOne
    @JoinColumn(name = "answered_question_id")
    private AnsweredQuestion answeredQuestion;

    @ManyToOne
    @JoinColumn(name = "answer")
    private Answer answer;

    public AnsweredQuestionDetail(Player player, AnsweredQuestion answeredQuestion, Answer answer) {
        this.player = player;
        this.answeredQuestion = answeredQuestion;
        this.answer = answer;
    }
}
