package com.emontazysta.controller;


import com.emontazysta.model.LoginRequest;
import com.emontazysta.model.dto.ForgotPasswordDto;
import com.emontazysta.model.dto.ResetPasswordDto;
import com.emontazysta.model.dto.TokenDto;
import com.emontazysta.service.AppUserService;
import com.emontazysta.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.emontazysta.configuration.Constants.API_BASE_CONSTANT;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_BASE_CONSTANT)
public class AuthController {

    private final TokenService tokenService;
    private final AppUserService userService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/gettoken")
    @Operation(description = "Allows authenticate user")
    public TokenDto token(@RequestBody LoginRequest userLogin) throws AuthenticationException {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLogin.username(), userLogin.password()));
        TokenDto tokenDto = new TokenDto();
        tokenDto.setToken(tokenService.generateToken(authentication));
        return tokenDto;
    }

    @PostMapping("/password/forgot")
    @Operation(description = "Generates email to reset the password for the user.")
    public void generateResetPasswordToken(@RequestParam @Valid ForgotPasswordDto forgotPasswordDto) {
        String username = forgotPasswordDto.getUsername();
        userService.generateResetPasswordToken(username);
    }

    @PostMapping("/password/reset")
    @Operation(description = "Allows to reset the password for the user.")
    public void resetPassword(@RequestBody @Valid ResetPasswordDto resetPasswordDto) {
        String resetPasswordToken = resetPasswordDto.getResetPasswordToken();
        String password = resetPasswordDto.getPassword();
        userService.changePassword(resetPasswordToken, password);
    }

}