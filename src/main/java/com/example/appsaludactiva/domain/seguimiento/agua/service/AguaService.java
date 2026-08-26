package com.example.appsaludactiva.domain.seguimiento.agua.service;

import com.example.appsaludactiva.domain.seguimiento.agua.dto.DetalleAguaDto;
import com.example.appsaludactiva.domain.seguimiento.agua.dto.RegistroAguaDto;
import com.example.appsaludactiva.domain.seguimiento.agua.entity.RegistroAgua;
import com.example.appsaludactiva.domain.seguimiento.agua.repository.RegistroAguaRepository;
import com.example.appsaludactiva.domain.usuario.entity.PerfilSalud;
import com.example.appsaludactiva.domain.usuario.repository.PerfilSaludRepository;
import com.example.appsaludactiva.infra.exceptions.ConflictoConRecurso;
import com.example.appsaludactiva.infra.exceptions.ValidacionException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AguaService {
    @Autowired
    private PerfilSaludRepository perfilSaludRepository;

    @Autowired
    private RegistroAguaRepository registroAguaRepository;

    @Transactional
    public DetalleAguaDto registroAgua(RegistroAguaDto dto, Long id) {
        LocalDate fechaActual = LocalDate.now();
        PerfilSalud usuario = perfilSaludRepository.findById(id).orElseThrow(() -> new ValidacionException("No existe un usuario con ese ID"));
        Optional<RegistroAgua> registoUnico = registroAguaRepository.findByPerfilSaludIdAndFechaRegistro(id, fechaActual);

        int meta = (int) (usuario.getPeso() * 35) / 250;

        RegistroAgua agua;
        int vasosActual = 0;
        if (registoUnico.isEmpty()) {
            agua = new RegistroAgua(dto, usuario, fechaActual);
        } else {
            agua = registoUnico.get();

            if (agua.getVasosTomados() >= meta) {
                throw new ConflictoConRecurso("Ya has cumplido o  rebasado la meta ya no es necesario registrar tu consumo");
            }
            vasosActual = agua.getVasosTomados() + dto.cantidadVasos();
            registoUnico.get().setVasosTomados(vasosActual);
        }

        if (vasosActual > 15) {
            throw new ConflictoConRecurso("El consumo ingresado supera el límite diario saludable de 15 vasos. Por favor, verifica el dato.");
        }
        registroAguaRepository.save(agua);

        String estadoHidratacion;
        List<String> recomendaciones;
        int vasos = (meta - vasosActual);
        int vasosFaltantes = (vasos > 0) ? vasos : 0;

        if (vasosActual < meta) {
            estadoHidratacion = "Hidratación Insuficiente";
            recomendaciones = List.of("Te faltan " + vasosFaltantes + " vasos para tu meta diaria.",
                    "Intenta tener un termo cerca de tu área de trabajo.");
        } else {
            estadoHidratacion = "¡Felicidades Alcanzaste o Superaste la Meta";
            recomendaciones = List.of("¡Excelente ritmo! Has cubierto los mililitros recomendados para hoy.",
                    "Mantén este hábito mañana.");
        }
        return new DetalleAguaDto(vasosFaltantes, estadoHidratacion, recomendaciones);
    }

    public DetalleAguaDto consultarHidratacion(Long id) {
        LocalDate fechaActual = LocalDate.now();
        PerfilSalud usuario = perfilSaludRepository.findById(id).orElseThrow(()
                -> new ValidacionException("No existe un usuario con ese ID"));
        Optional<RegistroAgua> registoUnico = registroAguaRepository.findByPerfilSaludIdAndFechaRegistro(id, fechaActual);
        int meta = (int) (usuario.getPeso() * 35) / 250;
        int vasosFaltantes = 0;
        String estadoHidratacion = "";
        List<String> recomendaciones = List.of();
        if(!registoUnico.isPresent()){
            vasosFaltantes = meta;
            estadoHidratacion = "Inicio de Hidratacion";
            recomendaciones = recomendaciones = List.of("Procura cumplir con la meta recomendada basada en tu peso",
                    "Mucha suerte en tu seguimiento para tener una buena Hidratacion");
        }else{
            vasosFaltantes = meta - registoUnico.get().getVasosTomados();
            if (vasosFaltantes != 0) {
                estadoHidratacion = "Continua con tu hidratacion";
                recomendaciones = recomendaciones = List.of("Procura cumplir con la meta recomendada basada en tu peso",
                        "Mucha suerte en tu seguimiento para tener una buena Hidratacion");
            }else{
                estadoHidratacion = "Felcidades Has cumplido la meta";
                recomendaciones = recomendaciones = List.of("Continua asi por mas dias, Felicidades",
                        "Recuerda que una buena hidratacion es vital para estar sano");
            }
        }
        return new DetalleAguaDto(vasosFaltantes, estadoHidratacion, recomendaciones);
    }
}
