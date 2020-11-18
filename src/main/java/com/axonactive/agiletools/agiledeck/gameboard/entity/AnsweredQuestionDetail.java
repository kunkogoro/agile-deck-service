package com.axonactive.agiletools.agiledeck.gameboard.entity;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.axonactive.agiletools.agiledeck.game.entity.AnswerContent;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "tbl_answered_question_details")
@Entity
@NoArgsConstructor
@Getter
@Setter
public class AnsweredQuestionDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "answered_question_id")
    private AnsweredQuestion answeredQuestion;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    @Embedded
    private AnswerContent answer;

    public AnsweredQuestionDetail(AnsweredQuestion answeredQuestion, Player player) {
        this.answeredQuestion = answeredQuestion;
        this.player = player;
    }

    
}
