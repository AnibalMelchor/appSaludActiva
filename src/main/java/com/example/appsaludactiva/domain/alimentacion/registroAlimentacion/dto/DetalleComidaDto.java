package com.example.appsaludactiva.domain.alimentacion.registroAlimentacion.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DetalleComidaDto(
        @Schema(description = "ID del alimento a registrar", example = "1")
        @NotNull Long alimentoId,
        @Schema(description = "Cantidad de porciones o gramos consumidos", example = "2.5")
        @NotNull @Positive Double cantidadConsumida
) {
}
