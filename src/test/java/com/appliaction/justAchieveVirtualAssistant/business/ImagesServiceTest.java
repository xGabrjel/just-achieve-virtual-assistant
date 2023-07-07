package com.appliaction.justAchieveVirtualAssistant.business;

import com.appliaction.justAchieveVirtualAssistant.business.support.ImagesUtils;
import com.appliaction.justAchieveVirtualAssistant.domain.Images;
import com.appliaction.justAchieveVirtualAssistant.domain.exception.NotFoundException;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity.ImagesEntity;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.mapper.ImagesEntityMapper;
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
    private ImagesService imagesService;

    @Mock
    private ImagesJpaRepository imagesJpaRepository;

    @Mock
    private ImagesRepository imagesRepository;

    @Mock
    private ImagesEntityMapper imagesEntityMapper;

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

        when(imagesJpaRepository.save(any(ImagesEntity.class))).thenReturn(savedEntity);

        // when
        String result = imagesService.uploadImage(file);

        // then
        assertNotNull(result);
        assertTrue(result.contains("File uploaded successfully"));
        verify(imagesJpaRepository, times(1)).save(any(ImagesEntity.class));
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

        when(imagesRepository.getImage(fileName)).thenReturn(Optional.of(image));

        // when
        byte[] result = imagesService.downloadImage(fileName);

        // then
        assertNotNull(result);
        assertArrayEquals(imageData, result);
        verify(imagesRepository, times(1)).getImage(fileName);
    }

    @Test
    void downloadImageFileNotFound() {
        // given
        String fileName = "nonexistent.jpg";

        when(imagesRepository.getImage(fileName)).thenReturn(Optional.empty());

        // when, then
        assertThrows(NotFoundException.class, () -> imagesService.downloadImage(fileName));
        verify(imagesRepository, times(1)).getImage(fileName);
    }

    @Test
    public void deleteImageExistingImageWorksCorrectly() {
        String fileName = "example.jpg";

        doNothing().when(imagesRepository).deleteImage(fileName);

        assertDoesNotThrow(() -> imagesService.deleteImage(fileName));

        verify(imagesRepository, times(1)).deleteImage(fileName);
    }

    @Test
    public void deleteNonExistingImageWorksCorrectly() {
        String fileName = "nonexistent.jpg";

        doThrow(NotFoundException.class).when(imagesRepository).deleteImage(fileName);

        assertThrows(NotFoundException.class, () -> imagesService.deleteImage(fileName));

        verify(imagesRepository, times(1)).deleteImage(fileName);
    }

    @Test
    public void updateExistingImageWorksCorrectly() throws IOException {
        String fileName = "example.jpg";
        MultipartFile file = mock(MultipartFile.class);
        Images existingImage = mock(Images.class);
        ImagesEntity imageEntity = mock(ImagesEntity.class);

        when(imagesRepository.getImage(fileName)).thenReturn(Optional.of(existingImage));
        when(imagesEntityMapper.mapToEntity(existingImage)).thenReturn(imageEntity);
        when(file.getOriginalFilename()).thenReturn("newfile.jpg");
        when(file.getContentType()).thenReturn("image/jpeg");
        when(file.getBytes()).thenReturn(new byte[0]);

        assertDoesNotThrow(() -> imagesService.updateImage(fileName, file));

        verify(imagesRepository, times(1)).getImage(fileName);
        verify(imagesEntityMapper, times(1)).mapToEntity(existingImage);
        verify(imageEntity, times(1)).setName("newfile.jpg");
        verify(imageEntity, times(1)).setType("image/jpeg");
        verify(imageEntity, times(1)).setImageData(any(byte[].class));
        verify(imagesJpaRepository, times(1)).save(imageEntity);
    }

    @Test
    public void updateNonExistingImageThrowsNotFoundExceptionCorrectly() throws IOException {
        String fileName = "nonexistent.jpg";
        MultipartFile file = mock(MultipartFile.class);

        when(imagesRepository.getImage(fileName)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> imagesService.updateImage(fileName, file));

        verify(imagesRepository, times(1)).getImage(fileName);
        verify(imagesEntityMapper, never()).mapToEntity(any());
        verify(imagesJpaRepository, never()).save(any());
    }
}