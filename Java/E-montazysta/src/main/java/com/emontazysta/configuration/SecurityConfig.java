package com.emontazysta.configuration;

import com.emontazysta.Role;
import com.emontazysta.model.AppUser;
import com.emontazysta.security.JwtAuthenticationFilter;
import com.emontazysta.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.PostConstruct;
import java.util.Set;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityConfig {
    private static final String[] SWAGGER_AUTH_LIST = {
            // -- swagger ui"
            "/v3/**", "/swagger-ui", "/swagger-ui.html", "/swagger-ui/**"};
    private final AppUserService appuserDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final SecurityProperties securityProperties;
    private final JwtAuthenticationFilter jwtAuthFilter;

    @PostConstruct
    void setUp() {
        appuserDetailsService.add(
                new AppUser(
                        null,
                        "Tomek",
                        "Pyrzak",
                        "tpyrzak@wp.pl",
                        securityProperties.getCloudManagerPassword(),
                        securityProperties.getCloudManagerUsername(),
                        Set.of(Role.ROLE_CLOUD_ADMIN)));
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers(Constants.API_BASE_CONSTANT + "/auth/**").permitAll();
        http.authorizeRequests().antMatchers(SWAGGER_AUTH_LIST).permitAll();
        http.authorizeRequests().anyRequest().authenticated();
        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(appuserDetailsService);
        authProvider.setPasswordEncoder(bCryptPasswordEncoder);
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}


