package com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository;

import com.appliaction.justAchieveVirtualAssistant.domain.Food;
import com.appliaction.justAchieveVirtualAssistant.domain.exception.NotFoundException;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity.FoodEntity;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity.UserProfileEntity;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.mapper.FoodEntityMapper;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.jpa.FoodJpaRepository;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.jpa.UserProfileJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FoodRepositoryTest {

    @InjectMocks
    private FoodRepository foodRepository;
    @Mock
    private FoodJpaRepository foodJpaRepository;
    @Mock
    private FoodEntityMapper foodEntityMapper;
    @Mock
    private UserProfileJpaRepository userProfileJpaRepository;

    @Test
    void findAllProductsShouldReturnListOfFoodEntitiesWorksCorrectly() {
        //given
        String username = "testUser";
        UserProfileEntity userProfileEntity = new UserProfileEntity();
        List<FoodEntity> foodEntities = new ArrayList<>();

        when(userProfileJpaRepository.findByUserUsername(username))
                .thenReturn(Optional.of(userProfileEntity));
        when(foodJpaRepository.findAllByProfileId(userProfileEntity))
                .thenReturn(foodEntities);

        //when
        List<FoodEntity> result = foodRepository.findAllProducts(username);

        //then
        assertEquals(foodEntities, result);
        verify(userProfileJpaRepository, times(1)).findByUserUsername(username);
        verify(foodJpaRepository, times(1)).findAllByProfileId(userProfileEntity);
    }

    @Test
    void saveIntoDatabaseWorksCorrectly() {
        //given
        Food food = new Food();
        food.setName("Apple");
        FoodEntity foodEntity = new FoodEntity();
        foodEntity.setName("Apple");

        when(foodEntityMapper.mapToEntity(food))
                .thenReturn(foodEntity);

        //when
        foodRepository.saveIntoDatabase(food);

        //then
        verify(foodEntityMapper, times(1)).mapToEntity(food);
        verify(foodJpaRepository, times(1)).saveAndFlush(foodEntity);
    }

    @Test
    void findAllProductsThrowsNotFoundExceptionWhenUserNotFoundWorksCorrectly() {
        //given
        String username = "nonExistingUser";

        //when, then
        assertThrows(NotFoundException.class, () -> foodRepository.findAllProducts(username));
        verify(userProfileJpaRepository, times(1)).findByUserUsername(username);
        verify(foodJpaRepository, never()).findAllByProfileId(any());
    }

    @Test
    void deleteAllProductsWorksCorrectly() {
        //given, when
        foodRepository.deleteAllProducts();
        List<FoodEntity> result = foodJpaRepository.findAll();

        //then
        assertThat(result).isEmpty();
        verify(foodJpaRepository, times(1)).deleteAll();
    }
}