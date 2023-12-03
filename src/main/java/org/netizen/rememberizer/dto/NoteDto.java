package org.netizen.rememberizer.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.*;
import org.netizen.rememberizer.validation.annotations.AtLeastOneNotNull;
import org.netizen.rememberizer.validation.markers.Marker;

@Getter
@Setter
@ToString
@AtLeastOneNotNull
@NoArgsConstructor
@AllArgsConstructor
public class NoteDto {

    @Null(groups = Marker.OnCreate.class)
    @NotNull(groups = Marker.OnUpdate.class)
    private Long id;

    private String subject;
    private String body;
}
