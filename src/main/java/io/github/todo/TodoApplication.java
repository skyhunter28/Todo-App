package io.github.todo;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
@SpringBootApplication
public class TodoApplication {
    public static void main(String[] args) {
        SpringApplication.run(TodoApplication.class, args);
    }

}
