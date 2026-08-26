package com.example.appsaludactiva.controller.seguimiento;

import com.example.appsaludactiva.controller.seguimiento.doc.DiagnosticoControllerDoc;
import com.example.appsaludactiva.domain.diagnosticos.DetalleCaloriasDto;
import com.example.appsaludactiva.domain.diagnosticos.DatosDiagnosticoDto;
import com.example.appsaludactiva.domain.diagnosticos.DiagnosticoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/diagnosticos")
public class DiagnosticoController implements DiagnosticoControllerDoc {

    @Autowired
    private DiagnosticoService diagnosticoService;

    @GetMapping("/imc/{id}")
    @Override
    public ResponseEntity<DatosDiagnosticoDto> obtenerDiagnosticoImc(@PathVariable Long id) {
        var diagnostico = diagnosticoService.calcularImcUsuario(id);
        return ResponseEntity.ok(diagnostico);
    }

    @GetMapping("/calorias/{id}")
    @Override
    public ResponseEntity<DetalleCaloriasDto> detalleClorias(@PathVariable Long id,
                                                             @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha){
        var detalle = diagnosticoService.detalleCalorias(id,fecha);
        return ResponseEntity.ok(detalle);
    }
}
