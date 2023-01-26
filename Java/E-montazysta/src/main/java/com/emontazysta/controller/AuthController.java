package com.emontazysta.controller;


import com.emontazysta.model.LoginRequest;
import com.emontazysta.model.dto.TokenDto;
import com.emontazysta.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.emontazysta.configuration.Constants.API_BASE_CONSTANT;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_BASE_CONSTANT)
public class AuthController {

    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/gettoken")
    @Operation(description = "Allows authenticate user")
    public TokenDto token(@RequestBody LoginRequest userLogin) throws AuthenticationException {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLogin.username(), userLogin.password()));
        TokenDto tokenDto = new TokenDto();
        tokenDto.setToken(tokenService.generateToken(authentication));
        return tokenDto;
    }


}