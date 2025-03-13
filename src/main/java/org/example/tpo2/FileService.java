package org.example.tpo2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

@Service
@PropertySource("classpath:new.properties") // Load external properties
public class FileService {

    private final EntryRepository entryRepository;
    private final ResourceLoader resourceLoader;

    @Value("${pl.edu.pja.tpo02.filename}") // Inject filename from new.properties
    private String filename;

    public FileService(EntryRepository entryRepository, ResourceLoader resourceLoader) {
        this.entryRepository = entryRepository;
        this.resourceLoader = resourceLoader;
    }

    public void loadEntriesFromFile() {
        try {
            Resource resource = resourceLoader.getResource("classpath:" + filename);
            BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()));

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    Entry entry = new Entry(parts[0], parts[1], parts[2]);
                    entryRepository.addEntry(entry);
                }
            }
            br.close();
            System.out.println("\nAll words loaded successfully!");

        } catch (IOException e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
    }

}
