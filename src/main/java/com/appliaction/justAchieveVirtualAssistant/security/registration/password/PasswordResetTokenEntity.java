package com.appliaction.justAchieveVirtualAssistant.security.registration.password;

import com.appliaction.justAchieveVirtualAssistant.security.support.TokenExpirationTime;
import com.appliaction.justAchieveVirtualAssistant.security.user.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "password_reset_token")
public class PasswordResetTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "password_id")
    private Long id;

    @Column(name = "token")
    private String token;

    @Column(name = "expiration_time")
    private Date expirationTime;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public PasswordResetTokenEntity(String token, UserEntity user) {
        this.token = token;
        this.user = user;
        this.expirationTime = TokenExpirationTime.getExpirationTime();
    }
}
