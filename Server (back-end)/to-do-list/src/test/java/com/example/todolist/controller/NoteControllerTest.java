package com.example.todolist.controller;

import com.example.todolist.logic.NoteService;
import com.example.todolist.persistence.enums.Status;
import com.example.todolist.persistence.model.Note;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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


    @BeforeEach
    public void setUp() {
        List<Note> testNotes = initTestNotes();
    }

    private List<Note> initTestNotes() {
        Note testNote1 = Note.builder().id(1L).message("Test Note 1").status(Status.NOT_DONE).build();
        Note testNote2 = Note.builder().id(2L).message("Test Note 2").status(Status.IN_WORK).build();
        return Arrays.asList(testNote1, testNote2);
    }

    @Test
    void whenValidInput_thenReturns200() throws Exception {
        List<Note> testNotes = initTestNotes();
        Note testNote1 = testNotes.get(0);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/notes"))
                .andExpect(status().isOk());

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/notes")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(testNote1)))
                .andExpect(status().isOk());

        this.mockMvc.perform(MockMvcRequestBuilders.put("/api/notes")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(testNote1)))
                .andExpect(status().isOk());

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/notes/1"))
                .andExpect(status().isOk());
    }

    @Test
    void whenInvalidInput_thenReturns4xx() throws Exception {
        List<Note> testNotes = initTestNotes();
        Note testNote1 = testNotes.get(0);

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
                        .content(objectMapper.writeValueAsString(testNote1)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void getNotes() throws Exception {
        List<Note> testNotes = initTestNotes();
        given(noteService.getNotes()).willReturn(testNotes);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/notes"))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponse = result.getResponse().getContentAsString();

        String expectedResponse = "[{\"id\":1,\"message\":\"Test Note 1\",\"status\":\"NOT_DONE\"},{\"id\":2,\"message\":\"Test Note 2\",\"status\":\"IN_WORK\"}]";
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void addNote() throws Exception {
        List<Note> testNotes = initTestNotes();
        Note testNote1 = testNotes.get(0);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testNote1)))
                .andExpect(status().isOk());

        verify(noteService, times(1)).addNote(any(Note.class));
    }

    @Test
    void updateNote() throws Exception {
        List<Note> testNotes = initTestNotes();
        Note testNote1 = testNotes.get(0);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testNote1)))
                .andExpect(status().isOk());

        verify(noteService, times(1)).updateNote(any(Note.class));
    }

    @Test
    void deleteNote() throws Exception{
        long id = 1;
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/notes/{id}", id))
                .andExpect(status().isOk());

        verify(noteService, times(1)).deleteNote(id);
    }
}