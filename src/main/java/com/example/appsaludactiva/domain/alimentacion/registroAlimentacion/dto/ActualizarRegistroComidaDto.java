package com.example.appsaludactiva.domain.alimentacion.registroAlimentacion.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public record ActualizarRegistroComidaDto(
        @Size(min = 1)
        @NotEmpty
        List<DetalleComidaDto> detalles) {
}
