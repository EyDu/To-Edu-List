package com.example.todolist.controller;

import com.example.todolist.logic.NoteService;
import com.example.todolist.persistence.data.Note;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class NoteControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    NoteService noteService;

    @Test
    void whenValidInput_thenReturns200() throws Exception {
        Note note = new Note(0, "dudu");

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

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/notes")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(1)))
                .andExpect(status().isOk());
    }

    @Test
    void getNotes() {
    }

    @Test
    void addNote() {
    }

    @Test
    void updateNote() {
    }

    @Test
    void deleteNote() {
    }
}