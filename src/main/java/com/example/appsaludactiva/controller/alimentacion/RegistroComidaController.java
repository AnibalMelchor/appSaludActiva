package com.example.appsaludactiva.controller.alimentacion;

import com.example.appsaludactiva.controller.alimentacion.doc.RegistroComidaControllerDoc;
import com.example.appsaludactiva.domain.alimentacion.registroAlimentacion.dto.*;
import com.example.appsaludactiva.domain.alimentacion.registroAlimentacion.service.RegistroComidaService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/registrar-alimentacion")
public class RegistroComidaController implements RegistroComidaControllerDoc {

    private final RegistroComidaService registroComidaService;

    public RegistroComidaController(RegistroComidaService registroComidaService) {
        this.registroComidaService = registroComidaService;
    }

    @Override
    @PostMapping("/{id}")
    public ResponseEntity<String> registroAlimentacion(@Valid @RequestBody CrearRegistroComidaDto datos, @PathVariable Long id){
        registroComidaService.registrarComida(datos,id);
        return ResponseEntity.status(HttpStatus.CREATED).body("Datos alimenticios registrados correctamente");
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<List<ListaRegistrosComidaDto>> obtenerRegistro(
            @PathVariable Long id, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha){
        List<ListaRegistrosComidaDto> registro = registroComidaService.obtenerRegistro(id,fecha);
        return ResponseEntity.ok(registro);
    }

    @Override
    @DeleteMapping("/{perfilId}/{comidaId}")
    public ResponseEntity<Void> eliminarRegistro(@PathVariable Long perfilId, @PathVariable Long comidaId){
        registroComidaService.EliminarRegistro(perfilId,comidaId);
        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping("/{perfilId}/{comidaId}")
    public ResponseEntity<ListaRegistrosComidaDto> obtenerRegistroUnico(@PathVariable Long perfilId, @PathVariable Long comidaId){
        ListaRegistrosComidaDto registro = registroComidaService.obtenerRegistroUnico(perfilId,comidaId);
        return ResponseEntity.ok(registro);
    }

    @Override
    @PutMapping("/{perfilId}/{comidaId}")
    public ResponseEntity<String> modificarRegistro(@Valid @RequestBody ActualizarRegistroComidaDto datos, @PathVariable Long perfilId, @PathVariable Long comidaId){
        registroComidaService.modificarRegistro(datos,perfilId,comidaId);
        return ResponseEntity.ok().body("Datos alimenticios modificados correctamente");
    }
}