package com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository;

import com.appliaction.justAchieveVirtualAssistant.domain.Images;
import com.appliaction.justAchieveVirtualAssistant.domain.exception.NotFoundException;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity.ImagesEntity;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.mapper.ImagesEntityMapper;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.jpa.ImagesJpaRepository;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.jpa.UserProfileJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ImagesRepositoryTest {

    @InjectMocks
    private ImagesRepository imagesRepository;
    @Mock
    private ImagesJpaRepository imagesJpaRepository;
    @Mock
    private ImagesEntityMapper imagesEntityMapper;
    @Mock
    private UserProfileJpaRepository userProfileJpaRepository;

    @Test
    void getImageReturnsOptionalOfImagesWorksCorrectly() {
        // given
        String fileName = "test.png";
        ImagesEntity imagesEntity = ImagesEntity.builder()
                .id(1L)
                .name(fileName)
                .type("PNG")
                .imageData(new byte[]{1, 2, 3})
                .build();
        Images images = Images.builder()
                .id(1L)
                .name(fileName)
                .type("PNG")
                .imageData(new byte[]{1, 2, 3})
                .build();

        when(imagesJpaRepository.findByName(fileName))
                .thenReturn(Optional.ofNullable(imagesEntity));
        when(imagesEntityMapper.mapFromEntity(imagesEntity))
                .thenReturn(images);

        ImagesRepository imagesRepository = new ImagesRepository(imagesJpaRepository, imagesEntityMapper, userProfileJpaRepository);

        // when
        Optional<Images> result = imagesRepository.getImage(fileName);

        // then
        assertTrue(result.isPresent());
        assertEquals(images, result.get());
    }

    @Test
    void getImageThrowsNotFoundExceptionWhenImageNotFoundWorksCorrectly() {
        // given
        String fileName = "test.png";

        when(imagesJpaRepository.findByName(fileName))
                .thenReturn(Optional.empty());

        // when
        Optional<Images> result = imagesRepository.getImage(fileName);

        //then
        assertThat(result).isEmpty();
        verify(imagesJpaRepository, times(1)).findByName(fileName);
    }
    @Test
    public void deleteExistingImageWorksCorrectly() {
        //given
        String fileName = "example.jpg";
        ImagesEntity imagesEntity = new ImagesEntity();

        when(imagesJpaRepository.findByName(fileName))
                .thenReturn(Optional.of(imagesEntity));

        //when, then
        assertDoesNotThrow(() -> imagesRepository.deleteImage(fileName));

        verify(imagesJpaRepository, times(1)).delete(imagesEntity);
    }

    @Test
    public void deleteImageNonExistingImageThrowsNotFoundExceptionCorrectly() {
        //given
        String fileName = "nonexistent.jpg";

        when(imagesJpaRepository.findByName(fileName))
                .thenReturn(Optional.empty());

        //when, then
        assertThrows(NotFoundException.class, () -> imagesRepository.deleteImage(fileName));
        verify(imagesJpaRepository, never()).delete(any());
    }
}