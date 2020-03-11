package com.example.finra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FinraApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinraApplication.class, args);
    }

}
