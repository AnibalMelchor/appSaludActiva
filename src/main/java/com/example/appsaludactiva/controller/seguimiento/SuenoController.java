package com.example.appsaludactiva.controller.seguimiento;

import com.example.appsaludactiva.controller.seguimiento.doc.SuenoControllerDoc;
import com.example.appsaludactiva.domain.seguimiento.sueno.dto.DetalleSuenoDto;
import com.example.appsaludactiva.domain.seguimiento.sueno.dto.RegistroSuenoDto;
import com.example.appsaludactiva.domain.seguimiento.sueno.service.SuenoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registro-diario")
public class SuenoController implements SuenoControllerDoc {
    @Autowired
    private SuenoService suenoService;

    @PostMapping("/sueno/{id}")
    @Override
    public ResponseEntity<DetalleSuenoDto> registroSueno(@Valid @RequestBody RegistroSuenoDto datos, @PathVariable Long id){
        var sueno = suenoService.registroSueno(datos,id);
        return ResponseEntity.status(HttpStatus.CREATED).body(sueno);
    }

    @GetMapping("/sueno/{id}")
    @Override
    public ResponseEntity<DetalleSuenoDto> detalleSueno(@PathVariable Long id){
        var sueno = suenoService.detalleSueno(id);
        return ResponseEntity.ok(sueno);
    }
}
