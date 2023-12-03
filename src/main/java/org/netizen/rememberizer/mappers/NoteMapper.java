package org.netizen.rememberizer.mappers;

import org.mapstruct.*;
import org.netizen.rememberizer.dto.NoteDto;
import org.netizen.rememberizer.entities.Note;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface NoteMapper {

    NoteDto toDto(Note source);
    Note toEntity(NoteDto source);
    void updateEntity(NoteDto noteDto, @MappingTarget Note note);
}
