package com.axonactive.agiletools.agiledeck.game.entity;

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

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tbl_questions")
@NamedQueries({
    @NamedQuery(name = Question.GET_ALL_BY_GAME_ID, query = "SELECT q FROM Question q WHERE q.game.id = :gameId")
})
public class Question {

    private static final String QUALIFIER = "com.axonactive.agiletools.agiledeck.game.entity.Question";

    public static final String GET_ALL_BY_GAME_ID = QUALIFIER + "getAllByGameId";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @Embedded
    private QuestionContent content;

}

