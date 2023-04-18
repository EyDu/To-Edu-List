package com.example.todolist.persistence.model;

import com.example.todolist.persistence.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Data
@Table(name = "notes")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notes_id_seq")
    @SequenceGenerator(name = "notes_id_seq", sequenceName = "notes_id_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Note)) return false;
        Note note = (Note) o;
        return Objects.equals(id, note.id);
    }
}
