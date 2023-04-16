package com.example.todolist.logic;

import com.example.todolist.persistence.enums.Status;
import com.example.todolist.persistence.model.Note;
import com.example.todolist.persistence.repositories.NoteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NoteServiceTest {
    @Mock
    NoteRepository noteRepository;

    @InjectMocks
    private NoteService noteService;

    @Test
    void getNotes() {
        List<Note> expectedNotes = new ArrayList<>();
        Note note1 = Note.builder().id(1L).message("Note 1").status(Status.NOT_DONE).build();
        Note note2 = Note.builder().id(2L).message("Note 2").status(Status.IN_WORK).build();
        expectedNotes.add(note1);
        expectedNotes.add(note2);

        when(noteRepository.findAll()).thenReturn(expectedNotes);

        List<Note> actualNotes = noteService.getNotes();

        assertEquals(expectedNotes, actualNotes);
    }

    @Test
    void getNoteById() {
        long noteId = 1L;
        Note expectedNote = Note.builder().id(noteId).message("Note").status(Status.NOT_DONE).build();

        when(noteRepository.findById(noteId)).thenReturn(Optional.of(expectedNote));

        Note actualNote = noteService.getNoteById(noteId);

        assertEquals(expectedNote, actualNote);
    }

    @Test
    void getNoteByIdNotFound() {
        long noteId = 1L;

        when(noteRepository.findById(noteId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> noteService.getNoteById(noteId));
    }

    @Test
    void addNote() {
        Note note = Note.builder().id(1L).message("Note").status(Status.NOT_DONE).build();

        noteService.addNote(note);

        verify(noteRepository, times(1)).save(note);
    }

    @Test
    void updateNote() {
        long noteId = 1L;
        Note existingNote = Note.builder().id(noteId).message("Note").status(Status.NOT_DONE).build();
        Note updatedNote = Note.builder().id(noteId).message("Updated Note").status(Status.IN_WORK).build();

        when(noteRepository.findById(noteId)).thenReturn(Optional.of(existingNote));
        when(noteRepository.save(existingNote)).thenReturn(updatedNote);

        noteService.updateNote(updatedNote);

        verify(noteRepository, times(1)).findById(noteId);
        verify(noteRepository, times(1)).save(existingNote);

        assertEquals(updatedNote.getMessage(), existingNote.getMessage());
        assertEquals(updatedNote.getStatus(), existingNote.getStatus());
    }

    @Test
    void updateNoteNotFound() {
        long noteId = 1L;
        Note updatedNote = Note.builder().id(noteId).message("Updated Note").status(Status.IN_WORK).build();

        when(noteRepository.findById(noteId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> noteService.updateNote(updatedNote));
    }

    @Test
    void deleteNote() {
        long noteId = 1L;

        noteService.deleteNote(noteId);

        verify(noteRepository, times(1)).deleteById(noteId);
    }
}