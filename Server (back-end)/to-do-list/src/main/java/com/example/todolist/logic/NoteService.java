package com.example.todolist.logic;

import com.example.todolist.persistence.data.Note;
import com.example.todolist.persistence.repositories.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;
    public List<Note> getNotes() {
        return noteRepository.getNotes();
    }

    public void addNote(Note note) {
        noteRepository.getNotes().add(note);
    }

    public void updateNote(Note changes) {
        // probably won't work but this is the idea
        // update later with JPARepository
        for (Note note: noteRepository.getNotes()) {
            if (note.getId() == changes.getId()) {
                note = changes;
            }
        }
    }

    public void deleteNote(int id) {
        noteRepository.getNotes().removeIf(note -> note.getId() == id);
    }
}
