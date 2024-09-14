package com.niladri.ecommerce.exception;

public class ResourceNotFound extends RuntimeException {
    String resourceName;
    String fieldName;
    String field;
    long fieldId;

    public ResourceNotFound() {
    }

    public ResourceNotFound(String resourceName, String field,String fieldName) {
        super(String.format("%s not found with %s : '%s'", resourceName, field,fieldName));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.field = field;
    }

    public ResourceNotFound(String resourceName, String field, long fieldId) {
        super(String.format("%s not found with %s : '%d'", resourceName, field, fieldId));
        this.resourceName = resourceName;
        this.field = field;
        this.fieldId = fieldId;
    }


}
