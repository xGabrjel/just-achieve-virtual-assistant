package com.appliaction.justAchieveVirtualAssistant.security.user;

import com.appliaction.justAchieveVirtualAssistant.api.dto.UserDTO;
import com.appliaction.justAchieveVirtualAssistant.api.dto.mapper.UserMapper;
import com.appliaction.justAchieveVirtualAssistant.domain.User;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.mockito.Mockito.when;

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
        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("users"))
                .andExpect(MockMvcResultMatchers.view().name("users"));
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
        mockMvc.perform(MockMvcRequestBuilders.get("/users/edit/{userId}", userId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("user"))
                .andExpect(MockMvcResultMatchers.model().attribute("user", userDTO))
                .andExpect(MockMvcResultMatchers.view().name("update-user"));
    }

    @Test
    void updateUserWorksCorrectly() throws Exception {
        //given
        int userId = 1;

        //when, then
        mockMvc.perform(MockMvcRequestBuilders.post("/users/update/{userId}", userId))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/users?success"));
    }

    @Test
    void deleteUserWorksCorrectly() throws Exception {
        //given
        int userId = 1;

        //when, then
        mockMvc.perform(MockMvcRequestBuilders.get("/users/delete/{userId}", userId))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/users?delete_success"));
    }
}