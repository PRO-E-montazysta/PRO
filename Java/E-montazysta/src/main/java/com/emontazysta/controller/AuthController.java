package com.emontazysta.controller;


import com.emontazysta.model.AppUser;
import com.emontazysta.model.LoginRequest;
import com.emontazysta.model.dto.TokenDto;
import com.emontazysta.security.JwtService;
import com.emontazysta.service.AppUserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.impl.DefaultClaims;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.stream.Collectors;

import static com.emontazysta.configuration.Constants.API_BASE_CONSTANT;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_BASE_CONSTANT + "/auth")
public class AuthController {


    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final AppUserService appUserService;


    @PostMapping("/gettoken")
    @Operation(description = "Allows authenticate user")
    public ResponseEntity<TokenDto> token(@RequestBody LoginRequest userLogin) throws AuthenticationException {
        TokenDto responseBody;
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userLogin.username(),
                            userLogin.password()
                    )
            );
            AppUser user = appUserService.findByUsername(userLogin.username());

            Claims claims = new DefaultClaims()
                    .setSubject(authentication.getName())
                    .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000));
            claims.put("authorities", authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(",")));

            responseBody = TokenDto.builder()
                    .token(jwtService.generateToken(claims, user))
                    .build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(responseBody);
    }


}