package org.netizen.rememberizer.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.netizen.rememberizer.dto.NoteDto;
import org.netizen.rememberizer.entities.Note;
import org.netizen.rememberizer.validation.markers.Marker;
import org.netizen.rememberizer.services.crud.NoteCrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.HttpURLConnection;

import static org.netizen.rememberizer.openapi.RequestDescription.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notes")
@Tag(name = "Notes", description = "Note management REST APIs")
public class NoteController {

    private final NoteCrudService service;

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Get note by id", responses = {
          @ApiResponse(responseCode = "" + HttpURLConnection.HTTP_OK, description = "Get note by id",
                  content = @Content(mediaType = APPLICATION_JSON_VALUE,
                          examples = @ExampleObject(value = GET_NOTE_BY_ID),
                          schema = @Schema(implementation = Note.class)))},
            description = "Request to get a note by its identifier")
    /** Action source : {@link org.netizen.rememberizer.services.crud.NoteCrudService#getNoteById(java.lang.Long)}. */
    public ResponseEntity<NoteDto> get(@Parameter(description = "Unique identifier of a note") @PathVariable
                                           @Min(value = 1, message = "ID must be equal or greater than 1") Long id) {
        log.info("Requesting note with id {}", id);
        NoteDto noteDto = service.getNoteById(id);
        return new ResponseEntity<>(noteDto, HttpStatus.OK);
    }

    @PostMapping
    @Validated({Marker.OnCreate.class})
    @Operation(summary = "Create a new note", responses = {
            @ApiResponse(responseCode = "" + HttpURLConnection.HTTP_CREATED, description = "Create a new note",
            content = @Content(mediaType = APPLICATION_JSON_VALUE,
            examples = @ExampleObject(value = CREATE_NOTE),
            schema = @Schema(implementation = Note.class)))},
    description = "Request to create a new note",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "New note to save"))
    /** Action source : {@link org.netizen.rememberizer.services.crud.NoteCrudService#saveNote(NoteDto)}. */
    public ResponseEntity<NoteDto> create(@Valid @RequestBody NoteDto noteDto) {
        log.info("Requesting to save note {}", noteDto);
        noteDto = service.saveNote(noteDto);
        return new ResponseEntity<>(noteDto, HttpStatus.CREATED);
    }

    @PutMapping
    @Validated({Marker.OnUpdate.class})
    @Operation(summary = "Update a note", responses = {
            @ApiResponse(responseCode = "" + HttpURLConnection.HTTP_OK, description = "Update a note",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = UPDATE_NOTE),
                            schema = @Schema(implementation = Note.class)))},
            description = "Request to update a note",
    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Existing note to update"))
    /** Action source : {@link org.netizen.rememberizer.services.crud.NoteCrudService#updateNote(NoteDto)}. */
    public ResponseEntity<NoteDto> update(@Valid @RequestBody NoteDto noteDto) {
        log.info("Requesting to update note");
        noteDto = service.updateNote(noteDto);
        return new ResponseEntity<>(noteDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete note by id", responses = {
            @ApiResponse(responseCode = "" + HttpURLConnection.HTTP_NO_CONTENT, description = "Delete note by id",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Note.class)))},
            description = "Request to delete note by id")
    /** Action source : {@link org.netizen.rememberizer.services.crud.NoteCrudService#deleteNoteById(Long)}. */
    public ResponseEntity<HttpStatus> delete(@PathVariable @Min(value = 1, message = "ID must be equal or greater than 1") Long id) {
        log.debug("Requesting to delete note with id {}", id);
        service.deleteNoteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Get paginated notes", responses = {
            @ApiResponse(responseCode = "" + HttpURLConnection.HTTP_OK, description = "Get paginated notes",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = GET_PAGINATED_NOTES),
                            schema = @Schema(implementation = Note.class)))},
            description = "Get paginated notes")
    /** Action source : {@link org.netizen.rememberizer.services.crud.NoteCrudService#getAllNotes(Pageable)}. */
    public ResponseEntity<Page<NoteDto>> getAll(@PageableDefault(value = 3, sort = "updatedAt") Pageable pageable) {
        log.info("Requesting to get page of notes");
        Page<NoteDto> allNotes = service.getAllNotes(pageable);
        return new ResponseEntity<>(allNotes, HttpStatus.OK);
    }
}
