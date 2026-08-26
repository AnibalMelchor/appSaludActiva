package com.example.appsaludactiva.controller.rutinas;

import com.example.appsaludactiva.controller.rutinas.doc.EjericicioControllerDoc;
import com.example.appsaludactiva.domain.rutina.catalogoEjercicios.dto.ActualizarEjercicioDto;
import com.example.appsaludactiva.domain.rutina.catalogoEjercicios.dto.CrearEjercicioDto;
import com.example.appsaludactiva.domain.rutina.catalogoEjercicios.dto.DetalleEjercicioDto;
import com.example.appsaludactiva.domain.rutina.catalogoEjercicios.dto.EjerciciosDto;
import com.example.appsaludactiva.domain.rutina.catalogoEjercicios.service.EjerciciosService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ejercicios")
public class EjerciciosController implements EjericicioControllerDoc {

    @Autowired
    private EjerciciosService ejerciciosService;

    @PostMapping
    @Override
    public ResponseEntity<String> registroEjercicio(@Valid @RequestBody CrearEjercicioDto datos){
        ejerciciosService.registroEjercicios(datos);
        return ResponseEntity.status(HttpStatus.CREATED).body("Ejercicio registrado con exito");
    }

    @GetMapping()
    @Override
    public List<EjerciciosDto> Ejercicios(){
        return ejerciciosService.catalogoEjercicios();
    }

    @GetMapping("{id}")
    @Override
    public ResponseEntity<DetalleEjercicioDto> detalleEjercicio(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(ejerciciosService.detalleEjercicio(id));
    }

    @PutMapping("{id}")
    @Override
    public ResponseEntity<String> actualizarEjercicio(@PathVariable Long id, @RequestBody ActualizarEjercicioDto datos){
        ejerciciosService.actualizarEjecicio(datos,id);
        return ResponseEntity.ok().body("Ejercicio actualizado correctamente");
    }

    @DeleteMapping("{id}")
    @Override
    public ResponseEntity<String> desactivarEjercicio(@PathVariable Long id){
        ejerciciosService.desactivarEjercicio(id);
        return ResponseEntity.status(HttpStatus.OK).body("Ejercicio desactivado con exito");
    }
}
