package com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository;

import com.appliaction.justAchieveVirtualAssistant.domain.Images;
import com.appliaction.justAchieveVirtualAssistant.domain.exception.NotFoundException;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity.ImagesEntity;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity.UserProfileEntity;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.mapper.ImagesEntityMapper;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.jpa.ImagesJpaRepository;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.jpa.UserProfileJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class ImagesRepository {

    private final ImagesJpaRepository imagesJpaRepository;
    private final ImagesEntityMapper imagesEntityMapper;
    private final UserProfileJpaRepository userProfileJpaRepository;

    public Optional<Images> getImage(String fileName) {
        return imagesJpaRepository.findByName(fileName)
                .stream()
                .map(imagesEntityMapper::mapFromEntity)
                .findFirst();
    }
    public Optional<Images> getImageByUserProfile(String username) {
        UserProfileEntity userProfile = userProfileJpaRepository.findByUserUsername(username)
                .orElseThrow(() -> new NotFoundException("UserEntity [%s] not found!".formatted(username)));

        return imagesJpaRepository.findByProfileId(userProfile)
                .stream()
                .map(imagesEntityMapper::mapFromEntity)
                .findFirst();
    }

    @Transactional
    public void deleteImage(String fileName) {
        ImagesEntity imagesEntity = imagesJpaRepository.findByName(fileName)
                .orElseThrow(() -> new NotFoundException("Image [%s] does not exist!".formatted(fileName)));

        imagesJpaRepository.delete(imagesEntity);
    }

    public void save(Images image) {
        ImagesEntity imagesEntity = imagesEntityMapper.mapToEntity(image);
        imagesJpaRepository.saveAndFlush(imagesEntity);
    }
}
