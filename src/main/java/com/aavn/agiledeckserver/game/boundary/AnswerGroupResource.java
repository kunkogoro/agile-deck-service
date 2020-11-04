package com.aavn.agiledeckserver.game.boundary;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.aavn.agiledeckserver.game.control.AnswerGroupService;

@Stateless
@Path("/answergroup")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class AnswerGroupResource implements Serializable{
    
    @Inject
    AnswerGroupService answerGroupService;

    @GET
    @Path("{id}")
    public Response getAnswerGroup(@PathParam("id") Integer id) {
        return Response.ok(answerGroupService.getById(id)).build();
    }

}
