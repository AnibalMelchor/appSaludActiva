package com.example.appsaludactiva.controller.seguimiento.doc;

import com.example.appsaludactiva.domain.seguimiento.sueno.dto.DetalleSuenoDto;
import com.example.appsaludactiva.domain.seguimiento.sueno.dto.RegistroSuenoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Seguimiendo de sueno", description = "Endpoints para llevar el seguimiento del control de sueno")
public interface SuenoControllerDoc {

    @Operation(summary = "Seguimiento al control de sueño",
            description = "Se registran las horas dormidas ")
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
                    description = "Horas de sueño ya registradas o no puedes dormir 24 horas o mas, ingresa una cantidad real o acude con un especialista",
                    content = @Content
            )
    })
    ResponseEntity<DetalleSuenoDto> registroSueno(RegistroSuenoDto datos,
                                                  @Parameter(description = "ID del usuario", required = true, example = "1")  Long perfilId);

    @Operation(summary = "Seguimiento al registro del sueño",
            description = "Se va mostrando el detalle de las horas dormidas")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Detalle mostrado correctamente",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No existe un usuario con ese ID",
                    content = @Content
            )
    })
    ResponseEntity<DetalleSuenoDto> detalleSueno(@Parameter(description = "ID del usuario", required = true, example = "1")  Long perfilId);
}
