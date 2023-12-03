package org.netizen.rememberizer.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.netizen.rememberizer.dto.NoteDto;
import org.netizen.rememberizer.services.crud.NoteCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = NoteController.class)
class NoteControllerTest {

    @MockBean private NoteCrudService service;
    @InjectMocks private NoteController controller;
    @Autowired private MockMvc mockMvc;

    private Long id;
    private NoteDto noteDto;
    private Pageable pageable;
    private Page<NoteDto> noteDtoPage;


    @BeforeEach
    void setUp() {
        id = 1L;
        noteDto = new NoteDto(1L, "Subject", "Body");
        pageable = PageRequest.of(0, 3, Sort.by("updatedAt"));
        noteDtoPage = new PageImpl<>(List.of(noteDto));
    }

    @Test
    void shouldAllMocksBeNotNull() {
        assertNotNull(service);
    }

    @Test
    void shouldGetNote() throws Exception {
        when(service.getNoteById(id)).thenReturn(noteDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/notes/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(noteDto.getId()));

        verify(service, times(1)).getNoteById(id);
        verifyNoMoreInteractions(service);
    }

    @Test
    void shouldCreateNote() throws Exception {
        NoteDto inputNoteDto = new NoteDto(null, "Subject", "Body");
        NoteDto expectedNoteDto = new NoteDto(id, "Subject", "Body");

        when(service.saveNote(any())).thenReturn(expectedNoteDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(inputNoteDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(expectedNoteDto.getId()));

        verify(service, times(1)).saveNote(any());
        verifyNoMoreInteractions(service);
    }

    @Test
    void shouldUpdateNote() throws Exception {
        NoteDto inputNoteDto = new NoteDto(id, "Subject", "Body");
        NoteDto expectedNoteDto = new NoteDto(id, "Subject", "Body");

        when(service.updateNote(any())).thenReturn(expectedNoteDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(inputNoteDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(expectedNoteDto.getId()));

        verify(service, times(1)).updateNote(any());
        verifyNoMoreInteractions(service);
    }

    @Test
    void shouldDeleteNote() throws Exception {
        doNothing().when(service).deleteNoteById(id);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/notes/{id}", id))
                .andExpect(status().isNoContent());

        verify(service, times(1)).deleteNoteById(id);
        verifyNoMoreInteractions(service);
    }

//    @Test
//    void shouldGetAllPagedNotes() throws Exception {
//        when(service.getAllNotes(pageable)).thenReturn(noteDtoPage);
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/notes")
//                        .param("page", "0")
//                        .param("size", "1")
//                        .param("sort", "updatedAt")
//                        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(header().string(HttpHeaders.LOCATION, "/api/v1/notes"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content").exists());
//
//        verify(service, times(1)).getAllNotes(pageable);
//    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}