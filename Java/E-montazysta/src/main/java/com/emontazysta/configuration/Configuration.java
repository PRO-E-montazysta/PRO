package com.emontazysta.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@org.springframework.context.annotation.Configuration
public class Configuration {

   @Bean
   public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
       http.httpBasic().disable();
       return http.build();
   }
}
