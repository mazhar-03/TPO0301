package org.example.tpo2;

import jakarta.persistence.*;

@Entity
@Table(name="Words")
public class Entry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String polish;
    private String english;
    private String german;

    public Entry(){}
    public Entry(Long id, String polish, String english, String german) {
        this.id = id;
        this.polish = polish;
        this.english = english;
        this.german = german;
    }

    public Entry(String polish, String english, String german) {
        this.polish = polish;
        this.english = english;
        this.german = german;
    }

    public String getPolish() { return polish; }
    public String getEnglish() { return english; }
    public String getGerman() { return german; }

    @Override
    public String toString() {
        return "Entry{" +
                "id=" + id +
                ", polish='" + polish + '\'' +
                ", english='" + english + '\'' +
                ", german='" + german + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPolish(String polish) {
        this.polish = polish;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public void setGerman(String german) {
        this.german = german;
    }
}
