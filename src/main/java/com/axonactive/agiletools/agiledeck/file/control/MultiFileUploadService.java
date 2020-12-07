package com.axonactive.agiletools.agiledeck.file.control;

import java.nio.file.Paths;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.IOException;
import java.nio.file.Files;

import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;
import javax.ws.rs.core.MultivaluedMap;

@RequestScoped
@Transactional
public class MultiFileUploadService {

    @ConfigProperty(name = "quarkus.file.dir")
    private String storageDir;
    
    public void createStorage(){
        java.nio.file.Path path = Paths.get(storageDir);
        if (!path.toFile().exists()) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getFileName(MultivaluedMap<String, String> header) {
        String[] contentDisposition = header.getFirst("Content-Disposition").split(";");
        for (String filename : contentDisposition) {
            if ((filename.trim().startsWith("filename"))) {
                String[] name = filename.split("=");
                return name[1].trim().replaceAll("\"", "");
            }
        }
        return "unknown";
    }
}
