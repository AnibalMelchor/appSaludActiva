package com.example.appsaludactiva.controller.seguimiento;

import com.example.appsaludactiva.controller.seguimiento.doc.AguaControllerDoc;
import com.example.appsaludactiva.domain.seguimiento.agua.dto.DetalleAguaDto;
import com.example.appsaludactiva.domain.seguimiento.agua.dto.RegistroAguaDto;
import com.example.appsaludactiva.domain.seguimiento.agua.service.AguaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/seguimiento/agua")
public class AguaController implements AguaControllerDoc {
    private final AguaService aguaService;

    public AguaController(AguaService aguaService) {
        this.aguaService = aguaService;
    }

    @PostMapping("/{perfilId}")
    @Override
    public ResponseEntity<DetalleAguaDto> registrarAgua(@Valid @RequestBody RegistroAguaDto datos, @PathVariable Long perfilId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(aguaService.registroAgua(datos, perfilId));
    }

    @GetMapping("/{perfilId}")
    @Override
    public ResponseEntity<DetalleAguaDto> consultarHidratacion(@PathVariable Long perfilId) {
        return ResponseEntity.ok(aguaService.consultarHidratacion(perfilId));
    }
}
