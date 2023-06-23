package com.appliaction.justAchieveVirtualAssistant.business;

import com.appliaction.justAchieveVirtualAssistant.business.support.ImagesUtils;
import com.appliaction.justAchieveVirtualAssistant.domain.Images;
import com.appliaction.justAchieveVirtualAssistant.domain.exception.NotFoundException;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity.ImagesEntity;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.ImagesRepository;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.jpa.ImagesJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ImagesServiceTest {

    @InjectMocks
    private ImagesService service;

    @Mock
    private ImagesJpaRepository jpaRepository;

    @Mock
    private ImagesRepository repository;

    @Test
    void uploadImageWorksCorrectly() throws IOException {
        // given
        String fileName = "test.jpg";
        String contentType = "image/jpeg";
        byte[] imageData = "Test image data".getBytes();
        MultipartFile file = new MockMultipartFile(fileName, fileName, contentType, imageData);
        ImagesEntity savedEntity = ImagesEntity.builder()
                .name(fileName)
                .type(contentType)
                .imageData(ImagesUtils.compressImage(imageData))
                .build();

        when(jpaRepository.save(any(ImagesEntity.class))).thenReturn(savedEntity);

        // when
        String result = service.uploadImage(file);

        // then
        assertNotNull(result);
        assertTrue(result.contains("File uploaded successfully"));
        verify(jpaRepository, times(1)).save(any(ImagesEntity.class));
    }

    @Test
    void downloadImageWorksCorrectly() {
        // given
        String fileName = "test.jpg";
        byte[] imageData = "Test image data".getBytes();
        Images image = Images.builder()
                .name(fileName)
                .type("image/jpeg")
                .imageData(ImagesUtils.compressImage(imageData))
                .build();

        when(repository.getImage(fileName)).thenReturn(Optional.of(image));

        // when
        byte[] result = service.downloadImage(fileName);

        // then
        assertNotNull(result);
        assertArrayEquals(imageData, result);
        verify(repository, times(1)).getImage(fileName);
    }

    @Test
    void downloadImageFileNotFound() {
        // given
        String fileName = "nonexistent.jpg";

        when(repository.getImage(fileName)).thenReturn(Optional.empty());

        // when, then
        assertThrows(NotFoundException.class, () -> service.downloadImage(fileName));
        verify(repository, times(1)).getImage(fileName);
    }
}