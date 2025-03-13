package org.example.tpo2.profiles;
import org.example.tpo2.Entry;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Profile("uppercase")  // Runs when profile is "uppercase"
public class UppercaseWordDisplay implements IWordDisplayService {
    @Override
    public void displayWords(List<Entry> entries) {
        System.out.println("\n=== Dictionary Entries (UPPERCASE) ===");
        entries.forEach(entry ->
                System.out.println(entry.getPolish().toUpperCase() + " - " +
                        entry.getEnglish().toUpperCase() + " - " +
                        entry.getGerman().toUpperCase()));
    }
}