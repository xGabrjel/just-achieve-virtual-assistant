package com.appliaction.justAchieveVirtualAssistant.business;

import com.appliaction.justAchieveVirtualAssistant.business.support.ImagesUtils;
import com.appliaction.justAchieveVirtualAssistant.domain.Images;
import com.appliaction.justAchieveVirtualAssistant.domain.exception.NotFoundException;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity.ImagesEntity;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.ImagesRepository;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.jpa.ImagesJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;


@Service
@AllArgsConstructor
public class ImagesService {

    private final ImagesRepository repository;
    private final ImagesJpaRepository jpaRepository;

    @Transactional
    public String uploadImage(MultipartFile file) throws IOException {
        jpaRepository.save(ImagesEntity.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImagesUtils.compressImage(file.getBytes())).build()
        );
        return "File uploaded successfully: [%s]".formatted(file.getOriginalFilename());
    }

    @Transactional
    public byte[] downloadImage(String fileName) {
        Optional<Images> bdImageData = repository.getImage(fileName);
        return bdImageData
                .map(imageEntity -> ImagesUtils.decompressImage(imageEntity.getImageData()))
                .orElseThrow(() -> new NotFoundException("File: [%s] not found".formatted(fileName)));
    }
}
