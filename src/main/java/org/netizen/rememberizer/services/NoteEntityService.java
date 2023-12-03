package org.netizen.rememberizer.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.netizen.rememberizer.entities.Note;
import org.netizen.rememberizer.repositories.NoteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoteEntityService implements EntityService<Note> {

    private final NoteRepository repository;

    @Override
    public Note getEntityById(Long id) {
        log.info("Getting note with id {}", id);
        return repository.findById(id).orElseThrow(() -> {
            String message = "Note with id " + id + " not found";
            return new EntityNotFoundException(message);
        });
    }

    @Override
    public Note saveEntity(Note note) {
        log.info("Saving note {}", note);
        return repository.save(note);
    }

    @Override
    public void deleteEntityById(Long id) {
        log.info("Deleting note with id {}", id);
        repository.deleteById(id);
    }

    @Override
    public Page<Note> getAllEntities(Pageable pageable) {
        log.info("Getting paginated notes");
        return repository.findAll(pageable);
    }
}
