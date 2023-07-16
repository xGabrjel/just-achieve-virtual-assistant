package com.appliaction.justAchieveVirtualAssistant.security.user;

import com.appliaction.justAchieveVirtualAssistant.configuration.AbstractIT;
import com.appliaction.justAchieveVirtualAssistant.domain.User;
import com.appliaction.justAchieveVirtualAssistant.security.registration.RegistrationRequest;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceITTest extends AbstractIT {

    private final UserService userService;

    @Test
    void registerUserWorksCorrectly() {
        //given
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setUsername("test");
        registrationRequest.setEmail("test@o2.pl");
        registrationRequest.setPassword("test123");

        //when
        User result = userService.registerUser(registrationRequest);

        //then
        assertNotNull(result);
        assertEquals(registrationRequest.getUsername(), result.getUsername());
        assertEquals(registrationRequest.getEmail(), result.getEmail());
        assertNotEquals(registrationRequest.getPassword(), result.getPassword());
        assertNotEquals(Collections.EMPTY_LIST, result.getRoles());
    }

    @Test
    void getAllUsersWorksCorrectly() {
        //given, when
        List<User> result = userService.getAllUsers();

        //then
        assertNotNull(result);
        assertThat(result).isNotEmpty();
    }

    @Test
    void findByEmailWorksCorrectly() {
        //given
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setUsername("anotherUsername1");
        registrationRequest.setEmail("anotherEmail1@o2.pl");
        registrationRequest.setPassword("anotherPassword123");
        userService.registerUser(registrationRequest);

        String adminUsername = "anotherUsername1";
        String adminEmail = "anotherEmail1@o2.pl";
        int adminId = 5;

        //when
        Optional<User> result = userService.findByEmail(adminEmail);

        //then
        assertThat(result).isNotEmpty();
        assertThat(result).isNotNull();
        assertEquals(adminUsername, result.get().getUsername());
        assertEquals(adminId, result.get().getUserId());
    }

    @Test
    void findByIdWorksCorrectly() {
        //given
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setUsername("anotherUsername2");
        registrationRequest.setEmail("anotherEmail2@o2.pl");
        registrationRequest.setPassword("anotherPassword123");
        userService.registerUser(registrationRequest);

        String adminUsername = "anotherUsername2";
        String adminEmail = "anotherEmail2@o2.pl";
        int adminId = 3;

        //when
        Optional<User> result = userService.findById(adminId);

        //then
        assertThat(result).isNotEmpty();
        assertThat(result).isNotNull();
        assertEquals(adminUsername, result.get().getUsername());
        assertEquals(adminEmail, result.get().getEmail());
    }

    @Test
    void findByUsernameWorksCorrectly() {
        //given
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setUsername("anotherUsername3");
        registrationRequest.setEmail("anotherEmail3@o2.pl");
        registrationRequest.setPassword("anotherPassword123");
        userService.registerUser(registrationRequest);

        String adminUsername = "anotherUsername3";
        String adminEmail = "anotherEmail3@o2.pl";
        int adminId = 2;

        //when
        Optional<User> result = userService.findByUsername(adminUsername);

        //then
        assertThat(result).isNotEmpty();
        assertThat(result).isNotNull();
        assertEquals(adminEmail, result.get().getEmail());
        assertEquals(adminId, result.get().getUserId());
    }

    @Test
    void updateUserWorksCorrectly() {
        //given
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setUsername("username");
        registrationRequest.setEmail("email@o2.pl");
        registrationRequest.setPassword("password123");
        User user = userService.registerUser(registrationRequest);

        String newUsername = "newUsername";
        String newEmail = "newEmail@o2.pl";

        //when
        userService.updateUser(user.getUserId(), newUsername, newEmail);
        User result = userService.findById(user.getUserId()).orElseThrow();

        //then
        assertEquals(result.getUsername(), newUsername);
        assertEquals(result.getEmail(), newEmail);
    }

    @Test
    void deleteUserWorksCorrectly() {
        //given
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setUsername("anotherUsername");
        registrationRequest.setEmail("anotherEmail@o2.pl");
        registrationRequest.setPassword("anotherPassword123");
        User user = userService.registerUser(registrationRequest);

        //when, then
        userService.deleteUser(user.getUserId());
        assertEquals(Optional.empty(), userService.findById(user.getUserId()));
    }

    @Test
    void userEntityCustomConstructorWorksCorrectly() {
        //given
        UserEntity user = new UserEntity("username", "email@o2.pl", "password", List.of(new RoleEntity("USER")));

        //when, then
        assertNotNull(user);
    }
}

