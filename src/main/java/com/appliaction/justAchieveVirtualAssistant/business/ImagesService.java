package com.appliaction.justAchieveVirtualAssistant.business;

import com.appliaction.justAchieveVirtualAssistant.business.support.ImagesUtils;
import com.appliaction.justAchieveVirtualAssistant.domain.Images;
import com.appliaction.justAchieveVirtualAssistant.domain.exception.NotFoundException;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.mapper.ImagesEntityMapper;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.ImagesRepository;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.jpa.ImagesJpaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class ImagesService {

    private final ImagesRepository repository;
    private final ImagesJpaRepository jpaRepository;
    private final ImagesEntityMapper imagesEntityMapper;

    @Transactional
    public String uploadImage(MultipartFile file) throws IOException {
        log.info("Uploading file [%s]".formatted(file.getOriginalFilename()));

        Images image = Images.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImagesUtils.compressImage(file.getBytes()))
                .build();

        repository.save(image);

        return "File uploaded successfully: [%s]".formatted(file.getOriginalFilename());
    }

    @Transactional
    public byte[] downloadImage(String fileName) {
        log.info("Downloading file [%s]".formatted(fileName));

        Optional<Images> bdImageData = repository.getImage(fileName);
        return bdImageData
                .map(image -> ImagesUtils.decompressImage(image.getImageData()))
                .orElseThrow(() -> new NotFoundException("File: [%s] not found".formatted(fileName)));
    }

    @Transactional
    public String deleteImage(String fileName) {
        log.info("Deleting file [%s]".formatted(fileName));

        repository.deleteImage(fileName);
        return "File deleted successfully: [%s]".formatted(fileName);
    }

    @Transactional
    public String updateImage(String fileName, MultipartFile file) throws IOException {
        log.info("The file [%s] will be replaced with new file: [%s]".formatted(fileName, file.getOriginalFilename()));

        if (repository.getImage(fileName).isEmpty()) {
            throw new NotFoundException("File: [%s] not found".formatted(fileName));
        }

        Images image1 = repository.getImage(fileName).orElseThrow();
        image1.setName(file.getOriginalFilename());
        image1.setType(file.getContentType());
        image1.setImageData(ImagesUtils.compressImage(file.getBytes()));
        repository.save(image1);

        return "File updated successfully: [%s]".formatted(file.getOriginalFilename());
    }
}
