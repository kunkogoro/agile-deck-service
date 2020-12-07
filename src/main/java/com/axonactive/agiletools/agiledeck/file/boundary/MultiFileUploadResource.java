package com.axonactive.agiletools.agiledeck.file.boundary;

import org.apache.commons.io.IOUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import com.axonactive.agiletools.agiledeck.file.control.MultiFileUploadService;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;

@Path("/files")
@Consumes(MediaType.MULTIPART_FORM_DATA)
@Produces(MediaType.TEXT_PLAIN)
public class MultiFileUploadResource {

    @Inject
    MultiFileUploadService multiFileUploadService;

    @ConfigProperty(name = "quarkus.file.dir")
    private String storageDir;

    @POST
    @Path("/upload")
    public Response handleFileUploadForm(@MultipartForm MultipartFormDataInput input) {
        
        multiFileUploadService.createStorage();

        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();

        List<InputPart> inputParts = uploadForm.get("file");
        String fileName;

        for (InputPart inputPart : inputParts) {
            try {
                MultivaluedMap<String, String> header = inputPart.getHeaders();
                fileName = multiFileUploadService.getFileName(header);

                InputStream inputStream = inputPart.getBody(InputStream.class, null);
                byte[] bytes = IOUtils.toByteArray(inputStream);

                File customDir = new File(storageDir);
                fileName = customDir.getAbsolutePath() + File.separator + fileName;
                Files.write(Paths.get(fileName), bytes, StandardOpenOption.CREATE_NEW);
            } catch (Exception e) {
                e.printStackTrace();
                return Response.status(500).build();
            }
        }
        return Response.ok().build();
    }

    @GET
    @Path("/download/{filename}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response downloadFileWithGet(@PathParam("filename") String file) {
        File fileDownload = new File(storageDir + File.separator + file);
        return Response.ok(fileDownload).header("Content-Disposition", "attachment;filename=" + file).build();
    }

}
