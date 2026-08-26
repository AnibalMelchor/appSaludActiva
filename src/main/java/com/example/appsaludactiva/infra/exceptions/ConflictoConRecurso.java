package com.example.appsaludactiva.infra.exceptions;

public class ConflictoConRecurso extends RuntimeException {
    public ConflictoConRecurso(String message) {
        super(message);
    }
}
