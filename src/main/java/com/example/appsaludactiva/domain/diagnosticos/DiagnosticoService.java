package com.example.appsaludactiva.domain.diagnosticos;

import com.example.appsaludactiva.domain.alimentacion.registroAlimentacion.entity.RegistroComida;
import com.example.appsaludactiva.domain.alimentacion.registroAlimentacion.repository.RegistroComidaRepository;
import com.example.appsaludactiva.domain.usuario.entity.PerfilSalud;
import com.example.appsaludactiva.domain.usuario.repository.PerfilSaludRepository;
import com.example.appsaludactiva.infra.exceptions.ValidacionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class DiagnosticoService {

    @Autowired
    private PerfilSaludRepository perfilSaludRepository;

    @Autowired
    private RegistroComidaRepository registroComidaRepository;

    public DatosDiagnosticoDto calcularImcUsuario(Long usuarioId) {
        var perfil = perfilSaludRepository.findById(usuarioId)
                .orElseThrow(() -> new ValidacionException("No existe un usuario con ese ID"));

        double imc = perfil.getPeso() / (perfil.getAltura() * perfil.getAltura());
        imc = Math.round(imc * 100.0) / 100.0;

        String estado;
        List<String> recomendaciones;

        if (imc < 18.5) {
            estado = "Bajo peso";
            recomendaciones = List.of(
                    "Incrementa la ingesta de calorías saludables (frutos secos, aguacate).",
                    "Prioriza entrenamientos de fuerza para ganar masa muscular.",
                    "Consulta con un nutricionista para un plan de superávit calórico."
            );
        } else if (imc < 25.0) {
            estado = "Peso normal";
            recomendaciones = List.of(
                    "¡Excelente! Mantén tu ritmo actual de alimentación equilibrada.",
                    "Realiza al menos 150 minutos de actividad física a la semana.",
                    "Asegúrate de mantener una buena hidratación diaria."
            );
        } else if (imc < 30.0) {
            estado = "Sobrepeso";
            recomendaciones = List.of(
                    "Modera el consumo de carbohidratos refinados y azúcares.",
                    "Combina ejercicios de fuerza con actividad cardiovascular.",
                    "Establece horarios fijos para tus comidas evitando el picoteo."
            );
        } else {
            estado = "Obesidad";
            recomendaciones = List.of(
                    "Prioriza caminatas diarias y actividades de bajo impacto para proteger tus articulaciones.",
                    "Reduce drásticamente alimentos ultraprocesados.",
                    "Es altamente recomendable acompañar tu proceso con seguimiento médico."
            );
        }
        return new DatosDiagnosticoDto(imc, estado, recomendaciones);
    }
    public DetalleCaloriasDto detalleCalorias(Long id,LocalDate fecha) {
        LocalDate fechaFiltracion = (fecha != null) ? fecha : LocalDate.now();
        PerfilSalud usuario = perfilSaludRepository.findById(id).orElseThrow(() -> new ValidacionException("No existe un usuario con ese ID"));
        LocalDateTime inicioDia = fechaFiltracion.atStartOfDay();
        LocalDateTime finDia = fechaFiltracion.atTime(LocalTime.MAX);
        List<RegistroComida> registrosDelDia = registroComidaRepository.findByPerfilSaludIdAndFechaRegistroBetweenOrderByFechaRegistroDesc(usuario.getId(), inicioDia,finDia);
        String estadoNutricional = "";
        List<String> recomendaciones = null;
        Double totalCalorias = registrosDelDia.stream()
                .mapToDouble(RegistroComida::getCaloriasTotales)
                .sum();
        if (totalCalorias == 0) {
            estadoNutricional = "Ninguno";
            recomendaciones = List.of("Listo para empezar tu seguimiento de calorias",
                    "Recuerda que al debes de consumir alrededor de 1500 a 2400 calorias en total");
        }else if (totalCalorias < 1500) {
            estadoNutricional = "Consumo Bajo";
            recomendaciones = List.of("Tu consumo de calorías está por debajo del mínimo recomendado para mantener tu energía.",
                    "Procura realizar colaciones saludables entre comidas.");
        }else if (totalCalorias < 2500){
            estadoNutricional = "Consumo Óptimo";
            recomendaciones = List.of("¡Excelente balance! Estás cumpliendo con tu meta diaria recomendada" +
                    " para mantener un metabolismo activo y saludable.");
        }else{
            estadoNutricional = "Consumo Elevado";
            recomendaciones = List.of("Has alcanzado el límite superior de calorías recomendado.",
                    "Te sugerimos priorizar alimentos ricos en fibra y proteínas en tu siguiente comida.");
        }
        return new DetalleCaloriasDto(totalCalorias,estadoNutricional,recomendaciones);
    }
}
