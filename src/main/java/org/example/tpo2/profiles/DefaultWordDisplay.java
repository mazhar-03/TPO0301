package org.example.tpo2.profiles;

import org.example.tpo2.Entry;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
@Profile("default")  // Runs when no specific profile is set
public class DefaultWordDisplay implements IWordDisplayService {
    @Override
    public void displayWords(List<Entry> entries) {
        System.out.println("\n=== Dictionary Entries (Original) ===");
        entries.forEach(System.out::println);
    }
}
