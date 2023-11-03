package com.appliaction.justAchieveVirtualAssistant.api.controller;

import com.appliaction.justAchieveVirtualAssistant.domain.User;
import com.appliaction.justAchieveVirtualAssistant.domain.VerificationToken;
import com.appliaction.justAchieveVirtualAssistant.security.event.Listener.RegistrationCompleteEventListener;
import com.appliaction.justAchieveVirtualAssistant.security.event.RegistrationCompleteEvent;
import com.appliaction.justAchieveVirtualAssistant.security.registration.RegistrationRequest;
import com.appliaction.justAchieveVirtualAssistant.security.registration.password.PasswordResetTokenService;
import com.appliaction.justAchieveVirtualAssistant.security.registration.token.VerificationTokenService;
import com.appliaction.justAchieveVirtualAssistant.security.support.UrlUtil;
import com.appliaction.justAchieveVirtualAssistant.security.user.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Optional;
import java.util.UUID;

@Controller
@AllArgsConstructor
@RequestMapping("/registration")
public class RegistrationController {

    private final UserService userService;
    private final ApplicationEventPublisher publisher;
    private final VerificationTokenService tokenService;
    private final PasswordResetTokenService passwordResetTokenService;
    private final RegistrationCompleteEventListener eventListener;

    @GetMapping("/registration-form")
    public String showRegistrationForm(
            Model model
    ) {
        model.addAttribute("user", new RegistrationRequest());
        return "registration";
    }

    @PostMapping("/new-user-registration")
    public String registerUser(
            @ModelAttribute("user") RegistrationRequest registration,
            HttpServletRequest request
    ) {
        User user = userService.registerUser(registration);
        publisher.publishEvent(new RegistrationCompleteEvent(user, UrlUtil.getApplicationUrl(request)));
        return "redirect:/registration/registration-form?success";
    }

    @GetMapping("/email-verifier")
    public String verifyEmail(
            @RequestParam("token") String token
    ) {
        Optional<VerificationToken> theToken = tokenService.findByToken(token);
        if (isPresentAndActive(theToken)) {
            return "redirect:/login?verified";
        }
        String verificationResult = tokenService.validateToken(token);
        return switch (verificationResult.toLowerCase()) {
            case "expired" -> "redirect:/error?expired";
            case "valid" -> "redirect:/login?valid";
            default -> "redirect:/error?invalid";
        };
    }

    private static boolean isPresentAndActive(Optional<VerificationToken> theToken) {
        return theToken.isPresent() && theToken.get().getUser().getActive();
    }

    @GetMapping("/forgot-password-request")
    public String forgotPasswordForm() {
        return "forgot-password-form";
    }

    @PostMapping("/forgot-password-reset")
    public String resetPasswordRequest(
            HttpServletRequest request,
            Model model
    ) {
        String email = request.getParameter("email");
        Optional<User> user = userService.findByEmail(email);
        if (user.isEmpty()) {
            return "redirect:/registration/forgot-password-request?not_found";
        }
        String passwordResetToken = UUID.randomUUID().toString();
        passwordResetTokenService.createPasswordResetTokenForUser(user.get(), passwordResetToken);

        String url = UrlUtil.getApplicationUrl(request) + "/registration/password-reset-form?token=" + passwordResetToken;
        try {
            eventListener.sendPasswordResetVerificationEmail(url);
        } catch (MessagingException | UnsupportedEncodingException e) {
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/registration/forgot-password-request?success";
    }

    @GetMapping("/password-reset-form")
    public String passwordResetForm(
            @RequestParam("token") String token,
            Model model
    ) {
        model.addAttribute("token", token);
        return "password-reset-form";
    }

    @PostMapping("/password-reset")
    public String resetPassword(
            HttpServletRequest request
    ) {
        String theToken = request.getParameter("token");
        String password = request.getParameter("password");
        String tokenVerificationResult = passwordResetTokenService.validatePasswordResetToken(theToken);
        if (isNotValid(tokenVerificationResult)) {
            return "redirect:/error?invalid_token";
        }
        Optional<User> theUser = passwordResetTokenService.findUserByPasswordResetToken(theToken);
        if (theUser.isPresent()) {
            passwordResetTokenService.resetPassword(theUser.get(), password);
            return "redirect:/login?reset_success";
        }
        return "redirect:/error?not_found";
    }

    private static boolean isNotValid(String tokenVerificationResult) {
        return !tokenVerificationResult.equalsIgnoreCase("valid");
    }
}
