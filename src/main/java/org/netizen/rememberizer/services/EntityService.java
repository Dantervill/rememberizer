package org.netizen.rememberizer.services;

import jakarta.persistence.EntityNotFoundException;
import org.netizen.rememberizer.entities.AbstractEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EntityService<T extends AbstractEntity> {

    /**
     * Get entity by id
     * @param id unique identifier of an entity
     * @return entity or {@link EntityNotFoundException} if it is not found.
     */
    T getEntityById(Long id);

    /**
     * Save entity
     * @param entity object representation of db table's record
     * @return saved entity
     */
    T saveEntity(T entity);

    /**
     * Delete entity by id
     * @param id unique identifier of an entity
     */
    void deleteEntityById(Long id);

    /**
     * Get paginated notes
     * @param pageable object representation of a page parameters
     * @return page of entities
     */
    Page<T> getAllEntities(Pageable pageable);
}
