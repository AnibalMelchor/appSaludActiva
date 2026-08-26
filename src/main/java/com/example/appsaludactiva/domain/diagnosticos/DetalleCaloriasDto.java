package com.example.appsaludactiva.domain.diagnosticos;

import java.util.List;

public record DetalleCaloriasDto(
        Double CaloriasConsumidas,
        String estadoNutricional,
        List<String> recomendaciones
) {
}
