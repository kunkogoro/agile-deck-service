package com.aavn.agiletools.agiledeck.play.boundary;

import java.net.URI;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.aavn.agiletools.agiledeck.play.control.GameBoardService;
import com.aavn.agiletools.agiledeck.play.entity.AnsweredQuestion;
import com.aavn.agiletools.agiledeck.play.entity.GameBoard;

@Path("/gameboards")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class GameBoardResource {
    
    @Inject
    GameBoardService gameBoardService;

    @Context
    UriInfo uriInfo;

    @PUT
    public Response create(@QueryParam("game") Long gameId) {
        GameBoard gameBoard = gameBoardService.create(gameId);
        URI location = this.uriInfo.getAbsolutePathBuilder().path(String.valueOf(gameBoard.getCode())).build();
        return Response.created(location).entity(gameBoard).build(); 
    }

    @GET
    @Path("{code}")
    public Response join(@PathParam("code") String code) {       
        AnsweredQuestion currentQuestion = gameBoardService.join(code);
        return Response.ok(currentQuestion).build(); 
    }
}
