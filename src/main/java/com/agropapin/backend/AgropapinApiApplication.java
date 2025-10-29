package com.agropapin.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AgropapinApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AgropapinApiApplication.class, args);
    }

}
