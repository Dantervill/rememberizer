package org.netizen.rememberizer.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "T_NOTE")
public class Note extends AbstractEntity {

    @Column(name = "SUBJECT")
    @Schema(description = "Subject of the note", example = "Things to buy in a mall")
    private String subject;

    @Column(name = "BODY")
    @Schema(description = "Body of the note", example = "Pants, t-shirts, socks")
    private String body;

    public Note(Long id, LocalDateTime createdAt, LocalDateTime updatedAt,
                String subject, String body) {
        super(id, createdAt, updatedAt);
        this.subject = subject;
        this.body = body;
    }

    public String toString() {
        return "Note(subject=" + this.getSubject() + ", body=" + this.getBody() + ")";
    }
}
