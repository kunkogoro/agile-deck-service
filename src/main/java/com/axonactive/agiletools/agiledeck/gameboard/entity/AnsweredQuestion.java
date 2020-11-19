package com.axonactive.agiletools.agiledeck.gameboard.entity;

import java.util.List;

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
import javax.persistence.Transient;

import com.axonactive.agiletools.agiledeck.game.entity.Answer;
import com.axonactive.agiletools.agiledeck.game.entity.AnswerContent;
import com.axonactive.agiletools.agiledeck.game.entity.QuestionContent;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "tbl_answered_questions")
@Entity
@NoArgsConstructor
@Getter @Setter
@NamedQueries({
    @NamedQuery(name = AnsweredQuestion.GET_BY_GAME_BOARD_ID_AND_IS_PLAYING, query = "SELECT aq FROM AnsweredQuestion aq WHERE aq.gameBoard.id = :id AND aq.playing = true")
})
public class AnsweredQuestion {

    private static final String QUANLIFIER = "com.axonactive.agiletools.agiledeck.gameboard.entity.AnsweredQuestion";
    
    public static final String GET_BY_GAME_BOARD_ID_AND_IS_PLAYING = QUANLIFIER + "getByGameBoardIdAndIsPlaying";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "game_board_id", nullable = false)
    private GameBoard gameBoard;

    @Embedded
    private QuestionContent content;
    
    @Transient
    private List<Answer> answerOptions;

    private boolean playing;
  
    @Embedded
    private AnswerContent preferedAnswer;

	public static AnsweredQuestion createWithoutQuestion(GameBoard gameBoard, List<Answer> defaultAnswerOptions) {
        AnsweredQuestion answeredQuestion = new AnsweredQuestion();
        answeredQuestion.setGameBoard(gameBoard);
        answeredQuestion.setAnswerOptions(defaultAnswerOptions);
        answeredQuestion.setPlaying(true);
		return answeredQuestion;
	}
}
