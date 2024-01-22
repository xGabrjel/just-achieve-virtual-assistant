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

import static com.appliaction.justAchieveVirtualAssistant.api.controller.RegistrationController.ROOT;

@Controller
@AllArgsConstructor
@RequestMapping(ROOT)
public class RegistrationController {

    static final String ROOT = "/registration";
    static final String SHOW_REGISTRATION_FORM = "/registration-form";
    static final String REGISTER_USER = "/new-user-registration";
    static final String VERIFY_EMAIL = "/email-verifier";
    static final String FORGOT_PASSWORD_REQUEST = "/forgot-password-request";
    static final String FORGOT_PASSWORD_RESET = "/forgot-password-reset";
    static final String PASSWORD_RESET_FORM = "/password-reset-form";
    static final String PASSWORD_RESET = "/password-reset";

    private final UserService userService;
    private final ApplicationEventPublisher publisher;
    private final VerificationTokenService tokenService;
    private final PasswordResetTokenService passwordResetTokenService;
    private final RegistrationCompleteEventListener eventListener;

    @GetMapping(SHOW_REGISTRATION_FORM)
    public String showRegistrationForm(
            Model model
    ) {
        model.addAttribute("user", new RegistrationRequest());
        return "registration";
    }

    @PostMapping(REGISTER_USER)
    public String registerUser(
            @ModelAttribute("user") RegistrationRequest registration,
            HttpServletRequest request
    ) {
        publisher.publishEvent(new RegistrationCompleteEvent(
                userService.registerUser(registration),
                UrlUtil.getApplicationUrl(request)));
        return "redirect:/registration/registration-form?success";
    }

    @GetMapping(FORGOT_PASSWORD_REQUEST)
    public String forgotPasswordForm() {
        return "forgot-password-form";
    }

    @GetMapping(PASSWORD_RESET_FORM)
    public String passwordResetForm(
            @RequestParam("token") String token,
            Model model
    ) {
        model.addAttribute("token", token);
        return "password-reset-form";
    }

    @GetMapping(VERIFY_EMAIL)
    public String verifyEmail(
            @RequestParam("token") String token
    ) {
        Optional<VerificationToken> theToken = tokenService.findByToken(token);
        if (theToken.isPresent() && theToken.get().getUser().getActive()) {
            return "redirect:/login?verified";
        }
        return switch (tokenService.validateToken(token).toLowerCase()) {
            case "expired" -> "redirect:/error?expired";
            case "valid" -> "redirect:/login?valid";
            default -> "redirect:/error?invalid";
        };
    }

    @PostMapping(FORGOT_PASSWORD_RESET)
    public String resetPasswordRequest(
            HttpServletRequest request,
            Model model
    ) {
        Optional<User> userOptional = userService.findByEmail(request.getParameter("email"));
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user != null) {
                String passwordResetToken = UUID.randomUUID().toString();
                passwordResetTokenService.createPasswordResetTokenForUser(user, passwordResetToken);
                try {
                    eventListener.sendPasswordResetVerificationEmail(
                            UrlUtil.getApplicationUrl(request) + "/registration/password-reset-form?token=" + passwordResetToken, user.getUsername());
                } catch (MessagingException | UnsupportedEncodingException e) {
                    model.addAttribute("error", e.getMessage());
                }
                return "redirect:/registration/forgot-password-request?success";
            }
        }
            return "redirect:/registration/forgot-password-request?not_found";
    }

    @PostMapping(PASSWORD_RESET)
    public String resetPassword(
            HttpServletRequest request
    ) {
        String theToken = request.getParameter("token");
        String password = request.getParameter("password");
        if (!passwordResetTokenService.validatePasswordResetToken(theToken).equalsIgnoreCase("valid")) {
            return "redirect:/error?invalid_token";
        }
        Optional<User> theUser = passwordResetTokenService.findUserByPasswordResetToken(theToken);
        if (theUser.isPresent()) {
            passwordResetTokenService.resetPassword(theUser.get(), password);
            return "redirect:/login?reset_success";
        }
        return "redirect:/error?not_found";
    }
}
