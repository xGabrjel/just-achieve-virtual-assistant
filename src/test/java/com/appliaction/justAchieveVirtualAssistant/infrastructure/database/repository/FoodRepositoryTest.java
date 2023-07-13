package com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository;

import com.appliaction.justAchieveVirtualAssistant.domain.Food;
import com.appliaction.justAchieveVirtualAssistant.domain.exception.NotFoundException;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity.FoodEntity;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.mapper.FoodEntityMapper;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.jpa.FoodJpaRepository;
import com.appliaction.justAchieveVirtualAssistant.security.user.UserEntity;
import com.appliaction.justAchieveVirtualAssistant.security.user.UserJpaRepository;
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
    private UserJpaRepository userJpaRepository;

    @Test
    void findAllProductsShouldReturnListOfFoodEntities() {
        // given
        String username = "testUser";
        UserEntity userEntity = new UserEntity();
        when(userJpaRepository.findByUsername(username)).thenReturn(Optional.of(userEntity));

        List<FoodEntity> foodEntities = new ArrayList<>();
        when(foodJpaRepository.findAllByUserId(userEntity)).thenReturn(foodEntities);

        // when
        List<FoodEntity> result = foodRepository.findAllProducts(username);

        // then
        assertEquals(foodEntities, result);
        verify(userJpaRepository, times(1)).findByUsername(username);
        verify(foodJpaRepository, times(1)).findAllByUserId(userEntity);
    }

    @Test
    void saveIntoDbWorksCorrectly() {
        //given
        Food food = new Food();
        food.setName("Apple");

        FoodEntity foodEntity = new FoodEntity();
        foodEntity.setName("Apple");

        when(foodEntityMapper.mapToEntity(food)).thenReturn(foodEntity);

        //when
        foodRepository.saveIntoDatabase(food);

        //then
        verify(foodEntityMapper, times(1)).mapToEntity(food);
        verify(foodJpaRepository, times(1)).saveAndFlush(foodEntity);
    }

    @Test
    void findAllProductsThrowsNotFoundExceptionWhenUserNotFound() {
        //given
        String username = "nonExistingUser";

        //when, then
        assertThrows(NotFoundException.class, () -> foodRepository.findAllProducts(username));
        verify(userJpaRepository, times(1)).findByUsername(username);
        verify(foodJpaRepository, never()).findAllByUserId(any());
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