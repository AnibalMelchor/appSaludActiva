package com.example.appsaludactiva.controller.seguimiento.doc;

import com.example.appsaludactiva.domain.diagnosticos.DetalleCaloriasDto;
import com.example.appsaludactiva.domain.diagnosticos.DatosDiagnosticoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

@Tag(name = "Informaccion diagnostica del Usuario", description = "Endpoints para mostrar un diagnostico de imc y calorias consumidas")
public interface DiagnosticoControllerDoc {

    @Operation(summary = "Diagnostico de Indice de Masa Corporal",
            description = "Se meustra un diagnostico sobre su estado de Masa de Corporal")

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Diagnostico de IMC obtenido exitosamente"),
            @ApiResponse(responseCode = "404", description = "No existe un usuario con ese ID")
    })
    ResponseEntity<DatosDiagnosticoDto> obtenerDiagnosticoImc(
            @Parameter(description = "ID del usuario", required = true, example = "1") Long id);

    @Operation(summary = "Diagnostico de Calorias",
            description = "Se muestra un diagnostico sobre su consumo de calorias por dia")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Diagnostico de calorias btenido exitosamente",
                    content = @Content

            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No existe un usuario con ese ID",
                    content = @Content
            )
    })
    ResponseEntity<DetalleCaloriasDto> detalleClorias(
            @Parameter(description = "ID del usuario", required = true, example = "1") Long id,
            @Parameter(description = "Fecha para el filtrado de las calorias consumidas", required = false, example = "2026-08-11") LocalDate fecha);
}
