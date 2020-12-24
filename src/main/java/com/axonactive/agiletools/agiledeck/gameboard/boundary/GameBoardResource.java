package com.axonactive.agiletools.agiledeck.gameboard.boundary;

import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.axonactive.agiletools.agiledeck.AgileDeckException;
import com.axonactive.agiletools.agiledeck.file.control.FileService;
import com.axonactive.agiletools.agiledeck.game.control.AnswerService;
import com.axonactive.agiletools.agiledeck.game.control.QuestionService;
import com.axonactive.agiletools.agiledeck.game.entity.Answer;
import com.axonactive.agiletools.agiledeck.game.entity.Game;
import com.axonactive.agiletools.agiledeck.game.entity.Question;
import com.axonactive.agiletools.agiledeck.gameboard.control.AnsweredQuestionDetailService;
import com.axonactive.agiletools.agiledeck.gameboard.control.AnsweredQuestionService;
import com.axonactive.agiletools.agiledeck.gameboard.control.CustomAnswerService;
import com.axonactive.agiletools.agiledeck.gameboard.control.GameBoardService;
import com.axonactive.agiletools.agiledeck.gameboard.control.PlayerService;
import com.axonactive.agiletools.agiledeck.gameboard.entity.AnsweredQuestion;
import com.axonactive.agiletools.agiledeck.gameboard.entity.AnsweredQuestionDetail;
import com.axonactive.agiletools.agiledeck.gameboard.entity.CustomAnswer;
import com.axonactive.agiletools.agiledeck.gameboard.entity.GameBoard;
import com.axonactive.agiletools.agiledeck.gameboard.entity.Player;

@Path("/gameboards")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
@Transactional
public class GameBoardResource {
    
    @Inject
    GameBoardService gameBoardService;

    @Inject
    PlayerService playerService;

    @Inject
    QuestionService questionService;

    @Inject
    AnsweredQuestionDetailService answeredQuestionDetailService;

    @Inject
    AnsweredQuestionService answeredQuestionService;

    @Inject
    AnswerService answerService;

    @Inject
    CustomAnswerService customAnswerService;

    @Inject
    FileService fileService;

    @Context
    UriInfo uriInfo;

    @PUT
    public Response create(@QueryParam("game") Long gameId) {
        GameBoard gameBoard = gameBoardService.create(gameId);
        List<Question> questions = questionService.getAllByGameID(gameId, null);
        Question question = questionService.random(questions, gameBoard.getId());
        answeredQuestionService.create(question, gameBoard);
        URI location = this.uriInfo.getAbsolutePathBuilder().path(String.valueOf(gameBoard.getCode())).build();
        return Response.created(location).entity(gameBoard).build(); 
    }


    @GET
    @Path("/join/{code}")
    public Response join(@PathParam("code") String code) {
        AnsweredQuestion currentQuestion = gameBoardService.join(code);
        Player player = playerService.create(code);
        AnsweredQuestionDetail answeredQuestionDetail = answeredQuestionDetailService.create(currentQuestion, player);
        Long gameId = gameBoardService.getByCode(code).getGame().getId();
        answeredQuestionDetail.getAnsweredQuestion().setAnswerOptions(answerService.getByGame(gameId));

        boolean isLastOne = false;
        List<Question> listQuestion = questionService.getAllByGameID(gameId, code);
        try{
            questionService.random(listQuestion, gameBoardService.getByCode(code).getId());
        }catch(AgileDeckException agileDeckException) {
            isLastOne = true;
        }
        Map<String, Object> data = new ConcurrentHashMap<>();
        data.put("answeredQuestionDetail", answeredQuestionDetail);
        data.put("isLastOne", isLastOne);
        return Response.ok(data).build();
    }

