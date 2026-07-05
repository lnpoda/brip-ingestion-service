package com.poda.brip_ingestion_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@SuperBuilder
public class ErrorResponseDto extends ResponseDto {
    private String message;
}
