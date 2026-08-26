package com.example.appsaludactiva.domain.rutina.registroDiario.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record RegistroEjercicioDto(
        @Min(value = 1, message = "Debes ingresar el id del usuario")
        @NotNull  Long ejercicio_id,
        @Min(value = 1, message = "Debes ingresar al menos 1 min de duracion")
        @NotNull int duracionMinutos

) {
}
