package org.example.tpo2.profiles;

import org.example.tpo2.Entry;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface IWordDisplayService {
    void displayWords(List<Entry> entries);
}
