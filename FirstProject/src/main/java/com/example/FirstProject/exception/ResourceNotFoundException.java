package com.example.FirstProject.exception;

public class ResourceNotFoundException extends RuntimeException{
    String resourceName;
    String filed;
    String fieldName;
    Long fieldId;
    public ResourceNotFoundException(String resourceName, String filed, String fieldName){
        super(String.format("  %s not found with %s: %s ", resourceName, filed,fieldName));
        this.resourceName = resourceName;
        this.filed = filed;
        this.fieldName = fieldName;
    }
    public ResourceNotFoundException(String resourceName, String filed , Long fieldId){
        super(String.format("  %s not found with %s: %d ", resourceName, filed,fieldId));

        this.resourceName = resourceName;
        this.filed = filed;
        this.fieldId = fieldId;
    }

public ResourceNotFoundException(){}
}
