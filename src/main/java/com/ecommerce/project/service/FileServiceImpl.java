package com.ecommerce.project.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    /**
     * Upload an image file to the specified directory.
     *
     * @param path The path where the file will be uploaded
     * @param file The MultipartFile to be uploaded
     * @return The name of the uploaded file
     * @throws IOException If an error occurs during file upload
     */
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        String originalFileName = file.getOriginalFilename();
        String randomId = UUID.randomUUID().toString();
        String fileName = randomId.concat(originalFileName.substring(originalFileName.lastIndexOf('.')));
        String filePath = path + File.separator + fileName;

        File folder = new File(path);
        if (!folder.exists())
            folder.mkdir();

        Files.copy(file.getInputStream(), Paths.get(filePath));
        return fileName;
    }
}
