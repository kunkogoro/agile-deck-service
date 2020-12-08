package com.axonactive.agiletools.agiledeck.file.control;

import org.apache.commons.io.IOUtils;
import org.eclipse.microprofile.config.ConfigProvider;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

@RequestScoped
@Transactional
public class FileService {
    private static final String STORAGE_DIR = ConfigProvider.getConfig().getValue("quarkus.file.dir", String.class);
    
    @PersistenceContext
    EntityManager em;

    public FileService() {
        createStorage();
    }

    private String getFileName(String contentDisposition) {
        String[] content = contentDisposition.split(";");
        for (String fileName : content) {
            if ((fileName.trim().startsWith("filename"))) {
                String[] name = fileName.split("=");
                return name[1].trim().replaceAll("\"", "");
            }
        }
        return "unknown";
    }

    private void createStorage() {
        java.nio.file.Path path = Paths.get(STORAGE_DIR);
        if (!path.toFile().exists()) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String saveFile(InputPart inputPart) {
        try {
            String contentDisposition = inputPart.getHeaders().getFirst("Content-Disposition");
            String fileName = getFileName(contentDisposition);

            InputStream inputStream = inputPart.getBody(InputStream.class, null);
            byte[] bytes = IOUtils.toByteArray(inputStream);

            File customDir = new File(STORAGE_DIR);
            String fileNamePath = customDir.getAbsolutePath() + File.separator + fileName;
            Files.write(Paths.get(fileNamePath), bytes, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

            return fileName;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("CAN NOT SAVE FILE");
        }
    }

    public List<String> saveMultiFiles(List<InputPart> inputParts) {
        List<String> fileNames = new ArrayList<>();
        for (InputPart inputPart: inputParts) {
            fileNames.add(saveFile(inputPart));
        }
        return fileNames;
    }

    public File getFile(String fileName) {
        File fileDownload = new File(STORAGE_DIR + File.separator + fileName);
        if (!fileDownload.exists()) {
            throw new IllegalArgumentException("FILE NOT EXISTED");
        }
        return fileDownload;
    }
    
    
}
