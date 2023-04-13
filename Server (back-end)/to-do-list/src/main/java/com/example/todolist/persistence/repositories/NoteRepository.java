package com.example.todolist.persistence.repositories;

import com.example.todolist.persistence.data.Note;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class NoteRepository {

    List<Note> notes = new ArrayList<>();

    public List<Note> getNotes() {
        return notes;
    }
}
