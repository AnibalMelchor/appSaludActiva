package com.example.appsaludactiva.domain.rutina.catalogoEjercicios.dto;

import com.example.appsaludactiva.domain.rutina.catalogoEjercicios.entity.Ejercicio;

public record EjerciciosDto(
        String nombre,
        String descripcion,
        int caloriasPorMinuto,
        boolean estatus
) {
    public EjerciciosDto(Ejercicio ejercicio) {
        this(ejercicio.getNombre(), ejercicio.getDescripcion(), ejercicio.getCaloriasPorMinuto(),ejercicio.isEstatus());

    }
}
