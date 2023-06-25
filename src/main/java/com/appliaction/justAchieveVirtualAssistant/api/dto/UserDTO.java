package com.appliaction.justAchieveVirtualAssistant.api.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @Length(min = 5, max = 25)
    private String username;
    @Email
    private String email;
    private Boolean active;
}
