package com.deepl.api.test.controller;

import com.deepl.api.test.service.TranslatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;


import java.io.*;
import java.nio.file.Path;

import static com.deepl.api.test.model.Cleaner.cleanFolder;


@Controller
public class TestController {
//    @Value("${spring.servlet.multipart.location}")
//    private String uploadDirectory;
    String outputDirectory = "src/main/resources/static/output/";
    private final TranslatorService translatorService;

    @Autowired
    public TestController(TranslatorService translatorService) {
        this.translatorService = translatorService;
    }

    @GetMapping("/file")
    public String index() {
        try{System.out.println("Trying to clean the folder...");}
        finally {
            boolean isCleaned = cleanFolder(outputDirectory);
            if (isCleaned) {
                System.out.println("Folder cleaned successfully.");
            }
            else {
                System.out.println("Could not clean the folder completely.");
            }
        }
        return "main";
    }

    @PostMapping("/file")
    public String uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            Path tempFile = Files.createTempFile(null, fileName);
            Files.copy(file.getInputStream(), tempFile, StandardCopyOption.REPLACE_EXISTING);
            try {
                File translatedFile = translatorService.documentTranslation(tempFile.toFile());
                return "redirect:/download/" + translatedFile.getName();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return "redirect:/file";
    }

    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        Path filePath = Path.of(outputDirectory, fileName);
        File file = filePath.toFile();
        System.out.println("FILE DOWNLOADED!!!");

        try {
            if (file.exists()) {
                Resource resource = new UrlResource(filePath.toUri());
                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);

                return ResponseEntity.ok()
                        .headers(headers)
                        .body(resource);
            } else {
                System.out.println("no file");
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found");
            }
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while processing the file", e);
        }
    }
}
