package com.miromori.project_sympla_entrega_2.Exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException() {
        super("Nothing found");
    }
}
