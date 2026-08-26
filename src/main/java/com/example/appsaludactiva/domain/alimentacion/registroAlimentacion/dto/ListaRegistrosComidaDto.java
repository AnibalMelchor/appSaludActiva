package com.example.appsaludactiva.domain.alimentacion.registroAlimentacion.dto;

import com.example.appsaludactiva.domain.alimentacion.registroAlimentacion.entity.RegistroComida;
import com.example.appsaludactiva.domain.alimentacion.registroAlimentacion.entity.TipoComida;

import java.time.LocalDateTime;
import java.util.List;

public record ListaRegistrosComidaDto(
        Long id,
        LocalDateTime fechaRegistro,
        TipoComida tipoComida,
        double caloriasTotales,
        List<DetallesRegistroComidasDto> detalles
) {
    public ListaRegistrosComidaDto(RegistroComida comida) {
        this(
                comida.getId(),
                comida.getFechaRegistro(),
                comida.getTipoComida(),
                comida.getCaloriasTotales(),
                comida.getDetalles().stream()
                        .map(DetallesRegistroComidasDto::new)
                        .toList()
        );
    }
}
