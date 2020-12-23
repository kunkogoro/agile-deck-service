package com.axonactive.agiletools.agiledeck.gameboard.entity;

import com.axonactive.agiletools.agiledeck.game.entity.Answer;
import com.axonactive.agiletools.agiledeck.game.entity.AnswerContent;
import com.axonactive.agiletools.agiledeck.game.entity.QuestionContent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Table(name = "tbl_answered_questions")
@Entity
@NoArgsConstructor
@Getter @Setter
@NamedQueries({
    @NamedQuery(name = AnsweredQuestion.GET_BY_GAME_BOARD_ID, query = "SELECT aq FROM AnsweredQuestion aq WHERE aq.gameBoard.id = :id ORDER BY aq.id DESC"),
    @NamedQuery(name = AnsweredQuestion.GET_BY_GAME_BOARD_ID_AND_IS_PLAYING, query = "SELECT aq FROM AnsweredQuestion aq WHERE aq.gameBoard.id = :id AND aq.playing = true"),
    @NamedQuery(name = AnsweredQuestion.GET_BY_GAME_BOARD_ID_AND_QUESTION_CONTENT, query = "SELECT aq FROM AnsweredQuestion aq WHERE aq.gameBoard.id = :gameBoardId AND aq.content.content = :content")
})
public class AnsweredQuestion {

    private static final String QUANLIFIER = "com.axonactive.agiletools.agiledeck.gameboard.entity.AnsweredQuestion";
    
    public static final String GET_BY_GAME_BOARD_ID_AND_IS_PLAYING = QUANLIFIER + "getByGameBoardIdAndIsPlaying";

    public static final String GET_BY_GAME_BOARD_ID = QUANLIFIER + "getByGameBoardId";

    public static final String GET_BY_GAME_BOARD_ID_AND_QUESTION_CONTENT = QUANLIFIER + "getByGameBoardIdAndQuestionContent";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "game_board_id", nullable = false)
    private GameBoard gameBoard;

    @Embedded
    private QuestionContent content;
    
    @Transient
    private List<Answer> answerOptions;

    @Transient
    private List<CustomAnswer> customAnswersOptions;

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

    public static AnsweredQuestion createWithoutCustomAnswer(GameBoard gameBoard, List<CustomAnswer> customAnswers){
        AnsweredQuestion answeredQuestion = new AnsweredQuestion();
        answeredQuestion.setGameBoard(gameBoard);
        answeredQuestion.setCustomAnswersOptions(customAnswers);
        answeredQuestion.setPlaying(true);
        return answeredQuestion;
    }

    public AnsweredQuestion(GameBoard gameBoard, QuestionContent content) {
        this.gameBoard = gameBoard;
        this.content = content;
    }
    
}
