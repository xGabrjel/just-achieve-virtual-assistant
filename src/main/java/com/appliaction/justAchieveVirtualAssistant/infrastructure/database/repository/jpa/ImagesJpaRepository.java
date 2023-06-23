package com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.jpa;

import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity.ImagesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImagesJpaRepository extends JpaRepository<ImagesEntity, Long> {
    Optional<ImagesEntity> findByName(String fileName);
}
