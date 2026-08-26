package com.example.appsaludactiva.controller.usuarios;

import com.example.appsaludactiva.controller.usuarios.doc.AutenticationControllerDoc;
import com.example.appsaludactiva.domain.usuario.dto.autentication.DatosAutenticacionDto;
import com.example.appsaludactiva.domain.usuario.dto.autentication.DatosJWTTokenDto;
import com.example.appsaludactiva.domain.usuario.entity.Credenciales;
import com.example.appsaludactiva.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacionController implements AutenticationControllerDoc {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;


    @PostMapping
    @Override
    public ResponseEntity<DatosJWTTokenDto> autenticarUsuario(@RequestBody @Valid DatosAutenticacionDto datos) {
        Authentication authToken = new UsernamePasswordAuthenticationToken(datos.usuario(), datos.contrasena());

        // 2. Autentica (Spring Security usará internamente AutenticacionService y BCrypt)
        var usuarioAutenticado = authenticationManager.authenticate(authToken);

        // 3. Obtiene la entidad validada y genera el token
        var credencial = (Credenciales) usuarioAutenticado.getPrincipal();
        var jwtToken = tokenService.generarToken(credencial);

        return ResponseEntity.ok(new DatosJWTTokenDto(jwtToken));
    }


}
