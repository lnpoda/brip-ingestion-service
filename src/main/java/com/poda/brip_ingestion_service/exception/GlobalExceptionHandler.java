package com.poda.brip_ingestion_service.exception;

import com.poda.brip_ingestion_service.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(exception = ResourceNotFoundException.class)
    public ErrorResponseDto handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest request) {
        return ErrorResponseDto.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .time(Instant.now())
                .message(exception.getMessage())
                .path(request.getDescription(false).replace("uri=",""))
                .build();
    }
    
}
