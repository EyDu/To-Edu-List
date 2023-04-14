package com.example.todolist.persistence.repositories;

import com.example.todolist.persistence.data.Note;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NoteRepositoryTest {

    @Test
    void getNotes() {
        NoteRepository noteRepository = new NoteRepository();
        List<Note> notes = noteRepository.getNotes();
        assertEquals(1, notes.size());
        assertEquals("lala", notes.get(0).getMessage());
    }
}