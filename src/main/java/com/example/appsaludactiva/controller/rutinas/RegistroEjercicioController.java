package com.example.appsaludactiva.controller.rutinas;

import com.example.appsaludactiva.controller.alimentacion.doc.RegistroComidaControllerDoc;
import com.example.appsaludactiva.controller.rutinas.doc.RegistroEjercicioControllerDoc;
import com.example.appsaludactiva.domain.rutina.registroDiario.dto.ModificarRegistroEjercicioDto;
import com.example.appsaludactiva.domain.rutina.registroDiario.dto.RegistroEjercicioDto;
import com.example.appsaludactiva.domain.rutina.registroDiario.dto.ResumenEjercicioDiarioDto;
import com.example.appsaludactiva.domain.rutina.registroDiario.service.RegistroEjercicioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/registro-ejercicio")
public class RegistroEjercicioController implements RegistroEjercicioControllerDoc {

    @Autowired
    private RegistroEjercicioService registroEjercicioService;

    @PostMapping("/{id}")
    @Override
    public ResponseEntity<String> guardarRegistro(@RequestBody @Valid RegistroEjercicioDto datos, @PathVariable Long id){
        registroEjercicioService.guardarRegistro(datos,id);
        return ResponseEntity.status(HttpStatus.CREATED).body("Registro exitoso de entrenamiento");
    }
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ResumenEjercicioDiarioDto> obtenerRegistroDiario(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @PathVariable Long id) {
        ResumenEjercicioDiarioDto resumen = registroEjercicioService.obtenerResumenDiario(fecha, id);
        return ResponseEntity.ok(resumen);
    }
    @Override
    @PutMapping("{id}")
    public ResponseEntity<String> modificarRegistros(@RequestBody ModificarRegistroEjercicioDto datos,@PathVariable Long id){
        registroEjercicioService.modificarRegistros(datos,id);
        return ResponseEntity.ok("Registro Modificado correctamente");
    }
    @Override
    @DeleteMapping("{id}")
    public ResponseEntity eliminarRegistroEjercicio(@PathVariable Long id){
        registroEjercicioService.eliminarRegistroEjercicio(id);
        return ResponseEntity.noContent().build();
    }
}
