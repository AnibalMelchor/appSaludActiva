package com.example.appsaludactiva.controller.usuarios;

import com.example.appsaludactiva.controller.usuarios.doc.UsuarioControllerDoc;
import com.example.appsaludactiva.domain.usuario.dto.ActualizarUsuarioDto;
import com.example.appsaludactiva.domain.usuario.dto.DetalleUsuarioDto;
import com.example.appsaludactiva.domain.usuario.dto.ListaUsuariosDto;
import com.example.appsaludactiva.domain.usuario.dto.RegistroUsuarioDto;
import com.example.appsaludactiva.domain.usuario.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController implements UsuarioControllerDoc {

    @Autowired
    private UsuarioService usuarioService;


    @PostMapping
    @Override
    public ResponseEntity<String> registrarUsuario(@RequestBody @Valid RegistroUsuarioDto datos) {
        usuarioService.registrarUsuario(datos);
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuario creado correctamente");
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<String> actualizarUsuario(@PathVariable Long id,@RequestBody ActualizarUsuarioDto datos){
        usuarioService.actualizarUsuario(id,datos);
        return ResponseEntity.status(HttpStatus.OK).body("Usuario modificado correctamente");
    }

    @GetMapping("/{id}")
    @Override
    public DetalleUsuarioDto obtenerUsuario(@PathVariable Long id){
        return usuarioService.detalleUsuarios(id);
    }

    @GetMapping
    @Override
    public List<ListaUsuariosDto> listarUsuarios(){
        return usuarioService.listarUsuarios();
    }

    @DeleteMapping("{id}")
    @Override
    public ResponseEntity<String> desactivarUsuario(@PathVariable Long id){
        usuarioService.desactivarUsario(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Usuario desactivado correctamente");
    }
}
