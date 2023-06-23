package com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository;

import com.appliaction.justAchieveVirtualAssistant.domain.Images;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.mapper.ImagesEntityMapper;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.jpa.ImagesJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class ImagesRepository {

    private final ImagesJpaRepository imagesJpaRepository;
    private final ImagesEntityMapper imagesEntityMapper;

    public Optional<Images> getImage(String fileName) {
        return imagesJpaRepository.findByName(fileName)
                .stream()
                .map(imagesEntityMapper::mapFromEntity)
                .findFirst();
    }
}
