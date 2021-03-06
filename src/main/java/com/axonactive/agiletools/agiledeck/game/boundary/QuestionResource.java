package com.axonactive.agiletools.agiledeck.game.boundary;

import com.axonactive.agiletools.agiledeck.AgileDeckException;
import com.axonactive.agiletools.agiledeck.game.control.GameService;
import com.axonactive.agiletools.agiledeck.game.control.QuestionService;
import com.axonactive.agiletools.agiledeck.game.entity.Game;
import com.axonactive.agiletools.agiledeck.game.entity.Question;
import com.axonactive.agiletools.agiledeck.gameboard.control.AnsweredQuestionService;
import com.axonactive.agiletools.agiledeck.gameboard.control.GameBoardService;
import com.axonactive.agiletools.agiledeck.gameboard.entity.AnsweredQuestion;
import com.axonactive.agiletools.agiledeck.gameboard.entity.GameBoard;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

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
        boolean isLastOne = false;
        GameBoard gameBoard = gameBoardService.getByCode(code);
        Game game = gameService.findById(gameBoard.getGame().getId());

        AnsweredQuestion currentAnsweredQuestion = answeredQuestionService.findCurrentPLaying(gameBoard.getId());
        currentAnsweredQuestion.setPlaying(false);
        answeredQuestionService.updateStatusPlaying(currentAnsweredQuestion);

        List<Question> listQuestion = questionService.getAllByGameID(game.getId(), code);

        Question question = questionService.random(listQuestion, gameBoard.getId());
        AnsweredQuestion newAnsweredQuestion = answeredQuestionService.create(question, gameBoard);
        try{
            questionService.random(listQuestion, gameBoard.getId());
        }catch(AgileDeckException ade) {
            isLastOne = true;
        }

        Map<String, Object> data = new ConcurrentHashMap<>();
        data.put("answeredQuestion", newAnsweredQuestion);
        data.put("isLastOne", isLastOne);
        return Response.ok(data).build();
    }

    @GET
    @Path("/{code}/order")
    public Response getQuestionOrder(@PathParam("code") String code) {
        boolean isLastOne = false;
        GameBoard gB = gameBoardService.getByCode(code);
        Game g = gameService.findById(gB.getGame().getId());

        AnsweredQuestion currentAnsweredQuestion = answeredQuestionService.findCurrentPLaying(gB.getId());
        currentAnsweredQuestion.setPlaying(false);
        answeredQuestionService.updateStatusPlaying(currentAnsweredQuestion);

        List<Question> listQuestion = questionService.getAllByGameID(g.getId(), code);

        Question nextQuestion = null;
        int d = 0;
        for(int i=0; i<listQuestion.size()-1; ++i) {
            ++d;
            if (listQuestion.get(i).getContent().getContent()
                    .equals(currentAnsweredQuestion.getContent().getContent())) {
                nextQuestion = listQuestion.get(i+1);
                break;
            }
        }

        if (Objects.isNull(nextQuestion)) {
            nextQuestion = listQuestion.get(0);
        } else if (d == listQuestion.size() - 1) {
            isLastOne = true;
        }

        Map<String, Object> data = new ConcurrentHashMap<>();
        data.put("answeredQuestion", answeredQuestionService.create(nextQuestion, gB));
        data.put("isLastOne", isLastOne);
        return Response.ok(data).build();
    }

    @POST
    public Response createQuestions(@QueryParam("gameBoardCode") String gameBoardCode, List<Question> questions) {
        GameBoard gameBoard = gameBoardService.getByCode(gameBoardCode);
        gameBoardService.validate(gameBoard);
        questionService.createQuestion(questions, gameBoard);

        return Response.ok().build();
    }
}
