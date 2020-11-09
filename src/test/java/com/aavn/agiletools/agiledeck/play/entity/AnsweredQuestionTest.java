package com.aavn.agiletools.agiledeck.play.entity;

import com.aavn.agiletools.agiledeck.game.entity.Answer;
import com.aavn.agiletools.agiledeck.game.entity.Game;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class AnsweredQuestionTest {

    @Test
    public void whenCreateWithoutQuestion_thenReturnCorrectAnswer() {
        List<Answer> answerList = Arrays.asList(
                new Answer(1L),
                new Answer(2L),
                new Answer(3L),
                new Answer(4L)
        );
        Game game = new Game();
        GameBoard gameBoard = new GameBoard("sdf-asdfag-sdvdafgfda-ger", game);
        Assertions.assertNotNull(AnsweredQuestion.createWithoutQuestion(gameBoard, answerList));
    }
}