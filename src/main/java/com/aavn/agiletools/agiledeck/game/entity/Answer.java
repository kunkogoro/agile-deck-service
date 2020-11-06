package com.aavn.agiletools.agiledeck.game.entity;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@Entity
@Table(name = "tbl_answers")
@NamedQueries({
    @NamedQuery( name = Answer.FIND_BY_GAME, query = "SELECT ans FROM Answer ans WHERE ans.game.id = :gameId")
})

public class Answer {

    private static final String QUALIFIER = "com.aavn.agiletools.agiledeck.game.entity.";
    
    public static final String FIND_BY_GAME = QUALIFIER + "findByGame";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private AnswerContent content;

    @ManyToOne
    @JoinColumn(name = "answer_group_id", nullable = false)
    private AnswerGroup answerGroup;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    public Answer(Long id) {
        this.id = id;
    }
}
