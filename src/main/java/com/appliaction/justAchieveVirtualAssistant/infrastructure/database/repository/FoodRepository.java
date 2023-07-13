package com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository;

import com.appliaction.justAchieveVirtualAssistant.domain.Food;
import com.appliaction.justAchieveVirtualAssistant.domain.exception.NotFoundException;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity.FoodEntity;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.mapper.FoodEntityMapper;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.jpa.FoodJpaRepository;
import com.appliaction.justAchieveVirtualAssistant.security.user.UserEntity;
import com.appliaction.justAchieveVirtualAssistant.security.user.UserJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class FoodRepository {

    private final FoodJpaRepository foodJpaRepository;
    private final FoodEntityMapper foodEntityMapper;
    private final UserJpaRepository userRepository;

    public void saveIntoDatabase(Food food) {
        FoodEntity foodEntity = foodEntityMapper.mapToEntity(food);
        foodJpaRepository.save(foodEntity);
    }

    public List<FoodEntity> findAllProducts(String username) {
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User with username [%s] not found!".formatted(username)));

        return foodJpaRepository.findAllByUserId(userEntity);
    }

    public void deleteAllProducts() {
        foodJpaRepository.deleteAll();
    }
}
