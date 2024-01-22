package com.appliaction.justAchieveVirtualAssistant.business;

import com.appliaction.justAchieveVirtualAssistant.api.dto.FoodDTO;
import com.appliaction.justAchieveVirtualAssistant.api.dto.mapper.FoodMapper;
import com.appliaction.justAchieveVirtualAssistant.domain.Food;
import com.appliaction.justAchieveVirtualAssistant.domain.UserProfile;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.entity.FoodEntity;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.mapper.FoodEntityMapper;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.FoodRepository;
import com.appliaction.justAchieveVirtualAssistant.infrastructure.database.repository.UserProfileRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

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
    private UserProfileRepository userProfileRepository;
    @Mock
    private FoodMapper foodMapper;

    @Test
    void saveProductWorksCorrectly() {
        //given
        String username = "user1";
        FoodDTO foodDTO = new FoodDTO();
        foodDTO.setName("Apple");

        when(userProfileRepository.findByUserUsername(username))
                .thenReturn(mock(UserProfile.class));

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
        Food food = mock(Food.class);
        FoodDTO foodDTO = mock(FoodDTO.class);

        when(foodRepository.findAllProducts(username))
                .thenReturn(foodEntities);
        when(foodEntityMapper.mapFromEntity(any(FoodEntity.class)))
                .thenReturn(food);
        when(foodMapper.map(any(Food.class)))
                .thenReturn(foodDTO);

        //when
        List<FoodDTO> result = foodService.findAllDTOByUsername(username);

        //then
        assertEquals(foodEntities.size(), result.size());
        assertTrue(result.contains(foodDTO));
    }

    @Test
    void deleteAllWorksCorrectly() {
        //when
        foodService.deleteAll();

        //then
        verify(foodRepository, times(1)).deleteAllProducts();
    }
}