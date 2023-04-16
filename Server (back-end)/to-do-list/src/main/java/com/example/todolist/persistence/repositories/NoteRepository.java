package com.example.todolist.persistence.repositories;

import com.example.todolist.persistence.enums.Status;
import com.example.todolist.persistence.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    Note findByMessage(String message);
    List<Note> findNotesByStatus(Status status);
}
