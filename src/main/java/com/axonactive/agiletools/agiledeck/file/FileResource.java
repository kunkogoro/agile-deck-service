package com.axonactive.agiletools.agiledeck.file;

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Path("/files")
@Transactional
public class FileResource {

    @Inject
    FileService fileService;

    @Context
    UriInfo uriInfo;

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    public Response upload(@MultipartForm MultipartFormDataInput input) {
        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        List<String> fileNames = fileService.saveMultiFiles(uploadForm.get("file"));

        List<String> pathFileName = fileNames.stream()
                .map(s -> s = uriInfo.getBaseUri().toString() + "files/download/" + s)
                .collect(Collectors.toList());

        return Response.ok()
                .header("files", pathFileName)
                .build();
    }

    @GET
    @Path("/download/{filename}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response download(@PathParam("filename") String fileName) {
        File file = fileService.getFile(fileName);
        return Response.ok(file)
                .header("Content-Disposition", "attachment;filename=" + fileName)
                .build();
    }




}