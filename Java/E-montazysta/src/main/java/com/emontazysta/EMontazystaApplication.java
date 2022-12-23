package com.emontazysta;

import com.emontazysta.model.AppUser;
import com.emontazysta.service.AppUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class EMontazystaApplication {

    public static void main(String[] args) {
        SpringApplication.run(EMontazystaApplication.class, args);
    }
    @Bean
    CommandLineRunner run(AppUserService userService) {
        List<Role> roles = new ArrayList<>();
        roles.add(Role.valueOf("MANAGER"));
        return args -> {
            userService.saveUser(new AppUser(null,"Tomek","Pyrzak","tpyrzak@wp.pl","admin","admin",roles));

        };
    }
}
