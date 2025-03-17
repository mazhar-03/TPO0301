package org.example.tpo2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

@Service
@PropertySource("classpath:new.properties") // Load external properties
public class FileService {

    private final EntryRepository entryRepository;

    @Value("${pl.edu.pja.tpo02.filename}")
    private String filename;

    public FileService(EntryRepository entryRepository) {
        this.entryRepository = entryRepository;
    }

    public void loadEntriesFromFile() {
        try {
            Path filePath = Path.of(filename);

            if (!Files.exists(filePath)) {
                System.out.println("File not found, creating a new words.csv.");
                return;
            }
            try (BufferedReader br = Files.newBufferedReader(filePath)) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 3) {
                        Entry entry = new Entry(parts[0], parts[1], parts[2]);
                        entryRepository.addEntry(entry);
                    }
                }
                System.out.println("\nAll words loaded successfully!");
            }
        } catch (IOException e) {
            System.out.println("\nError loading file: " + e.getMessage());
        }
    }

    public void addEntryToFile(Entry entry) {
        try {
            Path filePath = Path.of(filename);
            if (!Files.exists(filePath))
                Files.createFile(filePath);

            String entryLine = entry.getPolish() + "," + entry.getEnglish() + ',' + entry.getGerman() + "\n";
            Files.write(filePath, entryLine.getBytes(), StandardOpenOption.APPEND);
            System.out.println("Word saved to file successfully!");
        } catch (IOException e) {
            System.out.println("Error saving word to file: " + e.getMessage());
        }
    }
}