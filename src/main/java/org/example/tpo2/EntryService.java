package org.example.tpo2;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Parser;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {

    private final EntryRepository entryRepository;

//    @Value("${pl.edu.pja.tpo02.filename}")
//    private String filename;

    @Autowired
    public FileService(EntryRepository entryRepository) {
        this.entryRepository = entryRepository;
    }


//    public void addEntryToFile(Entry entry) {
//        try {
//            Path filePath = Path.of(filename);
//            if (!Files.exists(filePath))
//                Files.createFile(filePath);
//
//            String entryLine = entry.getId() + "," + entry.getPolish() + "," + entry.getEnglish() + ',' + entry.getGerman() + "\n";
//            Files.write(filePath, entryLine.getBytes(), StandardOpenOption.APPEND);
//            System.out.println("Word saved to file successfully!");
//        } catch (IOException e) {
//            System.out.println("Error saving word to file: " + e.getMessage());
//        }
//    }

    @Transactional
    public void loadInitialDataIfEmpty() {
        if (entryRepository.countEntries() == 0) {
            entryRepository.addEntry(new Entry("Dzien", "Day", "Tag"));
            entryRepository.addEntry(new Entry("Niebiski", "Blue", "Blau"));

            System.out.println("Initial data inserted.");
        } else {
            System.out.println("DB already contains data, no inserting.");
        }
    }


    public Entry searchWord(String word) {
        return getAllWords().stream()
                .filter(e ->
                        e.getPolish().equals(word) ||
                        e.getEnglish().equals(word) ||
                        e.getGerman().equals(word))
                .findFirst()
                .orElse(null);
    }

    public List<Entry> sortingWords(String lang, boolean ascending) {
        return getAllWords().stream()
                .sorted((e1, e2) -> {
                    String word1 = getFieldValue(e1, lang);
                    String word2 = getFieldValue(e2, lang);
                    return ascending ? word1.compareTo(word2) : word2.compareTo(word1);
                })
                .toList();
    }

    private String getFieldValue(Entry entry, String field) {
        return switch (field){
            case "polish" -> entry.getPolish();
            case "english" -> entry.getEnglish();
            case "german" -> entry.getGerman();
            default -> null;
        };
    }

    public void deleteEntry(Long id) {
        entryRepository.deleteById(id);
    }

//    public void modifyEntryInFile(Long id, String newPolish, String newEnglish, String newGerman) {
//        List<Entry> entries = entryRepository.allEntries(); // read all current entries
//        boolean updated = false;
//
//        for (Entry e : entries) {
//            if (e.getId().equals(id)) {
//                e.setPolish(newPolish);
//                e.setEnglish(newEnglish);
//                e.setGerman(newGerman);
//                updated = true;
//                break;
//            }
//        }
//
//        if (updated) {
//            writeAllEntriesToFile(entries);
//            System.out.println("Entry updated successfully!");
//        } else {
//            System.out.println("No entry found with ID: " + id);
//        }
//    }

//    public void writeAllEntriesToFile(List<Entry> entries) {
//        try (PrintWriter writer = new PrintWriter(new FileWriter("words.csv"))) {
//            for (Entry e : entries) {
//                writer.println(e.getId() + "," + e.getPolish() + "," + e.getEnglish() + "," + e.getGerman());
//            }
//        } catch (IOException e) {
//            System.err.println("Error writing to file: " + e.getMessage());
//        }
//    }

    public List<Entry> getAllWords() {
        return entryRepository.allEntries();
    }

}