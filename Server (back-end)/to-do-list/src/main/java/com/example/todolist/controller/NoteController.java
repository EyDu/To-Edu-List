package com.example.todolist.controller;

import com.example.todolist.logic.NoteService;
import com.example.todolist.persistence.data.Note;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/notes")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
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

    @DeleteMapping("/{id}")
    void deleteNote(@PathVariable int id) {
        noteService.deleteNote(id);
    }
}
