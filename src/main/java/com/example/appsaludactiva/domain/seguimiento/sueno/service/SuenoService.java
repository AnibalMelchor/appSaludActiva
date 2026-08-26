package com.example.appsaludactiva.domain.seguimiento.sueno.service;

import com.example.appsaludactiva.domain.seguimiento.sueno.dto.DetalleSuenoDto;
import com.example.appsaludactiva.domain.seguimiento.sueno.dto.RegistroSuenoDto;
import com.example.appsaludactiva.domain.seguimiento.sueno.entity.RegistroSueno;
import com.example.appsaludactiva.domain.seguimiento.sueno.repository.RegistroSuenoRepository;
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
public class SuenoService {
    @Autowired
    private PerfilSaludRepository perfilSaludRepository;

    @Autowired
    private RegistroSuenoRepository registroSuenoRepository;

    @Transactional
    public DetalleSuenoDto registroSueno(RegistroSuenoDto datos, Long id) {
        LocalDate fechaActual = LocalDate.now();
        PerfilSalud usuario = perfilSaludRepository.findById(id).orElseThrow(() -> new ValidacionException("No existe un usuario con ese ID"));
        Optional<RegistroSueno> registoUnico = registroSuenoRepository.getReferenceByPerfilSaludId(usuario.getId());
        String estadoSueno = "";
        List<String> recomendaciones = List.of("");
        if (registoUnico.isPresent()){
            throw new ConflictoConRecurso("Horas de sueño ya registradas");
        }
        double meta = 8;
        double horasDormidas = datos.cantidadHoras();
        if (horasDormidas >= 24){
            throw new ConflictoConRecurso("No puedes dormir 24 horas o mas, ingresa una cantidad real o acude con un especialista");
        }

        RegistroSueno registroSueno = new RegistroSueno(usuario,horasDormidas,fechaActual);
        registroSuenoRepository.save(registroSueno);

        if(horasDormidas >= 7 && 9 >= horasDormidas){
            estadoSueno = "Excelente";
            recomendaciones = List.of("Felicidades estas durmiendo las horas necesarias",
                    "Siguie asi el dormir bien te ayuda a llevar un productivo");
        }else if(horasDormidas == 6 || 9 == horasDormidas){
            estadoSueno = "Aceptable";
            recomendaciones = List.of("Has dormido las horas minimas, intenta mañana dormir las necesarias",
                    "Recuerda que debes tener un sueño reparador");
        }else{
            estadoSueno = "Insuficiente";
            recomendaciones = List.of("No estas dormiendo correctamente, eso afectara tu vida a largo plazo",
                    "Las horas recomendable para un sueño son entre 7 a 9");
        }

        return new DetalleSuenoDto(horasDormidas,estadoSueno,recomendaciones);
    }

    public DetalleSuenoDto detalleSueno(Long id) {
        PerfilSalud usuario = perfilSaludRepository.findById(id).orElseThrow(() -> new ValidacionException("No existe un usuario con ese ID"));
        Optional<RegistroSueno> registoUnico = registroSuenoRepository.getReferenceByPerfilSaludId(usuario.getId());
        double horasDormidas;
        String estadoSueno;
        List<String> recomendaciones;
        if (registoUnico.isEmpty()){
            horasDormidas = 0.0;
            estadoSueno = "Pendiente";
            recomendaciones = List.of("Recuerda que para tener un sueno reparador debes dormir entre 7 y 9 horas",
                    "Procura dormir y despertar a la misma horas todos los dias");
        }else {
            horasDormidas = registoUnico.get().getHorasDormidas();
            if(horasDormidas >= 7 && 9 >= horasDormidas){
                estadoSueno = "Excelente";
                recomendaciones = List.of("Felicidades estas durmiendo las horas necesarias",
                        "Siguie asi el dormir bien te ayuda a llevar un productivo");
            }else if(horasDormidas == 6 || 9 == horasDormidas){
                estadoSueno = "Aceptable";
                recomendaciones = List.of("Has dormido las horas minimas, intenta mañana dormir las necesarias",
                        "Recuerda que debes tener un sueño reparador");
            }else{
                estadoSueno = "Insuficiente";
                recomendaciones = List.of("No estas dormiendo correctamente, eso afectara tu vida a largo plazo",
                        "Las horas recomendable para un sueño son entre 7 a 9");
            }
        }
        return new DetalleSuenoDto(horasDormidas,estadoSueno,recomendaciones);
    }


}
