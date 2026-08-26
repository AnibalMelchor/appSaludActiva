package com.example.appsaludactiva.controller.usuarios.doc;

import com.example.appsaludactiva.domain.usuario.dto.ActualizarUsuarioDto;
import com.example.appsaludactiva.domain.usuario.dto.DetalleUsuarioDto;
import com.example.appsaludactiva.domain.usuario.dto.ListaUsuariosDto;
import com.example.appsaludactiva.domain.usuario.dto.RegistroUsuarioDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Crud del usuario", description = "Endpoints para realizar registro, modificacion, listar o desactivar usuario")
public interface UsuarioControllerDoc {

    @Operation(summary = "Registro del perfil",
            description = "Se registra el usuario para la creacion de su perfil")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado correctamente"),
            @ApiResponse(responseCode = "409", description = "Ya existe un usuario con ese usuario, favor de colocar uno diferente")
    })
    ResponseEntity<String> registrarUsuario(RegistroUsuarioDto datos);

    @Operation(summary = "Modificacion del perfil",
            description = "Se actualiza el perfil del usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario modificado correctamente"),
            @ApiResponse(responseCode = "409", description = "No existe un perfil con ese usuario")
    })
    ResponseEntity<String> actualizarUsuario(
            @Parameter(description = "ID del ejercicio", required = true, example = "1") Long id, ActualizarUsuarioDto datos);

    @Operation(summary = "Informacion de un perfil",
            description = "Se obtiene la informacion en especifico de un perfil")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se muestran el perfil registrado"),
            @ApiResponse(responseCode = "404", description = "No existe un perfil con ese ID")
    })
    DetalleUsuarioDto obtenerUsuario(@Parameter(description = "ID del ejercicio", required = true, example = "1") Long id);

    @Operation(summary = "Lista de usuarios",
            description = "Se muestra una lista de los usuarios registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuarios consultados correctamente"),
            @ApiResponse(responseCode = "404", description = "No hay ningun usuario registrado")
    })
    List<ListaUsuariosDto> listarUsuarios();

    @Operation(summary = "Desactivar un usuario",
            description = "Se permite desactivar un perfil")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Usuario desactivado exitosamente (Sin contenido)",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No existe un perfil con ese usuario",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Ya se encuentra desactivado este perfil",
                    content = @Content
            )
    })
    ResponseEntity<String> desactivarUsuario(@Parameter(description = "ID del ejercicio", required = true, example = "1") Long id);
}
