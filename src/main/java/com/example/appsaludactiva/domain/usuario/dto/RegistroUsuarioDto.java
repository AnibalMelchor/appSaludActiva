package com.example.appsaludactiva.domain.usuario.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record RegistroUsuarioDto(
        @NotBlank String nombreCompleto,
        @Past LocalDate fechaNacimiento,
        @Positive double altura,
        @Positive double peso,
        @NotBlank String usuario,
        @NotBlank String contrasena
) {
}
