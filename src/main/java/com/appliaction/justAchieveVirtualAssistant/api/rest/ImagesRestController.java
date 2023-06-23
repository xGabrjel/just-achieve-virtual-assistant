package com.appliaction.justAchieveVirtualAssistant.api.rest;

import com.appliaction.justAchieveVirtualAssistant.business.ImagesService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<?> uploadImage(@RequestParam("image")MultipartFile file) {
        String uploadImage = service.uploadImage(file);
        return ResponseEntity.status(HttpStatus.OK)
                .body(uploadImage);
    }
}
