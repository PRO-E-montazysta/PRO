package com.emontazysta.configuration;

import com.emontazysta.service.impl.AppUserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
               .headers(headers -> headers.frameOptions().disable())
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
