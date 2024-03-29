package com.emontazysta.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordDto {

    @NotBlank
    String resetPasswordToken;
    @NotBlank
    String password;
}
