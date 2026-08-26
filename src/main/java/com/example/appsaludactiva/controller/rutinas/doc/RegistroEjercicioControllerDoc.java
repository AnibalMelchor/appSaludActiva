package com.example.appsaludactiva.controller.rutinas.doc;

import com.example.appsaludactiva.domain.rutina.registroDiario.dto.ModificarRegistroEjercicioDto;
import com.example.appsaludactiva.domain.rutina.registroDiario.dto.RegistroEjercicioDto;
import com.example.appsaludactiva.domain.rutina.registroDiario.dto.ResumenEjercicioDiarioDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

@Tag(name = "Registro de ejercicios", description = "Endpoints para la gestión del diario de entrenamientos del usuario")
public interface RegistroEjercicioControllerDoc {

    @Operation(summary = "Registros de los entrenamientos del usuario",
            description = "Crea el registro de un entrenamiento relizado por el usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Registro exitoso de entrenamiento"),
            @ApiResponse(responseCode = "409", description = "El ejercicio seleccionado está inactivo en el catálogo o Una sesión no puede superar los 300 minutos"),
            @ApiResponse(responseCode = "404", description = "No existe un usuario con ese Id o no existe el ejercicio seleccionado ")
    })
    ResponseEntity<String> guardarRegistro(RegistroEjercicioDto datos, Long id);

    @Operation(summary = "Obtener registro de los entrenamientos realizados",
            description = "Obtener todos los registros realizados en una fecha especifica")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Registros obtenidos exitosamente",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Perfil o registros no encontrados",
                    content = @Content
            )
    })
    ResponseEntity<ResumenEjercicioDiarioDto> obtenerRegistroDiario( LocalDate fecha,Long id);

    @Operation(summary = "Modificar un entrenamiento en especifico",
            description = "Se modifica un entrenamiento registrado por algun error al registro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "No existe registro del ejercicio a modificar o no existe el ejercicio seleccionado"),
            @ApiResponse(responseCode = "409", description = "El ejercicio seleccionado está inactivo en el catálogo o una sesión no puede superar los 300 minutos")
    })
    ResponseEntity<String> modificarRegistros(ModificarRegistroEjercicioDto datos, Long id);

    @Operation(summary = "Eliminar un entrenamiento en especifico",
            description = "Se selecciona el entrenamiento a eliminar por algun error al registro")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Registro de entrenamiento eliminado exitosamente (Sin contenido)",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No existe registro del ejercicio a eliminar",
                    content = @Content
            )
    })
    ResponseEntity eliminarRegistroEjercicio(Long id);
}
