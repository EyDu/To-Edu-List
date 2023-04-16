package com.example.todolist.logic;

import com.example.todolist.persistence.model.Note;
import com.example.todolist.persistence.repositories.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private final NoteRepository noteRepository;

    @Autowired
    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<Note> getNotes() {
        return noteRepository.findAll();
    }

    public Note getNoteById(long noteId) {
        return noteRepository.findById(noteId)
                .orElseThrow(() -> new IllegalArgumentException("Note with id " + noteId + " not found"));
    }

    public void addNote(Note note) {
        noteRepository.save(note);
    }

    public void updateNote(Note changes) {
        Note noteToChange = noteRepository.findById(changes.getId())
                .orElseThrow(() -> new IllegalArgumentException("Note with id " + changes.getId() + " doesn't exist"));
        noteToChange.setMessage(changes.getMessage());
        noteToChange.setStatus(changes.getStatus());
        noteRepository.save(noteToChange);
    }

    public void deleteNote(Long id) {
        noteRepository.deleteById(id);
    }
}
