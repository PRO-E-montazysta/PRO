package com.emontazysta.configuration;

import com.emontazysta.service.AppUserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig  {
        private final AppUserDetailsServiceImpl userDetailsService;

//    private final BCryptPasswordEncoder bCryptPasswordEncoder;


   @Bean
   public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
       return http
               .csrf(csrf -> csrf.disable())
               .authorizeHttpRequests(auth ->
                       auth.anyRequest().authenticated())
               .userDetailsService(userDetailsService)
//               .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
               .sessionManagement(session ->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
               .httpBasic(Customizer.withDefaults())
               .build();
   }

   @Bean
    PasswordEncoder passwordEncoder() {
       return NoOpPasswordEncoder.getInstance(); // TODO zmienićna bcrypt encoder
   }






}
