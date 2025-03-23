package org.example.tpo2;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EntryService {

    private final EntryRepository entryRepository;

    @Autowired
    public EntryService(EntryRepository entryRepository) {
        this.entryRepository = entryRepository;
    }

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
                        e.getPolish().equalsIgnoreCase(word) ||
                                e.getEnglish().equalsIgnoreCase(word) ||
                                e.getGerman().equalsIgnoreCase(word))
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
        return switch (field) {
            case "polish" -> entry.getPolish();
            case "english" -> entry.getEnglish();
            case "german" -> entry.getGerman();
            default -> null;
        };
    }

    public void addEntry(Entry entry) {
        entryRepository.addEntry(entry);
    }

    public void deleteEntry(Long id) {
        entryRepository.deleteById(id);
    }

    public Entry updateEntry(Entry updatedEntry) throws WordNotFoundException {
        return entryRepository.update(updatedEntry);
    }

    public Optional<Entry> findById(Long id) {
        return entryRepository.findById(id);
    }

    public List<Entry> getAllWords() {
        return entryRepository.allEntries();
    }

    public List<String> findOnlyEnglish() {
        return entryRepository.getAllEnglishWords();
    }

    public List<String> findOnlyPolish() {
        return entryRepository.getAllPolishWords();
    }

    public List<String> findOnlyGerman() {
        return entryRepository.getAllGermanWords();
    }

    public long getCount() {
        return entryRepository.countEntries();
    }

    public List<Long> getAllIds() {
        return entryRepository.getAllIds();
    }
}
