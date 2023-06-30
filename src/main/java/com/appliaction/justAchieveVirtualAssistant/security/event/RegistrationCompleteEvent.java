package com.appliaction.justAchieveVirtualAssistant.security.event;

import com.appliaction.justAchieveVirtualAssistant.security.user.UserEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class RegistrationCompleteEvent extends ApplicationEvent {

    private UserEntity user;
    private String confirmationUrl;
    public RegistrationCompleteEvent(UserEntity user, String confirmationUrl) {
        super(user);
        this.user = user;
        this.confirmationUrl = confirmationUrl;
    }
}
