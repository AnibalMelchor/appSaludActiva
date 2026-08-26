package com.example.appsaludactiva.domain.diagnosticos;

import java.util.List;

public record DatosDiagnosticoDto(
        Double imc,
        String estadoNutricional,
        List<String> recomendaciones
) {
}
