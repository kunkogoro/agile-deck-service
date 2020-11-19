package com.axonactive.agiletools.agiledeck.gameboard.entity;

import javax.persistence.*;

import com.axonactive.agiletools.agiledeck.game.entity.AnswerContent;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "tbl_answered_question_details")
@Entity
@NoArgsConstructor
@Getter
@Setter
@NamedQueries({
        @NamedQuery(name = AnsweredQuestionDetail.GET_BY_ANSWER_QUESTION_AND_PLAYER,
                query = "SELECT aqd FROM AnsweredQuestionDetail aqd " +
                        "WHERE aqd.player.id = :playerId AND aqd.answeredQuestion.id = :answeredQuestionId")
})
public class AnsweredQuestionDetail {

    private static final String QUALIFIER = "com.axonactive.agiletools.agiledeck.gameboard.entity.AnsweredQuestionDetail.";

    public static final String GET_BY_ANSWER_QUESTION_AND_PLAYER = QUALIFIER + "getByAnswerQuestionAndPlayer";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
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
