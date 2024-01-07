package com.tiktak.expertiseservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
@SpringBootApplication
@EnableJpaRepositories
public class ExpertiseServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExpertiseServiceApplication.class, args);
    }

}
