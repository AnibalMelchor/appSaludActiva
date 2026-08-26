package com.example.appsaludactiva.infra.exceptions;

public class ValidacionException extends RuntimeException {
    public ValidacionException(String message) {
        super(message);
    }
}
