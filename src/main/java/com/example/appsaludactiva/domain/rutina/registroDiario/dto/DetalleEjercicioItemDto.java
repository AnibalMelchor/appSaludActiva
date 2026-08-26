package com.example.appsaludactiva.domain.rutina.registroDiario.dto;

public record DetalleEjercicioItemDto(
        String nombreEjercicio,
        int duracionMinutos,
        int caloriasQuemadas,
        String horaRegistro
) {
}
