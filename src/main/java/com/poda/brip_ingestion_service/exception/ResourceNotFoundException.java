package com.poda.brip_ingestion_service.exception;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String resource, String field, String value){
        super(resource+" with "+field+" having value "+value+" not found.");
    }
}
