package com.example.appsaludactiva.domain.alimentacion.registroAlimentacion.dto;

import com.example.appsaludactiva.domain.alimentacion.registroAlimentacion.entity.TipoComida;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CrearRegistroComidaDto(
        @Schema(description = "Tipo de comida a registrar", example = "DESAYUNO-COMIDA-CENA-COLACION")
        @NotNull TipoComida tipoComida,

        @Size(min = 1)
        @NotEmpty List<DetalleComidaDto> detalles
) {
}
