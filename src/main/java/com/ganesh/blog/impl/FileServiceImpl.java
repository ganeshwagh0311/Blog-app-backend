package com.ganesh.blog.impl;

import com.ganesh.blog.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    // Allowed file extensions for security
    private static final List<String> ALLOWED_EXTENSIONS = List.of(".png", ".jpg", ".jpeg", ".gif");

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        // Get original filename
        String name = file.getOriginalFilename();
        if (name == null || !name.contains(".")) {
            throw new IllegalArgumentException("Invalid file format. File must have an extension.");
        }

        // Validate file extension
        String fileExtension = name.substring(name.lastIndexOf(".")).toLowerCase();
        if (!ALLOWED_EXTENSIONS.contains(fileExtension)) {
            throw new IllegalArgumentException("Unsupported file format. Allowed formats: " + ALLOWED_EXTENSIONS);
        }

        // Generate unique filename with UUID
        String randomID = UUID.randomUUID().toString();
        String fileName = randomID + fileExtension;

        // Full file path
        Path filePath = Paths.get(path, fileName);

        // Create directory if it doesn't exist
        Files.createDirectories(filePath.getParent());

        // Save file
        Files.copy(file.getInputStream(), filePath);

        return fileName;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        // Construct full file path
        Path fullPath = Paths.get(path, fileName);
        File file = fullPath.toFile();

        // Check if file exists
        if (!file.exists()) {
            throw new FileNotFoundException("File not found at: " + file.getAbsolutePath());
        }

        return new FileInputStream(file);
    }
}
