package com.example.appsaludactiva.domain.rutina.catalogoEjercicios.dto;

public record ActualizarEjercicioDto(
        String nombreEjercicio,
        String descripcion,
        int caloriasPorMinuto
) {
}
