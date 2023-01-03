package com.emontazysta.configuration;

import com.emontazysta.service.AppUserService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig  {
       private final AppUserService appuserDetailsService;
       private final BCryptPasswordEncoder bCryptPasswordEncoder;

       private static final String[] SWAGGER_AUTH_LIST = {
            // -- swagger ui"
            "/v3/**",
            "/swagger-ui",
            "/swagger-ui.html",
            "/swagger-ui/**"
       };
       private RSAKey rsaKey;

       @Bean
       public AuthenticationManager authManager(AppUserService appuserDetailsService) {
           var authProvider = new DaoAuthenticationProvider();
           authProvider.setPasswordEncoder(bCryptPasswordEncoder);
           authProvider.setUserDetailsService(appuserDetailsService);
           return new ProviderManager(authProvider);
       }

       @Bean
       public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
           return http
                   .headers(headers -> headers.frameOptions().disable())
                   .csrf(AbstractHttpConfigurer::disable)
                   .authorizeHttpRequests( auth -> auth
                           .antMatchers("/api/v1/gettoken","/api/v1/h2-console/**").permitAll()
                           .antMatchers("/api/v1/users/create").hasAnyRole("ADMIN","CLOUD_ADMIN")
                           .antMatchers(SWAGGER_AUTH_LIST).permitAll()
                           .anyRequest().authenticated()
                   )
                   .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                   .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                   .build();
       }

       @Bean
       public JWKSource<SecurityContext> jwkSource() {
           rsaKey = Jwks.generateRsa();
           JWKSet jwkSet = new JWKSet(rsaKey);
           return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
       }

       @Bean
       JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwks) {
           return new NimbusJwtEncoder(jwks);
       }

       @Bean
       JwtDecoder jwtDecoder() throws JOSEException {
           return NimbusJwtDecoder.withPublicKey(rsaKey.toRSAPublicKey()).build();
       }

   }


