package com.example.appsaludactiva.domain.alimentacion.catalogoAlimentos.dto;

import com.example.appsaludactiva.domain.alimentacion.catalogoAlimentos.entity.UnidadMedida;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record CrearAlimentoDto(
        @NotBlank String nombre,

        @NotNull
        @PositiveOrZero(message = "Las calorías no pueden ser negativas")
        Integer caloriasPorcion,

        @NotNull
        @Positive(message = "El tamaño de porción debe ser mayor a cero")
        Double tamanoPorcion,

        @NotNull UnidadMedida unidadMedida
) {
}
