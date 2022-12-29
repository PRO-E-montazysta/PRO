package com.emontazysta;

import com.emontazysta.model.AppUser;
import com.emontazysta.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class EMontazystaApplication {

    public static void main(String[] args) {
        SpringApplication.run(EMontazystaApplication.class, args);
    }


    @Bean
    CommandLineRunner run(AppUserService userService) {
        List<Role> roles = new ArrayList<>();
        roles.add(Role.valueOf("MANAGER"));
        return args -> {
            userService.saveUser(new AppUser(null,"Tomek","Pyrzak","tpyrzak@wp.pl","password","admin",roles), passwordEncoder());

        };
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return  new BCryptPasswordEncoder();
    }



}
