package com.example.appsaludactiva.controller.alimentacion.doc;

import com.example.appsaludactiva.domain.alimentacion.registroAlimentacion.dto.ActualizarRegistroComidaDto;
import com.example.appsaludactiva.domain.alimentacion.registroAlimentacion.dto.CrearRegistroComidaDto;
import com.example.appsaludactiva.domain.alimentacion.registroAlimentacion.dto.ListaRegistrosComidaDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Registro de Comida", description = "Endpoints para la gestión del diario de alimentación del usuario")
public interface RegistroComidaControllerDoc {

    @Operation(summary = "Registrar una nueva comida", description = "Crea un registro de comida (desayuno, comida, cena, etc.) para el día actual y calcula el total de calorías.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Comida registrada exitosamente"),
            @ApiResponse(responseCode = "409", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "ya existe un registro del mismo tipo")
    })
    ResponseEntity<String> registroAlimentacion(
            CrearRegistroComidaDto datos,
            @Parameter(description = "ID del perfil del usuario", required = true, example = "1") Long id
    );

    @Operation(
            summary = "Obtener registros de comida por perfil y fecha",
            description = "Recupera la lista de registros de comida asociados al ID del perfil. Opcionalmente se puede filtrar por una fecha específica (formato YYYY-MM-DD)."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Registros obtenidos exitosamente",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ListaRegistrosComidaDto.class)))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Perfil o registros no encontrados",
                    content = @Content
            )
    })

    ResponseEntity<List<ListaRegistrosComidaDto>> obtenerRegistro(
            @Parameter(description = "ID del perfil del usuario", required = true, example = "1") Long id,
            @Parameter(description = "Fecha para el filtrado de las comidas registradas", required = false, example = "2026-08-11") LocalDate fecha
    );

    @Operation(
            summary = "Eliminar un registro de comida",
            description = "Elimina un registro de comida específico perteneciente al perfil indicado."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Registro de comida eliminado exitosamente (Sin contenido)",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Registro de comida o perfil no encontrado",
                    content = @Content
            )
    })
    ResponseEntity<Void> eliminarRegistro(@Parameter(description = "ID del perfil del usuario", required = true, example = "1") Long perfilId,
                                    @Parameter(description = "ID de la comida registrada", required = true, example = "1") Long comidaId);

    @Operation(summary = "Obtener detalle de una comida específica",
            description = "Devuelve el detalle de un registro de comida para pre-llenar la interfaz de edición.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalle obtenido correctamente"),
            @ApiResponse(responseCode = "400", description = "No existe la comida o no pertenece al perfil")
    })
    ResponseEntity<ListaRegistrosComidaDto> obtenerRegistroUnico(
            @Parameter(description = "ID del perfil del usuario", required = true, example = "1") Long perfilId,
            @Parameter(description = "ID de la comida registrada", required = true, example = "1") Long comidaId);

    @Operation(summary = "Modificar un registro de comida", description = "Reemplaza los alimentos consumidos y recalcula calorías. Solo se permite para registros creados el día de hoy.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Registro no encontrado"),
            @ApiResponse(responseCode = "409", description = "Intento de modificar un registro de día anterior o alimentos inválidos/desactivados")
    })
    ResponseEntity<String> modificarRegistro(ActualizarRegistroComidaDto datos,
                                     @Parameter(description = "ID del perfil del usuario", required = true, example = "1") Long perfilId,
                                     @Parameter(description = "ID de la comida registrada", required = true, example = "1") Long comidaId);
}
