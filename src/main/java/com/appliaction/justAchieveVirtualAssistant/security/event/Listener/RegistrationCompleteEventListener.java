package com.appliaction.justAchieveVirtualAssistant.security.event.Listener;

import com.appliaction.justAchieveVirtualAssistant.domain.User;
import com.appliaction.justAchieveVirtualAssistant.security.event.RegistrationCompleteEvent;
import com.appliaction.justAchieveVirtualAssistant.security.registration.token.VerificationTokenService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

@Component
@Setter
@RequiredArgsConstructor
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    private final VerificationTokenService tokenService;
    private final JavaMailSender mailSender;
    private User user;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        user = event.getUser();
        String vToken = UUID.randomUUID().toString();
        tokenService.saveVerificationTokenForUser(user, vToken);
        String url = event.getConfirmationUrl() + "/registration/verifyEmail?token=" + vToken;
        try {
            sendVerificationEmail(url);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendVerificationEmail(String url) throws MessagingException, UnsupportedEncodingException {
        String subject = "Email Verification";
        String senderName = "Users Verification Service";
        String mailContent = "<p> Hi " + user.getUsername() + "!" + " </p>" +
                "<p><br>Thank you for registering with us. " +
                "Please, follow the link below to complete your registration.</p>" +
                "<a href=\"" + url + "\"><b>Verify your email to activate your account</b></a>" +
                "<p> <br>Thank you <br> JustAchieve! Virtual Assistant";
        emailMessage(subject, senderName, mailContent, mailSender, user);
    }

    public void sendPasswordResetVerificationEmail(String url) throws MessagingException, UnsupportedEncodingException {
        String subject = "Password Reset Request Verification";
        String senderName = "Users Verification Service";
        String mailContent = "<p> Hi " + user.getUsername() + "!" + " </p>" +
                "<p><br>You recently requested to reset your password. " +
                "Please, follow the link below to complete the action.</p>" +
                "<a href=\"" + url + "\"><b>Reset password</b></a>" +
                "<p> <br>JustAchieve! Virtual Assistant";
        emailMessage(subject, senderName, mailContent, mailSender, user);
    }

    private static void emailMessage(
            String subject,
            String senderName,
            String mailContent,
            JavaMailSender mailSender,
            User theUser
    )
            throws MessagingException,
            UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("justachievevirtualassistant@onet.pl", senderName);
        messageHelper.setTo(theUser.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        mailSender.send(message);
    }
}
