package com.example.appsaludactiva.domain.alimentacion.catalogoAlimentos.dto;

import com.example.appsaludactiva.domain.alimentacion.catalogoAlimentos.entity.Alimento;
import com.example.appsaludactiva.domain.alimentacion.catalogoAlimentos.entity.UnidadMedida;

public record DetalleAlimentoDto(
        Long id,
        String nombre,
        Integer caloriasPorcion,
        Double tamanoPorcion,
        UnidadMedida unidadMedida,
        Boolean estatus
) {
    public DetalleAlimentoDto(Alimento alimento) {
        this(
                alimento.getId(),
                alimento.getNombre(),
                alimento.getCaloriasPorcion(),
                alimento.getTamanoPorcion(),
                alimento.getUnidadMedida(),
                alimento.isEstatus()
        );
    }
}
