package com.axonactive.agiletools.agiledeck.game.boundary;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.axonactive.agiletools.agiledeck.game.control.GameService;
import com.axonactive.agiletools.agiledeck.game.control.QuestionService;
import com.axonactive.agiletools.agiledeck.game.entity.Game;
import com.axonactive.agiletools.agiledeck.game.entity.Question;
import com.axonactive.agiletools.agiledeck.gameboard.control.AnsweredQuestionService;
import com.axonactive.agiletools.agiledeck.gameboard.control.GameBoardService;
import com.axonactive.agiletools.agiledeck.gameboard.entity.AnsweredQuestion;
import com.axonactive.agiletools.agiledeck.gameboard.entity.GameBoard;

@Path("/questions")
@Transactional
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class QuestionResource {

    @Inject 
    QuestionService questionService;

    @Inject 
    GameService gameService;

    @Inject 
    GameBoardService gameBoardService;

    @Inject
    AnsweredQuestionService answeredQuestionService;

    @GET
    @Path("/{code}")
    public Response getQuestion(@PathParam("code") String code){

        GameBoard gameBoard = gameBoardService.getByCode(code);
        Game game = gameService.findById(gameBoard.getGame().getId());

        AnsweredQuestion currentAnsweredQuestion = answeredQuestionService.findCurrenrPLaying(gameBoard.getId());
        currentAnsweredQuestion.setPlaying(false);
        answeredQuestionService.updateStatusPlaying(currentAnsweredQuestion);

        
        List<Question> listQuestion = questionService.getAllByGameID(game.getId());

        Question question = questionService.random(listQuestion, gameBoard.getId());

        AnsweredQuestion newAnsweredQuestion =  answeredQuestionService.create(question, gameBoard);

        return Response.ok(newAnsweredQuestion).build();
    }

}
