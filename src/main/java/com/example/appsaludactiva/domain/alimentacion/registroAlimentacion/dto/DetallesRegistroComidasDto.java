package com.example.appsaludactiva.domain.alimentacion.registroAlimentacion.dto;

import com.example.appsaludactiva.domain.alimentacion.registroAlimentacion.entity.DetalleRegistroComida;

public record DetallesRegistroComidasDto(
    Long id,
    String alimentoNombre,
    double cantidad,
    double caloriasSubtotales
) {
    public DetallesRegistroComidasDto(DetalleRegistroComida detalle) {
        this(
                detalle.getId(),
                detalle.getAlimento().getNombre(), // Se navega la relación ManyToOne
                detalle.getCantidad(),
                detalle.getCaloriasSubtotal()
        );
    }
}
