package com.example.appsaludactiva.domain.rutina.registroDiario.dto;

import java.time.LocalDate;
import java.util.List;

public record ResumenEjercicioDiarioDto(
        int totalCaloriasQuemadas,
        LocalDate fechaEjercicio,
        List<DetalleEjercicioItemDto> ejercicios
) {

}
