package org.netizen.rememberizer.services.crud;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.netizen.rememberizer.dto.NoteDto;
import org.netizen.rememberizer.entities.Note;
import org.netizen.rememberizer.mappers.NoteMapper;
import org.netizen.rememberizer.services.EntityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoteCrudService {

    private final NoteMapper mapper;
    private final EntityService<Note> service;

    @Transactional(readOnly = true)
    public NoteDto getNoteById(Long id) {
        return mapper.toDto(service.getEntityById(id));
    }

    public void deleteNoteById(Long id) {
        service.deleteEntityById(id);
    }

    public NoteDto saveNote(NoteDto noteDto) {
        Note note = mapper.toEntity(noteDto);
        note = service.saveEntity(note);
        return mapper.toDto(note);
    }

    @Transactional
    public NoteDto updateNote(NoteDto noteDto) {
        Note note = service.getEntityById(noteDto.getId());
        mapper.updateEntity(noteDto, note);
        note = service.saveEntity(note);
        return mapper.toDto(note);
    }

    public Page<NoteDto> getAllNotes(Pageable pageable) {
        return service.getAllEntities(pageable).map(mapper::toDto);
    }
}
