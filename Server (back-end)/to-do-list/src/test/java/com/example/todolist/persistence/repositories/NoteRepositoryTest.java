package com.example.todolist.persistence.repositories;

import com.example.todolist.persistence.enums.Status;
import com.example.todolist.persistence.model.Note;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class NoteRepositoryTest {
    
    @Autowired
    NoteRepository noteRepository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    public void setUp() {
        List<Note> testNotes = initTestNotes();
        for (Note testNote : testNotes) {
            entityManager.persist(testNote);
        }
        entityManager.flush();
    }

    private List<Note> initTestNotes() {
        Note testNote1 = Note.builder().message("Test Note 1").status(Status.NOT_DONE).build();
        Note testNote2 = Note.builder().message("Test Note 2").status(Status.IN_WORK).build();
        return Arrays.asList(testNote1, testNote2);
    }

    @Test
    void whenFindByMessage_thenReturnNote() {
        List<Note> testNotes = initTestNotes();
        Note foundNote = noteRepository.findByMessage("Test Note 1");

        assertEquals(foundNote.getMessage(), testNotes.get(0).getMessage());
        assertEquals(foundNote.getStatus(), testNotes.get(0).getStatus());

        assertNotEquals(foundNote.getMessage(), testNotes.get(1).getMessage());
    }

    @Test
    void whenFindByStatus_thenReturnNotes() {
        List<Note> testNotes = initTestNotes();
        List<Note> foundNotes = noteRepository.findNotesByStatus(Status.NOT_DONE);

        assertEquals(foundNotes.get(0).getMessage(), testNotes.get(0).getMessage());
        assertEquals(foundNotes.get(0).getStatus(), testNotes.get(0).getStatus());

        assertNotEquals(foundNotes.get(0).getStatus(), testNotes.get(1).getStatus());
    }
}