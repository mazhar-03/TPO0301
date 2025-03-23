package org.example.tpo2;

import org.example.tpo2.profiles.IWordDisplayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;

@Service
public class FlashcardsController {

    private final EntryRepository entryRepository;
    private final EntryService entryService;
    private final IWordDisplayService iWordDisplayService;
    private final Scanner scanner = new Scanner(System.in);

    @Autowired
    public FlashcardsController(EntryRepository entryRepository, EntryService entryService, IWordDisplayService iWordDisplayService) {
        this.entryRepository = entryRepository;
        this.entryService = entryService;
        this.iWordDisplayService = iWordDisplayService;
    }

    public void showMenu() {
        while (true) {
            System.out.println("\n=== Flashcards Application ===");
            System.out.println("1. Add new word");
            System.out.println("2. Show all words (with sorting)");
            System.out.println("3. Show only polish words");
            System.out.println("4. Show only english words");
            System.out.println("5. Show only german words");
            System.out.println("6. Take a quiz");
            System.out.println("7. Search for a word");
            System.out.println("8. Modify a word");
            System.out.println("9. Delete a word by its id");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addNewWord();
                case 2 -> showAllWords();
                case 3 -> showOnlyPolish();
                case 4 -> showOnlyEnglish();
                case 5 -> showOnlyGerman();
                case 6 -> startQuiz();
                case 7 -> searchWord();
                case 8 -> modifyWord();
                case 9 -> deleteWordById();
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
        entryService.addEntry(newEntry);
    }

    private void showAllWords() {
        List<Entry> entries = entryService.getAllWords();

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
        List<Entry> entries = entryService.getAllWords();
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

        Entry result = entryService.searchWord(word);
        if (result == null) System.out.println("No word found for: " + word);
        else {
            System.out.println("Found word: " + result.getId() + " - " + result.getPolish() + " - " + result.getEnglish() + " - " + result.getGerman());
        }
    }

    public void sortWords() {
        System.out.println("Sort by: 1-Polish    2-English    3-German");
        int lang = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Order: 1-Ascending  2-Descending");
        int order = scanner.nextInt();
        scanner.nextLine();

        String language = switch (lang) {
            case 1 -> "polish";
            case 2 -> "english";
            case 3 -> "german";
            default -> null;
        };

        if (language != null) {
            boolean ascending = (order == 1);
            List<Entry> sorted = entryService.sortingWords(language, ascending);
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

        Optional<Entry> entryOpt = entryService.findById(id);
        if (entryOpt.isEmpty()) {
            System.out.println("Entry not found.");
            return;
        }

        System.out.print("Enter new Polish word: ");
        String polish = scanner.nextLine();

        System.out.print("Enter new English translation: ");
        String english = scanner.nextLine();

        System.out.print("Enter new German translation: ");
        String german = scanner.nextLine();

        Entry updatedEntry = new Entry(id, polish, english, german);
        try {
            entryService.updateEntry(updatedEntry);
            System.out.println("Entry updated successfully!");
        } catch (WordNotFoundException e) {
            System.out.println("Entry with ID " + id + " not found.");
        }
    }

    private void deleteWordById() {
        System.out.print("Enter the ID of the word to delete: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        if (entryService.getAllIds().contains(id)) {
            entryService.deleteEntry(id);
            System.out.println("Word deleted.");
        } else {
            System.out.println("Word not found.");
        }

    }

    private void showOnlyEnglish() {
        if (entryService.getCount() != 0) {
            List<String> englishWords = entryService.findOnlyEnglish();
            List<Entry> fakeEntries = englishWords.stream()
                    .map(e -> new Entry(null, "", e, ""))
                    .toList();

            iWordDisplayService.displayWords(fakeEntries);
        }
        else System.out.println("Word not found.");
    }

    private void showOnlyPolish() {
        if (entryService.getCount() != 0) {
            List<String> polishWords = entryService.findOnlyPolish();
            List<Entry> fakeEntries = polishWords.stream()
                    .map(e -> new Entry(null, e, "", ""))
                    .toList();

            iWordDisplayService.displayWords(fakeEntries);
        }

        else System.out.println("Word not found.");
    }

    private void showOnlyGerman() {
        if (entryService.getCount() != 0) {
            List<String> germanWords = entryService.findOnlyGerman();
            List<Entry> fakeEntries = germanWords.stream()
                    .map(e -> new Entry(null, "",  "", e))
                    .toList();

            iWordDisplayService.displayWords(fakeEntries);
        }
        else System.out.println("Word not found.");
    }
}
