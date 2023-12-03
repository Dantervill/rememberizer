package org.netizen.rememberizer.services.crud;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.netizen.rememberizer.dto.NoteDto;
import org.netizen.rememberizer.entities.Note;
import org.netizen.rememberizer.mappers.NoteMapper;
import org.netizen.rememberizer.services.EntityService;
import org.springframework.data.domain.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NoteCrudServiceTest {

    @Mock private NoteMapper mapper;
    @Mock private EntityService<Note> entityService;
    @InjectMocks private NoteCrudService crudService;

    private Long id;
    private Note note;
    private NoteDto noteDto;
    private Pageable pageable;
    private Page<NoteDto> noteDtoPage;
    private Page<Note> notePage;

    @BeforeEach
    void setUp() {
        id = 1L;
        note = new Note(1L, LocalDateTime.now(), LocalDateTime.now(),
                "Subject", "Body");
        noteDto = new NoteDto(1L, "Subject", "Body");
        pageable = PageRequest.of(0, 3, Sort.by("updatedAt"));
        notePage = new PageImpl<>(List.of(note));
        noteDtoPage = new PageImpl<>(List.of(noteDto));
    }

    @Test
    void shouldAllMocksBeNotNull() {
        assertNotNull(mapper);
        assertNotNull(entityService);
    }

    @Test
    void shouldGetNoteById() {
        when(entityService.getEntityById(id)).thenReturn(note);
        when(mapper.toDto(note)).thenReturn(noteDto);

        NoteDto actual = crudService.getNoteById(id);

        assertEquals(noteDto, actual);
        verify(entityService, times(1)).getEntityById(id);
        verify(mapper, times(1)).toDto(note);
        verifyNoMoreInteractions(entityService);
        verifyNoMoreInteractions(mapper);
    }

    @Test
    void shouldDeleteNoteById() {
        crudService.deleteNoteById(id);

        verify(entityService, times(1)).deleteEntityById(id);
        verifyNoMoreInteractions(entityService);
    }

    @Test
    void shouldSaveNote() {
        NoteDto noteDto = new NoteDto(null, "Subject", "Body");
        Note note = new Note(null, null, null, "Subject", "Body");

        when(mapper.toEntity(noteDto)).thenReturn(note);
        when(entityService.saveEntity(note)).thenReturn(this.note);
        when(mapper.toDto(this.note)).thenReturn(this.noteDto);

        NoteDto actual = crudService.saveNote(noteDto);

        assertEquals(this.noteDto.getId(), actual.getId());
        verify(mapper, times(1)).toEntity(noteDto);
        verify(entityService, times(1)).saveEntity(note);
        verify(mapper, times(1)).toDto(this.note);
        verifyNoMoreInteractions(mapper);
        verifyNoMoreInteractions(entityService);
    }

    @Test
    void shouldUpdateNote() {
        NoteDto v1 = new NoteDto(1L, "Updated subject", "Body");
        NoteDto noteDto = new NoteDto(1L, "Updated subject", "Body");

        when(entityService.getEntityById(id)).thenReturn(note);
        note.setSubject(v1.getSubject());
        when(entityService.saveEntity(note)).thenReturn(note);
        when(mapper.toDto(note)).thenReturn(noteDto);

        NoteDto actual = crudService.updateNote(v1);

        assertEquals(v1.getSubject(), actual.getSubject());
        verify(entityService, times(1)).getEntityById(id);
        verify(mapper, times(1)).updateEntity(v1, note);
        verify(entityService, times(1)).saveEntity(note);
        verify(mapper, times(1)).toDto(note);
        verifyNoMoreInteractions(entityService);
        verifyNoMoreInteractions(mapper);
    }

    @Test
    void getAllNotesTest() {
        when(entityService.getAllEntities(pageable)).thenReturn(notePage);
        when(mapper.toDto(any())).thenReturn(noteDto);

        Page<NoteDto> actual = crudService.getAllNotes(pageable);

        assertEquals(noteDtoPage, actual);
        verify(entityService, times(1)).getAllEntities(pageable);
        verify(mapper, times(notePage.getContent().size())).toDto(any());
        verifyNoMoreInteractions(entityService);
        verifyNoMoreInteractions(mapper);
    }
}