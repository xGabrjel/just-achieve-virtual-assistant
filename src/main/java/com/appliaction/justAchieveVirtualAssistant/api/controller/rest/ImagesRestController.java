package com.appliaction.justAchieveVirtualAssistant.api.controller.rest;

import com.appliaction.justAchieveVirtualAssistant.business.ImagesService;
import com.appliaction.justAchieveVirtualAssistant.domain.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping("/image")
public class ImagesRestController {

    private ImagesService service;

    @GetMapping("{fileName}")
    public ResponseEntity<?> downloadImage(@PathVariable String fileName) {
        byte[] imageData = service.downloadImage(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }

    @PostMapping
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
        String uploadImage = service.uploadImage(file);
        return ResponseEntity.status(HttpStatus.OK)
                .body(uploadImage);
    }

    @DeleteMapping("{fileName}")
    public ResponseEntity<?> deleteImage(@PathVariable String fileName) {
        try {
            service.downloadImage(fileName);
            service.deleteImage(fileName);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(("Image [%s] deleted successfully.".formatted(fileName)));

        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @PutMapping("{fileName}")
    public ResponseEntity<?> updateImage(
            @PathVariable String fileName,
            @RequestParam("image")
            MultipartFile file
    ) throws IOException {

        try {
            service.downloadImage(fileName);
            service.updateImage(fileName, file);
            return ResponseEntity.ok("Image [%s] updated successfully.".formatted(fileName));

        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
}
