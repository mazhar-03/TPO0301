package org.example.tpo2;

import org.example.tpo2.profiles.IWordDisplayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

@Service
public class FlashcardsController {

    private final EntryRepository entryRepository;
    private final FileService fileService;
    private final IWordDisplayService iWordDisplayService;
    private final Scanner scanner = new Scanner(System.in);

    @Autowired
    public FlashcardsController(EntryRepository entryRepository, FileService fileService,IWordDisplayService IWordDisplayService) {
        this.entryRepository = entryRepository;
        this.fileService = fileService;
        this.iWordDisplayService = IWordDisplayService;
    }

    public void showMenu() {
        while (true) {
            System.out.println("\n=== Flashcards Application ===");
            System.out.println("1. Add new word");
            System.out.println("2. Show all words");
            System.out.println("3. Take a quiz");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  

            switch (choice) {
                case 1 -> addNewWord();
                case 2 -> showAllWords();
                case 3 -> startQuiz();
                case 4 -> {
                    System.out.println("Exiting application...");
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void addNewWord() {
        System.out.print("Enter Polish word: ");
        String polish = scanner.nextLine();
        System.out.print("Enter English translation: ");
        String english = scanner.nextLine();
        System.out.print("Enter German translation: ");
        String german = scanner.nextLine();

        Entry newEntry = new Entry(polish, english, german);
        entryRepository.addEntry(newEntry);
        //writing to the file also
        fileService.addEntryToFile(newEntry);
        System.out.println("Word added successfully!");
    }

    private void showAllWords() {
        List<Entry> entries = entryRepository.getAllEntries();
        if (entries.isEmpty()) {
            System.out.println("No words available.");
        } else {
            iWordDisplayService.displayWords(entries);
        }
    }

    private void startQuiz() {
        List<Entry> entries = entryRepository.getAllEntries();
        if (entries.isEmpty()) {
            System.out.println("No words available for the quiz.");
            return;
        }

        Random random = new Random();
        Entry entry = entries.get(random.nextInt(entries.size()));

        System.out.println("\nTranslate the word: " + entry.getPolish());
        System.out.print("English: ");
        String engAnswer = scanner.nextLine();
        System.out.print("German: ");
        String gerAnswer = scanner.nextLine();

        if (entry.getEnglish().equalsIgnoreCase(engAnswer) && entry.getGerman().equalsIgnoreCase(gerAnswer)) {
            System.out.println("Correct!");
        } else {
            System.out.println("Incorrect! The correct translation is: " + entry.getEnglish() + " - " + entry.getGerman());
        }
    }
}
