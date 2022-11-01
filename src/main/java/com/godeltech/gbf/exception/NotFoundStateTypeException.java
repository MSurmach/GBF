package com.godeltech.gbf.exception;

public class NotFoundStateTypeException extends RuntimeException {
    public NotFoundStateTypeException(Class<?> classType, String username, Long id) {
        super(String.format("State wasn't found in class : %s by user : %s with id : %s",classType,
                username,id));
    }
}
