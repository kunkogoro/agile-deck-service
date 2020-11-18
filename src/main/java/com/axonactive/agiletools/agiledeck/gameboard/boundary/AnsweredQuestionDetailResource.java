package com.axonactive.agiletools.agiledeck.gameboard.boundary;

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

import com.axonactive.agiletools.agiledeck.gameboard.control.AnsweredQuestionDetailService;
import com.axonactive.agiletools.agiledeck.gameboard.entity.AnsweredQuestionDetail;

@Path("/answeredquestiondetails")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
@Transactional
public class AnsweredQuestionDetailResource {
    @Inject
    AnsweredQuestionDetailService answeredQuestionDetailService;

    @GET
    @Path("{id}")
    public Response getAll(@PathParam("id") Long id) {
        List<AnsweredQuestionDetail> listAnsweredQuestionDetail = answeredQuestionDetailService.getAll(id);
        return Response.ok(listAnsweredQuestionDetail).build();
    }
}
