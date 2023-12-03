package org.netizen.rememberizer.dto.payload.response;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse<T> {

    private Date timestamp;
    private int status;
    private String error;
    private T message;
    private String path;
}
