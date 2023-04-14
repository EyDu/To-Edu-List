package com.example.todolist.logic;

import com.example.todolist.persistence.data.Note;
import com.example.todolist.persistence.repositories.NoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NoteServiceTest {
    @Mock
    NoteRepository noteRepository;

    @InjectMocks
    private NoteService noteService;

    @BeforeEach
    public void init() {
        Note note1 = new Note(1, "Test Note 1");
        Note note2 = new Note(2, "Test Note 2");
        Note note3 = new Note(3, "Test Note 3");
        List<Note> notes = new ArrayList<>();

        notes.add(note1);
        notes.add(note2);
        notes.add(note3);

        when(noteRepository.getNotes()).thenReturn(notes);
    }
    @Test
    void getNotes() {
        List<Note> expectedNotes = new ArrayList<>();
        expectedNotes.add(new Note(1, "Test Note 1"));
        expectedNotes.add(new Note(2, "Test Note 2"));
        expectedNotes.add(new Note(3, "Test Note 3"));

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
        Note newNote = new Note(1, "Test note");
        noteService.addNote(newNote);
        List<Note> notes = noteService.getNotes();
        assertEquals(4, notes.size());
        assertEquals(newNote, notes.get(3));
        assertTrue(notes.contains(newNote));
    }

    @Test
    void updateNote() {
        Note updatedNote = new Note(1, "New Text");
        noteService.updateNote(updatedNote);

        List<Note> notes = noteRepository.getNotes();
        Note note1 = notes.get(0);
        Note note2 = notes.get(1);

        assertEquals("New Text", note1.getMessage());
        assertEquals("Test Note 2", note2.getMessage());
    }

    @Test
    void deleteNote() {
        int idToDelete = 2;

        noteService.deleteNote(idToDelete);

        List<Note> notes = noteService.getNotes();
        assertEquals(2, notes.size());

        for (Note note:notes) {
            assertNotEquals(idToDelete, note.getId());
        }
    }
}