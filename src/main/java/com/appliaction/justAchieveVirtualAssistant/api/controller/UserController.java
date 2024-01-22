package com.appliaction.justAchieveVirtualAssistant.api.controller;

import com.appliaction.justAchieveVirtualAssistant.api.dto.UserDTO;
import com.appliaction.justAchieveVirtualAssistant.security.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.appliaction.justAchieveVirtualAssistant.api.controller.UserController.ROOT;

@Controller
@RequiredArgsConstructor
@RequestMapping(ROOT)
public class UserController {

    static final String ROOT = "/users";
    static final String SHOW_UPDATE_FORM = "/{userId}";
    static final String UPDATE_USER = "/updates/{userId}";
    static final String DELETE_USER = "/deletion/{userId}";

    private final UserService userService;

    @GetMapping
    public String getUsers(
            Model model
    ) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }

    @GetMapping(SHOW_UPDATE_FORM)
    public String showUpdateForm(
            @PathVariable("userId") int userId,
            Model model
    ) {
        model.addAttribute("user", userService.findById(userId));
        return "update-user";
    }

    @PostMapping(UPDATE_USER)
    public String updateUser(
            @PathVariable("userId") int userId,
            UserDTO user
    ) {
        userService.updateUser(userId, user.getUsername(), user.getEmail());
        return "redirect:/users?success";
    }

    @GetMapping(DELETE_USER)
    public String deleteUser(
            @PathVariable("userId") int userId
    ) {
        userService.deleteUser(userId);
        return "redirect:/users?delete_success";
    }
}
