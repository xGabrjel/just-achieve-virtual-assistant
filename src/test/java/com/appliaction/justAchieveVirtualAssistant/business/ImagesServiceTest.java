package com.appliaction.justAchieveVirtualAssistant.business;

import com.appliaction.justAchieveVirtualAssistant.business.support.ImagesUtils;
import com.appliaction.justAchieveVirtualAssistant.domain.Images;
import com.appliaction.justAchieveVirtualAssistant.domain.exception.NotFoundException;
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

        // when
        String result = imagesService.uploadImage(file);

        // then
        assertNotNull(result);
        assertTrue(result.contains("File uploaded successfully"));
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

        when(imagesRepository.getImage(fileName))
                .thenReturn(Optional.of(image));

        // when
        byte[] result = imagesService.downloadImage(fileName);

        // then
        assertNotNull(result);
        assertArrayEquals(imageData, result);
        verify(imagesRepository, times(1)).getImage(fileName);
    }

    @Test
    void downloadImageFileNotFoundWorksCorrectly() {
        // given
        String fileName = "nonexistent.jpg";

        when(imagesRepository.getImage(fileName))
                .thenReturn(Optional.empty());

        // when, then
        assertThrows(NotFoundException.class, () -> imagesService.downloadImage(fileName));
        verify(imagesRepository, times(1)).getImage(fileName);
    }

    @Test
    public void deleteImageExistingImageWorksCorrectly() {
        //given
        String fileName = "example.jpg";

        doNothing().when(imagesRepository).deleteImage(fileName);

        //when, then
        assertDoesNotThrow(() -> imagesService.deleteImage(fileName));

        verify(imagesRepository, times(1)).deleteImage(fileName);
    }

    @Test
    public void deleteNonExistingImageThrowsNotFoundExceptionWorksCorrectly() {
        // given
        String fileName = "example.jpg";

        doThrow(NotFoundException.class).when(imagesRepository).deleteImage(fileName);

        // when, then
        assertThrows(NotFoundException.class, () -> imagesService.deleteImage(fileName));

        verify(imagesRepository, times(1)).deleteImage(fileName);
    }

    @Test
    public void updateExistingImageWorksCorrectly() throws IOException {
        //given
        String fileName = "existing_image.jpg";
        MultipartFile file = new MockMultipartFile("new_image.jpg", new byte[]{});

        Images existingImage = Images.builder()
                .name(fileName)
                .type("image/jpeg")
                .imageData(new byte[]{})
                .build();

        when(imagesRepository.getImage(fileName))
                .thenReturn(Optional.of(existingImage));

        //when
        String result = imagesService.updateImage(fileName, file);

        //then
        verify(imagesRepository, times(1)).save(any(Images.class));
        assertEquals("File updated successfully: []", result);
    }

    @Test
    public void updateNonExistingImageThrowsNotFoundExceptionWorksCorrectly() {
        //given
        String fileName = "nonexistent.jpg";
        MultipartFile file = mock(MultipartFile.class);

        when(imagesRepository.getImage(fileName))
                .thenReturn(Optional.empty());

        //when, then
        assertThrows(NotFoundException.class, () -> imagesService.updateImage(fileName, file));

        verify(imagesRepository, times(1)).getImage(fileName);
        verify(imagesEntityMapper, never()).mapToEntity(any());
        verify(imagesJpaRepository, never()).save(any());
    }
}