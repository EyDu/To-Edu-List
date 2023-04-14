package com.example.todolist.controller;

import com.example.todolist.logic.NoteService;
import com.example.todolist.persistence.data.Note;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import static org.junit.jupiter.api.Assertions.assertEquals;
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

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/notes")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(1)))
                .andExpect(status().isOk());
    }

    @Test
    void whenInvalidInput_thenReturns4xx() throws Exception {

        //figure out how to achieve this (maybe with empty repository?)
        /*
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/notes"))
                .andExpect(status().is4xxClientError());
         */

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
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/notes"))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();

        //TODO: Most likely needs to be changed later (the expected part)
        assertEquals("[]", responseBody);
    }

    @Test
    void addNote() throws Exception {
//        Note note = new Note(1, "Test note");
//        List<Note> notes = new ArrayList<>();
//        notes.add(note);
//        doNothing().when(noteService).addNote(any(Note.class));
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/notes")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(note)))
//                .andExpect(status().isOk());
//
//        assertEquals(1, notes.size());
//        assertEquals(note, notes.get(0));
    }

    @Test
    void updateNote() {
    }

    @Test
    void deleteNote() {
    }
}