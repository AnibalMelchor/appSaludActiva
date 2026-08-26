package com.example.appsaludactiva.controller.rutinas.doc;

import com.example.appsaludactiva.domain.rutina.catalogoEjercicios.dto.ActualizarEjercicioDto;
import com.example.appsaludactiva.domain.rutina.catalogoEjercicios.dto.CrearEjercicioDto;
import com.example.appsaludactiva.domain.rutina.catalogoEjercicios.dto.DetalleEjercicioDto;
import com.example.appsaludactiva.domain.rutina.catalogoEjercicios.dto.EjerciciosDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "CRUD de ejercicios", description = "Endpoints para registrar,actualizar, listar y desactivar de ejercicios")
public interface EjericicioControllerDoc {

    @Operation(summary = "Registrar un nuevo ejercicio",
            description = "Crea el registro de un ejercicio (cardio, total body, biceps, etc.)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ejercicio creado correctamente"),
            @ApiResponse(responseCode = "409", description = "Ya existente un ejercicio con el mismo nombre")
    })
    ResponseEntity<String> registroEjercicio(CrearEjercicioDto datos);

    @Operation(summary = "Listar los ejercicios",
            description = "Se muestran los ejercicios previamente registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ejercicio consultados correctamente"),
            @ApiResponse(responseCode = "404", description = "No hay ningun ejercicio registrado")
    })
    List<EjerciciosDto> Ejercicios();

    @Operation(summary = "Obtener detalle de un ejercicio",
            description = "Se muestran la informacion de un ejercicio en especifico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se muestran el ejercicio registrado"),
            @ApiResponse(responseCode = "404", description = "Registro del ejercicio no encontrado")
    })
    ResponseEntity<DetalleEjercicioDto> detalleEjercicio(
            @Parameter(description = "ID del ejercicio", required = true, example = "1") Long id);

    @Operation(summary = "Modificar un ejercicio",
            description = "Se modfica un ejercicio previamente registrado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ejercicio modificado exitosamente"),
            @ApiResponse(responseCode = "404", description = "No existe un ejercicio con ese Id"),
            @ApiResponse(responseCode = "409", description = "No puedes actualizar un ejercicio desactivado")
    })
    ResponseEntity<String> actualizarEjercicio(
            @Parameter(description = "ID del ejercicio", required = true, example = "1") Long id, ActualizarEjercicioDto datos);


    @Operation(summary = "Desactivar un ejercicio",
            description = "Se descativa un ejercicio previamente registrado, no se elimina")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Ejercicio desactivado exitosamente (Sin contenido)",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No existe un ejercicio con ese Id",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Ya se encuentra desactivado este ejercicio",
                    content = @Content
            )
    })
    ResponseEntity<String> desactivarEjercicio(
            @Parameter(description = "ID del ejercicio", required = true, example = "1")Long id);
}
