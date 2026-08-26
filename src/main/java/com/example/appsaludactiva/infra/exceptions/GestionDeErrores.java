package com.example.appsaludactiva.infra.exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GestionDeErrores {
    @ExceptionHandler(ValidacionException.class)
    public ResponseEntity gestionarErrorDeValidacion(ValidacionException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DatosErrorRespuesta(ex.getMessage()));
    }
    @ExceptionHandler(ConflictoConRecurso.class)
    public ResponseEntity gestionarErrorConflicto(ConflictoConRecurso ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new DatosErrorRespuesta(ex.getMessage()));

    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity gestionarError400(MethodArgumentNotValidException ex){
        var errores = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(errores.stream().map(DatosErrorValidacion::new).toList());
    }
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity gestionarError404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity gestionarBadCredentials() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new DatosErrorRespuesta("Usuario o contraseña incorrectos"));
    }

    public record DatosErrorValidacion(
            String campo,
            String mensaje
    ){
        public DatosErrorValidacion(FieldError error){
            this(error.getField(), error.getDefaultMessage());
        }
    }
    public record DatosErrorRespuesta(
            String mensaje
    ) {}
}
