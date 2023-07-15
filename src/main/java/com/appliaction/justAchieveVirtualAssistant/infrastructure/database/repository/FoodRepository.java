package com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository;

import com.appliaction.justAchieveVirtualAssistant.domain.Food;
import com.appliaction.justAchieveVirtualAssistant.domain.exception.NotFoundException;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity.FoodEntity;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity.UserProfileEntity;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.mapper.FoodEntityMapper;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.jpa.FoodJpaRepository;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.jpa.UserProfileJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class FoodRepository {

    private final FoodJpaRepository foodJpaRepository;
    private final FoodEntityMapper foodEntityMapper;
    private final UserProfileJpaRepository userProfileJpaRepository;

    public void saveIntoDatabase(Food food) {
        FoodEntity foodEntity = foodEntityMapper.mapToEntity(food);
        foodJpaRepository.saveAndFlush(foodEntity);
    }

    public List<FoodEntity> findAllProducts(String username) {
        UserProfileEntity userProfileEntity = userProfileJpaRepository.findByUserUsername(username)
                .orElseThrow(() -> new NotFoundException("UserProfile with username [%s] not found!".formatted(username)));

        return foodJpaRepository.findAllByProfileId(userProfileEntity);
    }

    public void deleteAllProducts() {
        foodJpaRepository.deleteAll();
    }
}
