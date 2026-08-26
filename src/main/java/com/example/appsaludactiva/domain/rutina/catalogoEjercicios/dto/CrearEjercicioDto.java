package com.example.appsaludactiva.domain.rutina.catalogoEjercicios.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CrearEjercicioDto(
        @Size(max = 30, message = "El nombre no puede superar los 30 caracteres")
        @NotBlank String nombreEjercicio,
        @NotBlank String descripcion,
        @Min(value = 1, message = "Debes ingresar al menos 1 caloria")
        int caloriasPorMinuto
) {
}
