package com.appliaction.justAchieveVirtualAssistant.business;

import com.appliaction.justAchieveVirtualAssistant.api.dto.FoodDTO;
import com.appliaction.justAchieveVirtualAssistant.domain.Food;
import com.appliaction.justAchieveVirtualAssistant.domain.User;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity.FoodEntity;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.mapper.FoodEntityMapper;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.FoodRepository;
import com.appliaction.justAchieveVirtualAssistant.security.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FoodServiceTest {

    @InjectMocks
    private FoodService foodService;

    @Mock
    private FoodRepository foodRepository;

    @Mock
    private FoodEntityMapper foodEntityMapper;

    @Mock
    private UserRepository userRepository;

    @Test
    void saveProductWorksCorrectly() {
        //given
        FoodDTO foodDTO = new FoodDTO();
        foodDTO.setName("Apple");

        String username = "user1";
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(mock(User.class)));

        //when
        foodService.saveProduct(foodDTO, username);

        //then
        verify(foodRepository, times(1)).saveIntoDatabase(any(Food.class));
    }

    @Test
    void findAllByUsernameWorksCorrectly() {
        //given
        String username = "user1";
        List<FoodEntity> foodEntities = new ArrayList<>();
        foodEntities.add(mock(FoodEntity.class));
        when(foodRepository.findAllProducts(username)).thenReturn(foodEntities);

        Food food = mock(Food.class);
        when(foodEntityMapper.mapFromEntity(any(FoodEntity.class))).thenReturn(food);

        //when
        List<Food> result = foodService.findAllByUsername(username);

        //then
        assertEquals(foodEntities.size(), result.size());
        assertTrue(result.contains(food));
    }

    @Test
    void deleteAllWorksCorrectly() {
        //when
        foodService.deleteAll();

        //then
        verify(foodRepository, times(1)).deleteAllProducts();
    }
}