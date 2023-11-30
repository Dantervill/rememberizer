package org.netizen.rememberizer.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity(name = Note.T_NOTE_NAME)
@NoArgsConstructor
@Table(name = "T_NOTE")
public class Note extends AbstractEntity {

    public static final String T_NOTE_NAME = "Note";

    @Column(name = "SUBJECT")
    private String subject;

    @Column(name = "BODY")
    private String body;
}
