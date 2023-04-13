package com.example.todolist.controller;

import com.example.todolist.logic.NoteService;
import com.example.todolist.persistence.data.Note;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("api/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @GetMapping
    List<Note> getNotes() {
        return noteService.getNotes();
    }

    @PostMapping
    void addNote(@RequestBody Note note) {
        noteService.addNote(note);
    }


    @PutMapping
    void updateNote(@RequestBody Note changes) {
        noteService.updateNote(changes);
    }

    @DeleteMapping
    void deleteNote(@RequestBody int id) {
        noteService.deleteNote(id);
    }
}
