package com.appliaction.justAchieveVirtualAssistant.api.dto.mapper;

import com.appliaction.justAchieveVirtualAssistant.api.dto.ImagesDTO;
import com.appliaction.justAchieveVirtualAssistant.domain.Images;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class ImagesMapperTest {

    private final ImagesMapper imagesMapper = Mappers.getMapper(ImagesMapper.class);

    @Test
    void imagesMapFromEntityWorksCorrectly() {
        //given
        Images domain = Images.builder()
                .id(1L)
                .name("Test Image")
                .type("PNG")
                .imageData(new byte[]{1, 2, 3})
                .build();

        //when
        ImagesDTO dto = imagesMapper.map(domain);
        ImagesDTO nullMapping = imagesMapper.map(null);

        //then
        assertNull(nullMapping);
        assertEquals(domain.getId(), dto.getId());
        assertEquals(domain.getName(), dto.getName());
        assertEquals(domain.getType(), dto.getType());
        assertArrayEquals(domain.getImageData(), dto.getImageData());
    }
}