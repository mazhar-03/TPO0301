package org.example.tpo2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class FlashcardsApp {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(FlashcardsApp.class, args);
        FileService service = context.getBean(FileService.class);
        service.loadEntriesFromFile();

        FlashcardsController controller = context.getBean(FlashcardsController.class);
        controller.showMenu();
    }

}
