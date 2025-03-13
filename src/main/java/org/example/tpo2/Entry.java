package org.example.tpo2;

public class Entry {
    private String polish;
    private String english;
    private String german;

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
        return polish + " - " + english + " - " + german;
    }
}
