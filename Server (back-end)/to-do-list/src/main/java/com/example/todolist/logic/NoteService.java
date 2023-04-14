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
        Note noteToChange = noteRepository.getNotes().stream()
                .filter(note -> note.getId() == changes.getId())
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Note with id " + changes.getId() + " doesn't exist"));
        noteToChange.setMessage(changes.getMessage());
        noteToChange.setStatus(changes.getStatus());
    }

    public void deleteNote(int id) {
        noteRepository.getNotes().removeIf(note -> note.getId() == id);
    }
}
