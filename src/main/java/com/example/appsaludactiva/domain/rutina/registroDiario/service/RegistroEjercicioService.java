package com.example.appsaludactiva.domain.rutina.registroDiario.service;

import com.example.appsaludactiva.domain.rutina.catalogoEjercicios.entity.Ejercicio;
import com.example.appsaludactiva.domain.rutina.catalogoEjercicios.repository.EjerciciosRepository;
import com.example.appsaludactiva.domain.rutina.registroDiario.dto.DetalleEjercicioItemDto;
import com.example.appsaludactiva.domain.rutina.registroDiario.dto.ModificarRegistroEjercicioDto;
import com.example.appsaludactiva.domain.rutina.registroDiario.dto.RegistroEjercicioDto;
import com.example.appsaludactiva.domain.rutina.registroDiario.dto.ResumenEjercicioDiarioDto;
import com.example.appsaludactiva.domain.rutina.registroDiario.entity.RegistroEjercicio;
import com.example.appsaludactiva.domain.rutina.registroDiario.repository.RegistroEjercicioRepository;
import com.example.appsaludactiva.domain.usuario.entity.PerfilSalud;
import com.example.appsaludactiva.domain.usuario.repository.PerfilSaludRepository;
import com.example.appsaludactiva.infra.exceptions.ConflictoConRecurso;
import com.example.appsaludactiva.infra.exceptions.ValidacionException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class RegistroEjercicioService {

    @Autowired
    private PerfilSaludRepository perfilSaludRepository;

    @Autowired
    private RegistroEjercicioRepository registroEjercicioRepository;

    @Autowired
    private EjerciciosRepository ejerciciosRepository;


    @Transactional
    public void guardarRegistro (RegistroEjercicioDto datos, Long id){
        PerfilSalud usuario = perfilSaludRepository.findById(id)
                .orElseThrow(() -> new ValidacionException("No existe un usuario con ese Id "));

        Ejercicio ejercicio = ejerciciosRepository.findById(datos.ejercicio_id())
                .orElseThrow(() -> new ValidacionException("No existe el ejercicio seleccionado"));

        if (!ejercicio.isEstatus()) {
            throw new ConflictoConRecurso("El ejercicio seleccionado está inactivo en el catálogo");
        }
        if (datos.duracionMinutos() > 300) {
            throw new ConflictoConRecurso("Una sesión no puede superar los 300 minutos");
        }
        int caloriasQuemadas = ejercicio.getCaloriasPorMinuto() * datos.duracionMinutos();
        RegistroEjercicio registro = new RegistroEjercicio(datos,usuario,ejercicio,caloriasQuemadas);
        registroEjercicioRepository.save(registro);
    }

    public ResumenEjercicioDiarioDto obtenerResumenDiario(LocalDate fecha,Long id){
        perfilSaludRepository.findById(id)
                .orElseThrow(() -> new ValidacionException("No existe un usuario con el Id: " + id));

        LocalDate fechaConsulta = (fecha != null) ? fecha : LocalDate.now();

        LocalDateTime inicioDia = fechaConsulta.atStartOfDay();
        LocalDateTime finDia = fechaConsulta.atTime(LocalTime.MAX);

        List<RegistroEjercicio> registros = registroEjercicioRepository
                .findByPerfilSaludIdAndFechaRegistroBetweenOrderByFechaRegistroDesc(id, inicioDia, finDia);

        List<DetalleEjercicioItemDto> listaEjercicios = registros.stream()
                .map(registro -> new DetalleEjercicioItemDto(
                        registro.getEjercicio().getNombre(),
                        registro.getDuracionMinutos(),
                        registro.getCaloriasQuemadas(),
                        registro.getFechaRegistro().toLocalTime().toString()
                ))
                .toList();

        int totalCalorias = registros.stream()
                .mapToInt(RegistroEjercicio::getCaloriasQuemadas)
                .sum();

        return new ResumenEjercicioDiarioDto(totalCalorias, fecha, listaEjercicios);
    }

    public void modificarRegistros(ModificarRegistroEjercicioDto datos, Long id){
        RegistroEjercicio ejercicioRegistrado = registroEjercicioRepository.findById(id)
                .orElseThrow(() -> new ValidacionException("No existe registro del ejercicio a modificar"));

        Ejercicio ejercicio = ejerciciosRepository.findById(datos.ejercicio_id())
                .orElseThrow(() -> new ValidacionException("No existe el ejercicio seleccionado"));

        if (!ejercicio.isEstatus()) {
            throw new ConflictoConRecurso("El ejercicio seleccionado está inactivo en el catálogo");
        }
        if (datos.duracionMinutos() > 300) {
            throw new ConflictoConRecurso("Una sesión no puede superar los 300 minutos");
        }
        int caloriasQuemadas = ejercicio.getCaloriasPorMinuto() * datos.duracionMinutos();
        ejercicioRegistrado.modificarDatos(datos,ejercicio, caloriasQuemadas);
        registroEjercicioRepository.save(ejercicioRegistrado);
    }

    @Transactional
    public void eliminarRegistroEjercicio(Long id){
        RegistroEjercicio ejercicioRegistrado = registroEjercicioRepository.findById(id)
                .orElseThrow(() -> new ValidacionException("No existe registro del ejercicio a eliminar"));
        registroEjercicioRepository.delete(ejercicioRegistrado);
    }
}
