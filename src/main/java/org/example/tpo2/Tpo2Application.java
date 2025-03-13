package org.example.tpo2;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class Tpo2Application {
    public static void main(String[] args) {
        SpringApplication.run(Tpo2Application.class, args);
    }
    @Bean
    CommandLineRunner run(ApplicationContext context) {
        return args -> {
            FileService fileService = context.getBean(FileService.class);
            fileService.loadEntriesFromFile();

            FlashcardsController controller = context.getBean(FlashcardsController.class);
            controller.showMenu();
        };
    }
}
