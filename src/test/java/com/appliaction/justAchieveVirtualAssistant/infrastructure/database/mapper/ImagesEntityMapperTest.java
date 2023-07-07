package com.appliaction.justAchieveVirtualAssistant.infrastructure.database.mapper;

import com.appliaction.justAchieveVirtualAssistant.domain.Images;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity.ImagesEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class ImagesEntityMapperTest {

    private final ImagesEntityMapper imagesEntityMapper = Mappers.getMapper(ImagesEntityMapper.class);

    @Test
    void imagesMapFromEntityWorksCorrectly() {
        //given
        ImagesEntity entity = ImagesEntity.builder()
                .id(1L)
                .name("Test Image")
                .type("PNG")
                .imageData(new byte[]{1, 2, 3})
                .build();

        //when
        Images domain = imagesEntityMapper.mapFromEntity(entity);
        Images nullMapping = imagesEntityMapper.mapFromEntity(null);

        //then
        assertNull(nullMapping);
        assertEquals(entity.getId(), domain.getId());
        assertEquals(entity.getName(), domain.getName());
        assertEquals(entity.getType(), domain.getType());
        assertArrayEquals(entity.getImageData(), domain.getImageData());
    }
    @Test
    void imagesMapToEntityWorksCorrectly() {
        //given
        Images domain = Images.builder()
                .id(1L)
                .name("Test Image")
                .type("PNG")
                .imageData(new byte[]{1, 2, 3})
                .build();

        //when
        ImagesEntity entity = imagesEntityMapper.mapToEntity(domain);
        ImagesEntity nullMapping = imagesEntityMapper.mapToEntity(null);

        //then
        assertNull(nullMapping);
        assertEquals(domain.getId(), entity.getId());
        assertEquals(domain.getName(), entity.getName());
        assertEquals(domain.getType(), entity.getType());
        assertArrayEquals(domain.getImageData(), entity.getImageData());
    }
}