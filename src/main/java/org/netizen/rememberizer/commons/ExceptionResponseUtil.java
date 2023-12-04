package org.netizen.rememberizer.commons;

import jakarta.servlet.http.HttpServletRequest;
import org.netizen.rememberizer.dto.payload.response.ExceptionResponse;
import org.springframework.http.HttpStatus;

import java.util.Date;

public final class ExceptionResponseUtil {

    private ExceptionResponseUtil() {
        throw new AssertionError("Class is not instantiable");
    }

    public static ExceptionResponse<Object> getResponse(HttpStatus status, Object message, HttpServletRequest request) {
        return ExceptionResponse.builder()
                .timestamp(new Date())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .path(request.getRequestURI())
                .build();
    }
}
