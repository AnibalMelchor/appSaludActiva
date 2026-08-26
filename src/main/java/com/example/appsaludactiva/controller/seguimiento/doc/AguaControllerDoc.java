package com.example.appsaludactiva.controller.seguimiento.doc;

import com.example.appsaludactiva.domain.seguimiento.agua.dto.DetalleAguaDto;
import com.example.appsaludactiva.domain.seguimiento.agua.dto.RegistroAguaDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Seguimiendo de hidratacion", description = "Endpoints para llevar el seguimiento de hidratacion")
public interface AguaControllerDoc {

    @Operation(summary = "Seguimiento al registro de agua",
            description = "Se va registrando el seguimiento al cosumo de agua")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Registro realizado correctamente",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No existe un usuario con ese ID",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Ya has cumplido o  rebasado la meta ya no es necesario registrar tu consumo o " +
                                  "el consumo ingresado supera el límite diario saludable de 15 vasos. Por favor, verifica el dato.",
                    content = @Content
            )
    })
    ResponseEntity<DetalleAguaDto> registrarAgua(RegistroAguaDto datos,
                                                 @Parameter(description = "ID del usuario", required = true, example = "1") Long perfilId);

    @Operation(summary = "Informacion del seguimiento de agua",
            description = "Se meustra informacion de como va el seguimiendo del cosumo de agua")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Informacion consultada correctamente",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No existe un usuario con ese ID",
                    content = @Content
            )
    })
    ResponseEntity<DetalleAguaDto> consultarHidratacion(@Parameter(description = "ID del usuario", required = true, example = "1")Long perfilId);
}
