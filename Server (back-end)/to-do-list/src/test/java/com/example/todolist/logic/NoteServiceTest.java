package com.example.todolist.logic;

import com.example.todolist.persistence.enums.Status;
import com.example.todolist.persistence.model.Note;
import com.example.todolist.persistence.repositories.NoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
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


    @BeforeEach
    public void setUp() {
        List<Note> testNotes = initTestNotes();

        when(noteService.getNotes()).thenReturn(testNotes);
    }

    private List<Note> initTestNotes() {
        Note testNote1 = Note.builder().message("Test Note 1").status(Status.NOT_DONE).build();
        Note testNote2 = Note.builder().message("Test Note 2").status(Status.IN_WORK).build();
        Note testNote3 = Note.builder().message("Test Note 3").status(Status.FORGOTTEN).build();
        return Arrays.asList(testNote1, testNote2, testNote3);
    }

    @Test
    void getNotes() {
        List<Note> expectedNotes = initTestNotes();

        List<Note> actualNotes = noteService.getNotes();

        assertEquals(expectedNotes.size(), actualNotes.size());
        for (int i= 0; i < expectedNotes.size(); i++) {
            assertEquals(expectedNotes.get(i).getId(), actualNotes.get(i).getId());
            assertEquals(expectedNotes.get(i).getStatus(), actualNotes.get(i).getStatus());
            assertEquals(expectedNotes.get(i).getMessage(), actualNotes.get(i).getMessage());
        }
    }

    @Test
    void addNote() {
        Note newNote = Note.builder().message("New Note").status(Status.NOT_DONE).build();
        when(noteRepository.save(newNote)).thenReturn(newNote);

        noteService.addNote(newNote);

        verify(noteRepository, times(1)).save(newNote);


        List<Note> notes = noteService.getNotes();
        assertTrue(notes.contains(newNote));
    }

    @Test
    void updateNote() {
        Note updatedNote = Note.builder().id(1L).message("New Text").status(Status.NOT_DONE).build();

        when(noteRepository.save(updatedNote)).thenReturn(updatedNote);
        when(noteRepository.findById(updatedNote.getId())).thenReturn(Optional.of((updatedNote)));

        noteService.updateNote(updatedNote);

        List<Note> notes = noteService.getNotes();
        Note note1 = notes.get(0);
        Note note2 = notes.get(1);

        assertEquals("New Text", note1.getMessage());
        assertEquals("Test Note 2", note2.getMessage());
    }

    @Test
    void deleteNote() {
        long idToDelete = 2;
        Note noteToDelete = Note.builder().id(idToDelete).build();
        when(noteRepository.findById(idToDelete)).thenReturn(Optional.of(noteToDelete));

        noteService.deleteNote(2L);
        List<Note> remainingNotes = noteService.getNotes();
        assertNull(noteService.getNoteById(2L)); // should be deleted
        assertEquals(1L, remainingNotes.get(0).getId()); // IDs should be updated
        assertEquals(3L, remainingNotes.get(1).getId());

        for (Note note:remainingNotes) {
            assertNotEquals(idToDelete, note.getId());
        }
    }
}