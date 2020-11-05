package com.aavn.agiletools.agiledeck.play.boundary;

import java.net.URI;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.aavn.agiletools.agiledeck.play.control.GameBoardService;
import com.aavn.agiletools.agiledeck.play.entity.GameBoard;

@Path("/")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class GameBoardResource {
    
    @Inject
    GameBoardService gameBoardService;

    @Context
    UriInfo uriInfo;

    @POST
    public Response generate() {
        GameBoard gameBoard = gameBoardService.create(1);
        URI location = this.uriInfo.getAbsolutePathBuilder().path(gameBoard.getCode()).build();
        return Response.created(location).build(); 
    }

    @GET
    @Path("{code}")
    public Response getGameBoard(@PathParam("code") String code) {
        GameBoard gameBoard = gameBoardService.getByCode(code);
        return Response.ok(gameBoard).build(); 
    }
}
