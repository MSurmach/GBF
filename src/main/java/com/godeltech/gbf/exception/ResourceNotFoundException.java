package com.godeltech.gbf.exception;

import static com.godeltech.gbf.utils.ConstantUtils.ExceptionMessages.NO_FOUND_PATTERN;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(Class<?> resourceType, String fieldName, Object fieldValue) {
        super(String.format(NO_FOUND_PATTERN, resourceType, fieldName, fieldValue));
    }
}
