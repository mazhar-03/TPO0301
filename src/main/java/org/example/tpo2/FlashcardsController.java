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
    public FlashcardsController(EntryRepository entryRepository, FileService fileService, IWordDisplayService IWordDisplayService) {
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
            System.out.println("4. Search for a word");
            System.out.println("5. Modify a word in file");
            System.out.println("6. Delete a word on file");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addNewWord();
                case 2 -> showAllWords();
                case 3 -> startQuiz();
                case 4 -> searchWord();
                case 5 -> modifyWord();
                case 6 -> deleteWord();
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
    }

    private void showAllWords() {
        List<Entry> entries = entryRepository.allEntries();

        if (entries.isEmpty()) {
            System.out.println("No words available.");
            return;
        }

        System.out.print("Do you want to sort the words before displaying? (y/n): ");
        String sortChoice = scanner.nextLine().trim().toLowerCase();

        if (sortChoice.equals("y")) {
            sortWords();
        } else {
            iWordDisplayService.displayWords(entries);
        }
    }


    private void startQuiz() {
        List<Entry> entries = entryRepository.allEntries();
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

    public void searchWord() {
        System.out.println("Enter word for search: ");
        String word = scanner.nextLine();

        Entry result = fileService.searchWord(word);
        if(result == null) System.out.println("No word found for :" + word);
        else {
            System.out.println("Found word: "  + result.getId() + " - " +result.getPolish() + " - " + result.getEnglish() + " - " + result.getGerman());
        }
    }

    public void sortWords() {
        System.out.println("Sort by: 1-Polish    2-English    3-German");
        int lang = scanner.nextInt();
        scanner.nextLine(); // clear buffer

        System.out.println("Order: 1-Ascending  2-Descending");
        int order = scanner.nextInt();
        scanner.nextLine(); // clear buffer

        String language = switch (lang) {
            case 1 -> "polish";
            case 2 -> "english";
            case 3 -> "german";
            default -> null;
        };

        if (language != null) {
            boolean ascending = (order == 1);
            List<Entry> sorted = fileService.sortingWords(language, ascending);
            if (sorted.isEmpty()) {
                System.out.println("No words found.");
            } else {
                sorted.forEach(e ->
                        System.out.println(e.getId() + ". " +
                                e.getPolish() + " - " +
                                e.getEnglish() + " - " +
                                e.getGerman()));
            }
        } else {
            System.out.println("Invalid option!");
        }
    }

    private void modifyWord() {
        System.out.print("Enter the ID of the word to modify: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        System.out.print("Enter new Polish word: ");
        String polish = scanner.nextLine();

        System.out.print("Enter new English translation: ");
        String english = scanner.nextLine();

        System.out.print("Enter new German translation: ");
        String german = scanner.nextLine();

        fileService.modifyEntryInFile(id, polish, english, german);
    }

    private void deleteWord() {
        System.out.print("Enter the ID of the word to delete: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        fileService.deleteEntry(id);
        System.out.println("Entry deleted (if it existed).");
    }
}
