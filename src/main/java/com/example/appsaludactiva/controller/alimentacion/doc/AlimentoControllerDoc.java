package com.example.appsaludactiva.controller.alimentacion.doc;

import com.example.appsaludactiva.domain.alimentacion.catalogoAlimentos.dto.CrearAlimentoDto;
import com.example.appsaludactiva.domain.alimentacion.catalogoAlimentos.dto.DetalleAlimentoDto;
import com.example.appsaludactiva.domain.alimentacion.catalogoAlimentos.dto.ModificarAlimentoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "CRUD de alimentos", description = "Endpoints para registrar,actualizar, listar y desactivar alimentos")
public interface AlimentoControllerDoc {

    @Operation(summary = "Registrar un nuevo alimento",
            description = "Crea el registro de un alimento (pollo, bistek, sopa, etc.)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Alimento creado correctamente"),
            @ApiResponse(responseCode = "409", description = "Ya existe un alimento registrado con el mismo nombre")
    })
    ResponseEntity<String> crearAlimento(CrearAlimentoDto datos);

    @Operation(summary = "Listar los alimentos",
            description = "Se muestran los alimentos previamente registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se muestran los alimentos registrados")
    })
    ResponseEntity<List<DetalleAlimentoDto>> listarAlimentos();

    @Operation(summary = "Obtener detalle de un alimento",
            description = "Se muestran la informacion de un alimento en especifico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se muestran el alimento registrado"),
            @ApiResponse(responseCode = "404", description = "Registro de alimento no encontrado")
    })
    ResponseEntity<DetalleAlimentoDto> DetalleAlimento(
            @Parameter(description = "ID del alimento a consultar", required = true, example = "1")Long id);

    @Operation(summary = "Modificar un alimento",
            description = "Se modfica un alimento previamente registrado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alimento modificado exitosamente"),
            @ApiResponse(responseCode = "409", description = "Ya existe un alimento registrado con el mismo nombre")
    })
    ResponseEntity<String> modificarAlimento(ModificarAlimentoDto datos,
                                             @Parameter(description = "ID del alimento a modificar", required = true, example = "1") Long id);

    @Operation(summary = "Desactivar un alimento",
            description = "Se descativa un alimento previamente registrado, no se elimina")

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Alimento desactivado exitosamente (Sin contenido)",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Registro de alimento no encontrado",
                    content = @Content
            )
    })
    ResponseEntity desactivarAlimento(
            @Parameter(description = "ID del alimento a eliminar", required = true, example = "1") Long id);
}
