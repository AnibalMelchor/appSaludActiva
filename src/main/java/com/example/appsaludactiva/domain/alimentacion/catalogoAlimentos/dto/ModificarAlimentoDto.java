package com.example.appsaludactiva.domain.alimentacion.catalogoAlimentos.dto;

import com.example.appsaludactiva.domain.alimentacion.catalogoAlimentos.entity.UnidadMedida;

public record ModificarAlimentoDto(
        String nombre,
        Integer caloriasPorcion,
        Double tamanoPorcion,
        UnidadMedida unidadMedida
) {
}
