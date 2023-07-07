package com.appliaction.justAchieveVirtualAssistant.api.controller;

import com.appliaction.justAchieveVirtualAssistant.api.dto.UserDTO;
import com.appliaction.justAchieveVirtualAssistant.api.dto.mapper.UserMapper;
import com.appliaction.justAchieveVirtualAssistant.domain.User;
import com.appliaction.justAchieveVirtualAssistant.security.user.UserService;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class UserControllerTest {

    private final MockMvc mockMvc;
    @MockBean
    private final UserService userService;
    @MockBean
    private final UserMapper userMapper;

    @Test
    void getUsersPageWorksCorrectly() throws Exception {
        //given, when, then
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("users"))
                .andExpect(view().name("users"));
    }

    @Test
    void showUpdateFormWorksCorrectly() throws Exception {
        //given
        int userId = 1;

        User user = new User();
        user.setUserId(1);
        user.setUsername("test");
        user.setEmail("test@o2.pl");
        user.setActive(true);

        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(1);
        userDTO.setUsername("test");
        userDTO.setEmail("test@o2.pl");
        userDTO.setActive(true);

        when(userService.findById(userDTO.getUserId())).thenReturn(Optional.of(user));
        when(userMapper.map(user)).thenReturn(userDTO);

        //when, then
        mockMvc.perform(get("/users/edit/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("user", userDTO))
                .andExpect(view().name("update-user"));
    }

    @Test
    void updateUserWorksCorrectly() throws Exception {
        //given
        int userId = 1;

        //when, then
        mockMvc.perform(post("/users/update/{userId}", userId))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/users?success"));
    }

    @Test
    void deleteUserWorksCorrectly() throws Exception {
        //given
        int userId = 1;

        //when, then
        mockMvc.perform(get("/users/delete/{userId}", userId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users?delete_success"));
    }
}