package com.emontazysta.configuration;

import com.emontazysta.enums.Role;
import com.emontazysta.model.*;
import com.emontazysta.service.*;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
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
    private final ToolService toolService;
    private final ToolTypeService toolTypeService;
    private final WarehouseService warehouseService;
    private final ToolReleaseService toolReleaseService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final SecurityProperties securityProperties;



    private RSAKey rsaKey;

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
                        null,
                        Set.of(Role.CLOUD_ADMIN)));

        ToolType toolType1 = new ToolType(null,"Wiertarka Ręczna",1,3,10,null,null,null);
        ToolType toolType2 = new ToolType(null,"Wiertarka Stołowa",1,3,10,null,null,null);
        toolTypeService.add(toolType1);
        toolTypeService.add(toolType2);

        Warehouse warehouse1 = new Warehouse(null,"Magazyn1","jakiś opis","8.00-16.00",null, null,null,null);
        warehouseService.add(warehouse1);

       Tool tool1 =  new Tool(null,"Wiertarka", new Date(),"12345", null,warehouse1,null,toolType1);
       Tool tool2 = new Tool(null,"Młotek", new Date(),"12345",null,warehouse1,null,toolType2);
       Tool tool3 = new Tool(null,"Piła", new Date(),"12345",null,warehouse1,null,toolType2);
       Tool tool4 = new Tool(null,"Wiertarka", new Date(),"12345",null,warehouse1,null,toolType2);
       Tool tool6 = new Tool(null,"Piła", new Date(),"12345",null,null,null,toolType2);
       Tool tool7 = new Tool(null,"Młotek", new Date(),"12345",null,warehouse1,null,toolType2);
       Tool tool5 =  new Tool(null,"Wiertarka", new Date(),"12345", null,warehouse1,null,toolType1);

        toolService.add(tool1);
        toolService.add(tool2);
        toolService.add(tool3);
        toolService.add(tool4);
        toolService.add(tool5);
        toolService.add(tool6);
        toolService.add(tool7);

        ToolRelease toolRelease1 = new ToolRelease(null, LocalDateTime.now(),null,null,null,tool1,null,null);
        ToolRelease toolRelease2 = new ToolRelease(null, LocalDateTime.now(),null,null,null,tool1,null,null);
        ToolRelease toolRelease3 = new ToolRelease(null, LocalDateTime.now(),null,null,null,tool3,null,null);
        ToolRelease toolRelease4 = new ToolRelease(null, LocalDateTime.now(),null,null,null,tool5,null,null);
        ToolRelease toolRelease5 = new ToolRelease(null, LocalDateTime.now(),null,null,null,tool2,null,null);
        ToolRelease toolRelease6 = new ToolRelease(null, LocalDateTime.now(),null,null,null,tool7,null,null);
        toolReleaseService.add(toolRelease1);
        toolReleaseService.add(toolRelease2);
//        toolReleaseService.add(toolRelease3);
        toolReleaseService.add(toolRelease4);
        toolReleaseService.add(toolRelease5);
        toolReleaseService.add(toolRelease6);

    }

    @Bean
    public AuthenticationManager authManager(AppUserService appuserDetailsService) {
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(bCryptPasswordEncoder);
        authProvider.setUserDetailsService(appuserDetailsService);
        return new ProviderManager(authProvider);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(Customizer.withDefaults()); // https://t.ly/bK_b
        http.headers().frameOptions().disable();
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.oauth2ResourceServer().jwt();
        http.authorizeRequests().antMatchers("/api/v1/gettoken",
                                            "/api/v1/h2-console/**",
                                            "/api/v1/password/forgot",
                                            "/api/v1/password/reset")
                                            .permitAll();
        http.authorizeRequests().antMatchers(SWAGGER_AUTH_LIST).permitAll();
        http.authorizeRequests().antMatchers("/actuator/**").permitAll();
        http.authorizeRequests().anyRequest().authenticated();
        return http.build();
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

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "https://emontazysta.pl", "https://dev.emontazysta.pl"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}


