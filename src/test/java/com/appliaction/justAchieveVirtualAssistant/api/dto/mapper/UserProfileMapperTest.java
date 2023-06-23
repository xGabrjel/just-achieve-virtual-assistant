package com.appliaction.justAchieveVirtualAssistant.api.dto.mapper;

import com.appliaction.justAchieveVirtualAssistant.api.dto.UserProfileDTO;
import com.appliaction.justAchieveVirtualAssistant.domain.UserProfile;
import com.appliaction.justAchieveVirtualAssistant.util.DomainFixtures;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserProfileMapperTest {

    private final UserProfileMapper userProfileMapper = Mappers.getMapper(UserProfileMapper.class);

    @Test
    void userProfileMapperWorksCorrectly() {
        //given
        UserProfile domain = DomainFixtures.someUserProfile();

        //when
        UserProfileDTO dto = userProfileMapper.map(domain);
        UserProfileDTO nullMapping = userProfileMapper.map(null);

        //then
        assertNull(nullMapping);
        assertEquals(domain.getAge(), dto.getAge());
        assertEquals(domain.getSex(), dto.getSex());
        assertEquals(domain.getName(), dto.getName());
        assertEquals(domain.getPhone(), dto.getPhone());
        assertEquals(domain.getWeight(), dto.getWeight());
        assertEquals(domain.getHeight(), dto.getHeight());
        assertEquals(UserProfileDTO.class, dto.getClass());
        assertEquals(domain.getSurname(), dto.getSurname());
    }
}