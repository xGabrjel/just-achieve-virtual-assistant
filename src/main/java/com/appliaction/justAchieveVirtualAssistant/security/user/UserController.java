package com.appliaction.justAchieveVirtualAssistant.security.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @GetMapping
    public String getUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }

    @GetMapping("/edit/{userId}")
    public String showUpdateForm(@PathVariable("userId") int userId, Model model) {
        Optional<UserEntity> user = userService.findById(userId);
        model.addAttribute("user", user.get());
        return "update-user";
    }

    @PostMapping("/update/{userId}")
    public String updateUser(@PathVariable("userId") int userId, UserEntity user) {
        userService.updateUser(userId, user.getUsername(), user.getEmail());
        return "redirect:/users?success";
    }

    @GetMapping("/delete/{userId}")
    public String deleteUser(@PathVariable("userId") int userId) {
        userService.deleteUser(userId);
        return "redirect:/users?delete_success";
    }
}
