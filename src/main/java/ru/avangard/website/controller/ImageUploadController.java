package ru.avangard.website.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

//@CrossOrigin(origins = "https://remjest-avangard-testing-e1b1.twc1.net/")
@RestController
public class ImageUploadController {

    @Value("${upload.dir:uploads}")
    private String uploadDir = "uploads";

    @PostMapping("/api/upload/image")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {

        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String fileName = UUID.randomUUID() + extension;

        // Сохраняем в uploads/images/
        String imageDir = uploadDir + "/images";
        Path filePath = Paths.get(imageDir).resolve(fileName);
        Files.createDirectories(filePath.getParent()); // Создаем uploads/images/
        Files.write(filePath, file.getBytes());

        String imageUrl = "/images/" + fileName;
        return ResponseEntity.ok(imageUrl);
    }

    @PostMapping("/api/upload/video")
    public ResponseEntity<String> uploadVideo(@RequestParam("file") MultipartFile file) throws IOException {

        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String fileName = UUID.randomUUID() + extension;

        // Сохраняем в uploads/videos/
        String videoDir = uploadDir + "/videos";
        Path filePath = Paths.get(videoDir).resolve(fileName);
        Files.createDirectories(filePath.getParent()); // Создаем uploads/videos/
        Files.write(filePath, file.getBytes());

        // Возвращаем URL, который нужно будет обслужить
        // Добавим ResourceHandler для /videos/**
        String videoUrl = "/videos/" + fileName; // URL для видео
        return ResponseEntity.ok(videoUrl);
    }
}