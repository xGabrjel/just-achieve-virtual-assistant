package com.appliaction.justAchieveVirtualAssistant.security.registration;

import com.appliaction.justAchieveVirtualAssistant.security.event.Listener.RegistrationCompleteEventListener;
import com.appliaction.justAchieveVirtualAssistant.security.event.RegistrationCompleteEvent;
import com.appliaction.justAchieveVirtualAssistant.security.registration.password.PasswordResetTokenService;
import com.appliaction.justAchieveVirtualAssistant.security.registration.token.VerificationToken;
import com.appliaction.justAchieveVirtualAssistant.security.registration.token.VerificationTokenServiceImpl;
import com.appliaction.justAchieveVirtualAssistant.security.support.UrlUtil;
import com.appliaction.justAchieveVirtualAssistant.security.user.UserEntity;
import com.appliaction.justAchieveVirtualAssistant.security.user.UserServiceImpl;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/registration")
public class RegistrationController {

    private final UserServiceImpl userService;
    private final ApplicationEventPublisher publisher;
    private final VerificationTokenServiceImpl tokenService;
    private final PasswordResetTokenService passwordResetTokenService;
    private final RegistrationCompleteEventListener eventListener;

    @GetMapping("/registration-form")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new RegistrationRequest());
        return "registration";
    }

    @PostMapping("/register")
    public String registerUser(
            @ModelAttribute("user") RegistrationRequest registration,
            HttpServletRequest request
    ) {
        UserEntity user = userService.registerUser(registration);
        publisher.publishEvent(new RegistrationCompleteEvent(user, UrlUtil.getApplicationUrl(request)));
        return "redirect:/registration/registration-form?success";
    }

    @GetMapping("/verifyEmail")
    public String verifyEmail(
            @RequestParam("token") String token
    ) {
        Optional<VerificationToken> theToken = tokenService.findByToken(token);
        if (theToken.isPresent() && theToken.get().getUser().getActive()) {
            return "redirect:/login?verified";
        }
        String verificationResult = tokenService.validateToken(token);
        return switch (verificationResult.toLowerCase()) {
            case "expired" -> "redirect:/error?expired";
            case "valid" -> "redirect:/login?valid";
            default -> "redirect:/error?invalid";
        };
    }

    @GetMapping("/forgot-password-request")
    public String forgotPasswordForm() {
        return "forgot-password-form";
    }

    @PostMapping("/forgot-password")
    public String resetPasswordRequest(
            HttpServletRequest request, Model model
    ) {
        String email = request.getParameter("email");
        Optional<UserEntity> user = userService.findByEmail(email);
        if (user.isEmpty()) {
            return "redirect:/registration/forgot-password-request?not_found";
        }
        String passwordResetToken = UUID.randomUUID().toString();
        passwordResetTokenService.createPasswordResetTokenForUser(user.get(), passwordResetToken);

        String url = UrlUtil.getApplicationUrl(request) + "/registration/password-reset-form?token="+passwordResetToken;
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

    @PostMapping("/reset-password")
    public String resetPassword(
            HttpServletRequest request
    ) {
        String theToken = request.getParameter("token");
        String password = request.getParameter("password");
        String tokenVerificationResult = passwordResetTokenService.validatePasswordResetToken(theToken);
        if (!tokenVerificationResult.equalsIgnoreCase("valid")) {
            return "redirect:/error?invalid_token";
        }
        Optional<UserEntity> theUser = passwordResetTokenService.findUserByPasswordResetToken(theToken);
        if (theUser.isPresent()) {
            passwordResetTokenService.resetPassword(theUser.get(), password);
            return "redirect:/login?reset_success";
        }
        return "redirect:/error?not_found";
    }
}
