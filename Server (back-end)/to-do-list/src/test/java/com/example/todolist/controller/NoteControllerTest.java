package com.example.todolist.controller;

import com.example.todolist.logic.NoteService;
import com.example.todolist.persistence.data.Note;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class NoteControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    NoteService noteService;

    private Note sampleNote1;

    private Note sampleNote2;

    @BeforeEach
    public void setUp() {
        sampleNote1 = new Note(1, "Test Note 1");
        sampleNote2 = new Note(2, "Test Note 2");
    }

    @Test
    void whenValidInput_thenReturns200() throws Exception {
        Note note = new Note(0, "Test Note");

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/notes"))
                .andExpect(status().isOk());

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/notes")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(note)))
                .andExpect(status().isOk());

        this.mockMvc.perform(MockMvcRequestBuilders.put("/api/notes")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(note)))
                .andExpect(status().isOk());

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/notes/1"))
                .andExpect(status().isOk());
    }

    @Test
    void whenInvalidInput_thenReturns4xx() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/notes")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(1)))
                .andExpect(status().is4xxClientError());

        this.mockMvc.perform(MockMvcRequestBuilders.put("/api/notes")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(2)))
                .andExpect(status().is4xxClientError());

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/notes")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(new Note(1, "lala"))))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void getNotes() throws Exception {
        List<Note> notes = Arrays.asList(sampleNote1, sampleNote2);
        given(noteService.getNotes()).willReturn(notes);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/notes"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{id:1,message:\"Test Note 1\"},{id:2,message:\"Test Note 2\"}]"));

    }

    @Test
    void addNote() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleNote1)))
                .andExpect(status().isOk());

        verify(noteService, times(1)).addNote(any(Note.class));
    }

    @Test
    void updateNote() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleNote1)))
                .andExpect(status().isOk());

        verify(noteService, times(1)).updateNote(any(Note.class));
    }

    @Test
    void deleteNote() throws Exception{
        int id = 1;
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/notes/{id}", id))
                .andExpect(status().isOk());

        verify(noteService, times(1)).deleteNote(id);
    }
}