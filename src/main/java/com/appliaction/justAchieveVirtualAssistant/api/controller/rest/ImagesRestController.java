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

import static com.appliaction.justAchieveVirtualAssistant.api.controller.rest.ImagesRestController.ROOT;

@RestController
@AllArgsConstructor
@RequestMapping(ROOT)
public class ImagesRestController {

    static final String ROOT = "/image-manager";
    static final String DOWNLOAD = "/downloads/{fileName}";
    static final String UPLOAD = "/uploads";
    static final String DELETE = "/deletion/{fileName}";
    static final String UPDATE = "/updates/{fileName}";

    static final String DOWNLOAD_MESSAGE = "Download a photo of your favorite meal!";
    static final String UPLOAD_MESSAGE = "Add a photo of your favorite meal!";
    static final String DELETE_MESSAGE = "Remove the photo of your meal!";
    static final String UPDATE_MESSAGE = "Update your old photo with a new one!";

    private ImagesService service;

    @Operation(summary = DOWNLOAD_MESSAGE)
    @GetMapping(DOWNLOAD)
    public ResponseEntity<?> downloadImage(
            @Parameter(description = "Name of the photo along with the type - example: test.png")
            @PathVariable String fileName
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(service.downloadImage(fileName));
    }

    @Operation(summary = UPLOAD_MESSAGE)
    @PostMapping(value = UPLOAD, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImage(
            @Parameter(description = "The file from your disk")
            @RequestPart("image") MultipartFile file
    ) throws IOException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.uploadImage(file));
    }

    @Operation(summary = DELETE_MESSAGE)
    @DeleteMapping(DELETE)
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

    @Operation(summary = UPDATE_MESSAGE)
    @PutMapping(value = UPDATE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateImage(
            @Parameter(description = "Name of the photo along with the type - example: test.png")
            @PathVariable String fileName,
            @Parameter(description = "New file replacing the old one")
            @RequestPart("image") MultipartFile file
    ) throws IOException {
        try {
            service.downloadImage(fileName);
            return ResponseEntity.ok(service.updateImage(fileName, file));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
}
