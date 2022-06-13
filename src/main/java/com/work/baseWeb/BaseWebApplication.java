package com.work.baseWeb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BaseWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaseWebApplication.class, args);
    }

}
