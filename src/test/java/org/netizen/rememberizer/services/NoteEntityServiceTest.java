package org.netizen.rememberizer.services;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.netizen.rememberizer.entities.Note;
import org.netizen.rememberizer.repositories.NoteRepository;
import org.springframework.data.domain.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NoteEntityServiceTest {

    @Mock private NoteRepository repository;
    @InjectMocks NoteEntityService service;

    private Long id;
    private Note note;
    private String excMessage;
    private Pageable pageable;
    private Page<Note> notePage;

    @BeforeEach
    void setUp() {
        id = 1L;
        note = new Note(1L, LocalDateTime.now(), LocalDateTime.now(),
                "Subject", "Body");
        excMessage = "Note with id " + id + " not found";
        pageable = PageRequest.of(0, 3, Sort.by("updatedAt"));
        notePage = new PageImpl<>(List.of(note));
    }

    @Test
    void shouldAllMocksBeNotNull() {
        assertNotNull(repository);
    }

    @Test
    void shouldGetEntityByIdWithoutExc() {
        when(repository.findById(id)).thenReturn(Optional.of(note));

        Note actual = service.getEntityById(id);

        assertDoesNotThrow(() -> new EntityNotFoundException(excMessage));
        assertEquals(note, actual);
        verify(repository, times(1)).findById(id);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldThrowExcWhenGetEntityById() {
        when(repository.findById(id)).thenThrow(new EntityNotFoundException(excMessage));

        assertThatThrownBy(() -> service.getEntityById(id))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage(excMessage);
        verify(repository, times(1)).findById(id);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldSaveEntity() {
        Note beforeSave = new Note(null, null, null, "Subject", "Body");

        when(repository.save(beforeSave)).thenReturn(note);

        Note actual = service.saveEntity(beforeSave);

        assertEquals(note.getSubject(), actual.getSubject());
        verify(repository, times(1)).save(beforeSave);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldDeleteEntityById() {
        service.deleteEntityById(id);

        verify(repository, times(1)).deleteById(id);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldGetAllEntities() {
        when(repository.findAll(pageable)).thenReturn(notePage);

        Page<Note> actual = service.getAllEntities(pageable);

        assertEquals(notePage, actual);
        verify(repository, times(1)).findAll(pageable);
        verifyNoMoreInteractions(repository);
    }
}