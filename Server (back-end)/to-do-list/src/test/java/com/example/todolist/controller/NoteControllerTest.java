package com.example.todolist.controller;

import com.example.todolist.logic.NoteService;
import com.example.todolist.persistence.enums.Status;
import com.example.todolist.persistence.model.Note;
import com.example.todolist.persistence.repositories.NoteRepository;
import com.example.todolist.security.auth.AuthenticationController;
import com.example.todolist.security.config.JwtAuthenticationFilter;
import com.example.todolist.security.config.JwtService;
import com.example.todolist.security.config.SecurityConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NoteController.class)
@WithMockUser(username = "admin", password = "123", authorities = {"ADMIN"})
@Import(SecurityConfig.class)
class NoteControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    NoteService noteService;

    @MockBean
    JwtService jwtService;

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
        mockMvc.perform(MockMvcRequestBuilders.get("/api/notes"))
                .andExpect(status().isOk());

        verify(noteService).getNotes();
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