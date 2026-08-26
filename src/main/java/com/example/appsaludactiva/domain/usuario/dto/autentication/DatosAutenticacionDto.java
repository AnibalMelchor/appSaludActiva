package com.example.appsaludactiva.domain.usuario.dto.autentication;

import jakarta.validation.constraints.NotBlank;

public record DatosAutenticacionDto(
        @NotBlank String usuario,
        @NotBlank String contrasena
) {
}
