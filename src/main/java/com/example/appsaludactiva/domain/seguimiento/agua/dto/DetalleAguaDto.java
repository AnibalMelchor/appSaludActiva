package com.example.appsaludactiva.domain.seguimiento.agua.dto;

import java.util.List;

public record DetalleAguaDto(
        int metaActual,
        String estadoHidratacion,
        List<String> recomendaciones
) {
}
