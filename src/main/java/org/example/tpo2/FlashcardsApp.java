package org.example.tpo2;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FlashcardsApp {
    public static void main(String[] args) {
//        ApplicationContext context = SpringApplication.run(FlashcardsApp.class, args);
//        FileService service = context.getBean(FileService.class);
//        service.loadEntriesFromFile();
//
//        FlashcardsController controller = context.getBean(FlashcardsController.class);
//        controller.showMenu();


//        ConfigurableApplicationContext context = SpringApplication.run(FlashcardsApp.class, args);
//        EntryRepository entryRepository = context.getBean(EntryRepository.class);
//        Entry word1 = new Entry(1L, "Noc", "Night", "Nacht");
//        Entry word2 = new Entry(2L, "Dzien", "Day", "Tag");
//
//        entryRepository.addEntry(word1);
//        entryRepository.addEntry(word2);

//        ApplicationContext context = SpringApplication.run(FlashcardsApp.class, args);
//        FileService fileService = context.getBean(FileService.class);
//        fileService.loadInitialDataIfEmpty();
//
//        FlashcardsController controller = context.getBean(FlashcardsController.class);
//        controller.showMenu();


        ConfigurableApplicationContext context = SpringApplication.run(FlashcardsApp.class, args);
        EntryRepository repo = context.getBean(EntryRepository.class);

        Entry word1 = new Entry("Kat", "Cat", "Katze");
        Entry word2 = new Entry("Pies", "Dog", "Hund");

        repo.addEntry(word1);
        repo.addEntry(word2);

        word1.setPolish("Kot");

        try {
            repo.update(word1);
        } catch (EntryNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
