package com.appliaction.justAchieveVirtualAssistant.security.user;

import com.appliaction.justAchieveVirtualAssistant.api.dto.UserDTO;
import com.appliaction.justAchieveVirtualAssistant.api.dto.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public String getUsers(
            Model model
    ) {
        model.addAttribute("users", userService.getAllUsers().stream().map(userMapper::map));
        return "users";
    }

    @GetMapping("/edit/{userId}")
    public String showUpdateForm(
            @PathVariable("userId") int userId,
            Model model
    ) {
        UserDTO userDTO = userService.findById(userId).stream().map(userMapper::map).findFirst().orElseThrow();
        model.addAttribute("user", userDTO);
        return "update-user";
    }

    @PostMapping("/update/{userId}")
    public String updateUser(
            @PathVariable("userId") int userId,
            UserDTO user
    ) {
        userService.updateUser(userId, user.getUsername(), user.getEmail());
        return "redirect:/users?success";
    }

    @GetMapping("/delete/{userId}")
    public String deleteUser(
            @PathVariable("userId") int userId
    ) {
        userService.deleteUser(userId);
        return "redirect:/users?delete_success";
    }
}
