package com.example.appsaludactiva.domain.seguimiento.agua.dto;

import jakarta.validation.constraints.Min;

public record RegistroAguaDto(
        @Min(value = 1, message = "Debes ingresar al menos 1 vaso")
        int cantidadVasos
) {
}
