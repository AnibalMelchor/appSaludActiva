package com.example.appsaludactiva.controller.usuarios.doc;

import com.example.appsaludactiva.domain.usuario.dto.autentication.DatosAutenticacionDto;
import com.example.appsaludactiva.domain.usuario.dto.autentication.DatosJWTTokenDto;
import com.example.appsaludactiva.infra.exceptions.GestionDeErrores;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Autenticación", description = "Endpoint para iniciar sesión y obtener el token JWT de acceso")
public interface AutenticationControllerDoc {

    @Operation(
            summary = "Iniciar sesión de usuario",
            description = "Valida las credenciales de usuario y contraseña para autenticar a un usuario registrado y retornar un token JWT válido para consumir la API."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Autenticación exitosa. Retorna el token JWT.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DatosJWTTokenDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de entrada inválidos o faltantes en la petición."
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Credenciales incorrectas (usuario o contraseña no válidos).",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GestionDeErrores.DatosErrorRespuesta.class))
            )
    })
    ResponseEntity<DatosJWTTokenDto> autenticarUsuario(DatosAutenticacionDto datos);
}
