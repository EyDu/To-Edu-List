package com.example.todolist.persistence.repositories;

import com.example.todolist.persistence.data.Note;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class NoteRepository {

    // Temporary, quick solution to create working Tests
    List<Note> notes = new ArrayList<>(List.of(new Note(1, "lala")));

    public List<Note> getNotes() {
        return notes;
    }
}
