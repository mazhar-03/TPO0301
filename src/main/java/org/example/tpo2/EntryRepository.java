package org.example.tpo2;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Transactional
    public List<Entry> allEntries() {
        return entityManager.createQuery("from Entry", Entry.class).getResultList();
    }

    @Transactional
    public void deleteById(Long id){
        findById(id).ifPresent(entityManager::remove);
    }

    @Transactional
    public long countEntries(){
        return entityManager.createQuery("select count(*) from Entry", Long.class).getSingleResult();
    }

    public Optional findById(Long id) {
        return Optional.ofNullable(entityManager.find(Entry.class, id));
    }

    @Transactional
    public Entry update(Entry entry) throws WordNotFoundException {
        Entry dbEntry = findById(entry.getId())
                .orElseThrow(WordNotFoundException::new);
        dbEntry.setPolish(entry.getPolish());
        dbEntry.setEnglish(entry.getEnglish());
        dbEntry.setGerman(entry.getGerman());
        return dbEntry;
    }
}
