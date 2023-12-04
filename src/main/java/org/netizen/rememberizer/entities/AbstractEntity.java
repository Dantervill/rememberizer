package org.netizen.rememberizer.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier", example = "999")
    private Long id;

    @CreationTimestamp
    @Column(name = "CREATED_AT", nullable = false)
    @Schema(description = "Creation timestamp", example = "2023-12-01 08:32:40.811369")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "UPDATED_AT")
    @Schema(description = "Updating timestamp", example = "2023-12-01 08:32:40.811369")
    private LocalDateTime updatedAt;
}
