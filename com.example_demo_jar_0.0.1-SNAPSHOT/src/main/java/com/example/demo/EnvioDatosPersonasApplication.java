package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class EnvioDatosPersonasApplication {
    public static void main(String[] args) {
        SpringApplication.run(EnvioDatosPersonasApplication.class, args);
        System.out.println("Servidor iniciado en http://localhost:8080");
   
    }
}
