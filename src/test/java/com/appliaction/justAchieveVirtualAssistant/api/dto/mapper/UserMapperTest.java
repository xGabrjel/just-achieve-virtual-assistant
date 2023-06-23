package com.appliaction.justAchieveVirtualAssistant.api.dto.mapper;

import com.appliaction.justAchieveVirtualAssistant.api.dto.UserDTO;
import com.appliaction.justAchieveVirtualAssistant.domain.User;
import com.appliaction.justAchieveVirtualAssistant.util.DomainFixtures;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserMapperTest {

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Test
    void userMapperWorksCorrectly() {
        //given
        User domain = DomainFixtures.someUser();

        //when
        UserDTO dto = userMapper.map(domain);
        UserDTO nullMapping = userMapper.map(null);

        //then
        assertNull(nullMapping);
        assertEquals(UserDTO.class, dto.getClass());
        assertEquals(domain.getEmail(), dto.getEmail());
        assertEquals(domain.getActive(), dto.getActive());
    }
}