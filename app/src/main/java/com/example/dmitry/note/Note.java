package com.example.dmitry.note;

/**
 * Created by Dmitry on 25.01.2017.
 */

public class Note {
    String text;
    String name;
    int password;
    int id;
    String label;

    public Note(int id, String name, String text, int password, String label) {
        this.name = name;
        this.text = text;
        this.password = password;
        this.id = id;
        this.label = label;
    }

    public Note(int id, String name, String text) {
        this.name = name;
        this.text = text;
        this.id = id;
    }
}
