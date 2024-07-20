package br.com.craftlife.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CraftlifeApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CraftlifeApiApplication.class, args);
    }

}
