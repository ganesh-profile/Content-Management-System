package com.CMS.Content.Management.System.filehandler;

import jakarta.servlet.ServletContext;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
@RequiredArgsConstructor
public class multiPartFileHandler implements  fileHandlerService {

    private ServletContext servletContext;

    private Logger LOGGER = LoggerFactory.getLogger(multiPartFileHandler.class);

    @Override
    public String saveFile(MultipartFile multipartFile){
        String filename = multipartFile.getOriginalFilename();
        String upload_Dir = servletContext.getRealPath("/uploads");
        Path uploadPath = Paths.get(upload_Dir);
        try{
            if( !Files.exists(uploadPath)){
                Files.createDirectories(uploadPath);
                LOGGER.debug("Created directories for upload path : {}", upload_Dir);
            }
            Path filePath = uploadPath.resolve(filename);
            Files.copy(multipartFile.getInputStream() , filePath, StandardCopyOption.REPLACE_EXISTING);

            LOGGER.debug("Attempting to save file: {}", filename);
            LOGGER.debug("Upload directory: {}", upload_Dir);
            LOGGER.debug("Final file path: {}", filePath.toString());

            LOGGER.info("File saved successfully: {}", filename);
            return "/uploads/" + filename;
        }
        catch (IOException e) {
            LOGGER.error("Failed to save file: {}",filename , e );
            e.printStackTrace();
            return null;
        }
    }

}
