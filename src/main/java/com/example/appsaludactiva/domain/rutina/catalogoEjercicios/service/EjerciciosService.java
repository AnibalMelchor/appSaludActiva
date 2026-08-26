package com.example.appsaludactiva.domain.rutina.catalogoEjercicios.service;

import com.example.appsaludactiva.domain.rutina.catalogoEjercicios.dto.ActualizarEjercicioDto;
import com.example.appsaludactiva.domain.rutina.catalogoEjercicios.dto.CrearEjercicioDto;
import com.example.appsaludactiva.domain.rutina.catalogoEjercicios.dto.DetalleEjercicioDto;
import com.example.appsaludactiva.domain.rutina.catalogoEjercicios.dto.EjerciciosDto;
import com.example.appsaludactiva.domain.rutina.catalogoEjercicios.entity.Ejercicio;
import com.example.appsaludactiva.domain.rutina.catalogoEjercicios.repository.EjerciciosRepository;
import com.example.appsaludactiva.infra.exceptions.ConflictoConRecurso;
import com.example.appsaludactiva.infra.exceptions.ValidacionException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EjerciciosService {


    @Autowired
    private EjerciciosRepository ejerciciosRepository;

    public List<EjerciciosDto> catalogoEjercicios() {
        List<EjerciciosDto> listaEjercicios = new ArrayList<>();
        ejerciciosRepository.findAll().forEach(ejercicios -> {
            listaEjercicios.add(new EjerciciosDto(ejercicios));
        });
        if (listaEjercicios.isEmpty()){
            throw new ValidacionException("No hay ningun ejercicio registrado");
        }
        return listaEjercicios;
    }

    public DetalleEjercicioDto detalleEjercicio(Long id){
        Ejercicio ejercicio = ejerciciosRepository.findById(id).orElseThrow(() -> new ConflictoConRecurso("No existe un ejercicio con ese Id"));
        return new DetalleEjercicioDto(ejercicio);
    }

    @Transactional
    public void registroEjercicios(CrearEjercicioDto datos) {
        Optional<Ejercicio> registroUnico = ejerciciosRepository.findByNombre(datos.nombreEjercicio());
        if (registroUnico.isPresent()) {
           throw new ConflictoConRecurso("Ya existente un ejercicio con el mismo nombre");
        }else{
            Ejercicio ejercicio = new Ejercicio(datos);
            ejerciciosRepository.save(ejercicio);
        }
    }

    @Transactional
    public void actualizarEjecicio(ActualizarEjercicioDto datos, Long id){
        Ejercicio registroUnico = ejerciciosRepository.findById(id).orElseThrow(() -> new ValidacionException("No existe un ejercicio con ese Id"));
        if (!registroUnico.isEstatus()){
            throw new ConflictoConRecurso("No puedes actualizar un ejercicio desactivado");
        }else {
            registroUnico.ActualizarEjercicio(datos);
            ejerciciosRepository.save(registroUnico);
        }

    }

    @Transactional
    public void desactivarEjercicio(Long id){
        Ejercicio ejercicio = ejerciciosRepository.findById(id).orElseThrow(() -> new ValidacionException("No existe un ejercicio con ese Id"));
        if (!ejercicio.isEstatus()){
            throw new ConflictoConRecurso("Ya se encuentra desactivado este ejercicio");
        }else {
            ejercicio.DesactivarEjercicio();
        }
    }
}
