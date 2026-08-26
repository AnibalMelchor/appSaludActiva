package com.example.appsaludactiva.domain.seguimiento.sueno.dto;

import java.util.List;

public record DetalleSuenoDto(
        Double horasDormidas,
        String estadoSueno,
        List<String> recomendaciones
) {
}