    @GET
    @Path("/rejoin/{code}")
    public Response rejoin(@PathParam("code") String code, @QueryParam("playerId") Long playerId) {
        AnsweredQuestion currentQuestion = gameBoardService.join(code);

        Player player = playerService.findById(playerId);

        AnsweredQuestionDetail answeredQuestionDetail = answeredQuestionDetailService.rejoin(currentQuestion, player);
        if (Objects.isNull(answeredQuestionDetail)) {
            answeredQuestionDetail = answeredQuestionDetailService.create(currentQuestion, player);
        }

        GameBoard gameBoard = gameBoardService.getByCode(code);
        Long gameId = gameBoard.getGame().getId();
        
        List<CustomAnswer> customAnswers = customAnswerService.getByGameBoadId(gameBoard);
        if (customAnswers.isEmpty()) {
            answeredQuestionDetail.getAnsweredQuestion().setAnswerOptions(answerService.getByGame(gameId));
        } else {
            answeredQuestionDetail.getAnsweredQuestion().setCustomAnswersOptions(customAnswers);
        }
        
        boolean isLastOne = false;
        List<Question> listQuestion = questionService.getAllByGameID(gameId, code);
        try{
            questionService.random(listQuestion, gameBoardService.getByCode(code).getId());
        }catch(AgileDeckException ade) {
            isLastOne = true;
        }

        Map<String, Object> data = new ConcurrentHashMap<>();
        data.put("answeredQuestionDetail", answeredQuestionDetail);
        data.put("isLastOne", isLastOne);
        return Response.ok(data).build();
    }

    @GET
    @Path("/history/{code}")
    public Response getHistory(@PathParam("code") String code) {
        GameBoard currentGameBoard = gameBoardService.getByCode(code);
        gameBoardService.validate(currentGameBoard);

        Map<String, Object> history = new LinkedHashMap<>();
        List<AnsweredQuestion> questionList = answeredQuestionService.findByGameBoardId(currentGameBoard.getId());
        questionList.forEach(answeredQuestion -> {
            List<AnsweredQuestionDetail> answerList = answeredQuestionDetailService.getAllByAnsweredQuestionId(answeredQuestion.getId())
                    .stream()
                    .filter(answeredQuestionDetail -> Objects.nonNull(answeredQuestionDetail.getAnswer()))
                    .collect(Collectors.toList());
            if (!answerList.isEmpty()) {
                history.put(answeredQuestion.getContent().getContent(), answerList);
            }
        });

        return Response.ok(history).build();
    }

    @PUT
    @Path("/add-answer/{code}")
    public Response addNewAnswer(@PathParam("code") String code, Answer answer){

        GameBoard gameBoard = gameBoardService.getByCode(code);
        gameBoardService.validate(gameBoard);
        gameBoardService.validateLengthListAnswerOfGameBoard(gameBoard);

        if(gameBoardService.validateGameBoardInCustomAnswer(gameBoard)){
            customAnswerService.addAnswerOfGameBoard(answer, gameBoard);
            return Response.ok().build();
        }

        Game game = gameBoard.getGame();
        List<Answer> defaultAnswer = answerService.getByGame(game.getId());
        customAnswerService.createNewCustomAnswerOfGameBoard(defaultAnswer, gameBoard);
        customAnswerService.addAnswerOfGameBoard(answer, gameBoard);
        return Response.ok().build();
    }

    @PUT
    @Path("/update-answer-content/{code}")
    public Response updateAnswerContent(@PathParam("code") String code, CustomAnswer customAnswer){

        GameBoard gameBoard = gameBoardService.getByCode(code);
        gameBoardService.validate(gameBoard);

        if(gameBoardService.validateGameBoardInCustomAnswer(gameBoard)){
            customAnswerService.editContent(customAnswer);
            return Response.ok().build();
        }

        Game game = gameBoard.getGame();
        List<Answer> defaultAnswer = answerService.getByGame(game.getId());
        List<Answer> answerCopy = new ArrayList<>();
        defaultAnswer.forEach(answer -> {
            if(answer.getId() == customAnswer.getId()){
                Answer item = new Answer(customAnswer.getContent(), answer.getNumberOrder(), answer.getAnswerGroup(), answer.getGame(), answer.getQuestion());
                answerCopy.add(item);
            }else{
                answerCopy.add(answer);
            }
        });

        customAnswerService.createNewCustomAnswerOfGameBoard(answerCopy, gameBoard);

        return Response.ok().build();
    }


    @DELETE
    @Path("/delete-answer/{code}")
    public Response deleteAnswer(@PathParam("code") String code, Long customAnswerId){
        customAnswerService.delete(customAnswerId);
        return Response.ok().build();
    }
}
