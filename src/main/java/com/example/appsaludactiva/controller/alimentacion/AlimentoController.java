package com.example.appsaludactiva.controller.alimentacion;

import com.example.appsaludactiva.controller.alimentacion.doc.AlimentoControllerDoc;
import com.example.appsaludactiva.domain.alimentacion.catalogoAlimentos.dto.CrearAlimentoDto;
import com.example.appsaludactiva.domain.alimentacion.catalogoAlimentos.dto.DetalleAlimentoDto;
import com.example.appsaludactiva.domain.alimentacion.catalogoAlimentos.dto.ModificarAlimentoDto;
import com.example.appsaludactiva.domain.alimentacion.catalogoAlimentos.service.AlimentoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alimentos")

public class AlimentoController implements AlimentoControllerDoc {

    @Autowired
    private AlimentoService alimentoService;

    @PostMapping
    @Override
    public ResponseEntity<String> crearAlimento(@RequestBody @Valid CrearAlimentoDto datos){
        alimentoService.crearAlimento(datos);
        return ResponseEntity.status(HttpStatus.CREATED).body("Alimento creado correctamente");
    }

    @GetMapping
    @Override
    public ResponseEntity<List<DetalleAlimentoDto>> listarAlimentos(){
        var lista = alimentoService.obtenerTodosLosAlimentos();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<DetalleAlimentoDto> DetalleAlimento(@PathVariable Long id) {
        var alimento = alimentoService.obtenerAlimento(id);
        return ResponseEntity.ok(alimento);
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<String> modificarAlimento(@RequestBody ModificarAlimentoDto datos, @PathVariable Long id){
        alimentoService.modificarAlimento(datos,id);
        return ResponseEntity.ok("Alimento modificado correctamente");
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity desactivarAlimento(@PathVariable Long id){
        alimentoService.desactivarAlimento(id);
        return ResponseEntity.noContent().build();
    }
}
