package org.netizen.rememberizer.handler;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import lombok.extern.slf4j.Slf4j;
import org.netizen.rememberizer.commons.ExceptionResponseUtil;
import org.netizen.rememberizer.dto.payload.response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String LOG_INFO_MESSAGE = "Handling {}. Message \"{}\"";

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handle(EntityNotFoundException e, HttpServletRequest request) {
        log.info(LOG_INFO_MESSAGE, EntityNotFoundException.class.getSimpleName(), e.getMessage());
        final ExceptionResponse<?> response = ExceptionResponseUtil.getResponse(HttpStatus.NOT_FOUND, e.getMessage(), request);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handle(ConstraintViolationException e, HttpServletRequest request) {
        log.info(LOG_INFO_MESSAGE, ConstraintViolationException.class.getSimpleName(), e.getMessage());
        final Map<Path, List<String>> invalidFields = e.getConstraintViolations().stream()
                .collect(Collectors.groupingBy(ConstraintViolation::getPropertyPath,
                        Collectors.mapping(ConstraintViolation::getMessage, Collectors.toList())));
        final ExceptionResponse<?> response = ExceptionResponseUtil.getResponse(HttpStatus.BAD_REQUEST, invalidFields, request);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handle(MethodArgumentNotValidException e, HttpServletRequest request) {
        log.info(LOG_INFO_MESSAGE, MethodArgumentNotValidException.class.getSimpleName(), e.getMessage());
        final Map<String, List<String>> invalidFields = e.getFieldErrors().stream()
                .collect(Collectors.groupingBy(FieldError::getField,
                        Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())));
        final ExceptionResponse<?> response = ExceptionResponseUtil.getResponse(HttpStatus.BAD_REQUEST, invalidFields, request);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
