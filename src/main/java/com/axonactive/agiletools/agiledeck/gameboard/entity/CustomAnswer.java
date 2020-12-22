package com.axonactive.agiletools.agiledeck.gameboard.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.axonactive.agiletools.agiledeck.game.entity.AnswerContent;
import com.axonactive.agiletools.agiledeck.game.entity.AnswerGroup;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter
@NoArgsConstructor
@Entity
@Table( name = "tbl_custom_answers")
public class CustomAnswer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private AnswerContent content;

    @Column(name = "number_order", nullable = false)
    private Integer numberOrder;

    @ManyToOne
    @Column(name = "answer_group_id", nullable = false)
    private AnswerGroup answerGroup;

    @ManyToOne
    @Column(name = "game_board_id", nullable = false)
    private GameBoard gameBoard;


}
