package com.kappa.resources.controller;

import com.kappa.resources.service.FileStorageManager;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/drive")
public class DriveController {

    @Autowired
    FileStorageManager fileStorageManager;
    @PostMapping("/upload/single")
    public ResponseEntity<Map<String, String>> handleFileUploadUsingCurl(@RequestParam("file") MultipartFile file) throws IOException {
        Map<String, String> map = new HashMap<>();
        map.put("fileName", file.getOriginalFilename());
        map.put("fileSize", String.valueOf(file.getSize()));
        map.put("fileContentType", file.getContentType());
        map.put("message", "File upload done");
        return ResponseEntity.ok(map);
    }

  @PostMapping("/upload/multiple")
  public ResponseEntity<String> handleFileUploadMultiple(@RequestParam("files") MultipartFile[] files) throws IOException {
        return ResponseEntity.ok("Files uploaded successfully!");
  }

    @Async
    @PostMapping("/upload/async")
    public CompletableFuture<ResponseEntity<String>> handleConcurrentFilesUpload(
            @RequestParam("files") MultipartFile[] files) throws IOException {

        // Handle empty file error
        if (files.length == 0) {
            return CompletableFuture
                    .completedFuture(ResponseEntity.badRequest().body("No files submitted"));
        }
        // File upload process is submitted
        else {

            for (MultipartFile file : files) {
                fileStorageManager.save(file);
                //TODO: access and store each file into file storage
            }
            return CompletableFuture.completedFuture(
                    ResponseEntity.ok("File upload started"));
        }
    }

}
