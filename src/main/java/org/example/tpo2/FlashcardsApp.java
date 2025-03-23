package org.example.tpo2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class FlashcardsApp {
    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(FlashcardsApp.class, args);
        EntryService service = context.getBean(EntryService.class);
        service.loadInitialDataIfEmpty();

        FlashcardsController controller = context.getBean(FlashcardsController.class);
        controller.showMenu();
    }
}
