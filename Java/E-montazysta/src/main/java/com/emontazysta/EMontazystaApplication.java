package com.emontazysta;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
@RequiredArgsConstructor
public class EMontazystaApplication {

    public static void main(String[] args) {
        SpringApplication.run(EMontazystaApplication.class, args);
    }
}
