package com.example.appsaludactiva.domain.usuario.dto;

import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record ActualizarUsuarioDto(
        String nombreCompleto,
        LocalDate fechaNacimiento,
        Double altura,
        Double peso,
        String usuario,
        String contrasena
) {
}
