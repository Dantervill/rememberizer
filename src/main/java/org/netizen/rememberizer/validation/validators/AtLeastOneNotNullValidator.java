package org.netizen.rememberizer.validation.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.netizen.rememberizer.validation.annotations.AtLeastOneNotNull;
import org.netizen.rememberizer.dto.NoteDto;

import java.util.Objects;

public class AtLeastOneNotNullValidator implements ConstraintValidator<AtLeastOneNotNull, NoteDto> {

    @Override
    public boolean isValid(NoteDto noteDto, ConstraintValidatorContext constraintValidatorContext) {
        return Objects.nonNull(noteDto.getSubject()) || Objects.nonNull(noteDto.getBody());
    }
}
