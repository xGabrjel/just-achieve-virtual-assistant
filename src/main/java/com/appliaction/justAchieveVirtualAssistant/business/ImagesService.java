package com.appliaction.justAchieveVirtualAssistant.business;

import com.appliaction.justAchieveVirtualAssistant.business.support.ImagesUtils;
import com.appliaction.justAchieveVirtualAssistant.domain.Images;
import com.appliaction.justAchieveVirtualAssistant.domain.exception.NotFoundException;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity.ImagesEntity;
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

        jpaRepository.save(ImagesEntity.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImagesUtils.compressImage(file.getBytes())).build()
        );
        return "File uploaded successfully: [%s]".formatted(file.getOriginalFilename());
    }

    @Transactional
    public byte[] downloadImage(String fileName) {
        log.info("Downloading file [%s]".formatted(fileName));

        Optional<Images> bdImageData = repository.getImage(fileName);
        return bdImageData
                .map(imageEntity -> ImagesUtils.decompressImage(imageEntity.getImageData()))
                .orElseThrow(() -> new NotFoundException("File: [%s] not found".formatted(fileName)));
    }

    @Transactional
    public void deleteImage(String fileName) {
        log.info("Deleting file [%s]".formatted(fileName));

        repository.deleteImage(fileName);
    }

    @Transactional
    public void updateImage(String fileName, MultipartFile file) throws IOException {
        log.info("The file [%s] will be replaced with new file: [%s]".formatted(fileName, file.getOriginalFilename()));

        Images existingImage = repository.getImage(fileName)
                .orElseThrow(() -> new NotFoundException("File: [%s] not found".formatted(fileName)));

        ImagesEntity imageEntity = imagesEntityMapper.mapToEntity(existingImage);
        imageEntity.setName(file.getOriginalFilename());
        imageEntity.setType(file.getContentType());
        imageEntity.setImageData(ImagesUtils.compressImage(file.getBytes()));

        jpaRepository.save(imageEntity);
    }
}
