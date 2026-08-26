package com.example.appsaludactiva.domain.seguimiento.sueno.dto;

import jakarta.validation.constraints.Min;

public record RegistroSuenoDto(
        @Min(value = 1, message = "Debes ingresar al menos 1 hora")
        Double cantidadHoras
) {
}
