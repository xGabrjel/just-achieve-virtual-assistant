package com.appliaction.justAchieveVirtualAssistant.api.controller.rest;

import com.appliaction.justAchieveVirtualAssistant.business.ImagesService;
import com.appliaction.justAchieveVirtualAssistant.domain.exception.NotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping("/image-manager")
public class ImagesRestController {

    private ImagesService service;

    @Operation(summary = "Download a photo of your favorite meal!")
    @GetMapping("/downloads/{fileName}")
    public ResponseEntity<?> downloadImage(
            @Parameter(description = "Name of the photo along with the type - example: test.png")
            @PathVariable String fileName
    ) {
        byte[] imageData = service.downloadImage(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }

    @Operation(summary = "Add a photo of your favorite meal!")
    @PostMapping(value = "/uploads", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImage(
            @Parameter(description = "The file from your disk")
            @RequestPart("image") MultipartFile file
    ) throws IOException {
        String uploadImage = service.uploadImage(file);
        return ResponseEntity.status(HttpStatus.OK)
                .body(uploadImage);
    }

    @Operation(summary = "Remove the photo of your meal!")
    @DeleteMapping("/deletion/{fileName}")
    public ResponseEntity<?> deleteImage(
            @Parameter(description = "Name of the photo along with the type - example: test.png")
            @PathVariable String fileName
    ) {
        try {
            service.downloadImage(fileName);
            service.deleteImage(fileName);
            return ResponseEntity.ok("Image [%s] deleted successfully.".formatted(fileName));

        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @Operation(summary = "Update your old photo with a new one!")
    @PutMapping(value = "/updates/{fileName}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateImage(
            @Parameter(description = "Name of the photo along with the type - example: test.png")
            @PathVariable String fileName,
            @Parameter(description = "New file replacing the old one")
            @RequestPart("image") MultipartFile file
    ) throws IOException {

        try {
            service.downloadImage(fileName);
            String updateImage = service.updateImage(fileName, file);
            return ResponseEntity.ok(updateImage);

        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
}
