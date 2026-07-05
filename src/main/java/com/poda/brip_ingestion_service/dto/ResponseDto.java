package com.poda.brip_ingestion_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@SuperBuilder
public class ResponseDto {

    private int code;

    private Instant time;

    private String traceId;

    private String path;

}
