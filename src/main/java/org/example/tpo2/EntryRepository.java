package org.example.tpo2;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class EntryRepository {

    private final EntityManager entityManager;

    @Autowired
    public EntryRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public void addEntry(Entry entry) {
        entityManager.persist(entry);
    }

    public Optional<Entry> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Entry.class, id));
    }

    @Transactional
    public Entry update(Entry entry) throws WordNotFoundException {
        Entry existing = findById(entry.getId())
                .orElseThrow(WordNotFoundException::new);
        existing.setPolish(entry.getPolish());
        existing.setEnglish(entry.getEnglish());
        existing.setGerman(entry.getGerman());
        return existing;
    }

    @Transactional
    public void deleteById(Long id) {
        findById(id).ifPresent(entityManager::remove);
    }

    public long countEntries() {
        return entityManager.createQuery("SELECT COUNT(e) FROM Entry e", Long.class).getSingleResult();
    }

    public List<Entry> allEntries() {
        return entityManager.createQuery("FROM Entry", Entry.class).getResultList();
    }

    public List<String> getAllEnglishWords() {
        TypedQuery<String> query = entityManager.createQuery("SELECT e.english FROM Entry e", String.class);
        return query.getResultList();
    }
    public List<String> getAllPolishWords() {
        TypedQuery<String> query = entityManager.createQuery("SELECT e.polish FROM Entry e", String.class);
        return query.getResultList();
    }
    public List<String> getAllGermanWords() {
        TypedQuery<String> query = entityManager.createQuery("SELECT e.german FROM Entry e", String.class);
        return query.getResultList();
    }

    public List<Long> getAllIds(){
        TypedQuery<Long> query = entityManager.createQuery("SELECT e.id FROM Entry e", Long.class);
        return query.getResultList();
    }
}
