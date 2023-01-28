package com.emontazysta.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ForgotPasswordDto {

    @NotBlank
    String username;
}
